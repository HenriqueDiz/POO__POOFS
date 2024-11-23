import java.util.EnumSet;
import java.util.Set;

class ProdutoAlimentarTaxaReduzida extends ProdutoAlimentar {
    
    enum Certificacao {
        ISO22000, FSSC22000, HACCP, GMP
    }

    private final Set<Certificacao> certificacao;

    public ProdutoAlimentarTaxaReduzida(String codigo, String nome, String descricao, int quantidade, double preco, boolean biologico, int certificacaoBits) {
        super(codigo, nome, descricao, quantidade, preco, biologico);
        this.certificacao = decodeCertificacao(certificacaoBits);
    }

    private Set<Certificacao> decodeCertificacao(int certificacaoBits) { // São passados numeros do tipo 1001, 1010, 1100, etc (funciona ? pois boa questao)
        Set<Certificacao> certificacoes = EnumSet.noneOf(Certificacao.class);
        Certificacao[] values = Certificacao.values();
        for (int i = 0; i < values.length; i++)
            if ((certificacaoBits & (1 << i)) != 0) 
                certificacoes.add(values[i]);
        return certificacoes;
    }
}