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

# JPA (Java Persistence API)

1. Marcar Entidad.

2. Marcar Atributo ID.

3. Etiqueta ManyToMany

# Spring Data JPA

## Spring Data Repositories.

1. Permite sustituir la capa de persistencia.

2. Es parte de una larga familia de proyectos Spring.

3. Usa hibernate para el manejo de la persistencia.

4. Brinda una implementacion en tiempo real. 

Como mode ejemplo visualizaremos las operaciones CRUD con una base de datos h2(cache), inicialmente para esto deberemos adicionar las siguientes propiedades en nuestro aplication.properties

		spring.h2.console.enabled=true
		spring.h2.console.path=/ 

Una vez realizado esto, podremos desplegar nuestra aplicacion y accede a localhost:8080, allí nos saldrá una consola de h2 donde prodremos visualizar la informaciond e la BD h2		

# Spring MVC

MVC es un patron de diseño para GUI y web aplicactions.
M --> Modelo , V --> View , C --> Controller

## Configuando Spring MVC

1. Anotar clases controladoras con @Controller, con esto spring creará el clase como un bean lo cual será un componente de control de spring,

2. Mapear las peticionems http con la anotacion @RequestMapping

## Thymeleaf

1. Thymeleaf es un motor de template para Java.

2. Gano mucha popularidad en el mundo de Spring.

# Implementacion MVC.

Con los componentes anteriormente descritos procederemos a crear una implementacion con el patron de diseño MVC en SpringFrameWork. En nuestro proyecto bajo la ruta de main/java/guru.springframework.spring5webapp crearemos los siguientes tres directorios.

1. model = La carpeta model hará referencia a todos los Pojos necesarios para poder mapear la informacion almacenada en Base de datos como objetos java. Estos models deberán llevar diferentes anotaciones indicandole a Spring que son entidades.

		package guru.springframework.spring5webapp.model;

		import javax.persistence.*;
		import java.util.HashSet;
		import java.util.Set;

		@Entity
		public class Author {

		    @Id
		    @GeneratedValue(strategy = GenerationType.AUTO)
		    private Long id;
		    private String firstName;
		    private String lastName;

		    @ManyToMany(mappedBy = "authors")
		    private Set<Book> book = new HashSet();

		    public Author() {
		    }

		    public Author(String firstName, String lastName) {
		        this.firstName = firstName;
		        this.lastName = lastName;
		    }

		    public Long getId() { return id; }

		    public void setId(Long id) { this.id = id; }

		    public String getFirstName() { return firstName;}

		    public void setFirstName(String firstName) { this.firstName = firstName; }

		    public String getLastName() { return lastName; }

		    public void setLastName(String lastName) { this.lastName = lastName; }

		    public Set<Book> getBook() { return book; }

		    public void setBook(Set<Book> book) { this.book = book;}
		}

2. repositories = Esta carpeta esta creada con base en la implemetacion de las interfaces de los repositorios JPA, estas brindan lo necesario para obtener las operaciones basicas CRUD de nuestra base de datos.(Podremos evitarnos en patron DAO o configuracion directa con JDBC)

			package guru.springframework.spring5webapp.repositories;

			import guru.springframework.spring5webapp.model.Author;
			import org.springframework.data.repository.CrudRepository;

			public interface AuthorRepository extends CrudRepository<Author, Long>{
			}

3. controller = Esta carpeta hará referencia a nuestro controladores, estos serán los encargados de la capa logica de nuestra aplicacion y de intercomunicar los diferentes modelos o entidades con la capa de presentacion o templates html.

			package guru.springframework.spring5webapp.controllers;

			import guru.springframework.spring5webapp.repositories.AuthorRepository;
			import org.springframework.stereotype.Controller;
			import org.springframework.ui.Model;
			import org.springframework.web.bind.annotation.RequestMapping;

			@Controller
			public class authorController {

			    private AuthorRepository authorRepository;

			    public authorController(AuthorRepository authorRepository) {
			        this.authorRepository = authorRepository;
			    }

			    @RequestMapping("/authors")
			    public String getAuthors(Model model) {
			        model.addAttribute("authors", authorRepository.findAll());
			        return "authors";
			    }
			}

Adicionalmente bajo main/resources deberemos crear la siguiente carpeta.

1. templates = Esta carpeta hará referencia a todas nuestras vistas html, las cuales estaran enlazadas con el controlador.

			<!DOCTYPE html>
			<html lang="en" xmlns:th="http://www.thymeleaf.org">
			<head>
			    <meta charset="UTF-8"/>
			    <title>Spring Framework Guru</title>
			</head>
			<body>
			    <h1>Author List</h1>
			    <table>
			        <tr>
			            <th>ID</th>
			            <th>First Name</th>
			            <th>Last Name</th>
			            <th>Books</th>
			        </tr>
			        <tr th:each="author : ${authors}">
			            <td th:text="${author.id}">1023</td>
			            <td th:text="${author.firstName}">Andres</td>
			            <td th:text="${author.lastName}">Montoya</td>
			            <td>
			                <ul th:each="book : ${author.book}">
			                    <li th:text="${book.title}"></li>
			                    <li th:text="${book.publisher.name}"></li>
			                    <li th:text="${book.id}"></li>
			                </ul>
			            </td>
			        </tr>
			    </table>

			</body>
			</html>			

# Spring Configurations Options

1. Configuracion basada en XML. Se ve mucho en Aplicacion legacy Spring

Para la configuracion basada en XML, inicialmente deberemos crear en nuestro directorio "resource" un archivo xml para representar el bean.

		<?xml version="1.0" encoding="UTF-8"?>
		<beans xmlns="http://www.springframework.org/schema/beans"
		       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
		    <bean name="chuckNorrisQuotes" class="guru.springframework.norris.chuck.ChuckNorrisQuotes"> </bean>
		</beans>

