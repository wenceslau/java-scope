package br.com.letscode.aulas;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/*


    Um empresário comprou 2 terrenos em uma area central da cidade. E quer montar 2 estacionamentos e precisa de um
    sistema para controlar a entrada e saida dos veículos e controlar o valor a pagar conforme o tempo de permanência
    Porem é um requisito muito importante que o sistema seja o mais simples possível.
    Capacidade do estacionamento é variável e deve ser enviada na inncialização da aplicção

    Para um carro entrar ou saír no estabelecimento, a sua placa deverá ser fornecida ao sistema,
    caso não houver nenhuma outra entrada em aberto para aquela placa deverá ser exibida a mensagem:
    >> Entrada do veículo de placa: XXX

    Caso contrário deverá ser exibida a mensagem:
    >> Saída do veículo de placa: XXX. Tempo no estabelecimento: X minutos. Valor a ser cobrado: XX

     O valor a ser cobrado é definido pela seguinte forma:

    • De 0 a 5 minutos. Sem cobrança de valor
    • De 5 a 60 minutos. R$ 4,00
    • Acima de 60 minutos é cobrado um valor de R$ 6,00 por hora adicional.
        Ex.: 1 hora e 4 minutos de permanecia, deverá ser cobrado:
        4 reais da primeira hora e 6 reais dos 4 minutos que compoe a hora adicional. Total 10 reais.

    Ao realizar a entrada ou saída de veículos do estabelecimento o sistema deverá
    imprimir um relatório das entradas e saídas já realizadas, bem como o tempo que
    cada veículo que já saiu ficou no estabelecimento.

    OBS: deverão ser utilizados somente recursos do que foi apresentado neste curso.
    Com exceção da classe LocalDateTime e Math do java e a criação de metodos para organização do condigo que podem ser usados
 */

/**
 * Author Wenceslau Neto
 */
public class Estacionamento {

    public static String logsSaidas = "";

    public static void main(String[] args) {

        //Se a capacidade do estacionamento não for informada, usar o valor padrão
        int capacidade = 5;
        if (args.length != 0){
            capacidade = Integer.parseInt(args[0]);
        }

        //Declara as variaveis a serem usadas no sistema
        //String placas[] = new String[capacidade];
        //LocalDateTime tempos[] = new LocalDateTime[capacidade];

        Object dados[][] = new Object[capacidade][3];

        Scanner ler = new Scanner(System.in);

        String option;
        do {
            System.out.println("===========================================================================");
            System.out.println("Opções");
            System.out.println("\t 1 - Adicionar número da placa");
            System.out.println("\t 0 - Sair do programa");
            option = ler.next();

            switch (option){
                case "1":
                    System.out.println("Informe o número de placa");
                    String placa = ler.next();
                    programa(placa,dados);
                    break;
                case "0":
                    System.exit(0);
                default:
                    System.out.println("Informe uma opção valida");
            }

        } while (!option.equals("0"));
    }

    public static void programa(String placa, Object[][] dados) {

        //verifica se a placa ja existe, se, existir o processo é de saida
        int posicao = verificarPlaca(placa, dados);

        //igual a -1, placa nao existe
        if (posicao == -1) {
            entradaVeiculo(placa, dados);

        } else {
            saidaVeiculo(posicao,  dados);

        }
        imprimirRelatorio( dados);

    }

