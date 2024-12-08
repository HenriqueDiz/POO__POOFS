import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Scanner;

/**
 * Representa uma fatura, com número, cliente, data e produtos.
 * @author Henrique Diz
 * @author Tomás Gonçalves
 * @version 1.0
 */
class Fatura implements Serializable {
    protected int numero;
    protected Cliente cliente;
    protected Data data;
    protected List<Produto> produtos;

    /**
     * Construtor de uma fatura.
     *
     * @param numero número da fatura
     * @param cliente cliente da fatura
     * @param data data da fatura
     */    
    public Fatura(int numero, Cliente cliente, Data data) {
        this.numero = numero;
        this.cliente = cliente;
        this.data = data;
        this.produtos = new ArrayList<>();
    }
    
    /**
     * Método para adicionar um produto.
     *
     * @param produto produto a adicionar
     */
    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }
    
    /**
     * Método para ler input de um produto.
     *
     * @param scanner scanner para ler o input
     * @return produto lido
     */    
    public Produto inputProduto(Scanner scanner) {
        String tipo = Auxiliar.lerString("Tipo de produto (PAI, PAN, PAR, PFCP, PFSP): ", scanner, false);
        if (!tipo.equals("PAI") && !tipo.equals("PAN") && !tipo.equals("PAR") && !tipo.equals("PFCP") && !tipo.equals("PFSP")) {
            System.out.println("\nTipo de produto inválido.");
            return null;
        }
        String codigo = Auxiliar.lerString("Código do produto: ", scanner, false);
        if (getProduto(codigo) != null) {
            System.out.println("\nCódigo de produto já existente na Fatura.");
            return null;
        }
        String nome = Auxiliar.lerString("Nome do produto: ", scanner, false);
        String descricao = Auxiliar.lerString("Descrição do produto: ", scanner, false);
        int quantidade = Auxiliar.lerInteiro("Quantidade: ", scanner, false);
        double valorUnitario = Auxiliar.lerDouble("Valor unitário: ", scanner);

        switch (tipo) {
            case "PAI" -> {
                boolean biologico = Auxiliar.lerBooleano("É biológico (S/N): ", scanner);
                ProdutoAlimentarTaxaIntermedia.CategoriaAlimentar categoriaAlimentar = Auxiliar.lerCategoriaAlimentar("Categoria alimentar: ", false, scanner);
                return new ProdutoAlimentarTaxaIntermedia(codigo, nome, descricao, quantidade, valorUnitario, biologico, categoriaAlimentar);
            }
            case "PAN" -> {
                boolean biologicoNormal = Auxiliar.lerBooleano("É biológico (S/N): ", scanner);
                return new ProdutoAlimentarTaxaNormal(codigo, nome, descricao, quantidade, valorUnitario, biologicoNormal);
            }
            case "PAR" -> {
                boolean biologicoReduzida = Auxiliar.lerBooleano("É biológico (S/N): ", scanner);
                EnumSet<ProdutoAlimentarTaxaReduzida.Certificacao> certificacoesSet = Auxiliar.lerCertificacoes("Certificações (separadas por vírgula): ", scanner);
                return new ProdutoAlimentarTaxaReduzida(codigo, nome, descricao, quantidade, valorUnitario, biologicoReduzida, certificacoesSet);
            }
            case "PFCP" -> {
                String prescricao = Auxiliar.lerString("Prescrição: ", scanner, false);
                String medico = Auxiliar.lerString("Médico: ", scanner, false);
                return new ProdutoFarmaciaComPrescricao(codigo, nome, descricao, quantidade, valorUnitario, prescricao, medico);
            }
            case "PFSP" -> {                                                                                                                            
                ProdutoFarmaciaSemPrescricao.CategoriaFarmacia categoriaFarmacia = Auxiliar.lerCategoriaFarmacia("Categoria Farmácia: ", false, scanner);
                return new ProdutoFarmaciaSemPrescricao(codigo, nome, descricao, quantidade, valorUnitario, categoriaFarmacia);
            }
        }
        return null;
    }
    
    /**
     * Método para editar um produto.
     *
     * @param codigo código do produto a editar
     * @param scanner scanner para ler o input
     * @return true se o produto foi editado, false caso contrário
     */
    public void editarProduto(String codigo, Scanner scanner) {
        Produto produto = getProduto(codigo);
        if (produto != null) {
            String novoNome = Auxiliar.lerString("Editar Nome (atual: " + produto.getNome() + " - deixe em branco para manter): ", scanner, true);
            produto.setNome(novoNome.isEmpty() ? produto.getNome() : novoNome);

            String novaDescricao = Auxiliar.lerString("Editar Descrição (atual: " + produto.getDescricao() + " - deixe em branco para manter): ", scanner, true);
            produto.setDescricao(novaDescricao.isEmpty() ? produto.getDescricao() : novaDescricao);

            String novaQuantidade = Auxiliar.lerString("Editar Quantidade (atual: " + produto.getQuantidade() + " - deixe em branco para manter): ", scanner, true);
            if (!novaQuantidade.isEmpty()) produto.setQuantidade(Integer.parseInt(novaQuantidade));
            System.out.println("\nProduto editado com sucesso.");
            return;
        }
        System.out.println("\nProduto não encontrado.");
    }  
    
    /**
     * Método para remover um produto.
     *
     * @param codigo código do produto a remover
     */
    public void removerProduto(String codigo) {
        if (produtos.size() == 1) {
            System.out.println("\nNão é possível remover o único produto da fatura.");
            return;
        }
        for(Produto produto : produtos)
            if (produto.getCodigo().equals(codigo)) {
                produtos.remove(produto);
                System.out.println("\nProduto removido com sucesso.");
                return;
            }
        System.out.println("\nProduto não encontrado.");
    }
    
    /**
     * Método para procurar um produto pelo seu código.
     *
     * @param codigo código do produto
     * @return produto encontrado
     */
    public Produto getProduto(String codigo) {
        for (Produto produto : produtos)
            if (produto.getCodigo().equals(codigo))
                return produto;
        return null;
    }      
    
    /**
     * Método para imprimir uma fatura.
     *
     * @param detalhada true se detalhada, false caso contrário
     */
    public void imprimirFatura(boolean detalhada) {  // detalhada = true -> Opção 7, detalhada = false -> Opção 6
        System.out.println("\nFatura nº: " + numero);
        System.out.println("Data: " + data.toString());
        System.out.println("Cliente - " + cliente.toString() + "\n");
        if (detalhada){
            listarProdutos();
            System.out.printf("\n\nValor Total do IVA da Fatura: %.2f\n", calcularTotalDoIVA());
        }
        else System.out.println("Número de Produtos: " + getNumeroProdutos());
        System.out.printf("Valor Total da Fatura sem IVA: %.2f\n", calcularTotalSemIVA());
        System.out.printf("Valor Total da Fatura com IVA: %.2f\n\n", calcularTotalComIVA());
    }
    
    /**
     * Método para listar os produtos de uma fatura.
     */
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
   
    /**
     * Método para calcular o valor total da fatura com IVA.
     *
     * @return valor total com IVA
     */
    public double calcularTotalComIVA() {
        double total = 0.0;
        for (Produto produto : produtos)
            total += produto.calcularComIVA(cliente.getLocalizacao());
        return total;
    }
    
    /**
     * Método para calcular o valor do IVA.
     *
     * @return valor do IVA
     */
    public double calcularTotalDoIVA() {
        return calcularTotalComIVA() - calcularTotalSemIVA();
    }

    /**
     * Método para calcular o valor total da fatura sem IVA.
     *
     * @return valor total sem IVA
     */    
    public double calcularTotalSemIVA() {
        double total = 0.0;
        for (Produto produto : produtos)
            total += produto.calcularSemIVA();
        return total;
    }

    /**
     * Método para obter o número de produtos de uma fatura.
     *
     * @return número de produtos
     */
    public int getNumeroProdutos() {return produtos.size();}

    /**
     * Método para obter o número da fatura.
     *
     * @return número da fatura
     */
    public int getNumero() {return numero;}

    /**
     * Método para definir o número da fatura.
     *
     * @param numero número da fatura
     */
    public void setNumero(int numero) {this.numero = numero;}

    /**
     * Método para obter o cliente da fatura.
     *
     * @return cliente da fatura
     */
    public Cliente getCliente() {return cliente;}

    /**
     * Método para definir o cliente da fatura.
     *
     * @param cliente cliente da fatura
     */
    public void setCliente(Cliente cliente) {this.cliente = cliente;}

    /**
     * Método para obter a data da fatura.
     *
     * @return data da fatura
     */
    public Data getData() {return data;}

    /**
     * Método para definir a data da fatura.
     *
     * @param data data da fatura
     */
    public void setData(Data data) {this.data = data;}

    /**
     * Método para obter os produtos da fatura.
     *
     * @return produtos da fatura
     */
    public List<Produto> getProdutos() {return produtos;}

    /**
     * Método para definir os produtos da fatura.
     *
     * @param produtos produtos da fatura
     */
    public void setProdutos(List<Produto> produtos) {this.produtos = produtos;}
}