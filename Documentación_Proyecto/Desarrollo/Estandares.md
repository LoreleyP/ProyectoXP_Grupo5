# **Estándares de Desarrollo de Software para el Proyecto: Misioneros y Caníbales**

---

## **1. Propósito**

Establecer las normas básicas para mantener un código claro, comprensible y fácil de mantener dentro de un proyecto con una única clase. Esto incluye buenas prácticas para nombres de variables, estructuras de control, comentarios y manejo de errores.

---

## **2. Estándares para la Clase `MisionerosCanibales`**

### **2.1 Nombres de Clases y Métodos**
1. La clase debe seguir el formato **UpperCamelCase**.  
   - Ejemplo: `MisionerosCanibales`.

2. Los métodos deben usar **lowerCamelCase**, describiendo su propósito de manera clara.  
   - Ejemplo: 
     - `mostrarEstado()` para mostrar el estado actual.  
     - `esValido()` para verificar si un movimiento es válido.

---

### **2.2 Atributos y Variables**
1. Los atributos deben ser **privados** y utilizar **lowerCamelCase**.  
   - Ejemplo:
     ```java
     private int misionerosIzq;
     private int canibalesIzq;
     ```

2. Usar nombres descriptivos para variables y evitar abreviaturas.  
   - Ejemplo:
     - `misionerosIzquierda` es preferible a `mIzq`.

---

### **2.3 Métodos y Responsabilidades**
1. Cada método debe realizar una única tarea bien definida.  
   - Ejemplo:
     - `esValido()` debe enfocarse solo en validar estados.  
     - `esObjetivo()` solo debe verificar si el estado actual es el final.

2. Evitar métodos largos. Si un método crece demasiado, dividirlo en submétodos.

---

### **2.4 Manejo de Errores y Validaciones**
1. Validar las entradas del usuario y mostrar mensajes claros si estas son inválidas.  
   - Ejemplo:
     ```java
     if (misioneros + canibales > 2 || misioneros + canibales <= 0) {
         System.out.println("Movimiento inválido. Solo pueden cruzar 1 o 2 personas.");
         continue;
     }
     ```

2. Implementar lógica para revertir cambios si el estado no es válido.

---

### **2.5 Comentarios y Documentación**
1. Documentar clases, métodos y secciones importantes del código con comentarios claros.  
   - Ejemplo:
     ```java
     /**
      * Verifica si el estado actual es válido según las reglas del juego.
      * 
      * @return true si el estado es válido, false en caso contrario.
      */
     boolean esValido() {
         // Validaciones de los estados
     }
     ```

2. Usar comentarios en línea solo para aclarar partes complejas del código.

---

## **3. Ejemplo del Código Ajustado a los Estándares**

```java
package com.mycompany.misioneroscanibales;

import java.util.Scanner;

/**
 * Clase principal para resolver el problema de los Misioneros y Caníbales.
 * Simula el movimiento de personas entre orillas según las reglas establecidas.
 * 
 * @author Joffre
 */
public class MisionerosCanibales {

    static class Estado {
        private int misionerosIzq;
        private int canibalesIzq;
        private int misionerosDer;
        private int canibalesDer;
        private String bote; // Posición del bote: "izquierda" o "derecha"

        /**
         * Constructor del estado inicial.
         */
        public Estado(int mIzq, int cIzq, int mDer, int cDer, String bote) {
            this.misionerosIzq = mIzq;
            this.canibalesIzq = cIzq;
            this.misionerosDer = mDer;
            this.canibalesDer = cDer;
            this.bote = bote;
        }

        /**
         * Muestra el estado actual del juego.
         */
        public void mostrarEstado() {
            System.out.println("Izquierda: M=" + misionerosIzq + ", C=" + canibalesIzq +
                    " | Derecha: M=" + misionerosDer + ", C=" + canibalesDer +
                    " | Bote: " + bote);
        }

        /**
         * Verifica si el estado es válido según las reglas del juego.
         * 
         * @return true si el estado es válido, false en caso contrario.
         */
        public boolean esValido() {
            if (misionerosIzq < 0 || canibalesIzq < 0 || misionerosDer < 0 || canibalesDer < 0) {
                return false;
            }
            if ((misionerosIzq > 0 && misionerosIzq < canibalesIzq) || 
                (misionerosDer > 0 && misionerosDer < canibalesDer)) {
                return false;
            }
            return true;
        }

        /**
         * Verifica si el estado es el objetivo final.
         * 
         * @return true si todos los misioneros y caníbales están en la derecha.
         */
        public boolean esObjetivo() {
            return misionerosIzq == 0 && canibalesIzq == 0 && misionerosDer == 3 && canibalesDer == 3;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Estado estado = new Estado(3, 3, 0, 0, "izquierda");

        System.out.println("Estado inicial:");
        estado.mostrarEstado();

        while (!estado.esObjetivo()) {
            System.out.println("Ingrese el número de misioneros y caníbales a mover:");
            System.out.print("Misioneros: ");
            int misioneros = scanner.nextInt();
            System.out.print("Caníbales: ");
            int canibales = scanner.nextInt();

            if (misioneros + canibales > 2 || misioneros + canibales <= 0) {
                System.out.println("Movimiento inválido. Solo pueden cruzar 1 o 2 personas.");
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
                System.out.println("Movimiento inválido. Los caníbales superan a los misioneros en alguna orilla.");
                // Revertir movimiento
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
                System.out.println("¡Felicidades! Todos cruzaron el río de manera segura.");
                break;
            }
        }

        scanner.close();
    }
}
