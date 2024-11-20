public class Main {
    public static void main(String[] args) {
        GestaoFaturas gestao = new GestaoFaturas();

        Cliente cliente1 = new Cliente("Messias Malandro", "123456789", "Portugal");
        gestao.adicionarCliente(cliente1);

        ProdutoAlimentar produto1 = new ProdutoAlimentar("001", "maça", "Fruta", 10, 0.50, true, "reduzida");
        Fatura fatura1 = new Fatura("Fat01", cliente1, "2024-01-01");
        fatura1.adicionarProduto(produto1);
        gestao.adicionarFatura(fatura1);

        gestao.Clientes();
        gestao.Faturas();
    }
}
    