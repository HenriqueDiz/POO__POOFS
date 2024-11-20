class Cliente {

    private String nome;
    private int contribuinte;
    private String localizacao;

    // consttrutor
    public Cliente(String nome, int contribuinte, String localizacao) {
        this.nome = nome;
        this.contribuinte = contribuinte;
        this.localizacao = localizacao;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getContribuinte() { return contribuinte; }
    public void setContribuinte(int contribuinte) { this.contribuinte = contribuinte; }

    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
}