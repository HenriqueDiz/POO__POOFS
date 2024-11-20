class ProdutoAlimentar extends Produto {
    private boolean isbiologico;

    //adicionar estas cenas no UML?
    private int codigo;
    private String nome;
    private String descricao;
    private int quantidade;
    private double valorUnitario;
    private String tipoTaxa; 


    public ProdutoAlimentar(String codigo, String nome, String descricao, int quantidade, double valorUnitario, boolean isbiologico, String tipoTaxa) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.isbiologico = isbiologico;
        this.tipoTaxa = tipoTaxa;
    }

    @Override
    public double calcularIVA(String localizacao) {
        double taxaIVA = 0.0;
        // calc taxa ...
        // decontos e auemntos...
        return taxaIVA;
    }
}