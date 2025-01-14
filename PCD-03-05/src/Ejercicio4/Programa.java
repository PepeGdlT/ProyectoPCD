package Ejercicio4;

import messagepassing.MailBox;
/**
 * Clase que representa el programa principal para simular el funcionamiento de una caja de supermercado.
 */
public class Programa {
		/**
		 * Atributo que representa el número de personas en la simulación.
		 */
		public final static int NUMERO_GLOBAL = 30;
		/**
		 * Método principal que inicia la simulación de la caja de supermercado.
		 */
		
	    public static void main(String[] args) {
	    	// Crear buzones y personas
	        MailBox buzonSolicitarCaja = new MailBox();
	        MailBox accederCajaA = new MailBox();
	        MailBox accederCajaB = new MailBox();
	        MailBox buzonDejarCaja = new MailBox();
	        MailBox buzonPantalla = new MailBox();
	        MailBox[] buzonesPersonas = new MailBox[NUMERO_GLOBAL];
	        
	        Persona[] personas = new Persona[NUMERO_GLOBAL];
	        
	      
	        
	        // Crear hilos de personas y empezarlos
	        for (int i = 0; i < NUMERO_GLOBAL; i++) {
	            buzonesPersonas[i] = new MailBox();
	            personas[i] = new Persona(String.valueOf(i+1), buzonSolicitarCaja, accederCajaA, accederCajaB, buzonDejarCaja, buzonPantalla, buzonesPersonas[i]);
	            personas[i].start();
	            
	        }

	        // Crear hilo del controlador de cajas y empezarlo
	        Controlador controladorCajas = new Controlador(buzonSolicitarCaja, accederCajaA, accederCajaB, buzonDejarCaja, buzonPantalla, buzonesPersonas);
	        controladorCajas.start();
	        
	        
	        
	        try {
	        	controladorCajas.join(); 
	            for (Thread persona : personas) {
	                persona.join(); 
	            }
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        
	    }
}
