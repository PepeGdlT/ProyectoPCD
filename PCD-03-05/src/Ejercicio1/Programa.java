package Ejercicio1;


/**
 * Clase que representa el programa principal.
 */
public class Programa {

    static VariablesCompartidas compar = new VariablesCompartidas(new int[110], new int[10], new boolean[10]);

    /**
     * Metodo principal que inicia la ejecución del programa. Programa principal. 
     */
    public static void main(String[] args) {

    	
    	HiloProductor productor = new HiloProductor(compar);
    	productor.start();
    	
        HiloConsumidor[] consumidor = new HiloConsumidor[10];
        for (int i = 0; i < 10; i++) {
        	consumidor[i] = new HiloConsumidor(compar);
        	consumidor[i].start();
        }
      
        HiloSumador sumador = new HiloSumador(compar);
        sumador.start();
        
        
        try {
            productor.join(); 
            
            for (Thread cons : consumidor) {
            	cons.join(); 
            }
            sumador.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        
        
        
        
		
	}

}
