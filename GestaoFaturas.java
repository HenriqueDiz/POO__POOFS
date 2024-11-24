import java.util.*;

class GestaoFaturas {
    private final Map<Integer, List<Fatura>> gestaoFaturas;

    public GestaoFaturas() {
        this.gestaoFaturas = new HashMap<>();
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

    // Opção 6
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

    // Opção 7
    public void visualizarFatura(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Qual o id do cliente a que pertence a fatura ?");
        int id = scanner.nextInt();
        if (!gestaoFaturas.containsKey(id)) {
            System.out.println("Cliente não existe.");
            scanner.close();
            return;
        }
        System.out.println("Qual o número da fatura ?");
        int numeroFatura = scanner.nextInt();
        List<Fatura> faturas = gestaoFaturas.get(id);
        boolean faturaExiste = false;
        for (Fatura f : faturas)
            if (f.getNumero() == numeroFatura) {
                f.imprimirFatura();
                faturaExiste = true;
                break;
            }
        if (!faturaExiste) System.out.println("Fatura não existe.");
    }

    // Opção 8
    public void estatisticas() {
        System.out.println("\nNúmero de faturas: " + gestaoFaturas.size());
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
