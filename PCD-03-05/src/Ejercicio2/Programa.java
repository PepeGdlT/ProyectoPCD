package Ejercicio2;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Clase que contiene el programa principal y controla la simulación del cruce de vehículos y peatones.
 */
public class Programa {
	
	/**
     * MAX_PEATONES: Máximo de peatones.
     * MAX_VEHICULOS: Máximo de vehículos.
     * l: Lock para control de concurrencia en variables.
     * p: Lock para control de concurrencia en pantalla.
     * semNS: Semáforo para control de sincronización en dirección Norte-Sur.
     * semEO: Semáforo para control de sincronización en dirección Este-Oeste.
     * semPeatones: Semáforo para control de sincronización de peatones.
     * turno: Turno del semáforo.
     * peatonesPasando: Contador de peatones pasando.
     * NSPasando: Contador de vehículos pasando en dirección Norte-Sur.
     * EOPasando: Contador de vehículos pasando en dirección Este-Oeste.
     * stop: Booleano para detener la simulación.
     * esperaPeatones: Contador de peatones esperando.
     * esperaVehiculosNS: Contador de vehículos esperando en dirección Norte-Sur.
     * esperaVehiculosEO: Contador de vehículos esperando en dirección Este-Oeste.

	 */
	
	
    public final static int MAX_PEATONES = 10;
    public final static int MAX_VEHICULOS = 4;

    // Lock para control de concurrencia
    public static ReentrantLock l = new ReentrantLock();
    public static ReentrantLock p = new ReentrantLock();

    // Semáforos para control de sincronización
    public static Semaphore semNS = new Semaphore(0);
    public static Semaphore semEO = new Semaphore(0);
    public static Semaphore semPeatones = new Semaphore(0);

    // Enumeración para representar los turnos de los semáforos
    public static Turnos turno;

    /**
     * Metodo para establecer el turno del semáforo.
     * @param turno Turno del semáforo a establecer.
     */
    public static void setTurno(Turnos turno) {
        Programa.turno = turno;
    }

    // Contadores de vehículos y peatones pasando
    public static volatile int peatonesPasando;
    public static volatile int NSPasando;
    public static volatile int EOPasando;

    // Bandera para detener la simulación
    public static volatile boolean stop;

    // Contadores de espera para vehículos y peatones
    public static volatile int esperaPeatones = 0;
    public static volatile int esperaVehiculosNS = 0;
    public static volatile int esperaVehiculosEO = 0;

    /**
     * Metodo principal que inicia la simulación del cruce de vehículos y peatones.
     */
    public static void main(String[] args) {
        Programa.l.lock();
        stop = false;
        setTurno(Turnos.NS);
        Programa.l.unlock();
        
        Programa.p.lock();
        System.out.println("----------SEMAFORO NORTE/SUR----------");
        Programa.p.unlock();

        // Crear e iniciar hilo para cambiar el semáforo
        CambiaSemaforo cambio = new CambiaSemaforo();
        cambio.start();

        // Crear arreglos de hilos para vehículos y peatones
        HiloVehiculo[] vehiculos = new HiloVehiculo[50];
        HiloPeaton[] peatones = new HiloPeaton[100];

        // Iniciar hilos de vehículos
        for (int i = 0; i < 50; i++) {
            vehiculos[i] = new HiloVehiculo(String.valueOf(i + 1));
            vehiculos[i].start();
        }

        // Iniciar hilos de peatones
        for (int i = 0; i < 100; i++) {
                peatones[i] = new HiloPeaton(String.valueOf(i + 1));
                peatones[i].start();
            
        }

        // Esperar a que terminen todos los hilos
        try {
            cambio.join(); // Esperar a que termine el hilo de cambio de semáforo
            for (Thread vehiculoThread : vehiculos) {
                vehiculoThread.join(); // Esperar a que terminen los hilos de vehículos
            }
            for (Thread peatonThread : peatones) {
                peatonThread.join(); // Esperar a que terminen los hilos de peatones
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
