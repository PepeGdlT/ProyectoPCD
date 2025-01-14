package Ejercicio4;

import messagepassing.MailBox;
import messagepassing.Selector;

public class Controlador extends Thread {

	/**
	 * Atributos de la clase Controlador.
	 * buzonSolicitarCaja: Buzón para solicitar una caja.
	 * buzonAccederA: Buzón para acceder a la caja A.
	 * buzonAccederB: Buzón para acceder a la caja B.
	 * buzonDejarCaja: Buzón para dejar una caja.
	 * buzonPantalla: Buzón para mostrar información en pantalla.
	 * buzonesClientes: Array de buzones para los clientes.
	 * s: Selector para seleccionar un buzon.
	 * ocupadaCajaA: Indica si la caja A está ocupada.
	 * ocupadaCajaB: Indica si la caja B está ocupada.
	 */
	
	private MailBox buzonSolicitarCaja;
	private MailBox buzonAccederA;
	private MailBox buzonAccederB;
	private MailBox buzonDejarCaja;
	private MailBox buzonPantalla;
	private MailBox[] buzonesClientes;
	private Selector s;
	
	private boolean ocupadaCajaA = false;
	private boolean ocupadaCajaB = false;

	/**
	 * Constructor de la clase Controlador.
	 * @param buzonSolicitarCaja
	 * @param accederA
	 * @param accederB
	 * @param buzonDejarCaja
	 * @param buzonPantalla
	 * @param buzonesClientes
	 */
	
	public Controlador(MailBox buzonSolicitarCaja, MailBox accederA, MailBox accederB, MailBox buzonDejarCaja,
			MailBox buzonPantalla, MailBox[] buzonesClientes) {
		this.buzonSolicitarCaja = buzonSolicitarCaja;
		this.buzonAccederA = accederA;
		this.buzonAccederB = accederB;
		this.buzonDejarCaja = buzonDejarCaja;
		this.buzonPantalla = buzonPantalla;
		this.buzonesClientes = buzonesClientes;
		
		
		// Inicializar el selector, añadir los buzones y deshabilitarlos
		this.s = new Selector();
		s.addSelectable(buzonSolicitarCaja, false);
		s.addSelectable(accederA, false);
		s.addSelectable(accederB, false);
		s.addSelectable(buzonDejarCaja, false);
		s.addSelectable(buzonPantalla, false);

	}
	/**
	 * Método para ejecutar el hilo del controlador.
	 */
	
	@Override
	public void run() {
		while (true) {
			// Habilitar los buzones
			buzonSolicitarCaja.setGuardValue(true);
			buzonAccederA.setGuardValue(ocupadaCajaA == false);
			buzonAccederB.setGuardValue(ocupadaCajaB == false);
			buzonDejarCaja.setGuardValue(ocupadaCajaA == true || ocupadaCajaB == true);
			buzonPantalla.setGuardValue(true);

			String id = "";
			String respuesta = "";
			String[] respuestaPorCampos = null;
			// Seleccionar un buzon
			switch (s.selectOrBlock()) {
			case 1: {
				// Recibir el id del cliente
				id = (String) buzonSolicitarCaja.receive();
				// Generar un tiempo aleatorio
				int tiempo = (int) (Math.random() * 9000 + 1000);
				// Enviar respuesta
				respuesta = tiempo >= 5000 ? "A:" + tiempo : "B:" + tiempo;
				buzonesClientes[Integer.parseInt(id) - 1].send(respuesta);
				break;
			}

			case 2: {
				// acceder a la caja A
				id = (String) buzonAccederA.receive();
				ocupadaCajaA = true;
				respuesta = "true";
				buzonesClientes[Integer.parseInt(id) - 1].send(Boolean.valueOf(respuesta));
				break;
			}
			case 3: {
				// acceder a la caja B
				id = (String) buzonAccederB.receive();
				ocupadaCajaB = true;
				respuesta = "true";
				buzonesClientes[Integer.parseInt(id) - 1].send(Boolean.valueOf(respuesta));
				break;
			}
			case 4: {
				// dejar la caja A
				respuestaPorCampos = ((String) buzonDejarCaja.receive()).split(":");
				if (respuestaPorCampos[1].equals("A") && ocupadaCajaA == true) {
					ocupadaCajaA = false;
				}
				// Dejar la caja B
				else if (ocupadaCajaB == true) {
					ocupadaCajaB = false;
				}
				// Enviar respuesta
				respuesta = "SI";
				buzonesClientes[Integer.parseInt((respuestaPorCampos[0])) - 1].send(String.valueOf(respuesta));
				break;
			}

			case 5: {
				// Mostrar información en pantalla
				respuestaPorCampos = ((String) buzonPantalla.receive()).split(":");
				id = respuestaPorCampos[0];
				String caja = respuestaPorCampos[1];
				long tiempo = Long.parseLong(respuestaPorCampos[2]);
				// Imprimir en pantalla
				System.out.println("--------------------");
				System.out.println("Persona " + id + " ha usado la caja " + caja);
				System.out.println("Tiempo de pago = " + tiempo);
				System.out.println("Thread.sleep(" + tiempo + ")");
				System.out.println("Persona " + id + " liberando la caja " + caja);
				System.out.println("--------------------");
				break;
			}

			}
		}

	}

}