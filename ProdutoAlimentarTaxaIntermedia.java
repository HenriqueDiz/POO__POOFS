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

    @Override
    public double calcularIVA(String localizacao) {
        double taxaBase;
        switch (localizacao.toLowerCase()) {
            case "madeira":
                taxaBase = 12;
                break;
            case "açores":
                taxaBase = 9;
                break;
            default:
                taxaBase = 13;
        }
        
        if (categoria == Categoria.vinhos) {
            taxaBase += 1;
        }
        return taxaBase / 100; 
    }
}
