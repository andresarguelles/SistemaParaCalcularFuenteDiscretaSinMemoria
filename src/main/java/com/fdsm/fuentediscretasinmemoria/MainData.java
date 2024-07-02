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
    public static String direccion;


    public static void calculaCoincidenciaDeCadaSimbolo(String mensaje) {
        for (int i = 0; i < mensaje.length(); i++) {
            char letra = mensaje.charAt(i);
            registroDeSimbolos.put(letra, registroDeSimbolos.getOrDefault(letra, 0) + 1);
        }
    }

    public static void calcularProbabilidadDeCadaSimbolo() {
        for (Map.Entry<Character, Integer> entry : registroDeSimbolos.entrySet()) {
            listaDeSimbolos.add(new Simbolo(entry.getKey(), entry.getValue(), (double) entry.getValue() / totalSimbolos));
            registroDeProbabilidadDeSimbolos.put(entry.getKey(), (double) entry.getValue() / totalSimbolos);
        }
    }

    public static void calcularEntropia() {
        for (Map.Entry<Character, Double> entry : registroDeProbabilidadDeSimbolos.entrySet()) {
            double probabilidad = entry.getValue();
            entropia += probabilidad * (Math.log(1/probabilidad) / Math.log(2));
        }
    }

    public static void calcularInformacionTotal(){
        for (Map.Entry<Character, Double> entry : registroDeProbabilidadDeSimbolos.entrySet()) {
            double probabilidad = entry.getValue();
            informacionTotal += Math.log(1/probabilidad) / Math.log(2);
        }
    }

    public static void calcularRedundancia(){
        entropiaMaxima = Math.log(totalSimbolos) / Math.log(2);
        redundancia = 1 - (entropia/ entropiaMaxima);
        redundancia *=100;
    }

    public static void calcularEficiencia(){
        eficiencia = (entropia / entropiaMaxima) * 100;
    }

    public static void borrarDatos(){
        listaDeSimbolos.clear();
        registroDeSimbolos.clear();
        registroDeProbabilidadDeSimbolos.clear();
        totalSimbolos = 0;
        entropia = 0;
        informacionTotal = 0;
        redundancia = 0;
        eficiencia = 0;
    }


    public static List<Simbolo> obtenerDatos() {
        borrarDatos();
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

                totalSimbolos = mensaje.length();
                calculaCoincidenciaDeCadaSimbolo(mensaje.toString());
                calcularProbabilidadDeCadaSimbolo();
                calcularEntropia();
                calcularInformacionTotal();
                calcularRedundancia();
                calcularEficiencia();
            } else {
                return null;
            }
            scanner.close();
            return listaDeSimbolos;
        } catch (Exception e) {
            System.out.println("Ocurrió un error al leer el archivo: " + e.getMessage());
        }
        return null;
    }
}