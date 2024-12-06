class ProdutoAlimentarTaxaIntermedia extends ProdutoAlimentar {
    enum CategoriaAlimentar{
        congelados, enlatados, vinho;
    }

    private CategoriaAlimentar categoria;

    public ProdutoAlimentarTaxaIntermedia(String codigo, String nome, String descricao, int quantidade, double valorUnitario, boolean biologico, CategoriaAlimentar categoria) {
        super(codigo, nome, descricao, quantidade, valorUnitario, biologico);
        this.categoria = categoria;
    }

    public CategoriaAlimentar getCategoria() {return categoria;}

    public void setCategoria(CategoriaAlimentar categoria) {this.categoria = categoria;}

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

    @Override
    public String toString() {
        return super.toString() + ", Categoria: " + categoria + ", Taxa: Intermédia";
    }
}