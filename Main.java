public class Main {
    public static void main(String[] args) {
        GestaoFaturas gestao = new GestaoFaturas();

        Cliente cliente1 = new Cliente("Messias Malandro", 123456789, Cliente.Localizacao.portugalContinenal, 1);

        gestao.adicionarCliente(cliente1);

        Fatura fatura1 = new Fatura(101, cliente1, new Data(15, 3, 2024));

        gestao.adicionarFatura(cliente1.getId(), fatura1);

        gestao.listarClientes();
        gestao.listarFaturas();
    }
}
