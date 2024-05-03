package Ejercicio1;

/**
 * Clase que representa un hilo productor.
 */
class HiloProductor extends Thread {

    VariablesCompartidas bufferElem;
    
    /**
     * Constructor de la clase HiloProductor.
     * @param buffer Objeto VariablesCompartidas compartido entre hilos.
     */
    public HiloProductor(VariablesCompartidas buffer) {
        this.bufferElem = buffer;
    }

	/**
	 * Metodo run que se ejecuta al iniciar el hilo.
	 */
    @Override
    public void run() {
        bufferElem.productor();
    }

}