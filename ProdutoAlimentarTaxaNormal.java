class ProdutoAlimentarTaxaNormal extends ProdutoAlimentar {

    public ProdutoAlimentarTaxaNormal(String codigo, String nome, String descricao, int quantidade, double valorUnitario, boolean biologico) {
        super(codigo, nome, descricao, quantidade, valorUnitario, biologico);
    }

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
}