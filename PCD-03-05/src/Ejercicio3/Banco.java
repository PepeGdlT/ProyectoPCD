package Ejercicio3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Clase que representa un banco con múltiples colas de espera, máquinas de servicio y una pantalla de información.
 */
public class Banco {
	
	/**
	 * Atributos de la clase Banco.
	 * l: ReentrantLock para controlar el acceso a las variables de condición.
	 * escogerMesa: Condition para controlar el acceso a las mesas de espera.
	 * escogerMaquina: Condition para controlar el acceso a las máquinas de servicio.
	 * imprimirEnPantalla: Condition para controlar la impresión de información en pantalla.
	 * colasMesa: ArrayList de colas de espera en el banco.
	 * maquinaLibre: ArrayList de booleanos para indicar si las máquinas de servicio están disponibles.
	 * pantallaLibre: booleano para indicar si la pantalla de información está libre.
	 */
	
    private ReentrantLock l = new ReentrantLock(true);
    private Condition escogerMesa = l.newCondition();
    private Condition escogerMaquina = l.newCondition();
    private Condition imprimirEnPantalla = l.newCondition();

    private ArrayList<Cola> colasMesa;
    private ArrayList<Boolean> maquinaLibre;
    private boolean pantallaLibre;

    /**
     * Constructor de la clase Banco.
     */
    public Banco() {
    	
        this.colasMesa = new ArrayList<Cola>();
        for (int i = 0; i < 4; i++) {
            this.colasMesa.add(new Cola(true, 0));
        }
        this.maquinaLibre = new ArrayList<Boolean>(Collections.nCopies(3, true));
        this.pantallaLibre = true;
    }

    /**
     * Metodo para verificar si la pantalla está libre.
     * @return true si la pantalla está libre, false si está ocupada.
     */
    public boolean isPantallaLibre() {
        return pantallaLibre;
    }

    /**
     * Metodo para establecer el estado de la pantalla.
     * @param pantallaLibre true si la pantalla está libre, false si está ocupada.
     */
    public void setPantallaLibre(boolean pantallaLibre) {
        this.pantallaLibre = pantallaLibre;
    }

    /**
     * Metodo para establecer una máquina como disponible.
     * @param num número de la máquina a establecer como disponible.
     */
    public void setMaquinaTrue(int num) {
        l.lock();
        try {
            maquinaLibre.set(num, true);
            escogerMaquina.signal();
        } finally {
            l.unlock();
        }
    }

    /**
     * Metodo para verificar si alguna máquina está disponible.
     * @return el número de la máquina disponible o -1 si no hay máquinas disponibles.
     */
    private int isMaquinaLibre() {
        int i = 0;
        for (boolean maquina : maquinaLibre) {
            if (maquina == true) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     * Metodo para que un cliente acceda a una máquina disponible.
     * @return el número de la máquina a la que el cliente accedió.
     * @throws InterruptedException si ocurre una interrupción mientras el cliente espera.
     */
    public int accederMaquina() throws InterruptedException {
        l.lock();
        try {
            while (isMaquinaLibre() == -1) {
                escogerMaquina.await();
            }
            int numMaquina = isMaquinaLibre();
            maquinaLibre.set(numMaquina, false);
            return numMaquina;
        } finally {
            l.unlock();
        }
    }

    /**
     * Metodo para indicar que un cliente ha abandonado una mesa.
     * @param colaCogida número de la mesa que el cliente abandonó.
     */
    public void marchaMesa(int colaCogida) {
        l.lock();
        try {
            escogerMesa.signal();
        } finally {
            l.unlock();
        }
    }

    /**
     * Metodo para buscar la cola de espera más corta.
     * @return el número de la cola de espera más corta.
     */
    public int buscarCola() {
        l.lock();
        try {
            int minima = colasMesa.get(0).getTiempoEnCola();
            int colaCogida = 0;
            for (int i = 1; i < colasMesa.size(); i++) {
                if (colasMesa.get(i).getTiempoEnCola() < minima) {
                    minima = colasMesa.get(i).getTiempoEnCola();
                    colaCogida = i;
                }
            }
            return colaCogida;
        } finally {
            l.unlock();
        }
    }

    /**
     * Metodo para que un cliente acceda a una mesa de espera.
     * @param colaCogida número de la cola de espera a la que el cliente accede.
     * @param _Y tiempo que el cliente permanecerá en la mesa.
     * @throws InterruptedException si ocurre una interrupción mientras el cliente espera.
     */
    public void accederMesa(int colaCogida, int _Y) throws InterruptedException {
        l.lock();
        try {
            while (colasMesa.get(colaCogida).isLibre()) {
                escogerMesa.await();
            }
            colasMesa.get(colaCogida).setLibre(true);
            colasMesa.get(colaCogida).setTiempoEnCola(colasMesa.get(colaCogida).getTiempoEnCola() - _Y);
        } finally {
            l.unlock();
        }
    }

    /**
     * Metodo para imprimir información sobre el servicio solicitado por un cliente.
     * @param _id identificador del cliente.
     * @param _X tiempo en solicitar el servicio.
     * @param _Y tiempo en la mesa.
     * @param colaCogida número de la cola de espera.
     * @param maquinaEscogida número de la máquina de servicio.
     * @throws InterruptedException si ocurre una interrupción mientras se espera para imprimir.
     */
    public void imprimirPorPantalla(String _id, int _X, int _Y, int colaCogida, int maquinaEscogida)
            throws InterruptedException {
        l.lock();
        try {
            while (!isPantallaLibre()) {
                imprimirEnPantalla.await();
            }
            setPantallaLibre(false);
            System.out.println("Cliente " + _id + " ha solicitado su servicio en la máquina: " + (maquinaEscogida + 1)
                    + " \n " + "Tiempo en solicitar el servicio: " + _X + " \n " + "Será atendido en la mesa: "
                    + (colaCogida + 1) + " \n " + "Tiempo en la mesa: " + _Y + " \n " + "Tiempo de espera:" + " \n "
                    + "Mesa 1: " + colasMesa.get(0).getTiempoEnCola() + " \n " + "Mesa 2: "
                    + colasMesa.get(1).getTiempoEnCola() + " \n " + "Mesa 3: " + colasMesa.get(2).getTiempoEnCola()
                    + " \n " + "Mesa 4: " + colasMesa.get(3).getTiempoEnCola());
            colasMesa.get(colaCogida).setLibre(false);
            colasMesa.get(colaCogida).setTiempoEnCola(colasMesa.get(colaCogida).getTiempoEnCola() + _Y);
        } finally {
            l.unlock();
        }
    }

    /**
     * Metodo para indicar que se ha terminado de imprimir en la pantalla.
     */
    public void marcharPantalla() {
        l.lock();
        try {
            setPantallaLibre(true);
            imprimirEnPantalla.signal();
        } finally {
            l.unlock();
        }
    }
}
