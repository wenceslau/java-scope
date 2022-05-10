package br.com.letscode.supermercado.enums;

public enum TipoProduto {

    ALIMENTO(1.2) , BEBIDA(2.3), HIGIENTE(1.5);

    TipoProduto(double markup) {
        this.markup = markup;
    }

    private final double markup;

    public static Double precoVenda(TipoProduto tipoProduto, Double precoCusto) {
        return tipoProduto.getMarkup() * precoCusto;
    }

    public double getMarkup() {
        return markup;
    }
}
