Trabajo Final Programacion Concurrente Distribuida.

PROGRAMACIÓN CONCURRENTE Y DISTRIBUIDA
BOLETÍN DE PRÁCTICAS
CONVOCATORIAS DE JUNIO
CURSO 2023-2024
Contenido
ENTREGABLES ................................................................................................ 1
FORMATO DE LOS DOCUMENTOS............................................................ 1
EVALUACIÓN................................................................................................... 2
EJERCICIOS...................................................................................................... 3
EJERCICIO 1: INTRODUCCIÓN A LA PROGRAMACIÓN CONCURRENTE. ................. 3
EJERCICIO 2: SEMÁFOROS.................................................................................. 4
EJERCICIO 3: MONITORES. ................................................................................. 5
EJERCICIO 4: PASO DE MENSAJES....................................................................... 6
1
Entregables
La entrega constará de un único proyecto conteniendo 4 paquetes (uno para cada 
ejercicio) que se comprimirá en un fichero .zip y una memoria de prácticas en formato PDF. 
Los ejercicios se implementarán en Java 8 o una versión superior usando la codificación de 
caracteres UTF-8 (Project-Resource-Textfileencoding-Other-UTF-8). Tanto el código fuente 
como la memoria se entregarán a través de una tarea que será creada en el AULA 
VIRTUAL. No se admitirán entregas realizadas por otros medios.
En la memoria escrita se deben incluir necesariamente para cada ejercicio los 
siguientes apartados:
• Recursos no compartibles. Los que se hayan identificado en el problema.
• Condiciones de sincronización. Las detectadas en el problema.
• Pseudocódigo comentado. El de los diferentes hilos y monitores que intervengan 
en la solución.
• Cuestiones planteadas en los ejercicios.
• Código Java de los ejercicios comentado.
Formato de los documentos
La memoria de prácticas deberá estar escrita correctamente en sus aspectos ortográfico 
y gramatical, evitando el uso de vulgarismos y lenguaje SMS. Las páginas deberán estar 
numeradas y se debe incluir un índice. El tipo de letra utilizado para el texto normal será 
Times New Román (o semejante) en 12 puntos y en color negro. El interlineado será sencillo.
Los listados de código deberán estar formateados cuidando varios aspectos:
• Indentación.
• Tamaño de la letra adecuado para que no se corten los renglones, o cuando esto 
ocurra el código siga siendo legible y ordenado.
• Tipo de letra de paso fijo (Courier o semejante) en color negro.
Además, el código fuente entregado debe estar debidamente comentado tanto en los 
bloques de código más relevantes en relación con la programación concurrente y distribuida 
como mediante el uso de Javadoc para las clases y métodos desarrollados.
En la portada de la memoria de prácticas deberá aparecer arriba a la derecha la 
siguiente información: Apellidos, Nombre, Asignatura, Grupo y Subgrupo de Prácticas, 
Convocatoria, Año académico y Profesor.
2
Evaluación
La evaluación constará de dos partes principales: la corrección de las prácticas 
entregadas y la defensa de las mismas en una entrevista personal. Las prácticas se realizarán 
en parejas de 2.
 
