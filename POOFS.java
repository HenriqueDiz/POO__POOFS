import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Scanner;

/**
 * Classe principal da aplicação POOFS.
 * @author Henrique Diz
 * @author Tomás Gonçalves
 * @version 1.0
 */
class POOFS {
    /** Lista de clientes */
    private List<Cliente> clientes;

    /**
     * Construtor da aplicação POOFS.
     */    
    public POOFS() {
        this.clientes = new ArrayList<>();
    }

    // ------------------------------ Métodos da Aplicação ------------------------------

    /**
     * Opção 1 da aplicação POOFS - Criar um novo cliente.
     *
     * @param scanner scanner para ler o input
     */    
    public void criarCliente(Scanner scanner) {
        String nome = Auxiliar.lerString("Nome do novo cliente: ", scanner, false);
        int contribuinte = Auxiliar.lerInteiro("Nº de Contribuinte do novo cliente: ", scanner, true);
        Cliente.Localizacao localizacao = Auxiliar.lerLocalizacao("Localização do novo cliente: ", false, scanner);
        int id = clientes.size() + 1;

        Cliente novoCliente = new Cliente(nome, contribuinte, localizacao, id);
        clientes.add(novoCliente);
        
        System.out.println("\nCliente adicionado com sucesso!");
        exportBin();
    }


    /**
     * Opção 2 da aplicação POOFS - Editar um cliente existente.
     *
     * @param scanner scanner para ler o input
     */
    public void editarCliente(Scanner scanner) {
        if (clientes.isEmpty()) {
            System.out.println("\nNão há clientes registados na aplicação!");
            return;
        }

        int id = Auxiliar.lerInteiro("ID do cliente para Editar: ", scanner, false);
        Cliente cliente = searchClientePorId(id);
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        String novoNome = Auxiliar.lerString("Editar Nome (atual: " + cliente.getNome() + " - deixe em branco para manter): ", scanner, true);
        cliente.setNome(novoNome.isEmpty() ? cliente.getNome() : novoNome);

        Cliente.Localizacao novaLocalizacao = Auxiliar.lerLocalizacao("Editar Localização do cliente (atual: " + cliente.getLocalizacaoFormatada() + " - deixe em branco para manter): ", true, scanner);
        cliente.setLocalizacao(novaLocalizacao == null ? cliente.getLocalizacao() : novaLocalizacao);

        System.out.println("\nCliente editado com sucesso!");
        exportBin();
    }


