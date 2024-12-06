import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Scanner;

class Fatura implements Serializable {
    protected int numero;
    protected Cliente cliente;
    protected Data data;
    protected List<Produto> produtos;

    public Fatura(int numero, Cliente cliente, Data data) {
        this.numero = numero;
        this.cliente = cliente;
        this.data = data;
        this.produtos = new ArrayList<>();
    }

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }
    
    public Produto inputProduto(Scanner scanner) {
        String tipo = Auxiliar.lerString("Tipo de produto (PAI, PAN, PAR, PFCP, PFSP): ", scanner);
        String codigo = Auxiliar.lerString("Código do produto: ", scanner);
        String nome = Auxiliar.lerString("Nome do produto: ", scanner);
        String descricao = Auxiliar.lerString("Descrição do produto: ", scanner);
        int quantidade = Auxiliar.lerInteiro("Quantidade: ", scanner);
        double valorUnitario = Double.parseDouble(Auxiliar.lerString("Valor unitário: ", scanner));

        switch (tipo) {
            case "PAI" -> {
                boolean biologico = Auxiliar.lerBooleano("É biológico (true/false): ", scanner);
                ProdutoAlimentarTaxaIntermedia.CategoriaAlimentar categoriaAlimentar = Auxiliar.lerCategoriaAlimentar("Categoria alimentar: ", false, scanner);
                return new ProdutoAlimentarTaxaIntermedia(codigo, nome, descricao, quantidade, valorUnitario, biologico, categoriaAlimentar);
            }
            case "PAN" -> {
                boolean biologicoNormal = Auxiliar.lerBooleano("É biológico (true/false): ", scanner);
                return new ProdutoAlimentarTaxaNormal(codigo, nome, descricao, quantidade, valorUnitario, biologicoNormal);
            }
            case "PAR" -> {
                boolean biologicoReduzida = Auxiliar.lerBooleano("É biológico (true/false): ", scanner);
                String[] certificacoes = Auxiliar.lerString("Certificações (separadas por vírgula): ", scanner).split(",");
                EnumSet<ProdutoAlimentarTaxaReduzida.Certificacao> certificacoesSet = EnumSet.noneOf(ProdutoAlimentarTaxaReduzida.Certificacao.class);  // TODO - FALTA METODO PAR AS CERTIFICACOES
                for (String cert : certificacoes)
                    certificacoesSet.add(ProdutoAlimentarTaxaReduzida.Certificacao.valueOf(cert));
                return new ProdutoAlimentarTaxaReduzida(codigo, nome, descricao, quantidade, valorUnitario, biologicoReduzida, certificacoesSet);
            }
            case "PFCP" -> {
                String prescricao = Auxiliar.lerString("Prescrição: ", scanner);
                String medico = Auxiliar.lerString("Médico: ", scanner);
                return new ProdutoFarmaciaComPrescricao(codigo, nome, descricao, quantidade, valorUnitario, prescricao, medico);
            }
            case "PFSP" -> {                                                                                                                            
                ProdutoFarmaciaSemPrescricao.CategoriaFarmacia categoriaFarmacia = Auxiliar.lerCategoriaFarmacia("Categoria Farmácia: ", false, scanner);
                return new ProdutoFarmaciaSemPrescricao(codigo, nome, descricao, quantidade, valorUnitario, categoriaFarmacia);
            }
            default -> {
                System.out.println("Tipo de produto desconhecido.");
                return null;
            }
        }
    }

    public boolean editarProduto(String codigo, Scanner scanner) {  // Se calhar faz so sentido editar a quantidade ? (como é uma aplicacao de gestao de faturas, não vou estar a alterar a descrição de um produto)
        Produto produto = getProduto(codigo);
        if (produto != null) {
            String novoNome = Auxiliar.lerString("Editar Nome (atual: " + produto.getNome() + " - deixe em branco para manter): ", scanner);
            produto.setNome(novoNome.isEmpty() ? produto.getNome() : novoNome);

            String novaDescricao = Auxiliar.lerString("Editar Descrição (atual: " + produto.getDescricao() + " - deixe em branco para manter): ", scanner);
            produto.setDescricao(novaDescricao.isEmpty() ? produto.getDescricao() : novaDescricao);

            String novaQuantidade = Auxiliar.lerString("Editar Quantidade (atual: " + produto.getQuantidade() + " - deixe em branco para manter): ", scanner);
            if (!novaQuantidade.isEmpty()) produto.setQuantidade(Integer.parseInt(novaQuantidade));
            return true;
        }
        return false;
    }  

    public void removerProduto(String codigo) {
        for(Produto produto : produtos)
            if (produto.getCodigo().equals(codigo)) {
                produtos.remove(produto);
                return;
            }
        System.out.println("Produto não encontrado.");
    }

    public Produto getProduto(String codigo) {
        for (Produto produto : produtos)
            if (produto.getCodigo().equals(codigo))
                return produto;
        return null;
    }      

    public void imprimirFatura(boolean detalhada) {  // detalhada = true -> Opção 7, detalhada = false -> Opção 6
        System.out.println("\nFatura nº: " + numero);
        System.out.println("Data: " + data.toString());
        System.out.println("Cliente: " + cliente.toString() + "\n");
        if (detalhada){
            listarProdutos();
            System.out.printf("\n\nValor Total do IVA da Fatura: %.2f\n", calcularTotalDoIVA());
        }
        else System.out.println("Número de Produtos: " + getNumeroProdutos());
        System.out.printf("Valor Total da Fatura sem IVA: %.2f\n", calcularTotalSemIVA());
        System.out.printf("Valor Total da Fatura com IVA: %.2f\n\n", calcularTotalComIVA());
    }

    public void listarProdutos() {
        for (int i = 0; i < produtos.size(); i++) {
            Produto produto = produtos.get(i);
            System.out.println("\nProduto " + (i + 1) + " - " + produto.toString());
            System.out.printf("Valor do Produto sem IVA: %.2f\n", produto.calcularSemIVA());
            System.out.printf("Valor do Produto com IVA: %.2f\n", produto.calcularComIVA(cliente.getLocalizacao()));
            System.out.printf("Valor do IVA do Produto: %.2f\n", produto.calcularTotalDoIVA(cliente.getLocalizacao()));
            System.out.printf("Taxa do IVA do Produto: %.0f %%\n", produto.calcularTaxaIVA(cliente.getLocalizacao()) * 100);
        }
    }

    public double calcularTotalComIVA() {
        double total = 0.0;
        for (Produto produto : produtos)
            total += produto.calcularComIVA(cliente.getLocalizacao());
        return total;
    }

    public double calcularTotalDoIVA() {
        return calcularTotalComIVA() - calcularTotalSemIVA();
    }

    public double calcularTotalSemIVA() {
        double total = 0.0;
        for (Produto produto : produtos)
            total += produto.calcularSemIVA();
        return total;
    }

    public int getNumeroProdutos() {return produtos.size();}

    public int getNumero() {return numero;}

    public void setNumero(int numero) {this.numero = numero;}

    public Cliente getCliente() {return cliente;}

    public void setCliente(Cliente cliente) {this.cliente = cliente;}

    public Data getData() {return data;}

    public void setData(Data data) {this.data = data;}

    public List<Produto> getProdutos() {return produtos;}

    public void setProdutos(List<Produto> produtos) {this.produtos = produtos;}
}