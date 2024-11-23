class ProdutoFarmaciaSemDescricao extends Produto{

    enum categoria {
        beleza, bemEstar, bebes, animais, outro
    }

    private categoria categoria;

    public ProdutoFarmaciaSemDescricao(String codigo, String nome, String descricao, int quantidade, double valorUnitario, categoria categoria) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.categoria = categoria;
    }

    public String getCategoria() {return categoria.toString();}

    public void setCategoria(categoria categoria) {this.categoria = categoria;}

}