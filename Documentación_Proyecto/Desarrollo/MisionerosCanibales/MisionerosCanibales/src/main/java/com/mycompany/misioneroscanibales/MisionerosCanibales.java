/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.misioneroscanibales;

/**
 *
 * @author Joffre
 */
import java.util.Scanner;

public class MisionerosCanibales {

    static class Estado {
        int misionerosIzq, canibalesIzq, misionerosDer, canibalesDer;
        String bote; // Puede estar en "izquierda" o "derecha"

        Estado(int mIzq, int cIzq, int mDer, int cDer, String bote) {
            this.misionerosIzq = mIzq;
            this.canibalesIzq = cIzq;
            this.misionerosDer = mDer;
            this.canibalesDer = cDer;
            this.bote = bote;
        }

        void mostrarEstado() {
            System.out.println("Izquierda: M=" + misionerosIzq + ", C=" + canibalesIzq +
                    " | Derecha: M=" + misionerosDer + ", C=" + canibalesDer +
                    " | Bote: " + bote);
        }

        boolean esValido() {
            if (misionerosIzq < 0 || canibalesIzq < 0 || misionerosDer < 0 || canibalesDer < 0) {
                return false;
            }
            if ((misionerosIzq > 0 && misionerosIzq < canibalesIzq) || (misionerosDer > 0 && misionerosDer < canibalesDer)) {
                return false; // Caníbales superan a los misioneros
            }
            return true;
        }

        boolean esObjetivo() {
            return misionerosIzq == 0 && canibalesIzq == 0 && misionerosDer == 3 && canibalesDer == 3;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Estado estado = new Estado(3, 3, 0, 0, "izquierda");

        System.out.println("Estado inicial:");
        estado.mostrarEstado();

        while (!estado.esObjetivo()) {
            System.out.println("Ingrese el numero de misioneros y canibales a mover:");
            System.out.print("Misioneros: ");
            int misioneros = scanner.nextInt();

            System.out.print("Canibales: ");
            int canibales = scanner.nextInt();

            if (misioneros + canibales > 2 || misioneros + canibales <= 0) {
                System.out.println("Movimiento invalido. Solo pueden cruzar 1 o 2 personas.");
                continue;
            }

            if (estado.bote.equals("izquierda")) {
                estado.misionerosIzq -= misioneros;
                estado.canibalesIzq -= canibales;
                estado.misionerosDer += misioneros;
                estado.canibalesDer += canibales;
                estado.bote = "derecha";
            } else {
                estado.misionerosDer -= misioneros;
                estado.canibalesDer -= canibales;
                estado.misionerosIzq += misioneros;
                estado.canibalesIzq += canibales;
                estado.bote = "izquierda";
            }

            if (!estado.esValido()) {
                System.out.println("Movimiento invalido. Los canibales superan a los misioneros en alguna orilla.");
                // Deshacer movimiento
                if (estado.bote.equals("derecha")) {
                    estado.misionerosIzq += misioneros;
                    estado.canibalesIzq += canibales;
                    estado.misionerosDer -= misioneros;
                    estado.canibalesDer -= canibales;
                    estado.bote = "izquierda";
                } else {
                    estado.misionerosDer += misioneros;
                    estado.canibalesDer += canibales;
                    estado.misionerosIzq -= misioneros;
                    estado.canibalesIzq -= canibales;
                    estado.bote = "derecha";
                }
            } else {
                estado.mostrarEstado();
            }

            if (estado.esObjetivo()) {
                System.out.println("¡¡¡FELICIDADES!!! Todos han cruzado el rio de manera segura.");
                break;
            }
        }

        scanner.close();
    }
}

