abstract class ProdutoAlimentar extends Produto{
    protected boolean biologico;

    public ProdutoAlimentar(String codigo, String nome, String descricao, int quantidade, double valorUnitario, boolean biologico) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.biologico = biologico;
    }

    @Override
    public String toString() {
        return super.toString() + ", Biológico: " + biologico;
    }

    public boolean getBiologico() {return biologico;}

    public void setBiologico(boolean biologico) {this.biologico = biologico;}	
}
