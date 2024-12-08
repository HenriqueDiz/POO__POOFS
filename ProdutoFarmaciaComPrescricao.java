/**
 * Classe de um Produto de Farmácia com prescrição.
 * @author Henrique Diz
 * @author Tomás Gonçalves
 * @version 1.0
 */

class ProdutoFarmaciaComPrescricao extends Produto{

    /**
     * Prescrição do produto.
     */
    private String prescricao;

    /**
     * Médico que prescreveu o produto.
     */
    private String medico;

    /**
     * Construtor de um produto de farmácia com prescrição.
     *
     * @param codigo código do produto
     * @param nome nome do produto
     * @param descricao descrição do produto
     * @param quantidade quantidade do produto
     * @param valorUnitario valor unitário do produto
     * @param prescricao prescrição do produto
     * @param medico médico que prescreveu o produto
     */
    public ProdutoFarmaciaComPrescricao(String codigo, String nome, String descricao, int quantidade, double valorUnitario, String prescricao, String medico){
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.prescricao = prescricao;
        this.medico = medico;
    }

    /**
     * Calcula a taxa de IVA de um produto de farmácia com prescrição.
     *
     * @param localizacao localização do cliente
     * @return taxa IVA
     */
    @Override
    public double calcularTaxaIVA(Cliente.Localizacao localizacao) {
        int taxaBase = switch (localizacao) {
            case portugalContinental -> 6;
            case madeira -> 5;
            case açores -> 4;
        }; 
        return taxaBase / 100.0;
    }

    /**
     * Devolve uma representação do produto em String.
     *
     * @return representação do produto em String
     */
    @Override
    public String toString() {
        return super.toString() + ", Prescrição: " + prescricao + ", Médico: " + medico;
    }

    /**
     * Devolve a prescrição do produto.
     *
     * @return prescrição do produto
     */
    public String getPrescricao() {return prescricao;}

    /**
     * Define a prescrição do produto.
     *
     * @param prescricao prescrição do produto
     */
    public void setPrescricao(String prescricao) {this.prescricao = prescricao;}

    /**
     * Devolve o médico que prescreveu o produto.
     *
     * @return médico que prescreveu o produto
     */
    public String getMedico() {return medico;}

    /**
     * Define o médico que prescreveu o produto.
     *
     * @param medico médico que prescreveu o produto
     */
    public void setMedico(String medico) {this.medico = medico;}
}