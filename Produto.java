abstract class Produto {
    protected String codigo;
    protected String nome;
    protected String descricao;
    protected int quantidade;
    protected double valorUnitario;

    public Produto(String codigo, String nome, String descricao, int quantidade, double valorUnitario) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    public abstract double calcularIVA(String localizacao);
}