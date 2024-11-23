class ProdutoAlimentarTaxaNormal extends ProdutoAlimentar {

    public ProdutoAlimentarTaxaNormal(String codigo, String nome, String descricao, int quantidade, double preco, boolean biologico) {
        super(codigo, nome, descricao, quantidade, preco, biologico);
    }

    @Override
    public double calcularIVA(String localizacao) {
        double taxaBase;
        switch (localizacao.toLowerCase()) {
            case "madeira":
                taxaBase = 22;
                break;
            case "açores":
                taxaBase = 16;
                break;
            default: 
                taxaBase = 23;
        }
        return taxaBase / 100;
    }
<<<<<<< HEAD
}   
=======
}   
>>>>>>> 79927db3150e896b2476a586ace5c8b98a893321
