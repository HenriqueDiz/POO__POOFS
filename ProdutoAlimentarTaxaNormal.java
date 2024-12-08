/**
 * Classe de um Produto Alimentar com taxa de IVA normal.
 * @author Henrique Diz
 * @author Tomás Gonçalves
 * @version 1.0
 */
class ProdutoAlimentarTaxaNormal extends ProdutoAlimentar {
    
    /**
     * Construtor de um produto alimentar com taxa de IVA normal.
     *
     * @param codigo código do produto
     * @param nome nome do produto
     * @param descricao descrição do produto
     * @param quantidade quantidade do produto
     * @param valorUnitario valor unitário do produto
     * @param biologico se é biológico
     */    
    public ProdutoAlimentarTaxaNormal(String codigo, String nome, String descricao, int quantidade, double valorUnitario, boolean biologico) {
        super(codigo, nome, descricao, quantidade, valorUnitario, biologico);
    }

    /**
     * Calcula a taxa de IVA de um produto.
     *
     * @param localizacao localização do cliente
     * @return taxa IVA
     */
    @Override
    public double calcularTaxaIVA(Cliente.Localizacao localizacao) {
        int taxaBase = switch (localizacao) {
            case portugalContinental -> 23;
            case madeira -> 22;
            case açores -> 16;
        }; 
        if (biologico) taxaBase -= 10;
        return taxaBase / 100.0;
    }

    /**
     * Devolve uma representação do produto em String.
     *
     * @return representação do produto em String
     */
    @Override
    public String toString() {
        return super.toString() + ", Taxa: Normal";
    }
}