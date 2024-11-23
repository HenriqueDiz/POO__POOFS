import java.util.ArrayList;
import java.util.List;

class Fatura{
    protected int numero;
    protected Cliente cliente;
    protected Data data;
    protected List<Produto> produtos;

    public Fatura(int numero, Cliente cliente, Data data){
        this.numero = numero;
        this.cliente = cliente;
        this.data = data;
        this.produtos = new ArrayList<>();
    }

    public void adicionarProduto(Produto produto){
        produtos.add(produto);
    }

    public void imprimirFatura(){
        System.out.println("Fatura nº: " + numero);
        System.out.println("Cliente: " + cliente.toString() + "\n");
        listarProdutos(); // Falta apresentar os dados relevantes dos produtos
        System.out.println("Total IVA: " + calcularTotalIVA());
        System.out.println("Total sem IVA: " + calcularTotalSemIVA());
        System.out.println("Total com IVA: " + calcularTotalComIVA());
    }

    public void listarProdutos(){
        for(Produto produto : produtos)
            System.out.println(produto.produtoToString());
    }  

    public double calcularTotalComIVA(){
        return calcularTotalSemIVA() * 1.23;
    }

    public double calcularTotalIVA(){
        return calcularTotalComIVA() - calcularTotalSemIVA();
    } 

    public double calcularTotalSemIVA() {
        double total = 0.0;
        for (Produto produto : produtos)
            total += produto.valorUnitario * produto.quantidade;
        return total;
    }

    public List<Produto> getProdutos() {return produtos;}

    public void setProdutos(List<Produto> produtos) {this.produtos = produtos;}

    public int getNumero() {return numero;}

    public void setNumero(int numero) {this.numero = numero;}

    public Cliente getCliente() {return cliente;}

    public void setCliente(Cliente cliente) {this.cliente = cliente;}

    public Data getData() {return data;}

    public void setData(Data data) {this.data = data;}    
}
