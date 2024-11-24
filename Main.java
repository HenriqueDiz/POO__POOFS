import java.util.*;

public class Main {
    public static void main(String[] args) {
        
        GestaoFaturas gestaoFaturas = new GestaoFaturas();

        Cliente henrique = new Cliente("Henrique Says", 696969, Cliente.Localizacao.portugalContinental, 1);
        Cliente tomas = new Cliente("Tomás Miata", 123456, Cliente.Localizacao.madeira, 2); 

        List<Produto> produtos = new ArrayList<>();
        produtos.add(new ProdutoAlimentarTaxaIntermedia("001", "Vinho", "Vinho Tinto", 10, 5.0, false, ProdutoAlimentarTaxaIntermedia.CategoriaAlimentar.vinho));
        produtos.add(new ProdutoAlimentarTaxaNormal("002", "Arroz", "Arroz Branco", 20, 1.0, true));
        produtos.add(new ProdutoAlimentarTaxaReduzida("003", "Leite", "Leite Integral", 30, 0.8, false, 15));
        produtos.add(new ProdutoFarmaciaComPrescricao("004", "Medicamento A", "Para dor de cabeça", 5, 10.0, "Prescrição A", "Dr. Silva"));
        produtos.add(new ProdutoFarmaciaSemPrescricao("005", "Creme", "Creme para pele", 15, 7.0, ProdutoFarmaciaSemPrescricao.CategoriaFarmacia.beleza));
        Fatura fatura1 = new Fatura(9999, henrique, new Data(23, 11, 2024), produtos);
        
        gestaoFaturas.adicionaCliente(henrique);
        gestaoFaturas.adicionaCliente(tomas);
        gestaoFaturas.adicionaFatura(1, fatura1);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Criar cliente");
            System.out.println("2. Editar cliente");
            System.out.println("3. Listar clientes");
            System.out.println("4. Criar fatura");
            System.out.println("5. Editar fatura");
            System.out.println("6. Listar todas as faturas na aplicação");
            System.out.println("7. Visualizar fatura");
            System.out.println("8. Estatísticas");
            System.out.println("9. Sair");
            System.out.println("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            
            switch (opcao) {
                case 1 -> {
                    gestaoFaturas.criarCliente();
                }
                case 2 -> {
                    gestaoFaturas.editarCliente();
                }
                case 3 -> {
                    gestaoFaturas.listarClientes();
                }
                case 4 -> {
                    gestaoFaturas.criarFatura();
                }
                case 5 -> {  
                    gestaoFaturas.editarFatura();
                }
                case 6 -> {
                    gestaoFaturas.listarFaturas();
                }
                case 7 -> {
                    gestaoFaturas.visualizarFatura(scanner);
                }
                case 8 -> {
                    gestaoFaturas.estatisticas();
                }
                case 9 -> {
                    System.out.println("\nSaindo...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }
}