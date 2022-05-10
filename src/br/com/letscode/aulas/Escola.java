package br.com.letscode.aulas;

import java.io.*;
import java.util.Scanner;

public class Escola {

    /*
        Você deve contruir um algoritimo

        que deva receber o nome do aluno:
        deve receber o tipo da escola do aluno: Ex: PU (publica), PR (privada) MT (militar)
        OBS: O valor a ser informado pelo usuario deve ser a sigla PU, PR ou MT

        deve receber 5 notas
        Nota atividade 1
        Nota atividade 2
        Nota atividade 3
        Nota Prova
        Nota Participação

        o valor da nota das atividade 1, 2 e 3 não podem ser maior que 10
        o valor da nota da prova não pode ser maior que 50
        o valor da nota de participação não pode ser maior que 20

        Para escolas Publicas é necessario 65% de pontos para ser aprovado
        Para escolas Privadas é necessario 70% de pontos para ser aprovado
        Para escolas Milirares é necessario 80% de pontos para ser aprovado

        E se a nota de participação for menor 5 independente das demais notas o aluno esta reprovado


        O sistema deve realizar a somatoria das notas identificar o tipo da escola
        e mostrar a mensagem
        "O Aluno XXXX foi APROVADO. Total de pontos: YY
           ou
        "O Aluno XXXX foi REPROVADO. Total de pontos: YY

     */

    public static void main(String[] args) throws IOException {

        boolean tudoOk = false;

        lerArquivo();

        try {

            Scanner ler = new Scanner(System.in);

            String nomeAluno;
            TipoEscola tipoEscola = null;
            int[] notasAtividades;
            int notaProva;
            int notaParticipacao = 0;

            if (notaParticipacao == 0){
                return;
            }

            nomeAluno = lerNomeAluno(ler);
            try {
                tipoEscola = lerTipoEscola(ler);
            } catch (Exception exception) {
                System.err.println("Ocorreu um erro");
                System.exit(1);
            }
            notasAtividades = lerNotasAtividades(ler);
            notaProva = lerNotaProva(ler);
            notaParticipacao = lerNotaParticipacao(ler);


            int somaNotas = notaProva + notaParticipacao;
            for (int notas : notasAtividades) {
                somaNotas = somaNotas + notas;
            }

            boolean aprovado = TipoEscola.verificarAprovacao(tipoEscola, somaNotas);

            if (aprovado) {
                System.out.printf("O Aluno %s foi APROVADO. Total de pontos %d", nomeAluno, somaNotas);
            } else {
                System.out.printf("O Aluno %s foi REPROVADO. Total de pontos %d", nomeAluno, somaNotas);
            }

            tudoOk = true;
            //envia email

        }catch (IOException exception){

            System.out.println(exception.getMessage());

            tudoOk = false;

            return;

        } finally {

        }

        //envia email
    }

    private static int  lerNotaParticipacao(Scanner ler) throws IOException {
        int notaParticipacao;
        System.out.println("Nota Participação: ");
        do {
            notaParticipacao = ler.nextInt();
            if (notaParticipacao > 20){
                System.out.println("A nota de participação não pode ser maior que 20: Informe novamente: ");
            }
        }while (notaParticipacao > 20);
        return notaParticipacao;
    }

    private static int  lerNotaProva(Scanner ler) throws IOException {
        int notaProva;
        System.out.println("Nota Prova: ");
        do {
            notaProva = ler.nextInt();
            if (notaProva > 50){
                System.out.println("A nota da prova não pode ser maior que 50: Informe novamente: ");
            }
        }while (notaProva > 50);
        return notaProva;
    }

    private static int[] lerNotasAtividades(Scanner ler) throws IOException {
        int[] notasAtividades = new int[3];
        for (int i = 0; i < notasAtividades.length; i++) {
            System.out.println("Nota atividade " + (i  + 1) +":");
            int nota;
            do {
                nota = ler.nextInt();
                if (nota > 10){
                    System.out.println("A nota da atividade "+(i+1)+" não pode ser maior que 10. Informe novamente: ");
                }
            }while (nota > 10);
            notasAtividades[i] = nota;
        }
        return notasAtividades;
    }

    public static String lerNomeAluno(Scanner sc) throws IOException{
        System.out.println("Nome do aluno: ");
        String nomeAluno = sc.next();
        return  nomeAluno;
    }

    public static TipoEscola lerTipoEscola(Scanner ler) throws IOException{

        for (TipoEscola value : TipoEscola.values()) {
            System.out.print(value + "-" +value.getDescricao() + ", ");
        }
        System.out.println();
        System.out.println("Escolha um tipo de escola: ");

        TipoEscola tipo = null;
        do {
            try {
                String tipoEscola = ler.next();
                tipo = TipoEscola.valueOf(tipoEscola);
                int num = 1;
                int num2 = 0;
                int num3 = num/num2;

            } catch (Exception exception) {
                if (exception instanceof ArithmeticException){
                    System.err.println("Erro de divisao");
                } else if (exception instanceof IllegalArgumentException){
                    System.err.println("Erro de enum");
                }else{
                    System.err.println("Erro de desconhecido");
                }
                throw exception;
            }
        }
        while (tipo == null);

        return  tipo;
    }

    public static void lerArquivo() throws IOException {

        try(BufferedReader reader = new BufferedReader(new FileReader("teste.txt"));
            PrintWriter writer = new PrintWriter(new File("testWrite.txt"))){
            String conteudo = reader.readLine();
            int numero = Integer.parseInt(conteudo);
            writer.println(numero + 10);
            System.out.println(numero);
        }


//        try {
//            reader = new BufferedReader(new FileReader("teste.txt"));
//            String conteudo = reader.readLine();
//            int numero = Integer.parseInt(conteudo);
//            System.out.println(numero);
//        }finally {
//            reader.close();
//        }

    }
}
