package Ejercicio3;

/**
 * Clase que representa el programa principal para simular el funcionamiento del banco.
 */
public class Programa {
    
    /**
     * Metodo principal que inicia la simulación del banco.
     */
    public static void main(String[] args) {
        // Crear una instancia de Banco
        Banco banco = new Banco();
        
        // Iniciar múltiples hilos de clientes
        for(int i = 0 ; i < 50 ; i++) {
             new Cliente(String.valueOf(i+1), banco).start();
        }
    }
}
