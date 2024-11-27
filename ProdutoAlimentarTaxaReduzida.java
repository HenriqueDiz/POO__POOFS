import java.util.EnumSet;
import java.util.Set;

class ProdutoAlimentarTaxaReduzida extends ProdutoAlimentar {
    
    enum Certificacao {
        ISO22000, FSSC22000, HACCP, GMP
    }

    private final Set<Certificacao> certificacoes;

    public ProdutoAlimentarTaxaReduzida(String codigo, String nome, String descricao, int quantidade, double valorUnitario, boolean biologico, int certificacaoBits) {
        super(codigo, nome, descricao, quantidade, valorUnitario, biologico);
        this.certificacoes = decodeCertificacao(certificacaoBits);
    }

    private Set<Certificacao> decodeCertificacao(int certificacaoBits) {
        Set<Certificacao> certifi = EnumSet.noneOf(Certificacao.class);
        Certificacao[] values = Certificacao.values();
        for (int i = 0; i < values.length; i++)
            if ((certificacaoBits & (1 << i)) != 0) 
                certifi.add(values[i]);
        return certificacoes;
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
    public String obterSigla() {
        return "PAR"; 
    }

    public Set<Certificacao> getCertificacoes() {return certificacoes;}

    public void setCertificacoes(int certificacaoBits) {
        this.certificacoes.clear();
        this.certificacoes.addAll(decodeCertificacao(certificacaoBits));
    }
}