import java.io.Serializable;

/**
 * Representa uma data, com dia, mês e ano.
 * @author Henrique Diz
 * @author Tomás Gonçalves
 * @version 1.0
 */
class Data implements Serializable {
    private int dia, mes, ano;
   
    /**
     * Construtor de uma data.
     *
     * @param dia dia da data
     * @param mes mês da data
     * @param ano ano da data
     */    
    public Data(int dia, int mes, int ano) {
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }

    /**
     * Devolve uma representação da data em String.
     *
     * @return representação da data em String
     */
    @Override
    public String toString() {
        return dia + "/" + mes + "/" + ano;
    }

    /**
     * Devolve o dia da data.
     *
     * @return dia da data
     */
    public int getDia() { return dia; }

    /**
     * Define o dia da data.
     *
     * @param dia dia da data
     */
    public void setDia(int dia) { this.dia = dia; }

    /**
     * Devolve o mês da data.
     *
     * @return mês da data
     */
    public int getMes() { return mes; }
    
    /**
     * Define o mês da data.
     *
     * @param mes mês da data
     */
    public void setMes(int mes) { this.mes = mes; }

    /**
     * Devolve o ano da data.
     *
     * @return ano da data
     */
    public int getAno() { return ano; }
    
    /**
     * Define o ano da data.
     *
     * @param ano ano da data
     */
    public void setAno(int ano) { this.ano = ano; }
}