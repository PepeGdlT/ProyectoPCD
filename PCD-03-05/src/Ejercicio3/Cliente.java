package Ejercicio3;

/**
 * Clase que representa un cliente que solicita un servicio en el banco.
 */
public class Cliente extends Thread {
	/**
	 * Atributos de la clase Cliente.
	 * X: Tiempo en solicitar el servicio.
	 * Y: Tiempo en la mesa.
	 * id: Identificador del cliente.
	 * banco: Banco donde se solicita el servicio.
	 */
	
	
    private int X; // Tiempo en solicitar el servicio
    private int Y; // Tiempo en la mesa
    private String id; // Identificador del cliente
    private Banco banco; // Banco donde se solicita el servicio

    /**
     * Constructor de la clase Cliente.
     * @param _id identificador del cliente.
     * @param _banco banco donde se solicita el servicio.
     */
    public Cliente(String _id, Banco _banco) {
    	this.X = (int) (Math.random() * 2000 + 2000);
        this.Y = (int) (Math.random() * 2000 + 2000);
        this.id = _id;
        this.banco = _banco;
    }

    /**
     * Metodo que representa la ejecución del hilo del cliente.
     */
    public void run() {
        try {
            // Acceder a una máquina
            int numeroMaquina = banco.accederMaquina(); // AWAIT MAQUINA
            Thread.sleep(X);
            banco.setMaquinaTrue(numeroMaquina); // SIGNAL MAQUINA
           
            // Imprimir en pantalla
            int colaCogida = banco.buscarCola();
            banco.imprimirPorPantalla(id, X, Y, colaCogida, numeroMaquina); // AWAIT PANTALLA
            banco.marcharPantalla(); // SIGNAL PANTALLA
            Thread.sleep(Y);
            
            // Acceder a una mesa de espera
            banco.accederMesa(colaCogida, Y); // AWAIT MESA
            banco.marchaMesa(colaCogida); // SIGNAL MESA

        } catch (Exception e) {
            // Manejo de excepciones
        }
    }

    /**
     * Metodo para representar el objeto Cliente como una cadena de texto.
     * @return representación del objeto Cliente como una cadena de texto.
     */
    @Override
    public String toString() {
        return "Cliente [X=" + X + ", Y=" + Y + ", id=" + id + ", banco=" + banco + "]";
    }
}
