/**
 * Classe abstrata de um Produto Alimentar, comum a todos os produtos alimentares (com taxa reduzida, intermédia e normal).
 * @author Henrique Diz
 * @author Tomás Gonçalves
 * @version 1.0
 */
abstract class ProdutoAlimentar extends Produto{
    /**
     * Define se o produto é biológico
     */
    protected boolean biologico;

    /**
     * Construtor de um produto alimentar.
     *
     * @param codigo código do produto
     * @param nome nome do produto
     * @param descricao descrição do produto
     * @param quantidade quantidade do produto
     * @param valorUnitario valor unitário do produto
     * @param biologico se é biológico
     */    
    public ProdutoAlimentar(String codigo, String nome, String descricao, int quantidade, double valorUnitario, boolean biologico) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.biologico = biologico;
    }

    /**
     * Devolve uma representação do produto em String.
     *
     * @return representação do produto em String
     */
    @Override
    public String toString() {
        if (biologico) return super.toString() + ", Biológico: Sim";
        else return super.toString() + ", Biológico: Não";
    }
    /**
     * Devolve a categoria do produto.
     *
     * @return categoria do produto
     */
    public boolean getBiologico() {return biologico;}

    /**
     * Define a categoria do produto.
     *
     * @param biologico categoria do produto
     */
    public void setBiologico(boolean biologico) {this.biologico = biologico;}	
}
