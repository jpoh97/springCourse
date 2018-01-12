# Spring

## Requisitos.

1. JDK 1.8
2. Gradle
3. Maven

## Spring Boot

En la tarea de construccion de aplicacion web podemos encontrar tres pasos generales, el primero es la construccion de la aplicacion con todas sus dependecias, el segundo es la etapa de desarrollo de software y el tercero es el despliegue de la aplicacion en un servidor.

Spring Boot fue creado para simplificar los pasos 1,3. Su principal funcion es crear proyectos pre-configurados con todas sus dependencias, y que esten listos para ser desplegado.

Pasos

1. Inicialmente entraremos en la siguiente pagina para construir nuestro proyecto pre-configurado (Spring Initializr)
		
		https://start.spring.io/
		
2. En esta pagina deberemos empezar llenando unas configuraciones bases, como modo de ejemplo seleccionaremos las siguientes.

		Generate a --> Maven
		With --> Java
		Spring Boot --> 2.0.0(Snapshot)
		Group --> group.springwork (Se puede poner cualquier nombre)
		Name --> springFrameWork (Se puede poner cualquier nombre)
		
3. Luego deberemos seleccionar la opcion "Switch to the full version" y allí definir las caracteristicas de nuestro proyecto, como modo de ejemplo seleccionaremos la siguientes opciones.

		Web -->  Web, full stack develpment with tomcat and Spring MVC
		Sql -->  JPA, Java Persistence API, including psring-data-djpa, spring- orm and hibernate
		Sql -->  H2 Database
		Template Engines --> Thymeleaf
		Ops -->  ACtuator, production ready features to help you monitor and manage your application
		
4. Luego de realizar las configuraciones procederemos a darle en "Generate Project alt + enter".

Con los pasos anteriores ya tendremos nuestro proyecto preconfigurado y listo para desplegar un servidor tomcat.

## Abrir el proyecto en nuestro IDE.

Para realizar esta tarea deberemos seguir los siguientes pasos.

1. Abrir nuestro IDE de seleccion y elegir la opcion "Import Project".

2. En la configuracion del proyecto deberemos colocar las siguientes caracteristicas Maven y JDK 1.8 para que concuerde con la configuracion que seleccionamos en nuestro proyecto pre-configurado.

3. Deberemos esperar un momento para que nuestro IDE indexe el proyecto y estaremos listo para desplegar, para eso utilizaremos el comando

		mvn spring-boot:run
		
Y tendremos nuestro proyecto Desplegado !!.	

#JPA (Java Persistence API)
