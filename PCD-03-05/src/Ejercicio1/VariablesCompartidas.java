package Ejercicio1;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Clase que representa variables compartidas entre hilos.
 */
class VariablesCompartidas {
	
	/**
	 * Atributos de la clase VariablesCompartidas.
	 * bufferElem: Arreglo para almacenar elementos.
	 * bufferSuma: Arreglo para almacenar resultados de sumas.
	 * bufferCalculado: Arreglo de booleanos para indicar si se ha calculado el resultado.
	 * elementosGenerados: Booleano para indicar si se han generado elementos.
	 * l: Lock para control de concurrencia.
	 */
	
    private int bufferElem[];
    private int bufferSuma[];
    private boolean bufferCalculado[];
    private boolean elementosGenerados = false;
    private ReentrantLock l = new ReentrantLock();

    /**
     * Constructor de la clase VariablesCompartidas.
     * @param buffer1 Arreglo para almacenar elementos.
     * @param buffer2 Arreglo para almacenar resultados de sumas.
     * @param buffer3 Arreglo de booleanos para indicar si se ha calculado el resultado.
     */
    public VariablesCompartidas(int buffer1[], int buffer2[], boolean buffer3[]) {
        this.bufferElem = buffer1;
        this.bufferSuma = buffer2;
        this.bufferCalculado = buffer3;
    }



    /**
     * Metodo para generar elementos en el buffer.
     */
    public void productor() {
		l.lock();
		for (int i = 0; i < 10; i++) {
			// dividimos el array en 10 mini-arrays, de manera que si el numero del
			// mini-array es par, se
			// ponen numeros de operacion en las posiciones impares, y si el numero es
			// impar, en las posiciones pares
			for (int j = 10 * i + i; j < (10 * i + i) + 11; j++) {

				if (i % 2 == 0) {
					if (j % 2 == 0) {
						// si el miniarray es par, y la pos es par se ha de poner un numero
						bufferElem[j] = ((int) Math.round(Math.random() * 10));

					} else {
						// si el miniarray es par, y la pos es impar se ha de poner un numero de
						// operacion
						// 1 suma
						// 2 resta
						// 3 producto
						bufferElem[j] = ((int) Math.round(Math.random() * 2)) + 1;
					}
				} else {
					if (j % 2 == 0) {
						// si el miniarray es impar, y la pos es par se ha de poner un numero de
						// operacion
						bufferElem[j] = ((int) Math.round(Math.random() * 2)) + 1;
					} else {
						// si el miniarray es impar, y la pos es impar se ha de poner un numero
						// 1 suma
						// 2 resta
						// 3 producto
						bufferElem[j] = ((int) Math.round(Math.random() * 10));

					}
				}
			}
		}
		elementosGenerados = true;

		l.unlock();
    }

    /**
     * Metodo para consumir elementos del buffer y realizar sumas.
     */
	public void consumidor() {
		int n1Prin;
		int opPrin;
		int n2Prin;
		int op;
		int n3;
		int total = 0;

		l.lock();
		if (elementosGenerados) {
			// buscamos el primer mini-array que no haya sido calculado
			int i = 0;
			while (bufferCalculado[i] == true) {
				i++;
			}

			bufferCalculado[i] = true;

			n1Prin = bufferElem[(i * 10) + i];
			opPrin = bufferElem[((i * 10) + i) + 1];
			n2Prin = bufferElem[((i * 10) + i) + 2];

			if (opPrin == 1) {
				total += n1Prin + n2Prin;
			} else if (opPrin == 2) {
				total += n1Prin - n2Prin;
			} else if (opPrin == 3) {
				total += n1Prin * n2Prin;
			} else {
				System.err.println("ERROR");
			}
			// recorremos el mini-array para realizar las operaciones
			for (int j = ((i * 10) + i) + 3; j < ((i * 10) + i) + 11; j = j + 3) {

				op = bufferElem[j];
				n3 = bufferElem[j + 1];

				if (op == 1) {
					total += n3;
				} else if (op == 2) {
					total -= n3;
				} else if (op == 3) {
					total *= n3;

				}

			}
			// guardamos el resultado en el buffer de sumas
			bufferSuma[i] = total;
			System.out.println("Hilo consumidor " + (i + 1) + ":	" + bufferSuma[i]);
		}
		l.unlock();

	}

    /**
     * Metodo para sumar los resultados almacenados en el buffer.
     */
	public void sumador() {
		int total = 0;

		// esta condicion nos informa de que podemos realizar la suma de las secciones
		l.lock();
		boolean condicion = true;
		// comprobamos que todas las secciones han sido calculadas
		for (int i = 0; i < 10; i++) {
			if (bufferCalculado[i] == false) {
				condicion = false;
			}
		}
		l.unlock();
		// si todas las secciones han sido calculadas, realizamos la suma
		if (condicion) {
			l.lock(); 
			for (int i = 0; i < 10; i++) {
				total += bufferSuma[i];
			}
			l.unlock();
			l.lock();
			System.out.println("------------------");
			System.out.println("TOTAL: " + total);
			l.unlock();
		}

		

	}
}
