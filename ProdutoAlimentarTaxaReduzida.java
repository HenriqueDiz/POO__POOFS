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

    @Override
    public double calcularIVA(String localizacao) {
        double taxaBase;
        switch (localizacao.toLowerCase()) {
            case "madeira":
                taxaBase = 5;
                break;
            case "açores":
                taxaBase = 4;
                break;
            default: //ou seja continente
                taxaBase = 6;
        }
        if (certificacao.size() == 4) {
            taxaBase -= 1;
        }
        return taxaBase / 100; //pa ser percentagem
    }
}
