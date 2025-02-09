import java.util.Scanner;

/**
 * Classe Main
 * @author Henrique Diz
 * @author Tomás Gonçalves
 * @version 1.0
 */
public class Main {
    /**
     * Construtor padrão da classe Main - Não utilizado
     */
    public Main() {}

    /**
     * Método main - Método principal do programa
     * @param args argumentos da linha de comandos
     */
    public static void main(String[] args) {
        POOFS app = new POOFS();
        Scanner scanner = new Scanner(System.in);
        
        if (Auxiliar.binFileExists("docs/dados.bin")) app.loadBin("docs/dados.bin"); 
        else app.loadTxt("docs/poofs.txt");                                          

        while (true) {
            System.out.println("\n\n---------- POOFS ----------");
            System.out.println("1. Criar cliente");
            System.out.println("2. Editar cliente");
            System.out.println("3. Listar todos os clientes");
            System.out.println("4. Criar fatura");
            System.out.println("5. Editar fatura");
            System.out.println("6. Listar todas as faturas");
            System.out.println("7. Visualizar fatura");
            System.out.println("8. Estatísticas");
            System.out.println("9. Sair");
            System.out.println("---------- POOFS ----------\n");
            int opcao = Auxiliar.lerInteiro("Escolha uma opção: ", scanner, false);

            switch (opcao) {
                case 1 -> app.criarCliente(scanner);
                case 2 -> app.editarCliente(scanner);
                case 3 -> app.listarClientes();
                case 4 -> app.criarFatura(scanner);
                case 5 -> app.editarFatura(scanner);
                case 6 -> app.listarFaturas();
                case 7 -> app.visualizarFatura(scanner);
                case 8 -> app.estatisticas();
                case 9 -> {
                    System.out.println("A sair do programa...");
                    return;
                }
                default -> System.out.println("\nOpção inválida.");
            }
        }
    }
}