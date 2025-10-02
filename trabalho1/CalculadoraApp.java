import java.util.Scanner;

public class CalculadoraApp {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("=== CALCULADORA POLONESA ===");
            System.out.println("Escolha a notação da entrada:");
            System.out.println("1 - Infixa (ex: (5 + 9) * 2 + 6 * 5)");
            System.out.println("2 - Pós-fixa / RPN (ex: 5 9 + 2 * 6 5 * +)");
            System.out.println("3 - Pré-fixa (ex: + * + 5 9 2 * 6 5)");
            int opcao = sc.nextInt();
            sc.nextLine();

            System.out.println("Digite a expressão (separe tokens por espaço):");
            String expr = sc.nextLine().trim();

            String infixa = "";
            String posfixa = "";
            String prefixa = "";
            int resultado = 0;

            try {
                switch (opcao) {
                    case 1: // Infixa
                        infixa = expr;
                        posfixa = Calculadora.infixaParaPosfixa(expr.replaceAll(" ", ""));
                        prefixa = Calculadora.infixaParaPrefixa(expr.replaceAll(" ", ""));
                        resultado = Calculadora.avaliarPosfixa(posfixa);
                        break;
                    case 2: // Pós-fixa
                        posfixa = expr;
                        resultado = Calculadora.avaliarPosfixa(expr);
                        break;
                    case 3: // Pré-fixa
                        prefixa = expr;
                        System.out.println("Avaliação direta de prefixa não implementada.");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                        return;
                }

                System.out.println("\n--- RESULTADOS ---");
                if (!infixa.isEmpty()) System.out.println("Infixa  : " + infixa);
                if (!posfixa.isEmpty()) System.out.println("Pós-fixa: " + posfixa);
                if (!prefixa.isEmpty()) System.out.println("Pré-fixa: " + prefixa);
                System.out.println("Resultado = " + resultado);
            } 
            catch (Exception e) 
            {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }
}
