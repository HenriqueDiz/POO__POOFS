import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GestaoFaturas gestaoFaturas = new GestaoFaturas();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu Principal:");
            System.out.println("1. Criar Cliente");
            System.out.println("2. Editar Cliente");
            System.out.println("3. Criar Fatura");
            System.out.println("4. Editar Fatura");
            System.out.println("5. Listar Clientes");
            System.out.println("6. Listar Faturas");
            System.out.println("7. Estatísticas");
            System.out.println("8. Sair");
            System.out.println("Escolha uma opção: ");
            int opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> gestaoFaturas.criarCliente(scanner);
                case 2 -> gestaoFaturas.editarCliente(scanner);
                case 3 -> gestaoFaturas.listarClientes();
                case 4 -> gestaoFaturas.criarFatura(scanner);
                case 5 -> gestaoFaturas.editarFatura(scanner);
                case 6 -> gestaoFaturas.listarFaturas();
                case 7 -> gestaoFaturas.visualizarFatura(scanner);
                case 8 -> gestaoFaturas.estatisticas();
                case 9 -> {
                    System.out.println("A sair do programa...");
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }
}
