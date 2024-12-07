import java.io.Serializable;

/**
 * Classe abstrata de um Produto, com código, nome, descrição, quantidade e valor unitário.
 * @author Henrique Diz
 * @author Tomás Gonçalves
 * @version 1.0
 */
abstract class Produto implements Serializable {
    protected String codigo, nome, descricao;
    protected double valorUnitario;
    protected int quantidade;

    /**
     * Construtor de um produto.
     *
     * @param codigo codigo do produto
     * @param nome nome do produto
     * @param descricao descrição do produto
     * @param quantidade quantidade do produto
     * @param valorUnitario valor unitário do produto
     */    
    public Produto(String codigo, String nome, String descricao, int quantidade, double valorUnitario) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    /**
     * Calcula a taxa de IVA de um produto.
     *
     * @param localizacao localização do cliente
     * @return taxa IVA
     */    
    public abstract double calcularTaxaIVA(Cliente.Localizacao localizacao);

    /**
     * Devolve uma representação do produto em String.
     *
     * @return representação do produto em String
     */
    @Override
    public String toString(){
        return  "Nome: " + nome + 
                ", Descrição: " + descricao + 
                ", Código: " + codigo +
                ", Quantidade: " + quantidade + 
                ", Valor Unitário: " + valorUnitario;
    }

    /**
     * Calcula o valor total sem IVA.
     *
     * @return valor total sem IVA
     */    
    public double calcularSemIVA() {
        return quantidade * valorUnitario;
    }

    /**
     * Calcula o valor total com IVA.
     *
     * @param localizacao localização do cliente
     * @return valor total com IVA
     */    
    public double calcularComIVA(Cliente.Localizacao localizacao) {
        return calcularSemIVA() * (1 + calcularTaxaIVA(localizacao));
    }

    /**
     * Calcula o IVA total.
     *
     * @param localizacao localização do cliente
     * @return IVA total
     */    
    public double calcularTotalDoIVA(Cliente.Localizacao localizacao) {
        return calcularComIVA(localizacao) - calcularSemIVA();
    }

    /**
     * Devolve o nome do produto.
     *
     * @return nome do produto
     */
    public String getNome() {return nome;} 

    /**
     * Define o nome do produto.
     *
     * @param nome nome do produto
     */
    public void setNome(String nome) {this.nome = nome;}

    /**
     * Devolve o código do produto.
     *
     * @return código do produto
     */
    public String getCodigo() {return codigo;}

    /**
     * Define o código do produto.
     *
     * @param codigo código do produto
     */
    public void setCodigo(String codigo) {this.codigo = codigo;}

    /**
     * Devolve a descrição do produto.
     *
     * @return descrição do produto
     */
    public String getDescricao() {return descricao;}

    /**
     * Define a descrição do produto.
     *
     * @param descricao descrição do produto
     */
    public void setDescricao(String descricao) {this.descricao = descricao;}

    /**
     * Devolve o valor unitário do produto.
     *
     * @return valor unitário do produto
     */
    public double getValorUnitario() {return valorUnitario;}

    /**
     * Define o valor unitário do produto.
     *
     * @param valorUnitario valor unitário do produto
     */
    public void setValorUnitario(double valorUnitario) {this.valorUnitario = valorUnitario;}

    /**
     * Devolve a quantidade do produto.
     *
     * @return quantidade do produto
     */
    public int getQuantidade() {return quantidade;}

    /**
     * Define a quantidade do produto.
     *
     * @param quantidade quantidade do produto
     */
    public void setQuantidade(int quantidade) {this.quantidade = quantidade;}
}