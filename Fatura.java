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
    
    public Produto adicionarProduto(Scanner scanner) { // TODO: Adicionar métodos de INPUT E MODIFICAR ISTO
        System.out.print("Tipo de produto (PAI, PAN, PAR, PFCP, PFSP): ");
        String tipo = scanner.nextLine();
        System.out.print("Código do produto: ");
        String codigo = scanner.nextLine();
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Descrição do produto: ");
        String descricao = scanner.nextLine();
        System.out.print("Quantidade: ");
        int quantidade = Integer.parseInt(scanner.nextLine());
        System.out.print("Valor unitário: ");
        double valorUnitario = Double.parseDouble(scanner.nextLine());

        switch (tipo) {
            case "PAI" -> {
                System.out.print("É biológico (true/false): ");
                boolean biologico = Boolean.parseBoolean(scanner.nextLine());
                System.out.print("Categoria alimentar: ");
                ProdutoAlimentarTaxaIntermedia.CategoriaAlimentar categoriaAlimentar = ProdutoAlimentarTaxaIntermedia.CategoriaAlimentar.valueOf(scanner.nextLine());
                return new ProdutoAlimentarTaxaIntermedia(codigo, nome, descricao, quantidade, valorUnitario, biologico, categoriaAlimentar);
            }
            case "PAN" -> {
                System.out.print("É biológico (true/false): ");
                boolean biologicoNormal = Boolean.parseBoolean(scanner.nextLine());
                return new ProdutoAlimentarTaxaNormal(codigo, nome, descricao, quantidade, valorUnitario, biologicoNormal);
            }
            case "PAR" -> {
                System.out.print("É biológico (true/false): ");
                boolean biologicoReduzida = Boolean.parseBoolean(scanner.nextLine());
                System.out.print("Certificações (separadas por vírgula): ");
                String[] certificacoes = scanner.nextLine().split(",");
                EnumSet<ProdutoAlimentarTaxaReduzida.Certificacao> certificacoesSet = EnumSet.noneOf(ProdutoAlimentarTaxaReduzida.Certificacao.class);
                for (String cert : certificacoes) {
                    certificacoesSet.add(ProdutoAlimentarTaxaReduzida.Certificacao.valueOf(cert));
                }
                return new ProdutoAlimentarTaxaReduzida(codigo, nome, descricao, quantidade, valorUnitario, biologicoReduzida, certificacoesSet);
            }
            case "PFCP" -> {
                System.out.print("Código de prescrição: ");
                String codigoPrescricao = scanner.nextLine();
                System.out.print("Médico: ");
                String medico = scanner.nextLine();
                return new ProdutoFarmaciaComPrescricao(codigo, nome, descricao, quantidade, valorUnitario, codigoPrescricao, medico);
            }
            case "PFSP" -> {
                System.out.print("Categoria de farmácia: ");
                ProdutoFarmaciaSemPrescricao.CategoriaFarmacia categoriaFarmacia = ProdutoFarmaciaSemPrescricao.CategoriaFarmacia.valueOf(scanner.nextLine());
                return new ProdutoFarmaciaSemPrescricao(codigo, nome, descricao, quantidade, valorUnitario, categoriaFarmacia);
            }
            default -> {
                System.out.println("Tipo de produto desconhecido.");
                return null;
            }
        }
    }

    public boolean editarProduto(String codigo, Scanner scanner) { // TODO: Adicionar métodos de INPUT E MODIFICAR ISTO
        Produto produto = getProduto(codigo);
        if (produto != null) {
            System.out.print("Editar Nome (atual: " + produto.getNome() + "): ");
            String novoNome = scanner.nextLine();
            produto.setNome(novoNome.isEmpty() ? produto.getNome() : novoNome);

            System.out.print("Editar Descrição (atual: " + produto.getDescricao() + "): ");
            String novaDescricao = scanner.nextLine();
            produto.setDescricao(novaDescricao.isEmpty() ? produto.getDescricao() : novaDescricao);

            System.out.print("Editar Quantidade (atual: " + produto.getQuantidade() + "): ");
            String novaQuantidade = scanner.nextLine();
            if (!novaQuantidade.isEmpty()) {
                produto.setQuantidade(Integer.parseInt(novaQuantidade));
            }
            return true;
        }
        return false;
    }  

    public void removerProduto(int codigo) {
        for(Produto produto : produtos)
            if (produto.getCodigo().equals(codigo)) {
                produtos.remove(produto);
                return;
            }
        System.out.println("Produto não encontrado.");
    }

    public Produto getProduto(String codigo) {
        for (Produto produto : produtos) {
            if (produto.getCodigo().equals(codigo)) {
                return produto;
            }
        }
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