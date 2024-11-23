class ProdutoFarmaciaComDescricao extends Produto{

    private String prescricao, medico;

    public ProdutoFarmaciaComDescricao(String codigo, String nome, String descricao, int quantidade, double valorUnitario, String prescricao, String medico){
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.prescricao = prescricao;
        this.medico = medico;
    }

    public String getPrescricao() {return prescricao;}

    public void setPrescricao(String prescricao) {this.prescricao = prescricao;}

    public String getMedico() {return medico;}

    public void setMedico(String medico) {this.medico = medico;}

    @Override
    public double calcularIVA(String localizacao) {
        double taxaBase;
        switch (localizacao.toLowerCase()) {
            case "madeira":
                taxaBase = 5;
                break;
            case "açores":
                taxaBase = 4;
                break;
            default: 
                taxaBase = 6;
        }
        return taxaBase / 100;
    }
}
