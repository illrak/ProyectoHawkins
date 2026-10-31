/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.proyectohawkins.proyectohawkins;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author ilia
 */

public class ProyectoHawkins {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        final double MAX_ENERGIA = 200.0;
        final double LIMITE_RIESGO = 150.0;
        String operadorActual = "";
        double sensorMedido = 0.0;
        int codigoRiesgo = 0;
        int porcentajeEstado = 0;
        boolean portalAbierto = false;
        int opcionMenu = 0;
        System.out.println("Escribe su nombre");
        operadorActual = sc.nextLine();
        mostrarEncabezado(operadorActual);
        do {            
            System.out.println("1. Inspeccionar Sensor Individual");
            System.out.println("2. Iniciar Muestro Continuo");
            System.out.println("0. Salir de la Consola");
            System.out.println("\nSelecciona una opcion: ");
            opcionMenu = sc.nextInt();
            switch (opcionMenu) {
                case 1:
                    sensorMedido = leerLecturaSegura(sc, "Introduce lectura µT: ");
                    codigoRiesgo = evaluarNivelRiesgo(sensorMedido);
                    porcentajeEstado = calcularPorcentajeTruncado(sensorMedido, MAX_ENERGIA);
                    portalAbierto = ejecutarProtocoloAccion(codigoRiesgo);
                    System.out.println("Porcentaje de energia: " + porcentajeEstado + "%");
                    System.out.println("Portal abierto?: " + portalAbierto);
                    break;
                case 2:
                    simularMuestreoContinuo(sc, LIMITE_RIESGO);
                    break;
                case 0:
                    System.out.println("Cerrando sesion...");
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        } while (opcionMenu != 0);
    }
    public static void mostrarEncabezado(String operador) {
        System.out.printf("\n\t====================================\n");
        System.out.printf("\t       SISTEMA HAWKINS\n");
        System.out.printf("\t====================================\n");
        System.out.printf("\tOperador: %s\n", operador);
    }
    public static double leerLecturaSegura(Scanner sc, String mensaje) {
        double lectura = 0;
        boolean correcto = false;
        while (!correcto) {            
            try {
                System.out.print(mensaje);
                lectura = sc.nextDouble();
                correcto = true;
            } catch (InputMismatchException e) {
                System.out.println("Error. Introduce un numero valido");
                sc.nextLine();
            }
        }
        return lectura;
    }
    public static int calcularPorcentajeTruncado(double lectura, double maxEnergia) {
        double porcentaje = (lectura / maxEnergia) * 100;
        return (int) porcentaje;
    }
    public static void simularMuestreoContinuo(Scanner sc, double limite) {
        double lectura;
        double suma = 0;
        int contador = 0;
        while (true) {            
            lectura = leerLecturaSegura(sc,"Introduce lectura (negativo para salir: )");
            if (lectura < 0) {
                break;
            }
            if (lectura == 0) {
                continue;
            }
            if (lectura > limite) {
                System.out.println("Advertencia! Se ha superado el limite");
            }
            suma += lectura;
            contador++;
        }
        if (contador > 0) {
            double media = suma / contador;
            System.out.printf("Media de lecturas: %.2f µT%n", media);
        } else {
            System.out.println("No hay lecturas validas para calcular la media");
        }
    }
    public static boolean ejecutarProtocoloAccion(int codigoRiesgo) {
        switch (codigoRiesgo) {
            case 1:
                System.out.println("Modo Estable");
                return false;
            case 2:
                System.out.println("Anomalia Detectada");
                return false;
            case 3:
                System.out.println("ALERTA MAXIMA!");
                return true;
            default:
                System.out.println("Codigo no valido");
                return false;
        }
    }
    public static int evaluarNivelRiesgo(double microTeslas) {
        String etiqueta = (microTeslas < 50.0) ? "Seguro" :
                (microTeslas <= 150.0) ? "Alerta" : "Critico";
        System.out.println("Nivel de riesgo: " + etiqueta);
        if (microTeslas < 50.0) {
            return 1;
        } else if (microTeslas <= 150.0) {
            return 2;
        } else {
            return 3;
        }
    }
}