class ProdutoFarmaciaSemDescricao extends Produto{

    enum Categoria {
        beleza, bemEstar, bebes, animais, outro
    }

    private Categoria categoria;

    public ProdutoFarmaciaSemDescricao(String codigo, String nome, String descricao, int quantidade, double valorUnitario, Categoria categoria) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.categoria = categoria;
    }

    public String getCategoria() {return categoria.toString();}

    public void setCategoria(Categoria categoria) {this.categoria = categoria;}

    @Override
    public double calcularIVA(String localizacao) {
        double taxaBase = 23; //como e sempre 23 fazemos sempre constante...
        if (categoria == Categoria.animais) {
            taxaBase -= 1;
        }
        return taxaBase / 100;
    }
}