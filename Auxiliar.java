import java.util.EnumSet;
import java.util.Scanner;

/**
 * Classe Auxiliar - Leitura do Input do user
 */
class Auxiliar {
    /**
     * Construtor da classe Auxiliar.
     */
    public Auxiliar() {}
    
    /**
     * Método para ler String do user.
     *
     * @param msg parametro que pede tipo de String input
     * @param scanner scanner para ler o input
     * @param editar booleano que determina se o input está a ser editado
     * @return string do input, caso exista
     */
    public static String lerString(String msg, Scanner scanner, boolean editar) { 
        String input = "";
        while (true) {
            System.out.print(msg);
            input = scanner.nextLine();
            if (!input.trim().isEmpty() || (editar && input.isEmpty())) break;
            System.out.println("\nEntrada inválida. Por favor, digite uma string válida.");
        }
        return input;
    }

    /**
     * Método para ler String do user.
     *
     * @param msg parametro que pede tipo de Inteiro
     * @param scanner scanner para ler o input
     * @param contribuinte booleano que determina se o input é um contribuinte
     * @return input do inteiro, caso exista
     */
    public static int lerInteiro(String msg, Scanner scanner, boolean contribuinte) {
        int input = 0;
        while (true) {
            System.out.print(msg);
            try {
                input = Integer.parseInt(scanner.nextLine());
                if (contribuinte && (input < 100000000 || input > 999999999)) {
                    System.out.println("\nNúmero de contribuinte inválido. Por favor, digite um número de contribuinte válido.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("\nEntrada inválida. Por favor, digite um número inteiro válido.");
            }
        }
        return input;
    }

    /**
     * Método para ler Double do user.
     *
     * @param msg parametro que pede tipo de Double
     * @param scanner scanner para ler o input
     * @return input do double, caso exista
     */
    public static Double lerDouble(String msg, Scanner scanner) {
        while (true) {
            try {
                System.out.print(msg);
                String input = scanner.nextLine();
                return Double.valueOf(input);
            } catch (NumberFormatException e) {
                System.out.println("\nEntrada inválida. Por favor, digite um número decimal válido.");
            }
        }
    }

    /**
     * Método para ler Booleano do user.
     *
     * @param msg parametro que pede tipo de Boolean
     * @param scanner scanner para ler o input
     * @return true se o input for 'S', false se o input for 'N'
     */
    public static boolean lerBooleano(String msg, Scanner scanner) {
        while (true) {
            System.out.print(msg);
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("S")) return true;
            else if (line.equalsIgnoreCase("N")) return false;
            System.out.println("\nEntrada inválida. Por favor, digite 'S' para Sim ou 'N' para Não.");
        }
    }

    /**
     * Método para ler Booleano do user.
     *
     * @param msg parametro que pede localização
     * @param editar caso o input esteja a ser editado
     * @param scanner scanner para ler o input
     * @return input da localização, caso exista
     */
    public static Cliente.Localizacao lerLocalizacao(String msg, boolean editar, Scanner scanner) {
        Cliente.Localizacao input = null;
        while (true) {
            System.out.println("\n1. Portugal Continental");
            System.out.println("2. Madeira");
            System.out.println("3. Açores");
            System.out.print(msg);
            String line = scanner.nextLine();
            if (editar && line.isEmpty()) return null;
            try {
                int opcao = Integer.parseInt(line);
                input = switch (opcao) {
                    case 1 -> Cliente.Localizacao.portugalContinental;
                    case 2 -> Cliente.Localizacao.madeira;
                    case 3 -> Cliente.Localizacao.açores;
                    default -> throw new IllegalArgumentException("\nEntrada inválida. Por favor, digite uma opção válida");
                };
                break;
            } catch (NumberFormatException e) {
                System.out.println("\nEntrada inválida. Por favor, digite um número inteiro válido.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return input;
    }

    /**
     * Método para ler Data do user.
     *
     * @param msg parametro que pede tipo de Data
     * @param editar caso o input esteja a ser editado
     * @param scanner scanner para ler o input
     * @return input da data, caso exista
     */
    public static Data lerData(String msg, boolean editar, Scanner scanner) {
        int dia = 0, mes = 0, ano = 0;
        while (true) {
            try {
                System.out.print(msg);
                String input = scanner.nextLine();
                if (editar && input.isEmpty()) return null;

                String[] partes = input.split("/");
                if (partes.length != 3) throw new IllegalArgumentException("\nFormato de data inválido");

                dia = Integer.parseInt(partes[0]);
                mes = Integer.parseInt(partes[1]);
                ano = Integer.parseInt(partes[2]);

                if (dia < 1 || dia > 31) throw new IllegalArgumentException("\nDia inválido");
                if (mes < 1 || mes > 12) throw new IllegalArgumentException("\nMês inválido");
                if (ano < 1900 || ano > 9999) throw new IllegalArgumentException("\nAno inválido");

                break;
            } catch (NumberFormatException e) {
                System.out.println("\nEntrada inválida. Por favor, digite um número inteiro válido.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return new Data(dia, mes, ano);
    }

    /**
     * Método para ler Categoria Alimentar do user.
     *
     * @param msg parametro que pede tipo de Categoria Alimentar
     * @param editar caso o input esteja a ser editado
     * @param scanner scanner para ler o input
     * @return input da categoria alimentar, caso exista
     */
    public static ProdutoAlimentarTaxaIntermedia.CategoriaAlimentar lerCategoriaAlimentar(String msg, boolean editar, Scanner scanner) {
        ProdutoAlimentarTaxaIntermedia.CategoriaAlimentar categoria = null;
        while (true) {
            System.out.println("\n1. Congelados");
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
                    default -> throw new IllegalArgumentException("\nEntrada inválida. Por favor, digite uma opção válida");
                };
                break;
            } catch (NumberFormatException e) {
                System.out.println("\nEntrada inválida. Por favor, digite um número inteiro válido.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return categoria;
    }

    /**
     * Método para ler Categoria Farmacia do user.
     *
     * @param msg parametro que pede tipo de Categoria Farmacia
     * @param editar caso o input esteja a ser editado
     * @param scanner scanner para ler o input
     * @return input da categoria farmacia, caso exista
     */
    public static ProdutoFarmaciaSemPrescricao.CategoriaFarmacia lerCategoriaFarmacia(String msg, boolean editar, Scanner scanner) {
        ProdutoFarmaciaSemPrescricao.CategoriaFarmacia categoria = null;
        while (true) {
            System.out.println("\n1. Beleza");
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
                    default -> throw new IllegalArgumentException("\nEntrada inválida. Por favor, digite uma opção válida");
                };
                break;
            } catch (NumberFormatException e) {
                System.out.println("\nEntrada inválida. Por favor, digite um número inteiro válido.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return categoria;
    }

    /**
     * Método para ler Certificações do user.
     *
     * @param msg parametro que pede tipo de Certificações
     * @param scanner scanner para ler o input
     * @return input das certificações, caso existam
     */
    public static EnumSet<ProdutoAlimentarTaxaReduzida.Certificacao> lerCertificacoes(String msg, Scanner scanner) {
        EnumSet<ProdutoAlimentarTaxaReduzida.Certificacao> certificacoes = EnumSet.noneOf(ProdutoAlimentarTaxaReduzida.Certificacao.class);
        while (true) {
            System.out.println("\n1. ISO22000");
            System.out.println("2. FSSC22000");
            System.out.println("3. HACCP");
            System.out.println("4. GMP");
            System.out.print(msg);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                try {
                    String[] parts = input.split(",");
                    for (String part : parts) {
                        int index = Integer.parseInt(part.trim()) - 1;
                        if (index >= 0 && index < ProdutoAlimentarTaxaReduzida.Certificacao.values().length)
                            certificacoes.add(ProdutoAlimentarTaxaReduzida.Certificacao.values()[index]);
                        else 
                            throw new IllegalArgumentException("\nNúmero fora do intervalo de certificações.");
                    }
                    return certificacoes;
                } catch (NumberFormatException e) {
                    System.out.println("\nEntrada inválida. Por favor, digite números inteiros válidos separados por vírgulas.");
                    certificacoes.clear();
                }
                catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    certificacoes.clear();
                }
            } else System.out.println("\nEntrada inválida. Por favor, digite números inteiros válidos separados por vírgulas.");
        }
    }

    /**
     * Método para verofoficar se o ficheiro binário existe.
     *
     * @param fileName nome do ficheiro binário
     * @return true se o ficheiro existir, false caso contrário
     */
    public static boolean binFileExists(String fileName) {
        java.io.File file = new java.io.File(fileName);
        return file.exists();
    }
}