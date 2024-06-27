import java.util.*;

public class App {
    public static Map<Character, Integer> registroDeSimbolos = new HashMap<>();
    public static Map<Character, Double> registroDeProbabilidadDeSimbolos = new HashMap<>();
    public static int totalSimbolos = 0;
    public static double entropia = 0;
    public static double entropiaMaxima = 0;
    public static double informacionTotal = 0;
    public static double redundancia = 0;
    public static double eficiencia = 0;


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
        for (Map.Entry<Character, Double> entry : registroDeProbabilidadDeSimbolos.entrySet()) {
            double probabilidad = entry.getValue();
            entropia += probabilidad * (Math.log(1/probabilidad) / Math.log(2));
        }
        System.out.println("La entropía es: " + String.format("%.3f", entropia) + " bits/símbolo");
    }

    public static void calcularInformacionTotal(){
        for (Map.Entry<Character, Double> entry : registroDeProbabilidadDeSimbolos.entrySet()) {
            double probabilidad = entry.getValue();
            informacionTotal += Math.log(1/probabilidad) / Math.log(2);
        }
        System.out.println("La información total es: " + String.format("%.3f", informacionTotal) + " bits");
    }

    public static void calcularRedundancia(){
        entropiaMaxima = Math.log(totalSimbolos) / Math.log(2);
        redundancia = 1 - (entropia/ entropiaMaxima);
        redundancia *=100;
        System.out.println("La redundancia es: " + String.format("%.3f", redundancia) + "%" );
    }

    public static void calcularEficiencia(){
        eficiencia = (entropia / entropiaMaxima) * 100;
        System.out.println("La eficiencia es: " + String.format("%.3f", eficiencia) + "%");
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
            calcularInformacionTotal();
            calcularRedundancia();
            calcularEficiencia();
        } catch (Exception e) {
            System.out.println("No ingresaste ningún mensaje.");
        }

    }
}