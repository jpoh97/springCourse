# Inyecion de Dependencias

La inyeccion de dependencias es unas de las principales funcionalidades brindades por Spring framework, en esta seccion se mostrará con más cercania como es el funcionamiento de estas.

# Principios S.O.L.I.D en OOP!!.

## ¿Por que usar este principios?

La programacion orientada a objetos es un fuerte concepto, sin embargo esto asegura la calidad del desarrollo de software, los principios SOLID estan orientados en la gestion de las dependencias, cuando tenemos una gestion de dependencias pobre el codigo será fragil y dificil de cambiar.

## S. Single Responsability Principle, "Just because you can doesnt mean you should"

1. Cada clase deberá tener una sola responsabilidad.

2. Evade las clases "dios".

3. Tus clases deben de ser pequeñas.

4. Si tienes clases grandes, busca maneras de dividirlas en clases pequeñas.

En los codigo legacy se puede observar mucho este tipo de comportamiento en los cuales se pueden encontrar clases con aproximadamente 2000 lineas de codigo.
## O. Open Closed Principle

1. Tus clases deben estar abiertas para extensiones pero cerrada para modificaciones.

2. Se debe ser capaz de extender el comportamiento de una clase, sin modificarla.

3. Usa variables privadas con sus respectivos setter y getter cuando se necesiten.

4. Usar class y metodos abstractos.

## L. Liskov Substitution Principle

1. Los objetos en un programa podran ser reemplazados por instancias de sus subtipos sin alterar la exactitud del programa.

## I. Integration Segregation Principles

1. Crear interfaces granulares que el cliente especifique.

2. Diferentes interfaces especificas son mejor que una interfaz general.

3. Manten tus componentes enfocados y minimiza la dependencia entre ellos.

4. Evade la interfases Dioses.

## D. Dependency Inversion Principle

1. Las abstracciones no deberian de depender sobre los detalles

2. Los detalles no deberian depender sobre las abstracciones.

3. Es importante que los objetos a alto y bajo nivel dependan de la misma interaccion de abstraccion.

# Spring Aplication Context

En el contexto estaran almacenados todos los Spring bean utilizados por la aplicacion, allí spring especificará que es necesario para cada uno de estos y realizará la inyeccion de dependencias de los mismos.

Ejemplos.

1. Crearemos un pequeño controlador (cuando añadimos la anotamos @Controller automaticamente Spring lo interpreta como un bean.)

		package guru.springframework.didemo.controllers;

		import org.springframework.stereotype.Controller;

		@Controller
		public class MyController {

		    public String hello() {
		       System.out.println("Hello !!");
		       return "foo";
		    }
		}

2. Con nuestro controlador/bean creado, ahora veremos como se comporta en el contexto, para esto deberemos capturarlo desde nuestra aplicacion y podremos observar que nuestro controlador ya fue instanciado por Spring.

package guru.springframework.didemo;

import guru.springframework.didemo.controllers.MyController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

		@SpringBootApplication
		public class DiDemoApplication {

			public static void main(String[] args) {

				ApplicationContext ctx = SpringApplication.run(DiDemoApplication.class, args);
				MyController controller = (MyController) ctx.getBean("myController");
				controller.hello();
			}
		}

3. Con estos dos pasos podremos ejecutar nuestro proyecto y el resultado será la implemetacion de nuestro metodo hello, es decir un mensaje de "Hola" en la consola. Lo que nos quiere decir que spring se encarga del gestionamiento de lso beans en su contexto en donde instancian clases que son nombradas como beans.

# Basic Dependency Injection

1. La Inyeccion de dependencias es donde una dependencia necesitada es inyecatada por otro objeto.

2. La clase siendo inyectada no tiene la responsabilidad de intanciar el objeto siendo inyectado (Evitamos la declaracion de objetos new Object)

## Tipos de Inyecion de dependencias.

1. Por propiedades de clases(Menos preferido) --> Usando propiedades privates (No recomendado).

2. Por medio de Setters (Area de mucho debate).

3. Por medio de constructores (Más preferido).

## Concrete Claseses Vs Interfaces

1. DI puede realizarce sobre clases concretas o interfaces.

2. Generalmente DI en clases concretas debe evitarse.

3. DI con interfaces es más preferido, nos permite en tiempo de ejecucion decidir la implementacion a inyectar. Sigue el principio de segregation Interface ademas crea un codigo mucho más testeable.

## Inversion de Control IoC

1. Es una tecnica que termite que las dependencias sean inyectadas en tiempo real.

2. Las dependencias no estan predeterminadas.

Los marcos de trabajo amenudo juegan el rol de programa principal en la coordinacion y secuenciamiento de la actividad del programa . Esta invserion de control brinda a el marco de trabajo el poder de servir como un esqueleto extensible.

## Diferencia en IoC y DI

1. DI hace más referencia a la composicion entre clases, tu compones tus clases con DI en mente.

2. IoC es ambiente del codigo en tiempo real,  Spring Framework IoC container.