Como criterios de evaluación se tendrán en cuenta los siguientes:
• Corrección de las soluciones aportadas.
• Grado de concurrencia
• Entrevista de prácticas
• Claridad en la explicación (memoria de prácticas)
• Originalidad.
La copia de prácticas será motivo de suspenso para todos los participantes implicados.
La no asistencia (injustificada) a la entrevista de prácticas será motivo de suspenso de las 
mismas.
Este boletín se compone de cuatro ejercicios que serán valorados por el profesor con 
una puntuación de 0 a 10 cada uno. Para superar la parte práctica será necesario sacar una nota 
igual o superior a 5 en cada uno de ellos. Cuando todos los ejercicios estén superados la 
ponderación aplicada será la siguiente: 0.25*E1 + 0.25*E2 + 0.25*E3 + 0.25*E4. La fecha 
límite para la entrega del boletín de prácticas será el 5 de mayo a las 23:00 horas.
3
Ejercicios
Ejercicio 1: Introducción a la programación concurrente. 
Tenemos 10 procesos consumidores, un proceso generador de números y operaciones 
(producto, suma y resta) y un proceso sumador.
El proceso generador introduce de forma alternativa un número y una operación, en 
bloques de 6 números y 5 operaciones, en un array de 110 elementos de tamaño (para ello 
deberás codificar las operaciones como enteros, por ejemplo puedes decir que 1 = suma, 2 = 
resta y 3 = multiplicación).
Cada proceso consumidor tiene que leer 11 posiciones consecutivas del array y 
calcular el resultado final de realizar las operaciones indicadas. Por ejemplo, si en las 5 
primeras posiciones un proceso se encuentra con la siguiente secuencia '3*5+2' el resultado 
sería '17'. Una vez calculado el resultado de procesar las 11 posiciones, lo volcará en otro array 
e imprimirá por pantalla su ID y el resultado que ha calculado. Una vez que todos los procesos 
hayan calculado su parte y volcando la misma en el vector de resultados, el proceso sumador 
deberá calcular la suma de todos los resultados y mostrarla en pantalla. Resolver el ejercicio 
en Java utilizando ReentrantLock. 
(1 punto sobre 10) ¿Qué acciones pueden realizar los hilos concurrentemente? Justifica 
la respuesta. 
(1 punto sobre 10) Las impresiones que hacen los hilos, ¿son consecutivas o están 
desordenadas con las de los demás hilos? ¿Cuál de las opciones consideras que es la correcta? 
Justifica la respuesta. 
(1 punto sobre 10) Si no usaras ningún mecanismo para sincronización, ¿cómo podría 
ser la salida en pantalla del programa anterior?
4
Ejercicio 2: Semáforos. 
Imagina un cruce con semáforos para peatones y vehículos. El cruce tiene dos semáforos de
vehículos (Norte-Sur y Este-Oeste) y un semáforo de peatones.
Los semáforos de vehículos cambian de rojo a verde cada 10 segundos, permitiendo 5
segundos para el paso de los vehículos en la dirección correspondiente. El semáforo de
peatones permite el paso igualmente cada 10 segundos durante 5 segundos. En cada instante
solamente puede estar en verde uno de los tres semáforos, rotándose secuencialmente el turno
de paso cada 5 segundos.
Un vehículo tarda medio segundo en cruzar el cruce, mientras que un peatón tarda 3 segundos.
La calzada tiene capacidad para 4 vehículos simultáneos en cada dirección y el paso de
peatones admite 10 peatones al mismo tiempo. Además, cada vehículo que cruza el cruce en
una dirección, 7 segundos más tarde intenta cruzarlo en la otra dirección. Y cada peatón que
cruza el cruce de peatones, a los 8 segundos vuelve a intentar cruzarlo. Para evitar accidentes,
sólo pueden estar cruzando elementos del mismo (peatones, vehículos en dirección Norte-Sur
o vehículos en dirección Este-Oeste), por lo que el cruce debe estar vacío antes de que se
empiece a cruzar en un nuevo turno (a pesar de estar en verde).
Implementa un programa en Java utilizando semáforos para coordinar el paso continuo de 50
vehículos y 100 peatones en el cruce de manera sincronizada. Todos los vehículos empiezan
en dirección Norte-Sur (e irán continuamente alternando su dirección con el paso de los
turnos). Imprime por pantalla el semáforo que está en verde (turno) y el seguimiento de quién
está cruzando.
a) (0.5 puntos sobre 10) ¿Qué acciones pueden realizar simultáneamente los hilos?
b) (0.5 puntos sobre 10) Explica el papel de los semáforos que has usado para resolver el 
problema.
c) (0.5 puntos sobre 10) ¿Puede haber varios vehículos cruzando de Norte a Sur y de Este 
a Oeste simultáneamente? Justifica tu respuesta.
5
Ejercicio 3: Monitores.
En este ejercicio, vamos a representar mediante un programa concurrente la actividad 
en un banco al que van los clientes a ser atendidos para realizar gestiones. Para ser atendido el 
banco dispone de 4 mesas para atender a la gente simultáneamente. Vamos a suponer que un 
cliente entra en el banco, tiene que ir a una máquina y elegir el servicio que necesita (esta 
acción tarda X milisegundos en realizarla). El banco tiene un total de 3 máquinas, el cliente se 
tendrá que poner en la primera que encuentre libre, una vez seleccione el servicio que necesita 
se dirige a la cola con el menor tiempo de espera. Cuando un cliente está siendo atendido tarda 
Y milisegundos en la mesa. Usando monitores para la sincronización, haremos una simulación 
donde habrá 50 hilos clientes con sus valores X, Y inicializados de forma aleatoria. 
La impresión en pantalla por parte de los hilos clientes justo antes de ponerse en la cola de 
la mesa elegida debe tener el siguiente formato:
--------------------------------------------------------------
“Cliente id ha solicitado su servicio en la máquina: -----
Tiempo en solicitar el servicio: X
Será atendido en la mesa: -----
Tiempo en la mesa = Y
Tiempo de espera en la mesa1=----, mesa2=-----, mesa3=-----, mesa4=-----”
--------------------------------------------------------------
donde id, -----, X, Y, son valores particulares para cada hilo. Los valores de mesa 1, mesa 2, 
mesa 3 y mesa 4 son los valores que le llevan al cliente a tomar la decisión de a qué mesa 
dirigirse, es decir, sin incluirse él mismo. Los tiempos X e Y se reflejarán mediante el método 
Sleep() de Java.
Desarrollar un programa concurrente en Java para resolver el problema anterior usando 
monitores como mecanismo para la sincronización.
Según las condiciones de sincronización del problema,
a) a) (0.5 puntos sobre 10) Indica si la acción de ser atendido en las mesas es 
concurrente o en exclusión mutua justificando la respuesta.
b) b) (0.75 puntos sobre 10) ¿Qué tipo de monitor Java has usado?. Justifica la 
respuesta.
c) c) (0.5 puntos sobre 10) En el monitor diseñado, ¿has usado notify/signal o 
notifyAll/signalAll? Justifica la respuesta.
d) d) (0.75 puntos sobre 10) ¿Cómo se ha resuelto la exclusión mutua de la pantalla en 
este problema?
6
Ejercicio 4: Paso de mensajes.
Consideremos un conjunto de 30 personas que compran y tiene que pagar las compra. 
Para pagar hay dos cajas, caja A y B siendo la caja A más rápida que la B. Cada persona tiene 
que usar una caja para pagar durante un tiempo estimado (valor aleatorio entre 1 y 10) por la 
persona que controla las cajas. El controlador de cajas, cuando recibe llega una nueva persona 
a pagar estima el tiempo de pago y a aquellas personas cuyo tiempo estimado sea mayor o 
igual a 5 se les asignará la caja más rápida, es decir, la A. A las demás personas se les asignará 
la caja B. Cada persona comprará y pagará 5 veces, es decir, el hilo repite 5 veces la siguiente 
secuencia de acciones: 1) Realiza la compra (representado con una llamada 
Thread.sleep(tiempoAleatorio)), 2) solicita ponerse en una caja, 3) Realiza el pago en la caja, 
4) libera la caja y 5) imprime en pantalla la información que se indica a continuación:
--------------------------------------------------------------
“Persona id ha usado la caja X
 Tiempo de pago= T
 Thread.sleep(T)
 Persona id liberando la caja X”
--------------------------------------------------------------
donde X es la caja asignada y T el tiempo asignado al trabajo que quiere imprimir el 
hilo.
Desarrollar un programa concurrente en Java para resolver el problema anterior usando 
como mecanismo para la sincronización el paso de mensajes asíncrono.
a) (0.5 puntos sobre 10) ¿Se pueden usar simultáneamente las dos cajas? Justifica la 
respuesta.
b) (0.5 puntos sobre 10) ¿Cómo has resuelto la exclusión mutua de la pantalla?
