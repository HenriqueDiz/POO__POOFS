import java.util.List;

class Fatura{
    protected int numero;
    protected Cliente cliente;
    protected Data data;
    protected List<Produto> produtos; // ArrayList

    public Fatura(int numero, Cliente cliente, Data data, List<Produto> produtos) {
        this.numero = numero;
        this.cliente = cliente;
        this.data = data;
        this.produtos = produtos;
    }

    public void adicionarProduto(Produto produto){
        produtos.add(produto);
    }

    public void imprimirFatura(){
        System.out.println("\nFatura nº: " + numero);
        System.out.println("Cliente: " + cliente.toString() + "\n");
        listarProdutos();
        System.out.printf("Total IVA: %.2f\n", calcularTotalDoIVA());
        System.out.printf("Total sem IVA: %.2f\n", calcularTotalSemIVA());
        System.out.printf("Total com IVA: %.2f\n", calcularTotalComIVA());
    }

    public void listarProdutos(){
        for(Produto produto : produtos){
            System.out.println("\n" + produto.produtoToString());
            System.out.printf("Valor Total do Produto sem IVA: %.2f\n", produto.calcularSemIVA());
            System.out.printf("Valor Total do Produto com IVA: %.2f\n", produto.calcularComIVA(cliente.getLocalizacao()));
            System.out.printf("Valor Total do IVA do Produto: %.2f\n", produto.calcularTotalDoIVA(cliente.getLocalizacao()));
            System.out.printf("Taxa de IVA do Produto: %.0f %%\n", produto.calcularTaxaIVA(cliente.getLocalizacao()) * 100);
        }
    }  

    public double calcularTotalComIVA(){
        double total = 0.0;
        for (Produto produto : produtos)
            total += produto.calcularComIVA(cliente.getLocalizacao());
        return total;
    }

    public double calcularTotalDoIVA(){
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
}
