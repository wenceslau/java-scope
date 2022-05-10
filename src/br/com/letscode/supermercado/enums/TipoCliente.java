package br.com.letscode.supermercado.enums;

public enum TipoCliente {

    PF(0) , PJ(5), VIP(15);

    TipoCliente(int desconto) {
        this.desconto = desconto;
    }

    private final int desconto;

    public static Double valorDesconto(TipoCliente tipoCliente, Double valorVenda) {
        return valorVenda  * (tipoCliente.getDesconto() / 100);
    }

    public double getDesconto() {
        return desconto;
    }
}
