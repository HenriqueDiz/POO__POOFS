import java.util.Scanner;

class Auxiliar {
    
    // Método para ler uma string
    public static String lerString(String msg, Scanner scanner) { 
        String input = "";
        while (true) {
            System.out.print(msg);
            input = scanner.nextLine();
            if (!input.trim().isEmpty()) {
                break;
            }
            System.out.println("Entrada inválida. Por favor, digite uma string válida.");
        }
        return input;
    }

    // Método para ler um inteiro
    public static int lerInteiro(String msg, Scanner scanner) {
        int input = 0;
        while (true) {
            System.out.print(msg);
            try {
                input = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número inteiro válido.");
            }
        }
        return input;
    }

    // Método para ler um booleano
    public static boolean lerBooleano(String msg, Scanner scanner) {
        boolean input = false;
        while (true) {
            System.out.print(msg);
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("true") || line.equalsIgnoreCase("false")) {
                input = Boolean.parseBoolean(line);
                break;
            }
            System.out.println("Entrada inválida. Por favor, digite 'true' ou 'false'.");
        }
        return input;
    }

    // Método para ler Localização
    public static Cliente.Localizacao lerLocalizacao(String msg, boolean editar, Scanner scanner) {
        Cliente.Localizacao input = null;
        while (true) {
            System.out.println("1. Portugal Continental");
            System.out.println("2. Madeira");
            System.out.println("3. Açores");
            System.out.print(msg);
            if (editar && scanner.nextLine().isEmpty()) return null;
            try {
                int opcao = Integer.parseInt(scanner.nextLine());
                input = switch (opcao) {
                    case 1 -> Cliente.Localizacao.portugalContinental;
                    case 2 -> Cliente.Localizacao.madeira;
                    case 3 -> Cliente.Localizacao.açores;
                    default -> throw new IllegalArgumentException("Opção inválida");
                };
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite uma opção válida");
            } catch (IllegalArgumentException e) {
                System.out.println("Entrada inválida. Por favor, digite uma opção válida");
            }
        }
        return input;
    }

    // Método para ler uma data
    public static Data lerData(String msg, boolean editar, Scanner scanner) {
        int dia = 0, mes = 0, ano = 0;
        while (true) {
            try {
                System.out.print(msg);
                String input = scanner.nextLine();
                if (editar && input.isEmpty()) return null;

                String[] partes = input.split("/");
                if (partes.length != 3) throw new IllegalArgumentException("Formato de data inválido");

                dia = Integer.parseInt(partes[0]);
                mes = Integer.parseInt(partes[1]);
                ano = Integer.parseInt(partes[2]);

                if (dia < 1 || dia > 31) throw new IllegalArgumentException("Dia inválido");
                if (mes < 1 || mes > 12) throw new IllegalArgumentException("Mês inválido");
                if (ano < 1) throw new IllegalArgumentException("Ano inválido");

                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número inteiro válido.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return new Data(dia, mes, ano);
    }

    // Método para ler uma categoria alimentar
    public static ProdutoAlimentarTaxaIntermedia.CategoriaAlimentar lerCategoriaAlimentar(String msg, boolean editar, Scanner scanner) {
        ProdutoAlimentarTaxaIntermedia.CategoriaAlimentar categoria = null;
        while (true) {
            System.out.println("1. Congelados");
            System.out.println("2. Enlatados");
            System.out.println("3. Vinho");
            System.out.print(msg);
            if (editar && scanner.nextLine().isEmpty()) return null;
            try {
                int opcao = Integer.parseInt(scanner.nextLine());
                categoria = switch (opcao) {
                    case 1 -> ProdutoAlimentarTaxaIntermedia.CategoriaAlimentar.congelados;
                    case 2 -> ProdutoAlimentarTaxaIntermedia.CategoriaAlimentar.enlatados;
                    case 3 -> ProdutoAlimentarTaxaIntermedia.CategoriaAlimentar.vinho;
                    default -> throw new IllegalArgumentException("Opção inválida");
                };
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite uma opção válida");
            } catch (IllegalArgumentException e) {
                System.out.println("Entrada inválida. Por favor, digite uma opção válida");
            }
        }
        return categoria;
    }

    // Método para ler uma categoria de farmácia
    public static ProdutoFarmaciaSemPrescricao.CategoriaFarmacia lerCategoriaFarmacia(String msg, boolean editar, Scanner scanner) {
        ProdutoFarmaciaSemPrescricao.CategoriaFarmacia categoria = null;
        while (true) {
            System.out.println("1. Beleza");
            System.out.println("2. Bem Estar");
            System.out.println("3. Bebes");
            System.out.println("4. Animais");
            System.out.println("5. Outro");
            System.out.print(msg);
            if (editar && scanner.nextLine().isEmpty()) return null;
            try {
                int opcao = Integer.parseInt(scanner.nextLine());
                categoria = switch (opcao) {
                    case 1 -> ProdutoFarmaciaSemPrescricao.CategoriaFarmacia.beleza;
                    case 2 -> ProdutoFarmaciaSemPrescricao.CategoriaFarmacia.bemEstar;
                    case 3 -> ProdutoFarmaciaSemPrescricao.CategoriaFarmacia.bebes;
                    case 4 -> ProdutoFarmaciaSemPrescricao.CategoriaFarmacia.animais;
                    case 5 -> ProdutoFarmaciaSemPrescricao.CategoriaFarmacia.outro;
                    default -> throw new IllegalArgumentException("Opção inválida");
                };
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite uma opção válida");
            } catch (IllegalArgumentException e) {
                System.out.println("Entrada inválida. Por favor, digite uma opção válida");
            }
        }
        return categoria;
    }

    // Método para verificar se um ficheiro binário existe
    public static boolean binFileExists(String fileName) {
        java.io.File file = new java.io.File(fileName);
        return file.exists();
    }
}