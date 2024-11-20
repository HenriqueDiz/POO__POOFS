import java.util.ArrayList;
import java.util.List;

class Fatura {
    private String numero;
    private Cliente cliente;
    private String data;
    private List<Produto> produtos;

    public Fatura(String numero, Cliente cliente, String data) {
        this.numero = numero;
        this.cliente = cliente;
        this.data = data;
        this.produtos = new ArrayList<>();
    }

    public void adicionarProduto(Produto produto) {
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
        for (Produto produto : produtos) {
            double iva = produto.calcularIVA(cliente.getLocalizacao());
 total += (produto.valorUnitario * produto.quantidade) + iva;
        }
        return total;
    }

    public String getNumero() { return numero; }
    public Cliente getCliente() { return cliente; }
    public String getData() { return data; }
    public List<Produto> getProdutos() { return produtos; }
}
