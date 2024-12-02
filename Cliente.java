import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Cliente implements Serializable{

    public enum Localizacao {
        portugalContinental, madeira, açores
    }

    private Localizacao localizacao;
    private String nome;
    private int contribuinte, id;
    private List<Fatura> faturas;

    public Cliente(String nome, int contribuinte, Localizacao localizacao, int id) {
        this.nome = nome;
        this.contribuinte = contribuinte;
        this.localizacao = localizacao;
        this.id = id;
        this.faturas = new ArrayList<>();
    }

    public String clienteToString() { 
        String localizacaoFormatada = getLocalizacaoFormatada();
        return nome + ", Contribuinte: " + contribuinte + ", Localização: " + localizacaoFormatada + ", ID: " + id;
    }

    public String getLocalizacaoFormatada() {
        return switch (localizacao) {
            case portugalContinental -> "Portugal Continental";
            case madeira -> "Madeira";
            case açores -> "Açores";
        };
    }

    public void addFatura(Fatura fatura) {
        faturas.add(fatura);
    }

    public int getNumeroFaturas() {
        return faturas.size();
    }

    public List<Fatura> getFaturas() {
        return faturas;
    }

    protected  Fatura searchFaturaNumero(int numero) {
        for (Fatura fatura : faturas)
            if (fatura.getNumero() == numero)
                return fatura;
        return null;
    }

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}
    
    public int getContribuinte() {return contribuinte;}
    
    public void setContribuinte(int contribuinte) {this.contribuinte = contribuinte;}
    
    public Localizacao getLocalizacao() {return localizacao;}
    
    public void setLocalizacao(Localizacao localizacao) {this.localizacao = localizacao;}
    
    public int getId() {return id;}
    
    public void setId(int id) {this.id = id;}
}