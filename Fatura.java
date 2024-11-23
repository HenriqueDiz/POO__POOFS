import java.util.ArrayList;

class Fatura{
    protected int numero;
    protected Cliente cliente;
    protected Data data;
    protected ArrayList<Produto> produtos;

    public Fatura(int numero, Cliente cliente, Data data){
        this.numero = numero;
        this.cliente = cliente;
        this.data = data;
        this.produtos = new ArrayList<>();
    }

    public void adicionarProduto(Produto produto){
        produtos.add(produto);
    }

    public double calcularTotalSemIVA() {
        double total = 0.0;
        for (Produto produto : produtos) {
            total += produto.valorUnitario * produto.quantidade;
        }
        return total;
    }

    public double calcularTotalComIVA() {
        double total = 0.0;
        for (Produto produto : produtos)
            total += produto.valorUnitario * produto.quantidade * (1 + produto.calcularIVA(cliente.getLocalizacao()));
        return total;
    }

    public int getNumero() {return numero;}

    public void setNumero(int numero) {this.numero = numero;}

    public Cliente getCliente() {return cliente;}

    public void setCliente(Cliente cliente) {this.cliente = cliente;}

    public Data getData() {return data;}

    public void setData(Data data) {this.data = data;}

    public void listarProdutos(){
        for(Produto produto : produtos)
            System.out.println(produto);
    }       

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }
    
}
