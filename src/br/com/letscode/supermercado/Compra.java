package br.com.letscode.supermercado;

import br.com.letscode.supermercado.enums.TipoProduto;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Compra {

    private static final int NOME = 3;

    public static void init(){
        produtos[totalProdutos][0] = "MarcaA";
        produtos[totalProdutos][1] = "11";
        produtos[totalProdutos][2] = TipoProduto.ALIMENTO;
        produtos[totalProdutos][3] = "Arroz";
        produtos[totalProdutos][4] = 11.49;
        produtos[totalProdutos][5] = 5;
        produtos[totalProdutos][6] = LocalDateTime.now();
        produtos[totalProdutos][7] = TipoProduto.precoVenda(TipoProduto.HIGIENTE,11.49);
        produtos[totalProdutos][8] = 10;
        totalProdutos++;

        produtos[totalProdutos][0] = "MarcaA";
        produtos[totalProdutos][1] = "12";
        produtos[totalProdutos][2] = TipoProduto.ALIMENTO;
        produtos[totalProdutos][3] = "Feijao";
        produtos[totalProdutos][4] = 12.99;
        produtos[totalProdutos][5] = 10;
        produtos[totalProdutos][6] = LocalDateTime.now();
        produtos[totalProdutos][7] = TipoProduto.precoVenda(TipoProduto.HIGIENTE,12.99);
        produtos[totalProdutos][8] = 12;
        totalProdutos++;

        produtos[totalProdutos][0] = "MarcaA";
        produtos[totalProdutos][1] = "13";
        produtos[totalProdutos][2] = TipoProduto.HIGIENTE;
        produtos[totalProdutos][3] = "Sabão";
        produtos[totalProdutos][4] = 5.39;
        produtos[totalProdutos][5] = 1;
        produtos[totalProdutos][6] = LocalDateTime.now();
        produtos[totalProdutos][7] = TipoProduto.precoVenda(TipoProduto.HIGIENTE,5.39);
        produtos[totalProdutos][8] = 1;
        totalProdutos++;

        produtos[totalProdutos][0] = "MarcaA";
        produtos[totalProdutos][1] = "14";
        produtos[totalProdutos][2] = TipoProduto.BEBIDA;
        produtos[totalProdutos][3] = "Suco";
        produtos[totalProdutos][4] = 4.88;
        produtos[totalProdutos][5] = 1;
        produtos[totalProdutos][6] = LocalDateTime.now();
        produtos[totalProdutos][7] = TipoProduto.precoVenda(TipoProduto.HIGIENTE,4.88);
        produtos[totalProdutos][8] = 2;
        totalProdutos++;
    }

    //Marca: (String)
    //Identificador: (String)
    //Tipo: (Enum)
    //Nome: (String)
    //Preco Custo: (Double)
    //Quantidade: (int)
    //Data Compra: (LocalDatetime)
    //Preco: (Double) deve ser calculado a patir do preco de custo, markup cada tipo de produto tem o seu markup
    //Estoque: (int)
    private static Object[][] produtos = new Object[10][9];
    private static int totalProdutos = 0;

    public static void redimensionarTabelaProdutos(){
        Object[][] newTabela = new Object[produtos.length * 2][9];
        for (int i = 0; i < produtos.length; i++) {
            newTabela[i][0] = produtos[i][0];
            newTabela[i][1] = produtos[i][1];
        }
        produtos =  newTabela;
    }

    public static void imprimirEstoque() {
        System.out.println("\t Estoque:");
        System.out.println("\tMarca \tID \tTipo \tEstoque");

        for (int i = 0; i < produtos.length; i++) {
            if (produtos[i][0] == null)
                continue;
            System.out.print("\t"+ produtos[i][0]);
            System.out.print("\t"+ produtos[i][1]);
            System.out.print("\t"+ produtos[i][2]);
            System.out.print("\t"+ produtos[i][8]);
            System.out.println();
        }

    }

    public static void imprimirEstoqueTipo(Scanner ler) {
        System.out.println("\t Estoque por Tipo:");

        TipoProduto tipoProduto = lerTipoProduto(ler);

        System.out.println("\tMarca \tID \tEstoque");

        for (int i = 0; i < produtos.length; i++) {
            if (produtos[i][0]== null)
                continue;
            TipoProduto tipo = (TipoProduto) produtos[i][2];
            if (tipo.equals(tipoProduto)) {
                System.out.print("\t"+ produtos[i][0]);
                System.out.print("\t"+ produtos[i][1]);
                System.out.print("\t"+ produtos[i][8]);
            }
            System.out.println();
        }

    }

    public static void comprarProdutos(Scanner ler) {
        //antes de realizar qualquer novo cadastro verifica se tem espaco
        if (totalProdutos == produtos.length){
            redimensionarTabelaProdutos();
        }

        System.out.print("\t ID: ");
        String id = ler.next();

        //Verifica se o produto ja existe no estoque
        int posicao = verificarProduto(id);
        boolean jaExiste = true;
        //-1 indica que nao existe, cadastra a marca e o id
        if (posicao == -1) {
            jaExiste = false;
            posicao = totalProdutos;
            produtos[posicao][0] = "MINHA MARCA";
            produtos[posicao][1] = id;
        }

        //Solicita os demais dados
        solicitarDados(ler, posicao, jaExiste);

    }

    public static void solicitarDados(Scanner ler, int posicao, boolean jaExiste) {

        TipoProduto tipoProduto = lerTipoProduto(ler);
        produtos[posicao][2] = tipoProduto;

        System.out.print("\t Nome: ");
        String nome =  ler.next();
        produtos[posicao][NOME] = nome;

        Double precoCusto = lerPrecoProduto(ler);
        produtos[posicao][4] = precoCusto;

        Integer quantidade = lerQuantidade(ler);
        produtos[posicao][5] = quantidade;
        produtos[posicao][6] = LocalDateTime.now();

        Double precoVenda = TipoProduto.precoVenda(tipoProduto, precoCusto);
        produtos[posicao][7] = precoVenda;

        Integer estoque = (Integer) produtos[posicao][8];

        estoque = estoque == null ? quantidade : estoque + quantidade;
        produtos[posicao][8] = estoque;

        System.out.println("Compra realizada com  com sucesso");
        if (jaExiste == false) {
            totalProdutos++;
        }
    }

    public static Integer lerQuantidade(Scanner ler) {
        Integer quantidade = null;
        do {
            try {
                System.out.print("\t Quantidade: ");
                String valor = ler.next();
                quantidade = Integer.parseInt(valor);
                if (quantidade < 0){
                    System.err.println("\t QUantidade não pode ser menor que 0");
                    quantidade = null;
                }
            } catch (Exception exception) {
                System.err.println("\t Quantidade invalida. Digite apenas numeros inteiros");
            }
        } while (quantidade == null);
        return  quantidade;
    }

    public static Double lerPrecoProduto(Scanner ler) {
        Double precoCusto = null;
        do {
            try {
                System.out.print("\t Preço de Custo: ");
                String valor = ler.next();
                precoCusto = Double.parseDouble(valor);
                if (precoCusto < 0){
                    System.err.println("\t Preço não pode ser menor que 0");
                    precoCusto = null;
                }
            } catch (Exception exception) {
                System.err.println("\t Preço invalido. Formato deve ser no padrão americano Ex.: 12.00");
            }
        } while (precoCusto == null);
        return  precoCusto;
    }

    public static int verificarProduto(String id) {

        for (int i = 0; i < produtos.length; i++) {
            String idPrd = (String) produtos[i][1];
            if (id.equals(idPrd)){
                return i;
            }
        }

        return -1;
    }

    public static TipoProduto lerTipoProduto(Scanner ler) {
        TipoProduto tipoProduto = null;
        do {
            try {
                System.out.print("\t Tipo Produto: ");
                String valor = ler.next();
                tipoProduto = TipoProduto.valueOf(valor);
            } catch (Exception exception) {
                System.err.println("\t Tipo Invalido. Informe um tipo valido ");
            }
        } while (tipoProduto == null);
        return  tipoProduto;
    }

    public static boolean produtoExiste(String id) {
        for (int i = 0; i < produtos.length; i++) {
            String idProduto = (String) produtos[i][1];
            if (idProduto != null && idProduto.equalsIgnoreCase(id)){
                return true;
            }
        }
        return false;
    }

    public static Integer getEstoque(String id) {
        for (int i = 0; i < produtos.length; i++) {
            String idProduto = (String) produtos[i][1];
            if (idProduto != null && idProduto.equalsIgnoreCase(id)){
                return (Integer) produtos[i][8];
            }
        }
        return 0;
    }

    public static Double getPrecoVenda(String id) {
        for (int i = 0; i < produtos.length; i++) {
            String idProduto = (String) produtos[i][1];
            if (idProduto != null && idProduto.equalsIgnoreCase(id)){
                return (Double) produtos[i][7];
            }
        }
        return null;
    }

    public static void baixarEstoque(String id, int quantidade){
        for (int i = 0; i < produtos.length; i++) {
            String idProduto = (String) produtos[i][1];
            if (idProduto != null && idProduto.equalsIgnoreCase(id)){
                int estoque = (Integer) produtos[i][8];
                produtos[i][8] = estoque - quantidade;
            }
        }
    }

    public static void pesquisarProdutoPeloCodigo(Scanner ler) {
        System.out.println("\tDigite um codigo para pesquisar");
        String id = ler.next();
        for (int i = 0; i < produtos.length; i++) {
            String idProduto = (String) produtos[i][1];
            if (idProduto != null && idProduto.equalsIgnoreCase(id)){
                System.out.println("Marca | ID | Tipo | Nome | Custo | Qtd | UltimaCompra | Preco | Estoque |");
                for (int j = 0; j < 9; j++) {
                    System.out.print(produtos[i][j]+" | ");
                }
                System.out.println();
                return;
            }
        }
        System.out.println("Produto não existe");
    }

    public static void pesquisarProdutoPeloNome(Scanner ler) {
        System.out.println("\tDigite um nome para pesquisar");
        String nomePesquisa = ler.next();
        System.out.println("Marca | ID | Tipo | Nome | Custo | Qtd | UltimaCompra | Preco | Estoque |");
        for (int i = 0; i < produtos.length; i++) {
            String nome = (String) produtos[i][NOME];
            if (nome != null && nome.contains(nomePesquisa)){
                for (int j = 0; j < 9; j++) {
                    System.out.print(produtos[i][j]+" | ");
                }
                System.out.println();
            }
        }
    }
}
