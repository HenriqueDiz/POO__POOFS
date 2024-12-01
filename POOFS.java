import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class POOFS {
    private List<Cliente> clientes; // não pode ser final porque vai ser alterado

    public POOFS() {
        this.clientes = new ArrayList<>();
    }

    // ------------------------------ Métodos da Aplicação ------------------------------

    // Opção 1 - Criar cliente
    public void criarCliente(Scanner scanner) {
        System.out.print("Nome do novo cliente: ");
        String nome = scanner.nextLine();

        System.out.print("Nº de contribuinte: ");
        int contribuinte = Integer.parseInt(scanner.nextLine());

        System.out.println("1. Portugal Continental");
        System.out.println("2. Madeira");
        System.out.println("3. Açores");
        System.out.print("Localização do novo cliente: ");
        int opcao = Integer.parseInt(scanner.nextLine());

        Cliente.Localizacao localizacao = switch (opcao) {
            case 1 -> Cliente.Localizacao.portugalContinental;
            case 2 -> Cliente.Localizacao.madeira;
            case 3 -> Cliente.Localizacao.açores;
            default -> Cliente.Localizacao.portugalContinental;
        };

        int id = clientes.size() + 1;

        Cliente novoCliente = new Cliente(nome, contribuinte, localizacao, id);
        clientes.add(novoCliente);

        System.out.println("Cliente adicionado com Sucesso!");
        exportBin();
    }


    // Opção 2 - Editar cliente
    public void editarCliente(Scanner scanner) {
        System.out.print("ID do cliente para Editar: ");
        int clienteId = Integer.parseInt(scanner.nextLine());

        Cliente cliente = searchClientePorId(clienteId);
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        System.out.print("Editar Nome (atual: " + cliente.getNome() + "): ");
        String novoNome = scanner.nextLine();
        cliente.setNome(novoNome.isEmpty() ? cliente.getNome() : novoNome);

        System.out.print("Editar Nº de Contribuinte (atual: " + cliente.getContribuinte() + "): ");
        String novoContribuinte = scanner.nextLine();
        if (!novoContribuinte.isEmpty()) {
            cliente.setContribuinte(Integer.parseInt(novoContribuinte));
        }

        System.out.print("Editar Localização (1. Portugal Continental, 2. Madeira, 3. Açores): ");
        String novaLocalizacao = scanner.nextLine();
        if (!novaLocalizacao.isEmpty()) {
            Cliente.Localizacao localizacao = switch (Integer.parseInt(novaLocalizacao)) {
                case 1 -> Cliente.Localizacao.portugalContinental;
                case 2 -> Cliente.Localizacao.madeira;
                case 3 -> Cliente.Localizacao.açores;
                default -> cliente.getLocalizacao();
            };
            cliente.setLocalizacao(localizacao);
        }

        System.out.println("Cliente atualizado com sucesso!");
        exportBin();
    }


    // Opção 3 - Listar clientes
    public void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("\nNão existem Clientes Registados!");
            return;
        }
        for (Cliente cliente : clientes)
            System.out.println("\n" + cliente.clienteToString());
    }


    // Opção 4 - Criar fatura
    public void criarFatura(Scanner scanner) {
        System.out.print("ID do cliente para criar a fatura: ");
        int clienteId = Integer.parseInt(scanner.nextLine());

        Cliente cliente = searchClientePorId(clienteId);
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        System.out.print("Nº da fatura: ");
        int numeroFatura = Integer.parseInt(scanner.nextLine());
        Fatura fatura = cliente.searchFaturaNumero(numeroFatura);
        if (fatura != null) {
            System.out.println("Fatura já existe!");
            return;
        }

        System.out.print("Dia da fatura: ");
        int dia = Integer.parseInt(scanner.nextLine());
        System.out.print("Mês da fatura: ");
        int mes = Integer.parseInt(scanner.nextLine());
        System.out.print("Ano da fatura: ");
        int ano = Integer.parseInt(scanner.nextLine());

        Data dataFatura = new Data(dia, mes, ano);
        Fatura novaFatura = new Fatura(numeroFatura, cliente, dataFatura);
        
        String adicionarProduto;
        do {
            Produto produto = null; // TODO - Implementar método abstrato para adicionar produto (ou implementamos na class Auxiliar ?  nao sei)
            if(produto != null) {
                novaFatura.adicionarProduto(produto);
                System.out.println("Produto adicionado com sucesso!");
            }

            System.out.print("Adicionar outro produto (S/N) ? ");
            adicionarProduto = scanner.nextLine();
        } while (adicionarProduto.equalsIgnoreCase("S"));


        cliente.addFatura(novaFatura);

        System.out.println("Fatura criada com sucesso para " + cliente.getNome());
        exportBin();
    }


    // Opção 5 - Editar fatura
    public void editarFatura(Scanner scanner) {
        if (clientes.isEmpty()) {
            System.out.println("\nNão há clientes registados na aplicação!");
            return;
        }
        
        System.out.print("ID do cliente associado à fatura: ");
        int clienteID = Integer.parseInt(scanner.nextLine());
        
        Cliente cliente = searchClientePorId(clienteID);
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        System.out.print("Nº da fatura que deseja editar: ");
        int numeroFatura = Integer.parseInt(scanner.nextLine());

        Fatura fatura = cliente.searchFaturaNumero(numeroFatura);
        if (fatura == null) {
            System.out.println("Fatura não encontrada!");
            return;
        }

        // Editar data da fatura
        System.out.println("Editar Data da Fatura:");
        System.out.print("Novo Dia: ");
        int dia = Integer.parseInt(scanner.nextLine());
        System.out.print("Novo Mês: ");
        int mes = Integer.parseInt(scanner.nextLine());
        System.out.print("Novo Ano: ");
        int ano = Integer.parseInt(scanner.nextLine());
        fatura.setData(new Data(dia, mes, ano));

        // Editar produtos da fatura
        System.out.println("Editar Produtos da Fatura:");
        System.out.println("1. Adicionar Produto");
        System.out.println("2. Remover Produto");
        System.out.println("3. Editar Produto");
        System.out.println("4. Sair");
        System.out.print("Opção: ");
        int opcao = Integer.parseInt(scanner.nextLine());
        do{
            switch (opcao) {
                case 1 -> {
                    Produto produto = null; // TODO - chamar método abstrato para adicionar produto	
                    if(produto != null) {
                        fatura.adicionarProduto(produto);
                        System.out.println("Produto adicionado com sucesso!");
                    }
                }
                case 2 -> {
                    System.out.print("Código do Produto a remover: ");
                    int codigo = Integer.parseInt(scanner.nextLine());
                    fatura.removerProduto(codigo);
                }
                case 3 -> {
                    System.out.print("Código do Produto a editar: ");
                    int codigo = Integer.parseInt(scanner.nextLine());
                    Produto produto = fatura.getProduto(codigo);
                    if (produto == null) {
                        System.out.println("Produto não encontrado!");
                        return;
                    }
                    System.out.print("Editar Nome (atual: " + produto.getNome() + "): ");
                    String novoNome = scanner.nextLine();
                    produto.setNome(novoNome.isEmpty() ? produto.getNome() : novoNome);

                    System.out.print("Editar Descrição (atual: " + produto.getDescricao() + "): ");
                    String novaDescricao = scanner.nextLine();
                    produto.setDescricao(novaDescricao.isEmpty() ? produto.getDescricao() : novaDescricao);

                    System.out.print("Editar Quantidade (atual: " + produto.getQuantidade() + "): ");
                    String novaQuantidade = scanner.nextLine();
                    if (!novaQuantidade.isEmpty()) {
                        produto.setQuantidade(Integer.parseInt(novaQuantidade));
                    }

                    System.out.print("Editar Valor Unitário (atual: " + produto.getValorUnitario() + "): ");
                    String novoValorUnitario = scanner.nextLine();
                    if (!novoValorUnitario.isEmpty()) {
                        produto.setValorUnitario(Double.parseDouble(novoValorUnitario));
                    }
                }
                case 4 -> {
                    System.out.println("A sair...");
                    return;
                }
            }
        } while (opcao != 4);
        System.out.println("Fatura editada com sucesso!");
        exportBin();
    }

    // Opção 6 - Listar faturas
    public void listarFaturas() {
        boolean flag = false;
        for (Cliente cliente : clientes)
            for (Fatura fatura : cliente.getFaturas()){
                flag = true;
                fatura.imprimirFatura(false);
            }
        if (!flag) System.out.println("\nNão existem faturas registadas!");
    }

    // Opção 7 - Visualizar uma fatura específica
    public void visualizarFatura(Scanner scanner) {
        if (clientes.isEmpty()) {
            System.out.println("\nNão há clientes registados na aplicação!");
            return;
        }

        System.out.print("ID do cliente associado à fatura: ");
        int clienteID = Integer.parseInt(scanner.nextLine());

        Cliente cliente = searchClientePorId(clienteID);
        if (cliente == null) {
            System.out.println("\nCliente não encontrado!");
            return;
        }

        System.out.print("Nº da fatura que deseja visualizar: ");
        int numeroFatura = Integer.parseInt(scanner.nextLine());
    
        Fatura faturaEncontrada = cliente.searchFaturaNumero(numeroFatura);
        if (faturaEncontrada == null) {
            System.out.println("\nFatura não encontrada!");
            return;
        }
        faturaEncontrada.imprimirFatura(true);
    }

    // Opção 8 - Apresentar estatísticas
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

    // Método para carregar os dados de um ficheiro txt (1º Vez que o programa é executado)
    public void loadTxt(String filePath) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while(scanner.hasNextLine()) {
                String[] dados = scanner.nextLine().split(";");
                String nome = dados[0];
                int contribuinte = Integer.parseInt(dados[1]);
                Cliente.Localizacao localizacao = switch (dados[2]) {
                    case "Portugal Continental" -> Cliente.Localizacao.portugalContinental;
                    case "Madeira" -> Cliente.Localizacao.madeira;
                    case "Açores" -> Cliente.Localizacao.açores;
                    default -> Cliente.Localizacao.portugalContinental;
                };
                int id = Integer.parseInt(dados[3]);
                Cliente cliente = new Cliente(nome, contribuinte, localizacao, id);
                clientes.add(cliente);                                   // TODO: FALTA ACABAR DE CARREGAR OS DADOS DOS CLIENTES
            }
            System.out.println("Dados carregados do Ficheiro Txt com sucesso!!");
        } catch (IOException e) {
            System.out.println("Erro ao carregar dados... " + e.getMessage());
        }
    }   

    // Método para carregar os dados de um ficheiro binário (2º Vez e em diante que o programa é executado)
    public void loadBin(String filePath) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            int tamanhoClientes = in.readInt();
            for (int i = 0; i < tamanhoClientes; i++) {
                Cliente cliente = (Cliente) in.readObject();
                int tamanhoFaturas = in.readInt();
                for (int j = 0; j < tamanhoFaturas; j++) {
                    Fatura fatura = (Fatura) in.readObject();
                    int tamanhoProdutos = in.readInt();
                    for (int k = 0; k < tamanhoProdutos; k++) {
                        String sigla = in.readUTF();
                        Produto produto = null;
                        switch (sigla) {
                            case "PAI" -> {produto = (ProdutoAlimentarTaxaIntermedia) in.readObject();}
                            case "PAN" -> {produto = (ProdutoAlimentarTaxaNormal) in.readObject();}
                            case "PAR" -> {produto = (ProdutoAlimentarTaxaReduzida) in.readObject();}
                            case "PFCP" -> {produto = (ProdutoFarmaciaComPrescricao) in.readObject();}
                            case "PFSP" -> {produto = (ProdutoFarmaciaSemPrescricao) in.readObject();}
                        }
                        fatura.adicionarProduto(produto);
                    }
                    cliente.addFatura(fatura);
                }
                clientes.add(cliente);
            }
            System.out.println("Dados carregados do Ficheiro Bin com sucesso!!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar dados... " + e.getMessage());
        }
    }   

    // Método para guardar os dados em binário
    public void exportBin() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("dados.bin"))) {
            out.writeInt(clientes.size());
            for (Cliente cliente : clientes) {
                out.writeObject(cliente);
                out.writeInt(cliente.getFaturas().size());
                for (Fatura fatura : cliente.getFaturas()) {
                    out.writeObject(fatura);
                    out.writeInt(fatura.produtos.size());
                    for (Produto produto : fatura.produtos) {
                        out.writeUTF(produto.obterSigla());
                        out.writeObject(produto);
                    }
                }
            }
            System.out.println("Dados guardados!");
        } catch (IOException e) {
            System.err.println("Erro ao guardar dados: " + e.getMessage());
        }
    }
      

    // ------------------------------ Métodos Auxiliares da Aplicação POOFS ------------------------------


    // Função auxiliar para procurar um produto por código
    private Cliente searchClientePorId(int id) {
        for (Cliente cliente : clientes)
            if (cliente.getId() == id) return cliente;
        return null;
    }
}