import java.util.*;

public class Calculadora {

    // Avalia expressão em notação pós-fixa (tokens separados por espaço)
    public static int avaliarPosfixa(String expr) {
        Stack<Integer> pilha = new Stack<>();
        String[] tokens = expr.trim().split("\\s+");
        for (String token : tokens) {
            if (token.matches("\\d+")) {
                pilha.push(Integer.parseInt(token));
            } 
            else if (token.length() == 1 && "+-*/".contains(token)) {
                if (pilha.size() < 2) throw new IllegalArgumentException("Expressão inválida");
                int b = pilha.pop();
                int a = pilha.pop();
                switch (token.charAt(0)) {
                    case '+': pilha.push(a + b); break;
                    case '-': pilha.push(a - b); break;
                    case '*': pilha.push(a * b); break;
                    case '/': pilha.push(a / b); break;
                }
            } 
            else {
                throw new IllegalArgumentException("Token inválido: " + token);
            }
        }
        if (pilha.size() != 1) throw new IllegalArgumentException("Expressão inválida");
        return pilha.pop();
    }

    // Converte infixa para pós-fixa (tokens separados por espaço)
    public static String infixaParaPosfixa(String expr) {
        StringBuilder resultado = new StringBuilder();
        Stack<Character> pilha = new Stack<>();
        Map<Character, Integer> precedencia = Map.of('+', 1, '-', 1, '*', 2, '/', 2);

        for (int i = 0; i < expr.length(); ) {
            char c = expr.charAt(i);
            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }
            if (Character.isDigit(c)) {
                StringBuilder num = new StringBuilder();
                while (i < expr.length() && Character.isDigit(expr.charAt(i))) {
                    num.append(expr.charAt(i++));
                }
                resultado.append(num).append(' ');
            } else if (c == '(') {
                pilha.push(c);
                i++;
            } 
            else if (c == ')') {
                while (!pilha.isEmpty() && pilha.peek() != '(') {
                    resultado.append(pilha.pop()).append(' ');
                }
                if (!pilha.isEmpty()) pilha.pop();
                i++;
            } 
            else if ("+-*/".indexOf(c) != -1) {
                while (!pilha.isEmpty() && pilha.peek() != '(' &&
                        precedencia.get(c) <= precedencia.getOrDefault(pilha.peek(), 0)) {
                    resultado.append(pilha.pop()).append(' ');
                }
                pilha.push(c);
                i++;
            } 
            else {
                throw new IllegalArgumentException("Caractere inválido: " + c);
            }
        }
        while (!pilha.isEmpty()) {
            resultado.append(pilha.pop()).append(' ');
        }
        return resultado.toString().trim();
    }

    // Converte infixa para pré-fixa (tokens separados por espaço)
    public static String infixaParaPrefixa(String expr) {
        // Inverte expressão, troca parênteses e aplica pós-fixa
        StringBuilder invertida = new StringBuilder();
        for (int i = expr.length() - 1; i >= 0; i--) {
            char c = expr.charAt(i);
            if (c == '(') invertida.append(')');
            else if (c == ')') invertida.append('(');
            else invertida.append(c);
        }
        String posfixa = infixaParaPosfixa(invertida.toString());
        // Inverte pós-fixa para obter pré-fixa
        List<String> tokens = Arrays.asList(posfixa.split("\\s+"));
        Collections.reverse(tokens);
        return String.join(" ", tokens);
    }
}