import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class POOFS {
    private final List<Cliente> clientes;

    public POOFS() {
        this.clientes = new ArrayList<>();
    }

    // Opção 1 - Criar cliente
    public void criarCliente(Scanner scanner) {
        System.out.println("Nome do novo cliente: ");
        String nome = scanner.nextLine();

        System.out.println("Nº de contribuinte: ");
        int contribuinte = Integer.parseInt(scanner.nextLine());

        System.out.println("Localização do novo cliente:");
        System.out.println("1. Portugal Continental");
        System.out.println("2. Madeira");
        System.out.println("3. Açores");
        int opcao = Integer.parseInt(scanner.nextLine());

        Cliente.Localizacao localizacao = switch (opcao) {
            case 1 -> Cliente.Localizacao.portugalContinental;
            case 2 -> Cliente.Localizacao.madeira;
            case 3 -> Cliente.Localizacao.açores;
            default -> Cliente.Localizacao.portugalContinental;
        };

        int id = 0;
        do { 
            id++;
        } while (searchClientePorId(id) != null);

        Cliente novoCliente = new Cliente(nome, contribuinte, localizacao, id);
        clientes.add(novoCliente);

        System.out.println("Cliente adicionado com Sucesso!");
    }


    // Opção 2 - Editar cliente
    public void editarCliente(Scanner scanner) {
        System.out.println("ID do cliente para Editar: ");
        int clienteId = Integer.parseInt(scanner.nextLine());

        Cliente cliente = searchClientePorId(clienteId);
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        System.out.println("Editar Nome (atual: " + cliente.getNome() + "): ");
        String novoNome = scanner.nextLine();
        cliente.setNome(novoNome.isEmpty() ? cliente.getNome() : novoNome);

        System.out.println("Editar Nº de Contribuinte (atual: " + cliente.getContribuinte() + "): ");
        String novoContribuinte = scanner.nextLine();
        if (!novoContribuinte.isEmpty()) {
            cliente.setContribuinte(Integer.parseInt(novoContribuinte));
        }

        System.out.println("Editar Localização (1. Portugal Continental, 2. Madeira, 3. Açores): ");
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
        System.out.println("ID do cliente para criar a fatura: ");
        int clienteId = Integer.parseInt(scanner.nextLine());

        Cliente cliente = searchClientePorId(clienteId);
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        System.out.println("Nº da fatura: ");
        int numeroFatura = Integer.parseInt(scanner.nextLine());
        Fatura fatura = cliente.searchFaturaNumero(numeroFatura);
        if (fatura != null) {
            System.out.println("Fatura já existe!");
            return;
        }

        System.out.println("Dia da fatura: ");
        int dia = Integer.parseInt(scanner.nextLine());
        System.out.println("Mês da fatura: ");
        int mes = Integer.parseInt(scanner.nextLine());
        System.out.println("Ano da fatura: ");
        int ano = Integer.parseInt(scanner.nextLine());

        Data dataFatura = new Data(dia, mes, ano);

        Fatura novaFatura = new Fatura(numeroFatura, cliente, dataFatura); // criamos fatura aqui e depois basta usar o método da fatura - novaFatura.adicionarProduto(produto)
        String adicionarProduto;
        do {
            /* FALTA TERMINAR PROCESSO DE CRIAR FATURA - isto não pode ser feito aqui porque dependendo do tipo de produto diferentes atributos serao necessários
            A minha sugestao: fazer como o IVA, temos um metodo abstrato na classe Produto que é implementado nas subclasses de cada produto,
            e depois chamamos esse metodo para adicionar o produto
            */
            
            // novaFatura.adicionarProduto(produto)
            System.out.println("Adicionar outro produto (S/N) ? ");
            adicionarProduto = scanner.nextLine();
        } while (adicionarProduto.equalsIgnoreCase("S"));


        cliente.addFatura(novaFatura);

        System.out.println("Fatura criada com sucesso para " + cliente.getNome());
    }


    // Opção 5 - Editar fatura
    public void editarFatura(Scanner scanner) {
        if (clientes.isEmpty()) {
            System.out.println("\nNão há clientes registados na aplicação!");
            return;
        }
        
        System.out.println("ID do cliente associado à fatura: ");
        int clienteID = Integer.parseInt(scanner.nextLine());
        
        Cliente cliente = searchClientePorId(clienteID);
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        System.out.println("Nº da fatura que deseja editar: ");
        int numeroFatura = Integer.parseInt(scanner.nextLine());

        Fatura fatura = cliente.searchFaturaNumero(numeroFatura);
        if (fatura == null) {
            System.out.println("Fatura não encontrada!");
            return;
        }

        /* FALTA TERMINAR PROCESSO DE EDITAR FATURA */
        
        System.out.println("Editar Data da Fatura:");
        System.out.println("Novo Dia:");
        int dia = Integer.parseInt(scanner.nextLine());
        System.out.println("Novo Mês:");
        int mes = Integer.parseInt(scanner.nextLine());
        System.out.println("Novo Ano:");
        int ano = Integer.parseInt(scanner.nextLine());

        fatura.setData(new Data(dia, mes, ano));

        System.out.println("Fatura editada com sucesso!");
    }


    // Opção 6 - Listar faturas
    public void listarFaturas() {
        for (Cliente cliente : clientes)
            for (Fatura fatura : cliente.getFaturas())
                fatura.imprimirFaturaSimples();
    }

    // Opção 7 - Visualizar uma fatura específica
    public void visualizarFatura(Scanner scanner) {
        if (clientes.isEmpty()) {
            System.out.println("\nNão há clientes registados na aplicação!");
            return;
        }

        System.out.println("ID do cliente associado à fatura: ");
        int clienteID = Integer.parseInt(scanner.nextLine());

        Cliente cliente = searchClientePorId(clienteID);
        if (cliente == null) {
            System.out.println("\nCliente não encontrado!");
            return;
        }

        System.out.println("Nº da fatura que deseja visualizar: ");
        int numeroFatura = Integer.parseInt(scanner.nextLine());
    
        Fatura faturaEncontrada = cliente.searchFaturaNumero(numeroFatura);
        if (faturaEncontrada == null) {
            System.out.println("\nFatura não encontrada!");
            return;
        }
        faturaEncontrada.imprimirFaturaCompleta();
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

    // Função auxiliar para procurar um produto por código
    private Cliente searchClientePorId(int id) {
        for (Cliente cliente : clientes)
            if (cliente.getId() == id) return cliente;
        return null;
    }
}
    /*
    public void importarFaturas(String caminho) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) { // txt para clientes e faturas em binario maybe (so podemos usar 2 e em temos de usar os dois tipos)
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                int clienteId = Integer.parseInt(dados[0]);
                Cliente cliente = buscarClientePorId(clienteId);
                if (cliente == null) continue;

                Fatura fatura = new Fatura(Integer.parseInt(dados[1]), cliente, new Data(1, 1, 2024));
                for (int i = 2; i < dados.length; i += 4) {
                    String tipoProduto = dados[i];
                    // Produto creation logic (skipped here)
                }
                adicionarFatura(clienteId, fatura);
            }
        } catch (IOException e) {
            System.out.println("Erro ao importar faturas: " + e.getMessage());
        }
    }*/

    /*
    private Cliente buscarClientePorId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) return cliente;
        }
        return null;
    }*/
    /*
    public void exportarFaturas(String caminho) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            for (Map.Entry<Integer, List<Fatura>> entry : faturasPorCliente.entrySet()) {
                for (Fatura fatura : entry.getValue()) {
                    bw.write(entry.getKey() + ";" + fatura.getNumero() + ";" + fatura.getData());
                    for (Produto produto : fatura.getProdutos()) {
                        bw.write(";" + produto.getNome() + ";" + produto.getQuantidade() + ";" + produto.getValorUnitario());
                    }
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao exportar faturas: " + e.getMessage());
        }
    }*/