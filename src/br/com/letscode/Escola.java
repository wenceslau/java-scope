package br.com.letscode;

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

    public static void main(String[] args) {

        Scanner ler = new Scanner(System.in);
        String nomeAluno;
        String tipoEscola;
        int[] notasAtividades;
        int notaProva;
        int notaParticipacao;

        nomeAluno = lerNomeAluno(ler);
        tipoEscola = lerTipoEscola(ler);
        notasAtividades  = lerNotasAtividades(ler);
        notaProva = lerNotaProva(ler);
        notaParticipacao = lerNotaParticipacao(ler);

        TipoEscola tipo = TipoEscola.valueOf(tipoEscola);

        int somaNotas = notaProva + notaParticipacao;
        for (int notas : notasAtividades){
            somaNotas = somaNotas +  notas;
        }

        boolean aprovado = verificarAprovacao(tipo, somaNotas);

        if (aprovado) {
            System.out.printf("O Aluno %s foi APROVADO. Total de pontos %d", nomeAluno, somaNotas);
        }else{
            System.out.printf("O Aluno %s foi REPROVADO. Total de pontos %d", nomeAluno, somaNotas);
        }
    }

    private static boolean verificarAprovacao(TipoEscola tipo, int somaNotas) {
        if (somaNotas >= tipo.getMedia()){
            return true;
        }
        return false;
    }

    private static int  lerNotaParticipacao(Scanner ler) {
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

    private static int  lerNotaProva(Scanner ler) {
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

    private static int[] lerNotasAtividades(Scanner ler) {
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

    public static String lerNomeAluno(Scanner sc){
        System.out.println("Nome do aluno: ");
        String nomeAluno = sc.next();
        return  nomeAluno;
    }

    public static String lerTipoEscola(Scanner ler){
        System.out.println("Escola: ");
        String tipoEscola =  ler.next();
        switch (tipoEscola){
            case "PU" :
            case "PR" :
            case "MT" :
                break;
            default:
                System.out.println("A escola do tipo "+tipoEscola+" não existe.");
                System.exit(0);
        }
        return  tipoEscola;
    }
}
