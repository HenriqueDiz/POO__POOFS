class ProdutoFarmaciaSemPrescricao extends Produto{

    enum CategoriaFarmacia {
        beleza, bemEstar, bebes, animais, outro
    }

    private CategoriaFarmacia categoriaFarmacia;

    public ProdutoFarmaciaSemPrescricao(String codigo, String nome, String descricao, int quantidade, double valorUnitario, CategoriaFarmacia categoriaFarmacia) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.categoriaFarmacia = categoriaFarmacia;
    }

    public CategoriaFarmacia getCategoriaFarmacia() {return categoriaFarmacia;}

    public void setCategoriaFarmacia(CategoriaFarmacia categoriaFarmacia) {this.categoriaFarmacia = categoriaFarmacia;}

    @Override
    public double calcularTaxaIVA(Cliente.Localizacao localizacao) {
        int taxaBase = 23;
        if (categoriaFarmacia == CategoriaFarmacia.animais) taxaBase -= 1;
        return taxaBase / 100.0;
    }
}
