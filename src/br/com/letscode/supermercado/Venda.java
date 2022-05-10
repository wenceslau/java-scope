package br.com.letscode.supermercado;

import br.com.letscode.supermercado.enums.TipoCliente;

import java.util.Scanner;

public class Venda {

    private static Object[][] vendas = new Object[10][4];
    private static int totalVendas = 0;

    public static void venderProduto(Scanner ler) {

        //Inicia o cpf com o valor padrao
        String cpf = "00000000191";
        String resp = verificarInformarCpf(ler);

        //Se o usuario quer informar o CPF
        if (resp.equalsIgnoreCase("s")){
            cpf = lerCpf(ler);
        }

        //Identifica o tipo de cliente pelo CPF, ou solicitar o TIPO
        TipoCliente tipoCliente = identificarTipoCliente(cpf);
        //TipoCliente tipoCliente = lerTipoCliente(ler);

        Object[][] itensVendido = lerItens(ler);

        //MOstra na console todos os itens vendidos
        System.out.println("\tCodigo | Quantidade | Preco | ValorPagar");
        for (int i = 0; i < itensVendido.length; i++) {
            if (itensVendido[i][0] != null) {
                System.out.print("\t"+itensVendido[i][0] + " | ");
                System.out.print(itensVendido[i][1] + " | ");
                System.out.print(itensVendido[i][2] + " | ");
                System.out.println(itensVendido[i][3]);
            }
        }

        //SOlicita confirmacao para fechar a venda. Nao é possivel cancelar itens e nem para a venda
        System.out.println("\tDigite F para fechar a venda");
        ler.next();
        fecharVenda(cpf, tipoCliente, itensVendido);
    }

    private static String verificarInformarCpf(Scanner ler) {
        String resp = "";
        do{
            System.out.print("\tDeseja informar o CPF ou CNPJ? Digite S ou N: ");
            resp = ler.next().toUpperCase();
            if (resp.equals("S") || resp.equals("N")){
                break;
            }
        }while (true);
        return resp;
    }

    private static String lerCpf(Scanner ler) {
        String resp = null;
        do{
            System.out.print("\tDigite o CPF ou CPNJ: ");
            resp = ler.next();
            if (resp.length() < 11){
                System.out.println("\tCPF invalido, o CPF deve ter 11 posições \n");
                resp = null;
            }
        }while (resp == null);
        return resp;
    }

    private static TipoCliente identificarTipoCliente(String cpf) {
        if (cpf.equals("00000000191")){
            return TipoCliente.PF;
        }else if (cpf.length() > 11){
            return TipoCliente.PJ;
        }
        return  TipoCliente.VIP;
    }

    private static TipoCliente lerTipoCliente(Scanner ler) {
        TipoCliente tipoCliente = null;
        do {
            try {
                System.out.print("\tTipo Cliente: ");
                String valor = ler.next();
                tipoCliente = TipoCliente.valueOf(valor);
            } catch (Exception exception) {
                System.err.println("\tTipo Invalido. Informe um tipo valido ");
            }
        } while (tipoCliente == null);
        return  tipoCliente;
    }

