package com.fdsm.fuentediscretasinmemoria;
import java.io.File;
import java.util.*;

public class MainData {
    public static List<Simbolo> listaDeSimbolos = new ArrayList<>();
    public static Map<Character, Integer> registroDeSimbolos = new HashMap<>();
    public static Map<Character, Double> registroDeProbabilidadDeSimbolos = new HashMap<>();
    public static int totalSimbolos = 0;
    public static double entropia = 0;
    public static double entropiaMaxima = 0;
    public static double informacionTotal = 0;
    public static double redundancia = 0;
    public static double eficiencia = 0;
    public static String direccion = "src\\main\\java\\com\\fdsm\\fuentediscretasinmemoria\\doc\\Prueba.txt";


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
            listaDeSimbolos.add(new Simbolo(entry.getKey(), entry.getValue(), (double) entry.getValue() / totalSimbolos));
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


    public static List<Simbolo> obtenerDatos() {
        listaDeSimbolos.clear();
        registroDeSimbolos.clear();
        registroDeProbabilidadDeSimbolos.clear();
        totalSimbolos = 0;
        entropia = 0;
        informacionTotal = 0;
        redundancia = 0;
        eficiencia = 0;

        try {
            File file = new File(direccion);
            Scanner scanner = new Scanner(file);
            System.out.println("Leyendo el mensaje desde el archivo...");
            StringBuilder mensaje = new StringBuilder();

            if (scanner.hasNextLine()) {
                while (scanner.hasNextLine()) {
                    mensaje.append(scanner.nextLine());
                    mensaje.append(" ");
                }
                mensaje.deleteCharAt(mensaje.length() - 1);
                System.out.println("Mensaje: " + mensaje);
                totalSimbolos = mensaje.length();
                calculaCoincidenciaDeCadaSimbolo(mensaje.toString());
                calcularProbabilidadDeCadaSimbolo();
                System.out.println("\n\nTotal de símbolos: " + totalSimbolos);
                calcularEntropia();
                calcularInformacionTotal();
                calcularRedundancia();
                calcularEficiencia();

            } else {
                System.out.println("El archivo está vacío.");
            }
            scanner.close();
            return listaDeSimbolos;
        } catch (Exception e) {
            System.out.println("Ocurrió un error al leer el archivo: " + e.getMessage());
        }
        return null;
    }
}