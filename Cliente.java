import java.util.ArrayList;

class Cliente {

    public enum Localizacao {
        portugalContinental, madeira, açores
    }

    private Localizacao localizacao;
    private String nome;
    private int contribuinte, id;
    private ArrayList<Fatura> faturas;  //arraylist de faturas

    public Cliente(String nome, int contribuinte, Localizacao localizacao, int id) {
        this.nome = nome;
        this.contribuinte = contribuinte;
        this.localizacao = localizacao;
        this.id = id;
        this.faturas = new ArrayList<>();  //arraylist de faturas em cada cliente
    }

    public String clienteToString() {
        String localizacaoFormatada = getLocalizacaoFormatada();
        return nome + ", Contribuinte: " + contribuinte + ", Localização: " + localizacaoFormatada + ", ID: " + id;
    }

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}
    
    public int getContribuinte() {return contribuinte;}
    
    public void setContribuinte(int contribuinte) {this.contribuinte = contribuinte;}
    
    public String getLocalizacaoFormatada() {
        return switch (localizacao) {
            case portugalContinental -> "Portugal Continental";
            case madeira -> "Madeira";
            case açores -> "Açores";
        };
    }
    
    public Localizacao getLocalizacao() {return localizacao;}
    
    public void setLocalizacao(Localizacao localizacao) {this.localizacao = localizacao;}
    
    public int getId() {return id;}
    
    public void setId(int id) {this.id = id;}

    public ArrayList<Fatura> getFaturas() { //nececssario?
        return faturas;
    }

    public void addFatura(Fatura fatura) { //add fatura a cada cliente
        faturas.add(fatura);
    }
}
