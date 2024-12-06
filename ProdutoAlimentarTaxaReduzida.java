import java.util.EnumSet;
import java.util.Set;

class ProdutoAlimentarTaxaReduzida extends ProdutoAlimentar {
    
    enum Certificacao {
        ISO22000, FSSC22000, HACCP, GMP
    }

    private final Set<Certificacao> certificacoes;

    public ProdutoAlimentarTaxaReduzida(String codigo, String nome, String descricao, int quantidade, double valorUnitario, boolean biologico, Set<Certificacao> certificacoes) {
        super(codigo, nome, descricao, quantidade, valorUnitario, biologico);
        this.certificacoes = EnumSet.copyOf(certificacoes);
    }

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

    @Override
    public String toString() {
        return super.toString() + certificacoes + ", Taxa: Reduzida";
    }

    public Set<Certificacao> getCertificacoes() {return certificacoes;}

    public void setCertificacoes(Set<Certificacao> certificacoes) {
        this.certificacoes.clear();
        this.certificacoes.addAll(certificacoes);
    }
}