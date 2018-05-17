# JPA (Java Persistence API)

# One to One

Una entidad esta relacionada con otra entidad, se usa la anotacion @OneToOne

Para explicar esta relacion realizaremos un ejemplos con dos entidades, recipe and Notes, para cada una de estas se deberan agregar sus respectivos Setter y Getter.

Recipe

		@Entity
		public class Recipe {

		    @Id
		    @GeneratedValue(strategy = GenerationType.IDENTITY)
		    private Long id;

		    private String description;
		    private Integer prepTime;
		    private Integer cookTime;
		    private Integer servings;
		    private String source;
		    private String url;
		    private String directions;

		    // to add
		    //private Difficulty difficulty;
		    @Lob
		    private Byte[] image;

		    @OneToOne(cascade = CascadeType.ALL)
		    private Notes notes;

		}

Notes


		@Entity
		public class Notes {

		    @Id
		    @GeneratedValue(strategy = GenerationType.IDENTITY)
		    private Long id;

		    @OneToOne
		    private Recipe recipe;

		    @Lob
		    private String recipeNotes;
		}

Como se puede observar inicialmente es necesario que la clase esta anotada con @Entity para que spring sepa que estamos haciendo referencia a una clase que debera ser mapeada con la base de datos, posteriormente deberemos espeficar para cada una de las entidades cual será el Identificador unico, esto se hará con la etiqueta @Id y tambien diremos como queremos generar este identificador, para esto utilizamos la linea @GeneratedValue(strategy = GenerationType.IDENTITY). Despues de tos deberemos definir la relacion entre ambas entidades, para este ejemplo lo haremos con la anotacion @OneToOne en donde especificaremos que una entidad de recipe tiene asociada otra entidad de Notes, adicionalmente como pueden observar en Recipe hay una configuracion extra en la anotacion @OneToOne y es cascade, esta servirá para reflejar los cambios en las relaciones hijas de Recipe, por ejemplo si borramos un Recipe entonces tambien se borrará su Note asociada. Por ultimo tenemos la anotacion @Lob la cual es util para decirle a hibernate que el atributo anotado con Lob será de un tamaño extenso.

# One To Many



Una entidad esta relacionada a muchas entidades (List, Sets, Map, SortedSet, Sorted Map). Se usa la anotacion @OneToMany

# Many To One

La relacion inversa a One to Many y la anotacion usada será @ManyToOne

A cotinuacion realizaremos un ejemplo de relacion One to Many bidireccional, para esto tendremos dos entidades Recipe y Ingredents donde Para una entidad de recipe habran muchas relaciones con Ingredents es decir, para un recipe habran muchos ingredetens.

		@Entity
		public class Recipe {

		    @Id
		    @GeneratedValue(strategy = GenerationType.IDENTITY)
		    private Long id;

		    private String description;
		    private Integer prepTime;
		    private Integer cookTime;
		    private Integer servings;
		    private String source;
		    private String url;
		    private String directions;

		    // to add
		    //private Difficulty difficulty;
		    @Lob
		    private Byte[] image;

		    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
		    private Set<Ingredent> ingredents;

		    }

		@Entity
		public class Ingredent {

		    @Id
		    @GeneratedValue(strategy = GenerationType.IDENTITY)
		    private Long id;

		    private String description;
		    private BigDecimal amount;

		    @ManyToOne
		    private Recipe recipe;

		}

Como pueden observar en el anterior ejemplo, tendremos una relacion bidireccional OneToMany en donde la entidad recipe esta relacionada con muchas entiades Ingredent, este comportamiento lo podemos evidencia en la anotacion @OneToMany de la Entidad Recipe, ademas tambien usamos la configuracion de cascada en esta entidad, para que cumpla con los requisitos de bidireccionalidad, tambien deberemos definir en nuestra entidad Ingredent la anotacion @ManyToOne en donde haremos referencia de la entidad Ingredent a la entidad Recipe.


# Many to Many

Esta es la relacion más complicada de todas, en la cual a muchas entidades le corresponde muchas relaciones.

Muchas entidades estan relacionadas con muchas entidades.

Cada uba tiene un Set o List que hace referencia a otro.

Una tabla de union es definida para las relaciones.

Para ejemplificar la forma en que relacionaremos entidades many to many, realizaremos el siguiente ejemplo, tendremos dos entidades Recipe y Category estas entre si tienen una relacion muchos a muchos, como mencionamos antes para poder mapear esta relacion deberemos tener una tabla intermedia o Join table, entre las claves primarias de estas dos entidades, a continuacion realizaremos la implementacion de many to many,

En la entidad Recipe

	    @ManyToMany
	    @JoinTable(name = "recipe_category",
	        joinColumns = @JoinColumn(name = "recipe_id"),
	        inverseJoinColumns = @JoinColumn(name = "category_id"))
	    private Set<Category> categories;


En la entidad Category

			
	    @ManyToMany(mappedBy = "categories")
	    private Set<Recipe> recipes;

