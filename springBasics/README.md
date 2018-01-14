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