    private static int verificarPlaca(String placaEntrada, Object[][] dados) {
        //percorre todo array de placa ate achar a placa de entrada
        for (int i = 0; i < dados.length; i++) {
            Object object = dados[i][0];
            if (object instanceof String) {
                String placa = (String) object;
                if (placa != null) {
                    if (placa.equals(placaEntrada)) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    private static void entradaVeiculo(String placa, Object[][] dados) {

        int posicaoLivre = posicaoLivre(dados);

        //Vericia se o estacionamento ja esta lotado, se a posicao for -1 na ha vagas
        if (posicaoLivre == -1) {
            System.err.println("Não ha vagas");
        } else {
            //Hora da entrada e a posiçao livre do array
            LocalDateTime entrada = LocalDateTime.now();

            //Guarda a placa e a hora entrada no array
            dados[posicaoLivre][0] = placa;
            dados[posicaoLivre][1] = entrada;

            System.out.printf("Entrada do veículo de placa: %s realizada.%n", placa);
        }
    }

    private static int posicaoLivre(Object[][] dados) {
        //percorre todo array verifica qual a primeira posicao livre do array
        //se nao tiver posicao livre retorna -1, indica estacioamento lotado
        for (int i = 0; i < dados.length; i++) {
            String placa = (String) dados[i][0];
            if (placa == null) {
                return i;
            }
        }
        return -1;
    }

    private static void saidaVeiculo(int posicao, Object[][] dados) {

        String placa = (String) dados[posicao][0];

        Object valor = dados[posicao][1];
        LocalDateTime entrada = null;
        if (valor instanceof LocalDateTime){
            entrada = (LocalDateTime) valor;
        }
        if (entrada == null){
            System.out.println("Não foi econtrada a hora de entrada do veiculo");
            return;
        }

        LocalDateTime saida = LocalDateTime.now();

        //Calcula o tempo em minutos que o veiculo permaneceu, e o valor a pagar
        long minutos = Duration.between(entrada, saida).toMinutes();
        double valorPagar = calculaValorAPagar(minutos);

        System.out.printf("Saída do veículo placa %s. Tempo no estacionamento %s minutos. Valor a ser cobrado: %.2f %n",
                placa, minutos, valorPagar );

        //String para o log de saidas
        String historico = String.format("\tPlaca %s - tempo permanência: %d minutos - valor cobrado: %.2f %n", placa,
                minutos, valorPagar);

        logsSaidas += historico;
        dados[posicao][0] = null;
        dados[posicao][1] = null;

    }

    private static void imprimirRelatorio(Object[][] dados) {
        System.out.println("_______________________________________________________________________________");
        System.out.println("Veículos no estacionamento: ");

        //Percorre o array para mostrar os veiculos no estacionamento
        for (int i = 0; i < dados.length; i++) {
            String placa = (String) dados[i][0];
            if (placa == null){
                continue;
            }
            LocalDateTime dataHoraEntrada = (LocalDateTime) dados[i][1];
            String dataFormatada = dataHoraEntrada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            System.out.printf("\tPlaca %s \t Hora de entrada: %s %n", placa, dataFormatada);
        }

        //Mostra o log de saidas
        System.out.println();
        System.out.println("Relatorio de Saidas: ");
        System.out.printf("%s %n", logsSaidas);
    }

    private static double calculaValorAPagar(long tempoMinutos){

//        • De 0 a 5 minutos. Sem cobrança de valor
//        • De 5 a 60 minutos. R$ 4,00
//        • Acima de 60 minutos é cobrado um valor de R$ 6,00 por hora adicional.
//        • Sse ficou 1 hora e 1 minuto paga 4 pela primeira hora mais 6 pela hora adicional

        if (tempoMinutos <= 5){
            return 0.0;
        }else if (tempoMinutos > 5 && tempoMinutos <= 60 ){
            return 4.0;
        }else {
            //Valor de uma hora
            double aPagar = 4.0;

            //Divide o tempo em minutos para saber o valor em horas
            // 125/60 = 2.08, entao foram 2.08 horas de permanencia

            // 1 hora = 4 reais
            // 1 hora = 6 reais
            // 0.8 hora = 6 reais

            //Horas em valor decimal
            double horas = (tempoMinutos /60.0);

            //Decrementa a primeira hora que tem o valor fixo de 4 reais
            horas--;

            //Arredonta o valor para cima
            horas = Math.ceil(horas);

            //Multiplica o valor pelo número de horas adicionais
            aPagar = aPagar + (horas * 6.0);
            return aPagar;
        }
    }
}