Con la implementacion anterior bastará para la relacion Many To Many, inicialmente como es una integracion bidireccional entonces en ambas entidades se deberá aplicar la anotacion @ManyToMany, sin embargo para que esta implementacion funciones deberemos crear una tabla de union, esto lo haremos con la anotacion @JoinTable(name = "union"), asi le estaremos diciendo que nuestra tabla de union se llama "union", luego deberemos decirles que columnas deberemos "enviar" a la tabla de union, para esto obtendremos de nuestra entidad recipe la columna "recipe_id" y lo haremos de la siguiente manera joinColumns = @JoinColumn(name = "recipe_id"), tambien deberemos definir la columna que vendrá del otro lado de la relacion es decir de la entidad Category y para esto lo realizaremos asi inverseJoinColumns = @JoinColumn(name = "category_id")). como ultimo paso deberemos especificar en nuestra entidad Category que la relacion con la entidad Recipe esta determinada por el atributo "categories", y spring sabrá que haremos referencia a el atributo creado en la clase Recipe.

# Enums

Para el manejo de Enums deberemos realizar los siguientes pasos:

1. Inicialmente deberemos definir nuestro enum.

		public enum Difficulty {

		    EASY, MODERATE, HARD
		}

2. Posteriormente deberemos anotar a la entidad que usa este Enum de la siguiente manera.

		    @Enumerated(value = EnumType.STRING)
		    private Difficulty difficulty;


# Undirectional vs Bidirectional

Undirectional es un unico camino, el mapeo solo es realizado en una direccion, es decir un lado de la relacion no sabrá acerca del otro.

Bidirectional es en ambos caminos, ambos lados se concoes entre si, generalmente es recomendado usar bidireccional asi podrás navegar en el grafo en cualquier direcccion.

# Owning Side

El "Owning Side" en la relacion, mantendrá la llave foranea en la base de datos.

One to One es el lado donde la clave foranea esta especificada.

OneToMay y ManyToOne es en el Many side.

mappedBy es usada para definir el campo con "owns" de la relacion.

# Creating Repositories.

La creacion de repositorio es utilizada para usar operacion CRUD ya predefinidas por Spring, para esto deberemos realizar los siguientes pasos.

1. Creamos una interface que herede CrudRepository<param1, param2> donde param1 será el nombre de la entidad y param2 será el tipo de variable de la clave o id de la entiadd.

		public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}

# Exportacion de informacion

Spring nos permite importar informacion a nuestra bd h2, una vez sea iniciado el servidor, recordemos que h2 es una base de datos en memoria, por ende la informacion será eliminada una vez que el servidor este abajo, para subir informacion a memoria realizaremos los siguientes pasos.

1. Crearemos un archivo llamado data.sql en resources.

2. Insertaremos la informacion que deseemos en este archivo, usando sentencias SQL.

		INSERT INTO category (description) VALUES ('Mexican');
		INSERT INTO category (description) VALUES ('Fast food');
		INSERT INTO unit_of_measure (description) VALUES ('Teaspon');

3. Usualmente el editor nos solicita que debemos cambiar el dialecto utilizado, para esto deberemos cambiarlo y especificarle el dialecto de MySql

4. Iniciar la aplicacion.

# JPA Query Methods

falta documentacion 


# Project Lombok	

El proyecto lombok es una herramienta utilizada para que mediante anotaciones se produzca adecuadamente codigo java compilado para cierta funcionalidad.

IDES como IntellIJ, Eclipse, Netbeans pueden soportar Project Lombok.

Será necesario instalar un pluggin.

A continuacion se describiran algunas de las anotaciones de el proyecto Lombok.

1. @Getter --> Crea un metodo getter para todas las propiedades.

2. @Setter --> Crea un metodo setter para todas las propiedades no-final

3. @ToString --> Genera un string con el nombre de la clase y cada campo separado por comas.

4. @EqualAndHashCode --> Genera una implemetacion de equals y hashCode, por defecto usará todo los no-staticas y no-transient propiedades.

5. @NoArgsConstructor --> Genera un constructor sin argumentos. Causa un error en el compilar si hay campos final. Puede opcionalmente forzarlo a que inicialice los campos final con 0, false o null.

6. @RequiredArgsConstructor --> Genera un constructor para todos los campos que son final o marcados con @NotNull, el constructor lanzará un NullPointerException if cualquier @NotNull Field es null.

7. @Data --> Genera el tipico codigo repetitivo para los POJOS. Combina @Getter, @Setter, @ToString, @EqualsAndHasCode y @RequiredArgsContructor. 

8. @Value --> Es la variable inmutable de @Data, Todos los campos son creado privados y final por defecto.

9. @NotNull --> Setea un parametro de metodo o constructor y NullPointerException será lanzado si el parametro es null

10. @Builder --> Implementa el patron Builder para la creacion del objeto.

		Person.builder().name("adam").city("Medellin").job("DEveloper").build();

11. @Syncronized --> Una implementacion de Java synchronized.

12. @Log --> Crea un Logger Java.

13. @Slf4j --> Crea un logger SLF4J, es un logging facade.

# Adding Project Lombok IDE

1. Add Maven dependency, primero en nuestro archivo pom.xml deberemos añadir la siguiente dependencia.

			<dependency>
				<groupId>org.project.lombok</groupId>
				<artifactId>lombok</artifactId>
			</dependency>

2. Luego en settings --> pluggins --> browser repositories --> type lombok --> select and install "Lombok Pluggin".

# Ejemplos Spring + Project Lombok.

Falta Documentacion

# Adding Boostrap Project

