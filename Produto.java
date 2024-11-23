abstract class Produto {
    protected String codigo, nome, descricao;
    protected double valorUnitario;
    protected int quantidade;

    public Produto(String codigo, String nome, String descricao, int quantidade, double valorUnitario) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    public String getNome() {return nome;} 

    public void setNome(String nome) {this.nome = nome;}

    public String getDescricao() {return descricao;}

    public void setDescricao(String descricao) {this.descricao = descricao;}

    public double getValorUnitario() {return valorUnitario;}

    public void setValorUnitario(double valorUnitario) {this.valorUnitario = valorUnitario;}

    public int getQuantidade() {return quantidade;}

    public void setQuantidade(int quantidade) {this.quantidade = quantidade;}
}