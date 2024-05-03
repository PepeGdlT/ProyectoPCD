package Ejercicio1;

/**
 * Clase que representa un hilo sumador.
 */
class HiloSumador extends Thread {

    VariablesCompartidas var;
    
    /**
     * Constructor de la clase HiloSumador.
     * @param variables Objeto VariablesCompartidas compartido entre hilos.
     */
    public HiloSumador(VariablesCompartidas variables) {
        this.var = variables;
    }
    /**
     * Metodo run que se ejecuta al iniciar el hilo.
     */
    @Override
    public void run() {
        var.sumador();
    }

}