    private static Object[][] lerItens(Scanner ler) {
        //Codigo | Quantidade | Preco | ValorPagar
        Object[][] itensVendido = new Object[10][4];
        int totalItens = 0;

        System.out.println("\tDigite o ID e a Quantidade do produto ou X para encerrar");
        System.out.println("\t==========================================================");

        do {
            //Le o ID, o X é o ponto de parada da entrada de dados
            String idProduto = lerIdProduto(ler);
            if (idProduto.equalsIgnoreCase("X")){
                break;
            }
            //Ler a quantidade, se o X foi digitado, é devolvido -1 que é a parada da entrada de dados
            int quantidade = lerQuantidade(ler,idProduto);
            if (quantidade == -1){
                break;
            }

            //Se quantidade for maior que zero, a venda foi aceita
            if (quantidade > 0){

                //Preimeiro verifica se é necessario redminesionar a tabela
                if (totalItens == itensVendido.length){
                    itensVendido = redimensionarTabela(itensVendido,4);
                }

                //Armazena os itens vendiddos na matriz
                itensVendido[totalItens][0] = idProduto;
                itensVendido[totalItens][1] = quantidade;
                Double precoVenda = Compra.getPrecoVenda(idProduto);
                itensVendido[totalItens][2] = precoVenda;
                itensVendido[totalItens][3] = quantidade * precoVenda;

                //Baixa o estoque. O estoque deve ser baixado antes do fechamento da venda
                //POrque o usuario pode repetir o mesmo produto em outro item
                Compra.baixarEstoque(idProduto, quantidade);

                //Incrementa o total de itens
                totalItens++;
            }

            System.out.println();
        }while (true);

        return itensVendido;
    }

    private static String lerIdProduto(Scanner ler){
        boolean produtoExiste = false;
        String idProduto;
        do {
            System.out.print("\tID: ");
            idProduto = ler.next();
            if (idProduto.equalsIgnoreCase("X")) {
                return idProduto;
            }
            //verifica se o produto existe la na classe Produto
            produtoExiste = Compra.produtoExiste(idProduto);
            if (!produtoExiste) {
                System.out.println("\t>>>>>Produto não existe");
            }
        }while (!produtoExiste);

        return idProduto;
    }

    private static int lerQuantidade(Scanner ler, String idProduto){
        int qtd = -1;
        do{
            System.out.print("\tQuantidade: ");
            String quantidade = ler.next();
            //Se digitou X, é o ponto de parada da entrada de dados
            if (quantidade.equalsIgnoreCase("X")) {
                return -1;
            }
            try {
                qtd = Integer.parseInt(quantidade);
                //Verifica o estoque la na classe de compra
                int estoque = Compra.getEstoque(idProduto);
                if (qtd > estoque){
                    System.out.println("\t>>>>>Estoque insuficiente. Estoque atual: " + estoque);
                    //Se o estoque for zero, interrompe o processo
                    if (estoque == 0) {
                        return 0;
                    }
                    //se for so insuficiente outro valor pode ser digitado, define -1 par ao loop continuar
                    qtd = -1;
                }

            }catch (NumberFormatException ex){
                System.out.println("\t>>>>>Quantidade invalida, digite apenas numeros");
            }
        }while (qtd == -1);

        return qtd;
    }

    private static void fecharVenda(String cpf, TipoCliente tipoCliente, Object[][] itensVendido) {
        //Verifica se é necessario redimensionar a tabela
        if (totalVendas == vendas.length){
            vendas = redimensionarTabela(vendas,4);
        }
        //armazena os itens na tabela de vendas
        vendas[totalVendas][0] = cpf;
        vendas[totalVendas][1] = tipoCliente;

        //Faz a soma de todos os itens e a soma do valor poago
        Integer qtdProdutos = 0;
        Double totalPago = 0.0;
        for (int i = 0; i < itensVendido.length; i++) {
            if (itensVendido[i][0] != null) {
                qtdProdutos += (Integer) itensVendido[i][1];
                totalPago += (Double) itensVendido[i][3];
            }
        }
        vendas[totalVendas][2] = qtdProdutos;

        //Calcula o desconto de acordo com o tipo de cliente e aplica no total
        totalPago = totalPago - TipoCliente.valorDesconto(tipoCliente, totalPago);
        vendas[totalVendas][3] = totalPago;

        //Incrementa o total de vendas
        totalVendas++;

        System.out.println("Venda Fechada...");
        System.out.println("Total Produtos: "+ qtdProdutos + ". Valor a Pagar: " + totalPago);
    }

