import java.util.*;

class GestaoFaturas {
    private final Map<Integer, List<Fatura>> gestaoFaturas;
    private final Map<Integer, Cliente> clientes;
    
    public GestaoFaturas() {
        this.gestaoFaturas = new HashMap<>();
        this.clientes = new HashMap<>();
        //importarFaturas("faturas.txt"); // e se calhar para clientes tb?
    }

    // PARA TESTES
    public void adicionaCliente(Cliente cliente) {
        if (gestaoFaturas.containsKey(cliente.getId())) { // usar variavel static para o id dentro desta classe
            System.out.println("Cliente já existe.");
            return;
        }
        gestaoFaturas.put(cliente.getId(), new ArrayList<>());
        System.out.println("Cliente adicionado com sucesso.");
    }
    
    // PARA TESTES
    public void adicionaFatura(int clienteId, Fatura fatura) {
        if (!gestaoFaturas.containsKey(clienteId)) {
            System.out.println("Cliente não existe.");
            return;
        }
        gestaoFaturas.get(clienteId).add(fatura);
        System.out.println("Fatura adicionada com sucesso.");
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

    // auxiliar propcurar por id's (PPP idea)
    private final List<Produto> produtosDisponiveis = new ArrayList<>(); //sugestao copilot nao sei se está bem
    private Produto searchProdutoPorCodigo(String codigo) {
        for (Produto produto : produtosDisponiveis) {
            if (produto.getCodigo().equals(codigo)) {
                return produto;
            }
        }
        return null; //n encontramos nada
    }

    // Opção 1 - criar cliente
    public void criarCliente() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Insira Nome do novo cliente:");
        String nome = scanner.nextLine();

        System.out.println("Nº de contribuinte:");
        int contribuinte = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Selecione a localização do novo cliente:");
        System.out.println("1. Portugal Continental");
        System.out.println("2. Madeira");
        System.out.println("3. Açores");
        int opcao = scanner.nextInt();

        Cliente.Localizacao localizacao;
        switch (opcao) {
            case 1 -> localizacao = Cliente.Localizacao.portugalContinental;
            case 2 -> localizacao = Cliente.Localizacao.madeira;
            case 3 -> localizacao = Cliente.Localizacao.açores;
            default -> {
                System.out.println("ERRO! A localização será definida automaticamente para Portugal Continental!");
                localizacao = Cliente.Localizacao.portugalContinental;
            }
        }

        int id = clientes.size() + 1; // vemos os id's que ja temos e adicionamos 1 tipo PPP

        Cliente novoCliente = new Cliente(nome, contribuinte, localizacao, id);
        clientes.put(id, novoCliente);
        gestaoFaturas.put(id, new ArrayList<>());

        System.out.println("Cliente adicionado com Sucesso!! " + novoCliente.clienteToString());
    }

    // Opção 2 - editar cliente
    public void editarCliente() {
        Scanner scanner = new Scanner(System.in); //mano scanner de merdinha sempre a dar leak

        System.out.println("ID do cliente para Editar:");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (!clientes.containsKey(id)) {
            System.out.println("Cliente não existe!");
            return;
        }

        Cliente cliente = clientes.get(id);

        System.out.println("Cliente atual: " + cliente.clienteToString());
        System.out.println("Insira um novo nome, ou pressione Enter para manter o atual:");
        String novoNome = scanner.nextLine();
        if (!novoNome.isEmpty()) {
            cliente.setNome(novoNome);
        }

        System.out.println("Insira um novo número de contribuinte, ou insira 0 para manter o atual:"); //podia ser uma letra...mas tamos a dar scan de ints.. fazemos parseInt maybe? idk
        int novoContribuinte = scanner.nextInt();
        if (novoContribuinte != 0) {
            cliente.setContribuinte(novoContribuinte);
        }

        System.out.println("Selecione a nova localização, ou insira 0 para manter a atual:");
        System.out.println("1. Portugal Continental");
        System.out.println("2. Madeira");
        System.out.println("3. Açores");
        int novaLocalizacao = scanner.nextInt();
        if (novaLocalizacao >= 1 && novaLocalizacao <= 3) {
            Cliente.Localizacao localizacaoAtualizada = switch (novaLocalizacao) {
                case 1 -> Cliente.Localizacao.portugalContinental;
                case 2 -> Cliente.Localizacao.madeira;
                case 3 -> Cliente.Localizacao.açores;
                default -> cliente.getLocalizacao();
            };
            cliente.setLocalizacao(localizacaoAtualizada);
        }

        
        System.out.println("Cliente foi editado com Sucesso!!: " + cliente.clienteToString());
    }


