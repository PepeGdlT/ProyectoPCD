package Ejercicio1;

/**
 * Clase que representa un hilo consumidor.
 */
class HiloConsumidor extends Thread {

    VariablesCompartidas var;
    
    /**
     * Constructor de la clase HiloConsumidor.
     * @param variables Objeto VariablesCompartidas compartido entre hilos.
     */
    public HiloConsumidor(VariablesCompartidas variables) {
        this.var = variables;
    }

    
	/**
	 * Metodo run que se ejecuta al iniciar el hilo.
	 */
    @Override
    public void run() {
        var.consumidor();
    }

}