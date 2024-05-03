# Proyecto de Programación Concurrente y Distribuida

Este proyecto consiste en una serie de ejercicios que demuestran diferentes aspectos de la programación concurrente y distribuida en Java.

## Ejercicio 1

El primer ejercicio es una implementación de un productor, consumidores y un sumador. El productor genera números aleatorios y los almacena en un buffer compartido. Los consumidores leen estos números del buffer y los suman. Finalmente, el sumador suma todos los números generados por el productor.

## Ejercicio 2

El segundo ejercicio simula un cruce de vehículos y peatones controlado por semáforos. Los vehículos y peatones son representados por hilos que intentan cruzar en diferentes direcciones. Los semáforos controlan el acceso al cruce para evitar colisiones.

## Ejercicio 3

El tercer ejercicio simula el funcionamiento de un banco. Los clientes (representados por hilos) entran al banco, eligen el servicio que necesitan en una máquina y luego se dirigen a la cola con el menor tiempo de espera. Los clientes son atendidos en las mesas del banco.

## Ejercicio 4

El cuarto ejercicio simula el funcionamiento de una caja de supermercado. Las personas (representadas por hilos) compran y luego tienen que pagar en una de las dos cajas disponibles. El controlador de cajas asigna a cada persona a una caja basándose en el tiempo estimado de pago.

## Cómo ejecutar el proyecto

Para ejecutar el proyecto, necesitarás tener instalado Java en tu sistema. Puedes ejecutar cada ejercicio individualmente desde la línea de comandos con el comando `java` seguido del nombre del archivo `.java` correspondiente.

Por ejemplo, para ejecutar el primer ejercicio, navega hasta el directorio `PCD-03-05/src/Ejercicio1/` y ejecuta el siguiente comando:

```bash
java Programa.java
```

Repite este proceso para cada uno de los ejercicios.

## Contribuciones

Las contribuciones a este proyecto son bienvenidas. Si encuentras un error o tienes una sugerencia para mejorar el código, por favor, abre un issue o un pull request.

## Licencia

Este proyecto está licenciado bajo los términos de la licencia MIT.