import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;


class GestaoFaturas {
    private List<Cliente> clientes;
    private List<Fatura> faturas;

    public GestaoFaturas() {
        clientes = new ArrayList<>();
        faturas = new ArrayList<>();
    }

    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void adicionarFatura(Fatura fatura) {
        faturas.add(fatura);
    }

    public void Clientes() {
        for (Cliente cliente : clientes) {
            System.out.println("Nome: " + cliente.getNome() + ", NIF: " + cliente.getContribuinte() + ", Localização: " + cliente.getLocalizacao());
        }
    }

    public void Faturas() {
        for (Fatura fatura : faturas) {
            System.out.println("Fatura Nº: " + fatura.getNumero() + ", Cliente: " + fatura.getCliente().getNome() + ", Total Sem IVA: " + fatura.calcularTotalSemIVA() + ", Total Com IVA: " + fatura.calcularTotalComIVA());
        }
    }



    public void importarFaturas(String caminho) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {

                // criar fatur e produto...

                String[] dados = linha.split(";");
                Cliente cliente = new Cliente(dados[1], "111111111", "coimbra"); 
                Fatura fatura = new Fatura(dados[0], cliente, dados[2]);
                Produto produto = new ProdutoAlimentar(dados[3], "NomeProduto", "DescriçãoProduto", Integer.parseInt(dados[4]), Double.parseDouble(dados[5]), false, "normal");
                fatura.adicionarProduto(produto);
                adicionarFatura(fatura);
            }
        } catch (IOException e) {
            System.out.println("Erro ao importar faturas: " + e.getMessage());
        }
    }

    public void exportarFaturas(String caminho) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            for (Fatura fatura : faturas) {
                bw.write(fatura.getNumero() + ";" + fatura.getCliente().getNome() + ";" + fatura.getData());
                for (Produto produto : fatura.getProdutos()) {
                    bw.write(";" + produto.nome + ";" + produto.quantidade + ";" + produto.valorUnitario);
                }
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao exportar faturas: " + e.getMessage());
        }
    }

    public void estatistica() {
        int numeroFaturas = faturas.size();
        int numeroProdutos = 0;
        double totalSemIVA = 0.0;
        double totalIVA = 0.0;
        double totalComIVA = 0.0;

        for (Fatura fatura : faturas) {
            numeroProdutos += fatura.getProdutos().size();
            totalSemIVA += fatura.calcularTotalSemIVA();
            totalIVA += fatura.calcularTotalComIVA() - fatura.calcularTotalSemIVA();
            totalComIVA += fatura.calcularTotalComIVA();
        }

        System.out.println("Num. faturas: " + numeroFaturas);
        System.out.println("Num. produtos: " + numeroProdutos);
        System.out.println("Valor total sem IVA: " + totalSemIVA);
        System.out.println("Valor total do IVA: " + totalIVA);
        System.out.println("Valor total com IVA: " + totalComIVA);
    }
}