class ProdutoAlimentarTaxaIntermedia extends ProdutoAlimentar {
    enum Categoria{
        congelados, enlatados, vinhos;
    }

    private Categoria categoria;

    public ProdutoAlimentarTaxaIntermedia(String codigo, String nome, String descricao, int quantidade, double preco, boolean biologico, Categoria categoria) {
        super(codigo, nome, descricao, quantidade, preco, biologico);
        this.categoria = categoria;
    }

    public Categoria getCategoria() {return categoria;}

    public void setCategoria(Categoria categoria) {this.categoria = categoria;}
}