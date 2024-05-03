package Ejercicio2;

/**
 * Clase que representa un hilo de vehículos que cruzan la calle.
 */
public class HiloVehiculo extends Thread {
    private String id;

    /**
     * Constructor de la clase HiloVehiculo.
     * @param id Identificador único del vehículo.
     */
    public HiloVehiculo(String id) {
        this.id = id;
    }

    /**
     * Metodo que ejecuta el hilo para que los vehículos crucen la calle.
     */
    public void run() {
        while (!Programa.stop) {
            // Proceso de entrada NORTE SUR
            Programa.l.lock();
            if (Programa.turno != Turnos.NS || Programa.NSPasando >= 4 || Programa.EOPasando > 0 || Programa.peatonesPasando > 0) {
                try {
                    Programa.esperaVehiculosNS++;
                    Programa.l.unlock();
                    Programa.semNS.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                Programa.l.unlock();
            }

            // Liberar vehículos extra si caben más
            Programa.l.lock();
            if (Programa.esperaVehiculosNS > 0 && Programa.turno == Turnos.NS && Programa.NSPasando < 4) {
                Programa.l.unlock();
                Programa.semNS.release();
            } else {
                Programa.l.unlock();
            }

            // Cruzar
            Programa.p.lock();
            System.out.println("Vehículo " + id + " cruzando por semáforo NS");
            Programa.p.unlock();
            Programa.l.lock();
            Programa.NSPasando++;
            Programa.l.unlock();

            

            try {
                Thread.sleep(500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            Programa.l.lock();
            Programa.NSPasando--;
            Programa.l.unlock();

            // Proceso de salida
            Programa.l.lock();
            if (Programa.turno == Turnos.NS && Programa.esperaVehiculosNS > 0) {
                Programa.esperaVehiculosNS--;
                Programa.l.unlock();
                Programa.semNS.release();
            } else if (Programa.turno == Turnos.EO && Programa.esperaVehiculosEO > 0 && Programa.NSPasando == 0) {
                Programa.esperaVehiculosEO--;
                Programa.l.unlock();
                Programa.semEO.release();
            } else if (Programa.turno == Turnos.PEATONES && Programa.esperaPeatones > 0 && Programa.NSPasando == 0) {
                Programa.peatonesPasando--;
                Programa.l.unlock();
                Programa.semPeatones.release();
            } else {
                Programa.l.unlock();
            }

            // Cambio de dirección
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Proceso de entrada EO
            Programa.l.lock();
            if (Programa.turno != Turnos.EO || Programa.EOPasando == 4 || Programa.NSPasando > 0 || Programa.peatonesPasando > 0) {
                try {
                    Programa.esperaVehiculosEO++;
                    Programa.l.unlock();
                    Programa.semEO.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                Programa.l.unlock();
            }

            // Liberar vehículos extra si caben más
            Programa.l.lock();
            if (Programa.esperaVehiculosEO > 0 && Programa.turno == Turnos.EO && Programa.EOPasando < 4) {
                Programa.l.unlock();
                Programa.semEO.release();
            } else {
                Programa.l.unlock();
            }

            // Cruzar
            
            Programa.p.lock();
            System.out.println("Vehículo " + id + " cruzando por semáforo EO");

            Programa.p.unlock();
            Programa.l.lock();
            Programa.EOPasando++;
            Programa.l.unlock();


            try {
                Thread.sleep(500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            Programa.l.lock();
            Programa.EOPasando--;
            Programa.l.unlock();

            // Proceso de salida
            Programa.l.lock();
            if (Programa.turno == Turnos.EO && Programa.esperaVehiculosEO > 0) {
                Programa.esperaVehiculosEO--;
                Programa.l.unlock();
                Programa.semEO.release();
            } else if (Programa.turno == Turnos.PEATONES && Programa.esperaPeatones > 0 && Programa.EOPasando == 0) {
                Programa.esperaPeatones--;
                Programa.l.unlock();
                Programa.semPeatones.release();
            } else if (Programa.turno == Turnos.NS && Programa.esperaVehiculosNS > 0 && Programa.EOPasando == 0) {
                Programa.NSPasando--;
                Programa.l.unlock();
                Programa.semNS.release();
            } else {
                Programa.l.unlock();
            }

            // Cambio de dirección
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
