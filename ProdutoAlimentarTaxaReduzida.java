import java.util.EnumSet;
import java.util.Set;

/**
 * Classe de um Produto Alimentar com taxa de IVA reduzida, com certificações.
 * @author Henrique Diz
 * @author Tomás Gonçalves
 * @version 1.0
 */
class ProdutoAlimentarTaxaReduzida extends ProdutoAlimentar {

    /**
     * Enumeração para as certificações possíveis de um produto alimentar
     */    
    enum Certificacao {
        ISO22000, FSSC22000, HACCP, GMP
    }

    /**
     * Certificações do produto
     */
    private final Set<Certificacao> certificacoes; 
    
    /**
     * Construtor de um produto alimentar com taxa de IVA reduzida.
     *
     * @param codigo código do produto
     * @param nome nome do produto
     * @param descricao descrição do produto
     * @param quantidade quantidade do produto
     * @param valorUnitario valor unitário do produto
     * @param biologico se é biológico
     * @param certificacoes certificações do produto
     */
    public ProdutoAlimentarTaxaReduzida(String codigo, String nome, String descricao, int quantidade, double valorUnitario, boolean biologico, Set<Certificacao> certificacoes) {
        super(codigo, nome, descricao, quantidade, valorUnitario, biologico);
        this.certificacoes = EnumSet.copyOf(certificacoes);
    }

    /**
     * Calcula a taxa de IVA de um produto.
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
        if (certificacoes.size() == 4) taxaBase -= 1;
        if (biologico) taxaBase -= 10;
        if (taxaBase < 0) taxaBase = 0;
        return taxaBase / 100.0;
    }

    /**
     * Devolve uma representação do produto em String.
     *
     * @return representação do produto em String
     */
    @Override
    public String toString() {
        return super.toString() + ", Certificações: " + getCertificacoesFormatada() + ", Taxa: Reduzida";
    }

    public String getCertificacoesFormatada(){
        String str = "";
        for (Certificacao certificacao : certificacoes) {
            if (str.length() > 0) str += "/";
            str += certificacao;
        }
        return str;
    }

    /**
     * Devolve as certificações do produto.
     *
     * @return certificações do produto
     */
    public Set<Certificacao> getCertificacoes() {return certificacoes;}

    /**
     * Define as certificações do produto.
     *
     * @param certificacoes certificações do produto
     */
    public void setCertificacoes(Set<Certificacao> certificacoes) {
        this.certificacoes.clear();
        this.certificacoes.addAll(certificacoes);
    }
}