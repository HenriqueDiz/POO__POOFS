import java.util.ArrayList;
import java.util.Scanner;

class GestaoFaturas {
    private final ArrayList<Cliente> clientes; //arraylist de clientes

    public GestaoFaturas() {
        this.clientes = new ArrayList<>(); //arraylist de clientes
    }


    //Opção 1 - criar cliente
    public void criarCliente(Scanner scanner) {
        System.out.println("Nome do novo cliente:");
        String nome = scanner.nextLine();

        System.out.println("Nº de contribuinte:");
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

        int id = clientes.size() + 1;
        Cliente novoCliente = new Cliente(nome, contribuinte, localizacao, id);
        clientes.add(novoCliente);

        System.out.println("Cliente adicionado com Sucesso!! " + novoCliente.clienteToString());
    }


    //Opção 2 - editar cliente
    public void editarCliente(Scanner scanner) {
        System.out.println("ID do cliente para Editar:");
        int clienteId = Integer.parseInt(scanner.nextLine());
        Cliente cliente = searchClientePorId(clienteId);

        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        System.out.println("Editar Nome (atual: " + cliente.getNome() + "):");
        String novoNome = scanner.nextLine();
        cliente.setNome(novoNome.isEmpty() ? cliente.getNome() : novoNome);

        System.out.println("Editar Nº de Contribuinte (atual: " + cliente.getContribuinte() + "):");
        String novoContribuinte = scanner.nextLine();
        if (!novoContribuinte.isEmpty()) {
            cliente.setContribuinte(Integer.parseInt(novoContribuinte));
        }

        System.out.println("Editar Localização (1. Portugal Continental, 2. Madeira, 3. Açores):");
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


    //Opção 3 - listar clientes
    public void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Não existem Clientes Registados!");
            return;
        }
        for (Cliente cliente : clientes) {
            System.out.println(cliente.clienteToString());
        }
    }


    //Opção 4 - criar fatura
    public void criarFatura(Scanner scanner) {
        System.out.println("ID do cliente para criar a fatura:");
        int clienteId = Integer.parseInt(scanner.nextLine());

        Cliente cliente = searchClientePorId(clienteId);
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        System.out.println("Nº da fatura:");
        int numeroFatura = Integer.parseInt(scanner.nextLine());

        System.out.println("Dia da fatura:");
        int dia = Integer.parseInt(scanner.nextLine());
        System.out.println("Mês da fatura:");
        int mes = Integer.parseInt(scanner.nextLine());
        System.out.println("Ano da fatura:");
        int ano = Integer.parseInt(scanner.nextLine());

        Data dataFatura = new Data(dia, mes, ano);

        ArrayList<Produto> produtos = new ArrayList<>();
        String adicionarProduto;
        do {
            System.out.println("Código do produto:");
            String codigoProduto = scanner.nextLine();

            Produto produto = searchProdutoPorCodigo(codigoProduto);
            if (produto != null) {
                produtos.add(produto);
                System.out.println("Produto adicionado: " + produto.produtoToString());
            } else {
                System.out.println("Produto não encontrado!!");
            }

            System.out.println("Adicionar outro produto? (S/N)");
            adicionarProduto = scanner.nextLine();
        } while (adicionarProduto.equalsIgnoreCase("S"));

        Fatura novaFatura = new Fatura(numeroFatura, cliente, dataFatura);
        cliente.addFatura(novaFatura);

        System.out.println("Fatura criada com sucesso para " + cliente.getNome());
    }


    //Opção 5 - editar fatura
    public void editarFatura(Scanner scanner) {
        System.out.println("ID do cliente associado à fatura:");
        int clienteId = Integer.parseInt(scanner.nextLine());
        Cliente cliente = searchClientePorId(clienteId);

        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        System.out.println("Número da fatura a editar:");
        int numeroFatura = Integer.parseInt(scanner.nextLine());

        Fatura fatura = cliente.getFaturas().stream()
                .filter(f -> f.getNumero() == numeroFatura)
                .findFirst()
                .orElse(null);

        if (fatura == null) {
            System.out.println("Fatura não encontrada!");
            return;
        }

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


    //Opção6 - listar faturas
    public void listarFaturas() {
        for (Cliente cliente : clientes) {
            for (Fatura fatura : cliente.getFaturas()) {
                fatura.imprimirFatura(); //aproveitar metodo de imprimir maybe...
            }
        }
    }


    //Opção 7 - visualizar fatura
    public void visualizarFatura(Scanner scanner) {
        if (faturas.isEmpty()) {
            System.out.println("Não há faturas!");
            return;
        }
        
        System.out.println("Nº da fatura que deseja visualizar:");
        int numeroFatura = scanner.nextInt();
    
        Fatura faturaEncontrada = null;
        for (Fatura f : faturas) {
            if (f.getNumero() == numeroFatura) {
                faturaEncontrada = f;
                break;
            }
        }
    
        if (faturaEncontrada == null) {
            System.out.println("Fatura não encontrada!!");
            return;
        }
    
        System.out.println("\n============================");
        System.out.println("Fatura nº: " + faturaEncontrada.getNumero());
        System.out.println("Cliente: " + faturaEncontrada.getCliente().toString());
        System.out.println("Data: " + faturaEncontrada.getData());
        System.out.println("----------------------------");
    
        System.out.println("Produtos:");
        ArrayList<Produto> produtos = faturaEncontrada.getProdutos();
        if (produtos.isEmpty()) {
            System.out.println("Nao há produtos nesta Fatura!");
        } else {
            for (Produto produto : produtos) {
                System.out.println("\n" + produto.produtoToString());
                System.out.printf("Valor Total do Produto sem IVA: %.2f\n", produto.calcularSemIVA());
                System.out.printf("Taxa de IVA do Produto: %.0f %%\n", produto.calcularTaxaIVA(faturaEncontrada.getCliente().getLocalizacao()) * 100);
                System.out.printf("Valor do IVA do Produto: %.2f\n", produto.calcularTotalDoIVA(faturaEncontrada.getCliente().getLocalizacao()));
                System.out.printf("Valor Total do Produto com IVA: %.2f\n", produto.calcularComIVA(faturaEncontrada.getCliente().getLocalizacao()));
            }
        }
    
        System.out.println("\n----------------------------");
        System.out.printf("Total da Fatura sem IVA: %.2f\n", faturaEncontrada.calcularTotalSemIVA());
        System.out.printf("Total de IVA da Fatura: %.2f\n", faturaEncontrada.calcularTotalDoIVA());
        System.out.printf("Total da Fatura com IVA: %.2f\n", faturaEncontrada.calcularTotalComIVA());
        System.out.println("============================\n");
    }


    //Opção 8 - estatisticas
    public void estatisticas() {
        int totalClientes = clientes.size();
        long totalFaturas = clientes.stream()
                .flatMap(cliente -> cliente.getFaturas().stream())
                .count();

        System.out.println("Estatísticas:");
        System.out.println("Total de Clientes: " + totalClientes);
        System.out.println("Total de Faturas: " + totalFaturas);
    }

    private Cliente searchClientePorId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) return cliente;
        }
        return null;
    }

    private Produto searchProdutoPorCodigo(String codigo) { //maybe remover isto...
        //implementar procurar no arraylist de produtos
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
    
    
  
