package Ejercicio2;

/**
 * Clase que representa un hilo encargado de cambiar el estado del semáforo.
 */
public class CambiaSemaforo extends Thread {

    /**
     * Metodo para comprobar si hay vehículos o peatones esperando en un cruce para liberar el semáforo correspondiente.
     */
    private void comprobarBug() {
    	// Comprobamos si hay vehículos o peatones esperando en un cruce para liberar el semáforo correspondiente.
        Programa.l.lock();
        if (Programa.peatonesPasando == 0 && Programa.NSPasando == 0 && Programa.EOPasando == 0) {

            switch (Programa.turno) {
                case NS: {

                    if (Programa.esperaVehiculosNS > 0) {
                        Programa.l.unlock();
                        Programa.semNS.release();

                    } else {
                        Programa.l.unlock();
                    }

                    break;
                }
                case EO: {
                    if (Programa.esperaVehiculosEO > 0) {
                        Programa.l.unlock();
                        Programa.semEO.release();
                    } else {
                        Programa.l.unlock();
                    }
                    break;
                }
                case PEATONES: {
                    if (Programa.esperaPeatones > 0) {
                        Programa.l.unlock();
                        Programa.semPeatones.release();
                    } else {
                        Programa.l.unlock();
                    }
                    break;
                }

            }
        } else {
            Programa.l.unlock();
        }


    }

    /**
     * Metodo que ejecuta el hilo para cambiar el estado del semáforo.
     */
    public void run() {
        while (!Programa.stop) {
        	// Cambiamos el estado del semáforo.
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            comprobarBug();
            
            Programa.l.lock();
            Programa.setTurno(Turnos.EO);
            Programa.l.unlock();
            
            Programa.p.lock();
            System.out.println("----------SEMAFORO ESTE/OESTE---------");
            Programa.p.unlock();

           

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            comprobarBug();
            
            Programa.l.lock();
            Programa.setTurno(Turnos.PEATONES);
            
            Programa.l.unlock();

            Programa.p.lock();
            System.out.println("----------SEMAFORO PEATONES-----------");
            Programa.p.unlock();
            

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            comprobarBug();
           
            
            Programa.l.lock();
            Programa.setTurno(Turnos.NS);
            Programa.l.unlock();

            Programa.p.lock();
            System.out.println("----------SEMAFORO NORTE/SUR----------");
            Programa.p.unlock();
            
        }
    }

}
