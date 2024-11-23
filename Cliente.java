class Cliente {

    enum Localizacao{
        portugalContinenal, madeira, açores
    }

    private Localizacao localizacao;
    private String nome;
    private int contribuinte, id;

    public Cliente(String nome, int contribuinte, Localizacao localizacao, int id) {
        this.nome = nome;
        this.contribuinte = contribuinte;
        this.localizacao = localizacao;
        this.id = id;
    }

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

    public int getContribuinte() {return contribuinte;}

    public void setContribuinte(int contribuinte) {this.contribuinte = contribuinte;}

    public String getLocalizacao() {return localizacao.toString();}

    public void setLocalizacao(Localizacao localizacao) {this.localizacao = localizacao;}

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}
}