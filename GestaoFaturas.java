import java.util.*;
import java.io.*;

class GestaoFaturas {
    private final Map<Integer, List<Fatura>> gestaoFaturas;

    public GestaoFaturas() {
        this.gestaoFaturas = new HashMap<>();
        importarFaturas();
    }

    public void importarFaturas(String caminho) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
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
    }

    /*
    private Cliente buscarClientePorId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) return cliente;
        }
        return null;
    }*/

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
    }

    public void estatisticas() {
        int numeroFaturas = faturasPorCliente.values().stream().mapToInt(List::size).sum();
        int numeroProdutos = faturasPorCliente.values().stream()
                .flatMap(List::stream)
                .mapToInt(f -> f.getProdutos().size())
                .sum();
        double totalSemIVA = faturasPorCliente.values().stream()
                .flatMap(List::stream)
                .mapToDouble(Fatura::calcularTotalSemIVA)
                .sum();
        double totalComIVA = faturasPorCliente.values().stream()
                .flatMap(List::stream)
                .mapToDouble(Fatura::calcularTotalComIVA)
                .sum();
        double totalIVA = totalComIVA - totalSemIVA;

        System.out.println("Num. Faturas: " + numeroFaturas);
        System.out.println("Num. Produtos: " + numeroProdutos);
        System.out.println("Valor Total sem IVA: " + totalSemIVA);
        System.out.println("Valor Total do IVA: " + totalIVA);
        System.out.println("Valor Total com IVA: " + totalComIVA);
    }

    public void visualizarFatura(){
        System.out.println("Qual o número da fatura que deseja visualizar ? ");
        Scanner scanner = new Scanner(System.in);
        int numero = scanner.nextInt();
        for (List<Fatura> listaFaturas : gestaoFaturas.values()) {
            for (Fatura fatura : listaFaturas) {
                if (fatura.getNumero() == numero) {
                
                    break;
                }
            }
        }
        scanner.close();
    }

    public void listarFaturas() {
        for (List<Fatura> listaFaturas : gestaoFaturas.values()) {
            for (Fatura fatura : listaFaturas) {
                System.out.println("Fatura nº: " + fatura.getNumero());
                System.out.println("Cliente: " + fatura.getCliente().getNome());
                System.out.println("Localização do Cliente: " + fatura.getCliente().getLocalizacao());
                System.out.println("Número de Produtos: " + fatura.getProdutos().size());
                System.out.println("Valor Total sem IVA: " + fatura.calcularTotalSemIVA());
                System.out.println("Valor Total com IVA: " + fatura.calcularTotalComIVA() + "\n");
            }
        }
    }
}
