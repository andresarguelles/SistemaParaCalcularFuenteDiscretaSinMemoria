import java.util.*;

public class Main {
    public static Map<Character, Integer> registroDeSimbolos = new HashMap<>();
    public static Map<Character, Double> registroDeProbabilidadDeSimbolos = new HashMap<>();
    public static int totalSimbolos = 0;

    public static void calculaCoincidenciaDeCadaSimbolo(String mensaje) {
        for (int i = 0; i < mensaje.length(); i++) {
            char letra = mensaje.charAt(i);
            registroDeSimbolos.put(letra, registroDeSimbolos.getOrDefault(letra, 0) + 1);
        }
        System.out.println("\nRegistro de símbolos\n");
        System.out.println(registroDeSimbolos);
    }

    public static void calcularProbabilidadDeCadaSimbolo() {
        for (Map.Entry<Character, Integer> entry : registroDeSimbolos.entrySet()) {
            registroDeProbabilidadDeSimbolos.put(entry.getKey(), (double) entry.getValue() / totalSimbolos);
        }
        System.out.println("\nRegistro de probabilidad de símbolos\n");
        System.out.print("{");
        int contadorDeLineas = 0;
        for (Map.Entry<Character, Double> entry : registroDeProbabilidadDeSimbolos.entrySet()) {
            System.out.print(entry.getKey() + "=" + String.format("%.3f", entry.getValue())+ ", ");
            if(++contadorDeLineas % 12 == 0)
                System.out.println();
        }
        System.out.print("\b\b}");
    }

    public static void calcularEntropia() {
        double entropia = 0;
        for (Map.Entry<Character, Double> entry : registroDeProbabilidadDeSimbolos.entrySet()) {
            double probabilidad = entry.getValue();
            entropia += probabilidad * (Math.log(probabilidad) / Math.log(2));
        }
        entropia *= -1;
        System.out.println("La entropía es: " + entropia + " bits/símbolo");
    }

    public static void main(String[] args) {
        // Verificar si el usuario ingresó algo
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Ingresa el mensaje: ");
            String mensaje;
            mensaje = scanner.nextLine();
            totalSimbolos = mensaje.length();
            calculaCoincidenciaDeCadaSimbolo(mensaje);
            calcularProbabilidadDeCadaSimbolo();
            System.out.println("\n\nTotal de símbolos: " + totalSimbolos);
            calcularEntropia();
        } catch (Exception e) {
            System.out.println("No ingresaste ningún mensaje.");
        }

    }
}