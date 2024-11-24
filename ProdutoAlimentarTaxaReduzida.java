import java.util.EnumSet;
import java.util.Set;

class ProdutoAlimentarTaxaReduzida extends ProdutoAlimentar {
    
    enum Certificacao {
        ISO22000, FSSC22000, HACCP, GMP
    }

    private final Set<Certificacao> certificacao;

    public ProdutoAlimentarTaxaReduzida(String codigo, String nome, String descricao, int quantidade, double valorUnitario, boolean biologico, int certificacaoBits) {
        super(codigo, nome, descricao, quantidade, valorUnitario, biologico);
        this.certificacao = decodeCertificacao(certificacaoBits);
    }

    private Set<Certificacao> decodeCertificacao(int certificacaoBits) {
        Set<Certificacao> certificacoes = EnumSet.noneOf(Certificacao.class);
        Certificacao[] values = Certificacao.values();
        for (int i = 0; i < values.length; i++)
            if ((certificacaoBits & (1 << i)) != 0) 
                certificacoes.add(values[i]);
        return certificacoes;
    }

    @Override
    public double calcularTaxaIVA(Cliente.Localizacao localizacao) {
        int taxaBase = switch (localizacao) {
            case portugalContinental -> 6;
            case madeira -> 5;
            case açores -> 4;
        }; 
        if (certificacao.size() == 4) taxaBase -= 1;
        if (biologico) taxaBase -= 10;
        return taxaBase / 100.0;
    }

    public Set<Certificacao> getCertificacao() {return certificacao;}

    public void setCertificacao(int certificacaoBits) {
        this.certificacao.clear();
        this.certificacao.addAll(decodeCertificacao(certificacaoBits));
    }
}