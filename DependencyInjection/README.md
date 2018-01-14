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

3. Con estos dos pasos podremos ejecutar nuestro proyecto y el resultado será la implemetacion de nuestro metodo hello, es decir un mensaje de "Hola" en la consola. Lo que nos quiere decir que spring se encarga del gestionamiento de lso beans en su contexto.