Como se puede observar creamos un bean en la parte inferior, el cual llamaremos chuckNorrisQuotes y pertenece a la clase guru.springframework.norris.chuck.ChuckNorrisQuotes, con esto ya tendremos la configuracion de nuestro bean para ser inyectado, adicionalmente en nuestra clase principal deberemos importar el classpath de este archivo para que pueda ser reconocido por Spring.

package com.example.jokeapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

		@SpringBootApplication
		@ImportResource("classpath:chuck-config.xml")
		public class JokeappApplication {

			public static void main(String[] args) {
				SpringApplication.run(JokeappApplication.class, args);
			}
		}
		

2. Configuracion basada en anotaciones, fue introducida en Spring Framework 3, esta se basa en un scaneo de los componentes que son anotados a nivel de clase con @Controller, @Service, @Component, @Repository

3. Configuracion basada en JAVA, usa clases java para definir Spring Beans, las clases de configuracion son definidas con la anotacion @Configuration y los bean son declarados con la anotacion @Bean

		package com.example.jokeapp.config;
		import guru.springframework.norris.chuck.ChuckNorrisQuotes;
		import org.springframework.context.annotation.Bean;
		import org.springframework.context.annotation.Configuration;

		@Configuration
		public class ChuckConfiguration {

		    @Bean
		    public ChuckNorrisQuotes ChuckNorrisQuotes() {
		        return new ChuckNorrisQuotes();
		    }
		}

La clase ChuckNorrisQuotes no estaba anotada por ende Spring no la reconocia como un bean, para esto utilizamos la configuracion con Java @Configuration y anotamos el metodo ChuckNorrisQuotes como un bean para poder inyectarlo donde sea necesario.

	    private final ChuckNorrisQuotes chuckNorrisQuotes;

	    public JokeServiceImp(ChuckNorrisQuotes chuckNorrisQuotes) {
		        this.chuckNorrisQuotes = chuckNorrisQuotes;
		    }

4. Groovy Bean Definition DSL configuration, permite declarar beans in Groovy, prestado de grails

## Which Use ??

1. Se puede usar una combinacion de todos estos metodos.

2. Estos metodos trabajan sin problemas a la hora de definir beans en el contexto de spring.

3. La industria tiende a favor de la configuracion basada en JAVA

# Spring Stereotypes

Los Stereotypes son usadaos para definir Spring beans dentro del contexto de spring y esto se logra atravez de un scaneo de componentes en donde Spring busca en los diferentes paquetes por aquellas anotaciones referentes a stereotypes

1. @Component, Indica que la clase anotada es un componente y creará un bean(Si no tienes un rol en especifico puedes usar un componente)

2. @Controller, hace referencia a los controladores del patron de diseño MVC, adicionalmente pueden venir acompañados de @RequestMapping para las peticiones.

3. @RestController, Tiene la misma funcionalidad de @Controller añadiendo la funcionalidad de response body(Es una anotacion que sirve para renderizar un objeto como respuesta a una peticion.)

4. @Repository, es una anotacion que indica que estas accediendo a la capa de datos. Repository es un mecanismo para el encapsulamiento del storage y buscar emular el comportamiento de colecciones de objetos.

5. @Service, Indica que la clase anotada es un Service(Es una operacion ofrecida como una interface que permanece en el modelo sin ningun estado de encapsulamiento) 

# Componente Scan.

El scaneo de componentes solo se realiza en el paquete donde se encuentre la anotacion @SpringBootAplication usalmente esta en la clase DiDemoApplication el cual es creado por SpringBoot.

Para solucionar esto deberemos definir en cuales paquetes queremos que Spring realice la busqueda.

		@ComponentScan(basePackages= {"guru.services","guru.springframework"})

Con la anotacion anterior Spring ya no buscará en el paquete por defecto, por ende deberemos definir todos los paquetes en los cuales queremos buscar.	

# Spring Boot Configuration

1. Maven o Gradle son soportados.

2. Cada version de Spring Boot esta configurada para trabajar con una version especifica de Spring Framework.

3. Otros sistemas como Ant pueden ser usados pero no es recomendado.}

## Maven Support

1. Los proyectos maven heredan desde el Spring Boot parent POM.

2. Cuando no se especifique la version en tu POM, habilita la version que hereda de el "parent"

3. El Spring Boot Maven Plugin permite empaquetar ejecutables jar.

## Gradle Support.

1. Gradle Support depende de Spring Boot Gradle Plugin.

2. Requiere Gradle 3.4 o mayor.

3. Gradle plugin brinda soporte para el manejo de dependencias, empaquetando como jar o war, y permitiendo que corras la aplicacion desde la linea de comandos.

## Spring Boot Starters

1. Starters estan en el nivel top de dependencias populares para librearias Java.

2. Traeran dependencias para el proyecto y las relacionará con componentes Spring.

3. Starter 'spring-boot-started-data-jpa' trae en:

3.1 Hibernate
3.2 Spring Data JPA

# Anotacion Para Usar Spring Boot.

1. @SpringBootApplication : Es la principal anotacion para usar Spring boot, esta incluye.

1.1 @Configuration - Declara la clase como una Spring Configuration

1.2 @EnableAutoConfiguration - Habilita la configuracion automatica, adicionalmente auto-configuracion traerá muchas clases de configuracion que brindan los jar de Spring Boot, por ende podemos especificar las clases a excluir de la siguiente manera.

		@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})

1.3 @ComponentScan - Escanea por componentes en el paquete actual y a todos sus paquetes hijos.











