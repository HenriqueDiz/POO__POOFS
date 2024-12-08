/**
 * Classe de um Produto de Farmácia sem prescrição.
 * @author Henrique Diz
 * @author Tomás Gonçalves
 * @version 1.0
 */

class ProdutoFarmaciaSemPrescricao extends Produto{

    /**
     * Enumeração das categorias de produtos de farmácia.
     */
    enum CategoriaFarmacia {
        /** Beleza */
        beleza,
        /** Bem-Estar */
        bemEstar,
        /** Bebés */
        bebes,
        /** Animais */
        animais,
        /** Outro */
        outro
    }

    /**
     * Categoria do produto.
     */
    private CategoriaFarmacia categoriaFarmacia;
    
    /**
     * Construtor de um produto de farmácia sem prescrição.
     *
     * @param codigo código do produto
     * @param nome nome do produto
     * @param descricao descrição do produto
     * @param quantidade quantidade do produto
     * @param valorUnitario valor unitário do produto
     * @param categoriaFarmacia categoria do produto
     */
    public ProdutoFarmaciaSemPrescricao(String codigo, String nome, String descricao, int quantidade, double valorUnitario, CategoriaFarmacia categoriaFarmacia) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.categoriaFarmacia = categoriaFarmacia;
    }

    /**
     * Calcula a taxa de IVA de um produto de farmácia sem prescrição.
     *
     * @param localizacao localização do cliente
     * @return taxa IVA
     */
    @Override
    public double calcularTaxaIVA(Cliente.Localizacao localizacao) {
        int taxaBase = 23;
        if (categoriaFarmacia == CategoriaFarmacia.animais) taxaBase -= 1;
        return taxaBase / 100.0;
    }

    /**
     * Devolve uma representação do produto em String.
     *
     * @return representação do produto em String
     */
    @Override
    public String toString() {
        String categoriaFormatada = switch (categoriaFarmacia) {
            case beleza -> "Beleza";
            case bemEstar -> "Bem-estar";
            case bebes -> "Bebés";
            case animais -> "Animais";
            case outro -> "Outro";
        };
        return super.toString() + ", Categoria: " + categoriaFormatada;
    }

    /**
     * Devolve a categoria do produto.
     *
     * @return categoria do produto
     */
    public CategoriaFarmacia getCategoriaFarmacia() {return categoriaFarmacia;}

    /**
     * Define a categoria do produto.
     *
     * @param categoriaFarmacia categoria do produto
     */
    public void setCategoriaFarmacia(CategoriaFarmacia categoriaFarmacia) {this.categoriaFarmacia = categoriaFarmacia;}
}
