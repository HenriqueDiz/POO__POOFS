class ProdutoFarmaciaComPrescricao extends Produto{

    private String prescricao, medico;

    public ProdutoFarmaciaComPrescricao(String codigo, String nome, String descricao, int quantidade, double valorUnitario, String prescricao, String medico){
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.prescricao = prescricao;
        this.medico = medico;
    }

    public String getPrescricao() {return prescricao;}

    public void setPrescricao(String prescricao) {this.prescricao = prescricao;}

    public String getMedico() {return medico;}

    public void setMedico(String medico) {this.medico = medico;}

    @Override
    public double calcularTaxaIVA(Cliente.Localizacao localizacao) {
        int taxaBase = switch (localizacao) {
            case portugalContinental -> 6;
            case madeira -> 5;
            case açores -> 4;
        }; 
        return taxaBase / 100.0;
    }

    @Override
    public String toString() {
        return super.toString() + ", Prescrição: " + prescricao + ", Médico: " + medico;
    }
}