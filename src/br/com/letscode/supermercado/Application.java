package br.com.letscode.supermercado;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

        Scanner ler = new Scanner(System.in);
        application(ler);

    }
    private static void application(Scanner ler){

        String option;
        do {
            System.out.println("===========================================================================");
            System.out.println("Opções");
            System.out.println("\t 1 - Comprar produtos");
            System.out.println("\t 2 - Imprimir estoque");
            System.out.println("\t 3 - Listar os produto pelo Tipo");
            System.out.println("\t 4 - Pesquisar produto pelo Codigo");
            System.out.println("\t 5 - Pesquisar produto pelo Nome");
            System.out.println("\t 6 - Vender produtos");
            System.out.println("\t 7 - Relatorio de vendas analitico");
            System.out.println("\t 8 - Relatorio de vendas sintetico por CPF");

            option = ler.next();

            switch (option){
                case "1":
                    Compra.comprarProdutos(ler);
                    break;
                case "2":
                    Compra.imprimirEstoque();
                    break;
                case "3":
                    Compra.imprimirEstoqueTipo(ler);
                    break;
                case "4":
                    Compra.pesquisarProdutoPeloCodigo(ler);
                    break;
                case "5":
                    Compra.pesquisarProdutoPeloNome(ler);
                    break;
                case "6":
                    Venda.venderProduto(ler);
                    break;
                case "7":
                    Venda.relatorioAnalitico();
                    break;
                case "8":
                    Venda.relatorioSinteticoPorCpf();
                    break;
                case "0":
                    System.exit(0);
                default:
                    System.out.println("Informe uma opção valida");
            }

        } while (!option.equals("0"));
    }

}
