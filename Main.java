import java.util.Scanner;

public class Main {
    
    public static boolean binFileExists(String fileName) {
        java.io.File file = new java.io.File(fileName);
        return file.exists();
    }

    public static void main(String[] args) {
        POOFS app = new POOFS();
        Scanner scanner = new Scanner(System.in);
        
        //ciclo geral de loading no Projeto
        if(binFileExists("dados.bin")){
            app.loadBin("dados.bin"); //load bin
        }else{ app.loadTxt("dados.txt");}//se nao existir ainda, ent é a 1a vez, logo load txt


        while (true) {
            System.out.println("\n1. Criar cliente");
            System.out.println("2. Editar cliente");
            System.out.println("3. Listar todos os clientes");
            System.out.println("4. Criar fatura");
            System.out.println("5. Editar fatura");
            System.out.println("6. Listar todas as faturas");
            System.out.println("7. Visualizar fatura");
            System.out.println("8. Estatísticas");
            System.out.println("9. Sair");
            System.out.println("Escolha uma opção: ");
            int opcao = Integer.parseInt(scanner.nextLine());

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
                default -> System.out.println("Opção inválida.");
            }
        }
    }
}