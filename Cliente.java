import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um cliente, com nome, contribuinte, localização e ID.
 * @author Henrique Diz
 * @author Tomás Gonçalves
 * @version 1.0
 */
class Cliente implements Serializable{

    /**
     * enumeração para as localizações possíveis de um cliente
     */    
    public enum Localizacao {
        portugalContinental, madeira, açores
    }

    /**
     * localização do cliente
     */
    private Localizacao localizacao;

    /**
     * nome do cliente
     */
    private String nome;

    /**
     * contribuinte do cliente
     */
    private int contribuinte, id;
    
    /**
     * id do cliente
     */
    public  List<Fatura> faturas;

    /**
     * Construtor de um cliente
     *
     * @param nome nome do cliente
     * @param contribuinte contribuinte do cliente
     * @param localizacao localização do cliente
     * @param id id do cliente
     */    
    public Cliente(String nome, int contribuinte, Localizacao localizacao, int id) {
        this.nome = nome;
        this.contribuinte = contribuinte;
        this.localizacao = localizacao;
        this.id = id;
        this.faturas = new ArrayList<>();
    }

    /**
     * Devolve uma representação do cliente em String.
     *
     * @return  representação do cliente em String
     */
    @Override
    public String toString() { 
        String localizacaoFormatada = getLocalizacaoFormatada();
        return "Nome: " + nome + ", Contribuinte: " + contribuinte + ", Localização: " + localizacaoFormatada + ", ID: " + id;
    }

    /**
     * Método para obter a localização formatada
     *
     * @return localização formatada
     */    
    public String getLocalizacaoFormatada() {
        return switch (localizacao) {
            case portugalContinental -> "Portugal Continental";
            case madeira -> "Madeira";
            case açores -> "Açores";
        };
    }
    
    /**
     * Método para adicionar uma fatura
     *
     * @param fatura fatura a adicionar
     */
    public void adicionarFatura(Fatura fatura) {
        faturas.add(fatura);
    }

    /**
     * Método para saber numero de faturas de um cliente
     *
     * @return numero de faturas
     */    
    public int getNumeroFaturas() {
        return faturas.size();
    }

    /**
     * Método para procurar uma fatura pelo seu número
     *
     * @param numero numero da fatura
     * @return fatura encontrada
     */    
    protected  Fatura searchFaturaNumero(int numero) {
        for (Fatura fatura : faturas)
            if (fatura.getNumero() == numero)
                return fatura;
        return null;
    }

    /**
     * Devolve o nome do cliente
     *
     * @return nome do cliente
     */
    public String getNome() {return nome;}

    /**
     * Define o nome do cliente
     *
     * @param nome nome do cliente
     */
    public void setNome(String nome) {this.nome = nome;}
    
    /**
     * Devolve o contribuinte do cliente
     *
     * @return contribuinte do cliente
     */
    public int getContribuinte() {return contribuinte;}
    
    /**
     * Define o contribuinte do cliente
     *
     * @param contribuinte contribuinte do cliente
     */
    public void setContribuinte(int contribuinte) {this.contribuinte = contribuinte;}
    
    /**
     * Devolve a localização do cliente
     *
     * @return localização do cliente
     */
    public Localizacao getLocalizacao() {return localizacao;}
    
    /**
     * Define a localização do cliente
     *
     * @param localizacao localização do cliente
     */
    public void setLocalizacao(Localizacao localizacao) {this.localizacao = localizacao;}
    
    /**
     * Devolve o id do cliente
     *
     * @return id do cliente
     */
    public int getId() {return id;}
    
    /**
     * Define o id do cliente
     *
     * @param id id do cliente
     */
    public void setId(int id) {this.id = id;}

    /**
     * Devolve as faturas do cliente
     *
     * @return faturas do cliente
     */
    public List<Fatura> getFaturas() {return faturas;}

    /**
     * Define as faturas do cliente
     *
     * @param faturas faturas do cliente
     */
    public void setFaturas(List<Fatura> faturas) {this.faturas = faturas;}
}