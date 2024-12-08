/**
 * Classe de um Produto Alimentar com taxa de IVA intermédia.
 * @author Henrique Diz
 * @author Tomás Gonçalves
 * @version 1.0
 */
class ProdutoAlimentarTaxaIntermedia extends ProdutoAlimentar {
    /**
     * Enumeração para as categorias alimentares possíveis de um produto alimentar
     */
    enum CategoriaAlimentar {
        /** Congelados */
        congelados,
        /** Enlatados */
        enlatados,
        /** Vinho */
        vinho
    }

    /**
     * Categoria alimentar do produto
     */
    private CategoriaAlimentar categoria;

    /**
     * Construtor de um produto alimentar com taxa de IVA intermédia.
     *
     * @param codigo código do produto
     * @param nome nome do produto
     * @param descricao descrição do produto	
     * @param quantidade quantidade do produto
     * @param valorUnitario valor unitário do produto
     * @param biologico se é biológico
     * @param categoria categoria alimentar
     */    
    public ProdutoAlimentarTaxaIntermedia(String codigo, String nome, String descricao, int quantidade, double valorUnitario, boolean biologico, CategoriaAlimentar categoria) {
        super(codigo, nome, descricao, quantidade, valorUnitario, biologico);
        this.categoria = categoria;
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
            case portugalContinental -> 13;
            case madeira -> 12;
            case açores -> 9;
        }; 
        if (categoria == CategoriaAlimentar.vinho) taxaBase += 1;
        if (biologico) taxaBase -= 10;
        if (taxaBase < 0) taxaBase = 0;
        return taxaBase / 100.0;
    }

    /**
     * Devolve uma representação do produto em String.
     *
     * @return representação do produto em String
     */
    @Override
    public String toString() {
        String categoriaFormatada = categoria.name().substring(0, 1).toUpperCase() + categoria.name().substring(1);
        return super.toString() + ", Categoria: " + categoriaFormatada + ", Taxa: Intermédia";
    }

    /**
     * Devolve a categoria do produto.
     *
     * @return categoria do produto
     */
    public CategoriaAlimentar getCategoria() {return categoria;}

    /**
     * Define a categoria do produto.
     *
     * @param categoria categoria do produto
     */
    public void setCategoria(CategoriaAlimentar categoria) {this.categoria = categoria;}
}