package Ejercicio3;

/**
 * Clase que representa una cola de espera en el banco.
 */
public class Cola {
	/**
	 * Atributos de la clase Cola.
	 * libre: Indica si la cola está libre o ocupada.
	 * tiempoEnCola: Tiempo que los clientes han pasado en la cola.
	 */
	
    private boolean libre; // Indica si la cola está libre o ocupada
    private int tiempoEnCola; // Tiempo que los clientes han pasado en la cola

    /**
     * Constructor de la clase Cola.
     * @param libre true si la cola está libre, false si está ocupada.
     * @param tiempoEnCola tiempo que los clientes han pasado en la cola.
     */
    public Cola(boolean libre, int tiempoEnCola) {
        this.libre = libre;
        this.tiempoEnCola = tiempoEnCola;
    }

    /**
     * Metodo para obtener el tiempo que los clientes han pasado en la cola.
     * @return tiempo que los clientes han pasado en la cola.
     */
    public int getTiempoEnCola() {
        return tiempoEnCola;
    }

    /**
     * Metodo para verificar si la cola está libre.
     * @return true si la cola está libre, false si está ocupada.
     */
    public boolean isLibre() {
        return libre;
    }

    /**
     * Metodo para establecer el estado de la cola.
     * @param libre true si la cola está libre, false si está ocupada.
     */
    public void setLibre(boolean libre) {
        this.libre = libre;
    }

    /**
     * Metodo para establecer el tiempo que los clientes han pasado en la cola.
     * @param tiempoEnCola tiempo que los clientes han pasado en la cola.
     */
    public void setTiempoEnCola(int tiempoEnCola) {
        this.tiempoEnCola = tiempoEnCola;
    }

    /**
     * Metodo para representar el objeto Cola como una cadena de texto.
     * @return representación del objeto Cola como una cadena de texto.
     */
    @Override
    public String toString() {
        return "Cola [libre=" + libre + ", tiempoEnCola=" + tiempoEnCola + "]";
    }
}
