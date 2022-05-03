package br.com.letscode;

public class MeuArray {

    private static int quantidade = 0;
    private static int quantidadeTabela = 0;

    private static String[] palavras = new String[5];
    private static String[][] tabelaPalavras = new String[5][2];


    public static void main(String[] args) {

        adicionar("PL1");
        adicionar("PL2");
        adicionar("PL3");
        adicionar("PL4");
        adicionar("PL5");
        adicionar("PL6");
        adicionar("PL6");
        adicionar("PL6");
        adicionar("PL6");
        adicionar("PL6");
        adicionar("PL6");
        adicionar("PL6");
        adicionar("PL6");
        adicionar("PL6");
        adicionar("PL6");
        adicionar("PL6");
        adicionar("PL6");

    }

    public static void adicionar(String elemento){

        if (quantidade == palavras.length) {
            redimensionarEficiente();
        }
        palavras[quantidade] = elemento;
        quantidade++;
    }

    public static void adicionarTabela(String elemento, int coluna){

        if (quantidadeTabela == palavras.length) {
            redimensionarEficiente();
        }
        tabelaPalavras[quantidadeTabela][coluna] = elemento;
        quantidadeTabela++;
    }

    public static void redimensionarEficiente(){
        String[] newArray = new String[palavras.length * 2];
        for (int i = 0; i < palavras.length; i++) {
            newArray[i] = palavras[i];
        }
        palavras =  newArray;
    }

    public static void redimensionarTabelaEficiente(){
        String[][] newTabela = new String[palavras.length * 2][2];
        for (int i = 0; i < palavras.length; i++) {
            newTabela[i][0] = tabelaPalavras[i][0];
            newTabela[i][1] = tabelaPalavras[i][1];
        }
        tabelaPalavras =  newTabela;
    }

    public static String[] redimencionarNaoEficiente(String[] array){

        boolean estaCheio = true;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                estaCheio = false;
                break;
            }
        }

        if (estaCheio){
            String[] newArray = new String[array.length * 2];
            for (int i = 0; i < array.length; i++) {
                newArray[i] = array[i];
            }
            return newArray;
        }
        return array;
    }

}