    // Opção 3 - listar clientes
    public void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente adicionado na Database");
            return;
        }

        for (Cliente cliente : clientes.values()) {
            System.out.println(cliente.clienteToString());
        }
    }

    // Opção 4 - criar fatura
    public void criarFatura() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ID do cliente para criar a fatura:");
        int clienteId = scanner.nextInt();
        scanner.nextLine();
    
        if (!clientes.containsKey(clienteId)) {
            System.out.println("Cliente não encontrado!");
            return;
        }
    
        Cliente cliente = clientes.get(clienteId);
        System.out.println("Nº da fatura:");
        int numeroFatura = scanner.nextInt();
        scanner.nextLine(); 
    
        System.out.println("Dia da fatura:");
        int dia = scanner.nextInt();
        System.out.println("Mês da fatura:");
        int mes = scanner.nextInt();
        System.out.println("Ano da fatura:");
        int ano = scanner.nextInt();
        scanner.nextLine();
    
        Data dataFatura = new Data(dia, mes, ano);
    
        List<Produto> produtos = new ArrayList<>();
        String adicionarProduto = "S";
        while (adicionarProduto.equalsIgnoreCase("S")) {
            System.out.println("Código do produto:");
            String codigoProduto = scanner.nextLine();
    
            Produto produto = searchProdutoPorCodigo(codigoProduto);  // cena auxiliar
    
            if (produto != null) {
                produtos.add(produto);
                System.out.println("Produto adicionado: " + produto.produtoToString());
            } else {
                System.out.println("Produto não encontrado!!");
            }
    
            System.out.println("Adicionar outro produto? (S/N)");
            adicionarProduto = scanner.nextLine();
        }
    
        Fatura novaFatura = new Fatura(numeroFatura, cliente, dataFatura, produtos);
    
        gestaoFaturas.get(clienteId).add(novaFatura);
        System.out.println("Fatura criada com sucesso para " + cliente.getNome());
    }

    // Opção 5 - editar fatura
    public void editarFatura() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ID do cliente para editar a fatura:");
        int clienteId = scanner.nextInt();
        scanner.nextLine();
    
        if (!gestaoFaturas.containsKey(clienteId)) {
            System.out.println("Cliente não encontrado!!");
            return;
        }
    
        System.out.println("Digite o número da fatura a editar:");
        int numeroFatura = scanner.nextInt();
        scanner.nextLine();
    
        List<Fatura> faturas = gestaoFaturas.get(clienteId);
        Fatura faturaEdicao = null;
    
        for (Fatura fatura : faturas) {
            if (fatura.getNumero() == numeroFatura) {
                faturaEdicao = fatura;
                break;
            }
        }
    
        if (faturaEdicao == null) {
            System.out.println("Fatura não encontrada!!");
            return;
        }
        System.out.println("Fatura: ");
        faturaEdicao.imprimirFatura();
    
        // sub menu de edição
        System.out.println("Selecione uma opção:");
        System.out.println("1. Data da fatura");
        System.out.println("2. Produtos da fatura");
        System.out.println("3. Cancelar");
        int opcaoEdicao = scanner.nextInt();
        scanner.nextLine();
        switch (opcaoEdicao) {
            case 1: //editar data
                System.out.println("Novo dia:");
                int novoDia = scanner.nextInt();
                System.out.println("Novo mês:");
                int novoMes = scanner.nextInt();
                System.out.println("Novo ano:");
                int novoAno = scanner.nextInt();
                scanner.nextLine();
    
                faturaEdicao.setData(new Data(novoDia, novoMes, novoAno));
                System.out.println("Data alterada com Sucesso!!");
                break;
    
            case 2: //editar produtos
                String adicionarProduto = "S";
                while (adicionarProduto.equalsIgnoreCase("S")) {
                    System.out.println("Código do produto a adicionar ou remover:");
                    String codigoProduto = scanner.nextLine();
    
                    Produto produto = searchProdutoPorCodigo(codigoProduto);
                    if (produto != null) {
                        faturaEdicao.adicionarProduto(produto);
                        System.out.println("Produto adicionado com Sucesso!!");
                    } else {
                        System.out.println("Produto não encontrado!");
                    }
    
                    System.out.println("Adicionar novo produto? (S/N)");
                    adicionarProduto = scanner.nextLine();
                }
                break;
    
            case 3:
                System.out.println("Edição foi Cancelada!");
                break;
    
            default:
                System.out.println("Opção inválida");
                break;
        }
    
        System.out.println("Fatura editada com sucesso!!");
        faturaEdicao.imprimirFatura();
    }

    // Opção 6 - listar todas as faturas na aplicação
    public void listarFaturas() {
        for (List<Fatura> listaFaturas : gestaoFaturas.values()) {
            for (Fatura fatura : listaFaturas) {
                System.out.println("\nFatura nº: " + fatura.getNumero());
                System.out.println("Cliente: " + fatura.getCliente().getNome());
                System.out.println("Localização do Cliente: " + fatura.getCliente().getLocalizacao());
                System.out.println("Número de Produtos: " + fatura.getNumeroProdutos());
                System.out.printf("Valor Total sem IVA: %.2f%n", fatura.calcularTotalSemIVA());
                System.out.printf("Valor Total com IVA: %.2f%n", fatura.calcularTotalComIVA());
            }
        }
    }

    // Opção 7 - visualizar fatura
    public void visualizarFatura() {
        Scanner scanner = new Scanner(System.in); //continua a dizer que tem leak do scanner
        try {
            System.out.println("Qual o id do cliente a que pertence a fatura ?");
            int id = scanner.nextInt();
            if (!gestaoFaturas.containsKey(id)) {
                System.out.println("Cliente não existe.");
                return;
            }
            System.out.println("Qual o número da fatura ?");
            int numeroFatura = scanner.nextInt();
            List<Fatura> faturas = gestaoFaturas.get(id);
            boolean faturaExiste = false;
    
            for (Fatura f : faturas) {
                if (f.getNumero() == numeroFatura) {
                    f.imprimirFatura();
                    faturaExiste = true;
                    break;
                }
            }
            if (!faturaExiste) System.out.println("Fatura não existe.");
        } catch (InputMismatchException e) {
            System.out.println("Input inválido");
            scanner.nextLine(); // limpar buffer
        }
    }

    // Opção 8 - estatísticas
    public void estatisticas() {
        int totalFaturas = 0; //iniciar 0 faturas 
        for (List<Fatura> listaFaturas : gestaoFaturas.values()) {
            totalFaturas += listaFaturas.size(); 
        }
        System.out.printf("Número de faturas: %d%n", totalFaturas); 

        int totalProdutos = 0;
        double valorTotalSemIVA = 0.0, valorTotalComIVA = 0.0, valorTotalDoIVA = 0.0;
        for (List<Fatura> listaFaturas : gestaoFaturas.values())
            for (Fatura fatura : listaFaturas){
                totalProdutos += fatura.getNumeroProdutos();
                valorTotalSemIVA += fatura.calcularTotalSemIVA();
                valorTotalComIVA += fatura.calcularTotalComIVA();
                valorTotalDoIVA += fatura.calcularTotalDoIVA();            
            }
        System.out.printf("Número total de produtos: %d%n", totalProdutos);
        System.out.printf("Valor total sem IVA: %.2f%n", valorTotalSemIVA);
        System.out.printf("Valor total com IVA: %.2f%n", valorTotalComIVA);
        System.out.printf("Valor total do IVA: %.2f%n", valorTotalDoIVA);
    }
}
