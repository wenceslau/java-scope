package br.com.letscode;

public enum TipoEscola {

    PU("Publica", 70),
    PR("Privada", 70),
    MT("Militar", 70),
    MU("Municipal", 70);

    TipoEscola(String descricao, int media){
        this.descricao = descricao;
        this.media = media;
    }
    private String descricao;
    private int media;

    public String getDescricao() {
        return this.descricao;
    }

    public int getMedia() {
        return this.media;
    }
}