    /**
     * Opção 3 da aplicação POOFS - Listar todos os clientes registados.
     */
    public void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("\nNão existem Clientes Registados!");
            return;
        }
        for (Cliente cliente : clientes)
            System.out.println("\n" + cliente.toString());
    }


    /**
     * Opção 4 da aplicação POOFS - Criar uma nova fatura.
     *
     * @param scanner scanner para ler o input
     */
    public void criarFatura(Scanner scanner) {
        if (clientes.isEmpty()) {
            System.out.println("\nNão há clientes registados na aplicação!");
            return;
        }

        int id = Auxiliar.lerInteiro("ID do cliente para criar fatura: ", scanner, false);

        Cliente cliente = searchClientePorId(id);
        if (cliente == null) {
            System.out.println("\nCliente não encontrado!");
            return;
        }

        int numeroFatura = Auxiliar.lerInteiro("Nº da fatura: ", scanner, false);
        Fatura fatura = cliente.searchFaturaNumero(numeroFatura);
        if (fatura != null) {
            System.out.println("\nFatura já existe!");
            return;
        }

        Data dataFatura = Auxiliar.lerData("Data da fatura [dd/mm/yy]: ", false, scanner);

        Fatura novaFatura = new Fatura(numeroFatura, cliente, dataFatura);
        String input;
        do {
            Produto produto = novaFatura.inputProduto(scanner);
            if (produto != null) {
                novaFatura.adicionarProduto(produto);
                System.out.println("\nProduto adicionado com sucesso!");
            }
            do {
                System.out.print("\nAdicionar outro produto [S/N] ? ");
                input = scanner.nextLine().trim().toUpperCase();
                if (!input.equals("S") && !input.equals("N"))
                    System.out.println("\nEntrada inválida. Por favor, insira 'S' para Sim ou 'N' para Não.");
            } while (!input.equals("S") && !input.equals("N"));
        } while (input.equals("S"));  

        cliente.adicionarFatura(novaFatura);
        System.out.println("\nFatura criada com sucesso!");
        exportBin();
    }


    /**
     * Opção 5 da aplicação POOFS - Editar uma fatura existente.
     *
     * @param scanner scanner para ler o input 
     */
    public void editarFatura(Scanner scanner) {
        if (clientes.isEmpty()) {
            System.out.println("\nNão há clientes registados na aplicação!");
            return;
        }
        
        int id = Auxiliar.lerInteiro("ID do cliente associado à fatura: ", scanner, false);
        
        Cliente cliente = searchClientePorId(id);
        if (cliente == null) {
            System.out.println("\nCliente não encontrado!");
            return;
        }

        int numeroFatura = Auxiliar.lerInteiro("Nº da fatura a editar: ", scanner, false);
        Fatura fatura = cliente.searchFaturaNumero(numeroFatura);
        if (fatura == null) {
            System.out.println("\nFatura não encontrada!");
            return;
        }

        Data data = Auxiliar.lerData("Editar Data da Fatura (atual: " + fatura.data.toString() + " - deixe em branco para manter): ", true, scanner);
        fatura.setData(data == null ? fatura.getData() : data);
        
        int opcao;
        do {
            System.out.println("\n----- Editar Produtos da Fatura -----");
            System.out.println("1. Adicionar Produto");
            System.out.println("2. Editar Produto");
            System.out.println("3. Remover Produto");
            System.out.println("4. Sair");
            opcao = Auxiliar.lerInteiro("Opção: ", scanner, false);
            switch (opcao) {
                case 1 -> {
                    Produto novoProduto = fatura.inputProduto(scanner);
                    if (novoProduto != null) {
                        fatura.adicionarProduto(novoProduto);
                        System.out.println("\nProduto adicionado com sucesso!");
                    }
                }
                case 2 ->{ 
                    String codigoEditar = Auxiliar.lerString("Código do produto a editar: ", scanner, true);
                    fatura.editarProduto(codigoEditar, scanner);
                }
                case 3-> {
                    String codigoRemover = Auxiliar.lerString("Código do produto a remover: ", scanner, true);
                    fatura.removerProduto(codigoRemover);
                }
                case 4 -> System.out.println("\nA sair do menu de edição de fatura...");
                default -> System.out.println("\nOpção inválida.");
            }
        } while (opcao != 4);
        System.out.println("\nFatura editada com sucesso!");
        exportBin();
    }


    /**
     * Opção 6 da aplicação POOFS - Listar todas as faturas registadas.
     */
    public void listarFaturas() {
        boolean flag = false;
        for (Cliente cliente : clientes)
            for (Fatura fatura : cliente.getFaturas()){
                flag = true;
                fatura.imprimirFatura(false);
            }
        if (!flag) System.out.println("\nNão existem faturas registadas!");
    }


    /**
     * Opção 7 da aplicação POOFS - Visualizar uma fatura específica.
     *
     * @param scanner scanner para ler o input
     */
    public void visualizarFatura(Scanner scanner) {
        if (clientes.isEmpty()) {
            System.out.println("\nNão há clientes registados na aplicação!");
            return;
        }

        int id = Auxiliar.lerInteiro("ID do cliente associado à fatura: ", scanner, false);
        Cliente cliente = searchClientePorId(id);
        if (cliente == null) {
            System.out.println("\nCliente não encontrado!");
            return;
        }

        int numero = Auxiliar.lerInteiro("Nº da fatura que deseja visualizar: ", scanner, false);
        Fatura fatura = cliente.searchFaturaNumero(numero);
        if (fatura == null) {
            System.out.println("\nFatura não encontrada!");
            return;
        }
        fatura.imprimirFatura(true);
    }


    /**
     * Opção 8 da aplicação POOFS - Apresentar estatísticas sobre as faturas.
     */
    public void estatisticas() {
        int totalFaturas = 0, totalProdutos = 0;
        double valorTotalSemIVA = 0.0, valorTotalComIVA = 0.0, valorTotalDoIVA = 0.0;
        for (Cliente cliente : clientes) {
            totalFaturas += cliente.getNumeroFaturas();
            for (Fatura fatura : cliente.getFaturas()) {
                totalProdutos += fatura.getNumeroProdutos();
                valorTotalSemIVA += fatura.calcularTotalSemIVA();
                valorTotalComIVA += fatura.calcularTotalComIVA();
                valorTotalDoIVA += fatura.calcularTotalDoIVA();
            }
        }
        System.out.printf("\nNúmero de faturas: %d%n", totalFaturas);
        System.out.printf("Número total de produtos: %d%n", totalProdutos);
        System.out.printf("Valor total sem IVA: %.2f%n", valorTotalSemIVA);
        System.out.printf("Valor total com IVA: %.2f%n", valorTotalComIVA);
        System.out.printf("Valor total do IVA: %.2f%n", valorTotalDoIVA);
    }

    // ------------------------------ Métodos para Exportar e Importar os Dados da Aplicação POOFS ------------------------------

    /**
     * Carregar os dados de um ficheiro de texto (1ª vez que o programa é executado).
     *
     * @param filePath path do ficheiro de texto
     */
    public void loadTxt(String filePath) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            Cliente cliente = null;
            while (scanner.hasNextLine()) { 
                String line = scanner.nextLine();
                if (line.isEmpty()) continue;
                if (line.startsWith("# Fatura")) {     // Fatura
                    String[] dadosFatura = scanner.nextLine().split(";");
                    int numero = Integer.parseInt(dadosFatura[0]);
                    Data data = new Data(Integer.parseInt(dadosFatura[1]), Integer.parseInt(dadosFatura[2]), Integer.parseInt(dadosFatura[3]));   
                    Fatura fatura = new Fatura(numero, cliente, data);
                    while (scanner.hasNextLine()) {    // Produtos   
                        String buffer = scanner.nextLine();
                        if (buffer.isEmpty()) break;
                        String[] dadosProduto = buffer.split(";");
                        String sigla = dadosProduto[0];
                        String codigo = dadosProduto[1];
                        String nomeProduto = dadosProduto[2];
                        String descricao = dadosProduto[3];
                        int quantidade = Integer.parseInt(dadosProduto[4]);
                        double valorUnitario = Double.parseDouble(dadosProduto[5]);
                        Produto produto = switch (sigla) {
                            case "PAI" -> new ProdutoAlimentarTaxaIntermedia(codigo, nomeProduto, descricao, quantidade, valorUnitario, Boolean.parseBoolean(dadosProduto[6]), ProdutoAlimentarTaxaIntermedia.CategoriaAlimentar.valueOf(dadosProduto[7]));
                            case "PAN" -> new ProdutoAlimentarTaxaNormal(codigo, nomeProduto, descricao, quantidade, valorUnitario, Boolean.parseBoolean(dadosProduto[6]));
                            case "PAR" -> {
                                EnumSet<ProdutoAlimentarTaxaReduzida.Certificacao> certificacoes = EnumSet.noneOf(ProdutoAlimentarTaxaReduzida.Certificacao.class);
                                for (String cert : dadosProduto[7].split(","))
                                    certificacoes.add(ProdutoAlimentarTaxaReduzida.Certificacao.valueOf(cert));
                                yield new ProdutoAlimentarTaxaReduzida(codigo, nomeProduto, descricao, quantidade, valorUnitario, Boolean.parseBoolean(dadosProduto[6]), certificacoes);
                            }
                            case "PFCP" -> new ProdutoFarmaciaComPrescricao(codigo, nomeProduto, descricao, quantidade, valorUnitario, dadosProduto[6], dadosProduto[7]);
                            case "PFSP" -> new ProdutoFarmaciaSemPrescricao(codigo, nomeProduto, descricao, quantidade, valorUnitario, ProdutoFarmaciaSemPrescricao.CategoriaFarmacia.valueOf(dadosProduto[6]));
                            default -> throw new IllegalArgumentException("Tipo de produto desconhecido: " + sigla);
                        };
                        fatura.adicionarProduto(produto);
                    }
                    if (cliente != null) cliente.adicionarFatura(fatura);
                } else {    // Cliente
                    int id = Integer.parseInt(line);
                    String[] dadosCliente = scanner.nextLine().split(";");
                    String nomeCliente = dadosCliente[0];
                    int contribuinte = Integer.parseInt(dadosCliente[1]);
                    Cliente.Localizacao localizacao = switch (dadosCliente[2]) {
                        case "Portugal Continental" -> Cliente.Localizacao.portugalContinental;
                        case "Madeira" -> Cliente.Localizacao.madeira;
                        case "Açores" -> Cliente.Localizacao.açores;
                        default -> Cliente.Localizacao.portugalContinental;
                    };
                    cliente = new Cliente(nomeCliente, contribuinte, localizacao, id);
                    clientes.add(cliente);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar dados... " + e.getMessage());
        }
        System.out.println("\nDados carregados do Ficheiro de Texto com sucesso!");
        exportBin();
    }
            

    /**
     * Carregar os dados de um ficheiro binário (2ª vez e em diante que o programa é executado).
     *
     * @param filePath path do ficheiro binário
     */
    public void loadBin(String filePath) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            int tamanhoClientes = in.readInt();
            for (int i = 0; i < tamanhoClientes; i++) {
                Cliente cliente = (Cliente) in.readObject();
                clientes.add(cliente);
            }
            System.out.println("\nDados carregados do Ficheiro Binário com sucesso!");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar dados... " + e.getMessage());
        }
    }


    /**
     * Guardar os dados num ficheiro binário (método chamado sempre que se faz uma alteração nos dados (Criar/Editar Clientes/Faturas).
     */
    public void exportBin() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("docs/dados.bin"))) {
            out.writeInt(clientes.size());
            for (Cliente cliente : clientes)
                out.writeObject(cliente);
            System.out.println("\nDados guardados no Ficheiro Binário com sucesso!");
        } catch (IOException e) {
            System.err.println("\nErro ao guardar dados: " + e.getMessage());
        }
    }
      

    // ------------------------------ Métodos Auxiliares da Aplicação POOFS ------------------------------

    /**
     * Procurar um produto pelo seu código.
     *
     * @param id id do cliente
     * @return cliente encontrado
     */
    private Cliente searchClientePorId(int id) {
        for (Cliente cliente : clientes)
            if (cliente.getId() == id) return cliente;
        return null;
    }
}