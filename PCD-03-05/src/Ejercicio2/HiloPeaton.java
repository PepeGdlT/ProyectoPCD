package Ejercicio2;

/**
 * Clase que representa un hilo de peatones que cruzan la calle.
 */
public class HiloPeaton extends Thread {

    private String id;

    /**
     * Constructor de la clase HiloPeaton.
     * @param id Identificador único del peatón.
     */
    public HiloPeaton(String id) {
        this.id = id;
    }

    /**
     * Metodo que ejecuta el hilo para que los peatones crucen la calle.
     */
    public void run() {
    	
        while (!Programa.stop) {
            // Proceso de entrada
            Programa.l.lock();
            if (Programa.turno != Turnos.PEATONES || Programa.peatonesPasando >= 10 || Programa.NSPasando > 0 || Programa.EOPasando > 0) {

                try {
                    Programa.esperaPeatones++;
                    Programa.l.unlock();
                    Programa.semPeatones.acquire();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                Programa.l.unlock();
            }

            // Liberar peatones extra si caben más
            Programa.l.lock();
            if (Programa.esperaPeatones > 0 && Programa.turno == Turnos.PEATONES && Programa.peatonesPasando < 10) {
                Programa.l.unlock();
                Programa.semPeatones.release();
            } else {
                Programa.l.unlock();
            }

            // Cruzar
            
            Programa.p.lock();
            System.out.println("Peatón " + id + " cruzando");
            Programa.p.unlock();
            Programa.l.lock();
            Programa.peatonesPasando++;
            Programa.l.unlock();


            try {
                Thread.sleep(3000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            Programa.l.lock();
            Programa.peatonesPasando--;
            Programa.l.unlock();

            // Proceso de salida
            Programa.l.lock();

            if (Programa.turno == Turnos.PEATONES && Programa.esperaPeatones > 0) {
                Programa.esperaPeatones--;
                Programa.l.unlock();
                Programa.semPeatones.release();

            } else if (Programa.turno == Turnos.NS && Programa.esperaVehiculosNS > 0 && Programa.peatonesPasando == 0) {
                Programa.esperaVehiculosNS--;
                Programa.l.unlock();
                Programa.semNS.release();

            } else if (Programa.turno == Turnos.EO && Programa.esperaVehiculosEO > 0 && Programa.peatonesPasando == 0) {
                Programa.EOPasando--;
                Programa.l.unlock();
                Programa.semEO.release();

            } else {
                Programa.l.unlock();
            }

            // Volver a cruzar
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

}
