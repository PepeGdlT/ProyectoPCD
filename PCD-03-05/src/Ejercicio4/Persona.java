package Ejercicio4;

import messagepassing.MailBox;

public class Persona extends Thread {

	/**
	 * Atributos de la clase Persona.
	 * id: Identificador de la persona.
	 * buzonSolicitarCaja: Buzón para solicitar una caja.
	 * accederA: Buzón para acceder a la caja A.
	 * accederB: Buzón para acceder a la caja B.
	 * buzonDejarCaja: Buzón para dejar una caja.
	 * buzonPantalla: Buzón para mostrar información en pantalla.
	 * buzonRespuestas: Buzón para recibir respuestas.
	 */
	
	private String id;
	private MailBox buzonSolicitarCaja;
	private MailBox accederA;
	private MailBox accederB;
	private MailBox buzonDejarCaja;
	private MailBox buzonPantalla;
	private MailBox buzonRespuestas;

	/**
	 * Constructor de la clase Persona.
	 * @param id
	 * @param buzonSolicitarCaja
	 * @param accederA
	 * @param accederB
	 * @param buzonDejarCaja
	 * @param buzonPantalla
	 * @param buzonRespuestas
	 */
	
	public Persona(String id, MailBox buzonSolicitarCaja, MailBox accederA, MailBox accederB, MailBox buzonDejarCaja,
			MailBox buzonPantalla, MailBox buzonRespuestas) {
		this.id = id;
		this.buzonSolicitarCaja = buzonSolicitarCaja;
		this.accederA = accederA;
		this.accederB = accederB;
		this.buzonDejarCaja = buzonDejarCaja;
		this.buzonPantalla = buzonPantalla;
		this.buzonRespuestas = buzonRespuestas;
	}
	
	
	/**
	 * Método que representa la ejecución del hilo de la persona.
     */
	 
	@Override
	public void run() {

		for (int i = 0; i < 5; i++) {

			// Tiempo en el que compra
			try {
				Thread.sleep((long) (Math.random() * 9000 + 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// solicitamos caja
			buzonSolicitarCaja.send(id);
			String[] respuestaSolicitud = ((String) buzonRespuestas.receive()).split(":");
			// System.out.println("he recibido respuesta");

			String cajaAsignada = respuestaSolicitud[0];
			long tiempoAsignado = Long.parseLong(respuestaSolicitud[1]);

			// solicitamos acceder a la caja asignada
			if (cajaAsignada.equals("A")) {
				accederA.send(id);
			} else { // será la caja B
				accederB.send(id);
			}

			// esperamos la respuesta de la solicitud y dormimos
			boolean accederCaja = (boolean) buzonRespuestas.receive();
			// System.out.println("puedo acceder a una caja");
			if (accederCaja) {
				try {
					Thread.sleep(tiempoAsignado);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			// solicitamos liberar caja
			buzonDejarCaja.send(id + ":" + cajaAsignada);
			// esperamos a que se libere para poder solicitar imprimir
			String puedoImprimir = (String) buzonRespuestas.receive();
			// System.out.println("he liberado y ahora quiero imprimir");

			if (puedoImprimir.equals("SI")) {
				buzonPantalla.send(id + ":" + cajaAsignada + ":" + respuestaSolicitud[1]);
			}

		}
	}

}