    private static Object[][] redimensionarTabela(Object[][] tabela, int numColunas){
        Object[][] newTabela = new Object[tabela.length * 2][numColunas];
        for (int i = 0; i < tabela.length; i++) {
            for (int j = 0; j < numColunas; j++) {
                newTabela[i][j] = tabela[i][j];
            }
        }
        return newTabela;
    }

    public static void relatorioAnalitico() {
        System.out.println("RELATORIO ANALITICO");
        System.out.println("CPF | Tipo | Total Produtos | Total Pago |");
        for (int i = 0; i < vendas.length; i++) {
            if (vendas[i][0] != null) {
                for (int j = 0; j < 4; j++) {
                    System.out.print(vendas[i][j] + " | ");
                }
                System.out.println();
            }
        }
    }

    public static void relatorioSinteticoPorCpf() {
        Object[][] vendasPorCPF = new Object[10][3];
        int totalElemVendasCPF = 0;

        //Percorre a matrix
        for (int i = 0; i < vendas.length; i++) {
            String cpf = (String) vendas[i][0];
            //Verifca se não é uma posicao vazia
            if (cpf != null) {
                //pega os dados a serem agrupados
                Integer totalProduto = (Integer) vendas[i][2];
                Double totalPago = (Double) vendas[i][3];

                //Verifica se o cpf ja ta na matriz de agrupamento
                if (cpfExisteNaMatriz(vendasPorCPF, cpf)){
                    //Se sim, o metodo abaixo incrementa os dados de venda e quantidade
                    vendasPorCPF = atualizaDadosNaMatriz(vendasPorCPF, cpf, totalProduto, totalPago);
                }else{
                    //antes de adicionar verifica se nao precisa redimensionar
                    if (totalElemVendasCPF == vendasPorCPF.length){
                        vendasPorCPF = redimensionarTabela(vendasPorCPF, 3);
                    }
                    //Se nao adiciona o valor na matriz
                    vendasPorCPF[totalElemVendasCPF][0] = cpf;
                    vendasPorCPF[totalElemVendasCPF][1] = totalProduto;
                    vendasPorCPF[totalElemVendasCPF][2] = totalPago;

                    totalElemVendasCPF++;
                }
            }
        }


        System.out.println("RELATORIO SINTETICO");
        System.out.println("CPF | Total Produtos | Total Pago |");
        for (int i = 0; i < vendasPorCPF.length; i++) {
            if (vendasPorCPF[i][0] != null) {
                for (int j = 0; j < 3; j++) {
                    System.out.print(vendasPorCPF[i][j] + " | ");
                }
                System.out.println();
            }
        }
    }
    
    public static boolean cpfExisteNaMatriz( Object[][] vendasPorCPF, String cpf){
        //Percorre a matriz para ver se o cpf ja existe nela
        for (int i = 0; i < vendasPorCPF.length; i++) {
            String cpfMatriz = (String) vendasPorCPF[i][0];
            if (cpfMatriz!= null && cpfMatriz.equals(cpf)){
                return true;
            }
        }
        return  false;
    }
    public static Object[][] atualizaDadosNaMatriz(Object[][] vendasPorCPF, String cpfAtualizar, Integer totalProd, Double totalPago ){
        //percorre a matriz
        for (int j = 0; j < vendasPorCPF.length; j++) {
            String cpf = (String) vendasPorCPF[j][0];
            //verifica se o nao é posicao vazia e se o cpf é o cpf que precisa ser atualizado
            if (cpf != null && cpf.equals(cpfAtualizar)){
                //pega os dados de quntidade e valor pago
                Integer totalProdA = (Integer) vendasPorCPF[j][1];
                Double totalPagoA = (Double) vendasPorCPF[j][2];

                //Incrementa eles com os novos valores
                totalProdA = totalProdA + totalProd;
                totalPagoA = totalPagoA + totalPago;

                // Adiciona eles na matrix novamente
                vendasPorCPF[j][1] = totalProdA;
                vendasPorCPF[j][2] = totalPagoA;
            }
        }
        return vendasPorCPF;
    }
}
