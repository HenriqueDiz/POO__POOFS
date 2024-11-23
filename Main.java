import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        
        GestaoFaturas gestaoFaturas = new GestaoFaturas();

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("1. Criar e editar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Criar e editar faturas");
            System.out.println("4. Listar faturas");
            System.out.println("5. Visualizar fatura");
            System.out.println("6. Estatísticas");
            System.out.println("7. Sair");
            System.out.println("Escolha uma opção: ");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> {
                }
                case 2 -> {
                }
                case 3 -> {
                }
                case 4 -> {
                }
                case 5 -> {
                }
                case 6 -> {
                }
                case 7 -> System.out.println("FUI...");
                default -> System.out.println("Opção inválida.");
            }
            scanner.close();
        }
    }
}
