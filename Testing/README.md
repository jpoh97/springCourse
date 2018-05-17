# Spring Test

# Code Under Test

Es el codigo o aplicacion que será testeada.

#Unit test 

1. Codigo escrito para testear el Code Under Test.

2. Desieñado para testear secciones especificas del codigo.

3. Deberá ser "unity"(pequeño) y ejecutarse muy rapido.

4. No deberá tenera dependencias externas(NO DB, NO spring context).

# Integration Test

1. Diseñado para testear el comportamiento entre objetos o partes de el sistema en general.

2. Tienen un alcanze mucho más grande.

3. Pueden incluir the Spring Context, Database y brokers the mensajerias.

4. Correran mucho más lentos que los unit test.

# Functional test

1. Tipicamente significan el testeo de la aplicacion corriendo.

2. La aplicaccion esta live, probablemente desplegada en un ambiente conocido.

3. Puntos funcionales son probados(Calling web service, envio y recepcion de mensajes etc.)

# TDD (Test Driven Development)

"Write tests first, whick will fail, then code to fix test."

# BDD (Behavior Driven Development) 

Basado en TDD, especifica que los test de cualquier parte del software deberan ser especificados en terminos del compartamiento deseado para esa unidad(Given, When, Then).

Ejemplos de BDD --> JBehave, cucunber, spock


# Mock

Una false implementacion de una clase usadad para el testeo. Como un test doble.

# Spy

Un mock parcial, permitiendo sobreescribir los metodos seleccionados de clases reales.

# Objetivos del Testing

1. Generalmente, tu querrás que la mayoria de tus test sean unit test.

2. Trata de testear logica de negocio especifica in unit test.

3. Usa integration test para probar interacciones.

4. Piensa en una piramide, la base son los unit test, el medio son los test de integracion y la cima son los test funcionales.

#Test Scope Dependencies

Usando spring-boot-starter-test:

1. Junit: El estandar para unit test para aplicacion Java.

2. AssertJ -> Asertion library.

3. Mockito --> A java mockito framework.

#JUnit 4 Anotaciones

Es el framework para testeo más popular en la comunidad de Spring

@Test --> Identifica a un metodo como un metodo de testeo.

@Before --> Ejecuta antes de cada test. Es usado para preparar el ambiente del test(leer variables de entrada, inicializar clases).

@After --> Se ejecuta despues de cada test. Es usado para limpiar el ambiente del test. Este puede ademas salvar mucha memoria limpiando algunas costosas estructuras en memoria.

@Ignore --> Marca que el test deberá estar desabilitado.

@Test(Expected = Exception.class) --> Falla si el metodo no lanza una expeccion nombrada.(es muy usada cuando se espera una excepcion.)

@Test(timeout = 10) --> Falla si el metodo se demorá más de 100 mili segundos

#Spring Boot Anotations

@RunWith(SpringoRunner.class) --> Corre los test con el Spring Context, le permite a spring trabajar con junit

@SpringoBootTest --> Busca para spring boot Aplicacion para configuracion.

@TestConfiguration --> Especifica un configuracion spring para tu test.

@MockBeab --> Injecta un mock mockito.

@SpyBean --> Injecta un spy mockito.

@DataJpaTest --> Usado para testear la capa de datos embebidad en la base de datos.

@Transactional --> Corre el test en transaction, hace rollback cuando complete por defecto.

@BeforeTransaction --> Accion a correr antes de empezar la transaccion

@AfterTransaction --> Accion a correr despues de empezar la transaccion

@Commit --> Especifica que la transaction deberá ser comiteada despues del test.

@Rollback --> Especifica que la transaction deberá hacer rollback despues del test.

@Sql --> Especifica scripts sql a correr antes.

@Repeat --> Repite un test x numero de veces.

# Creating a Junit Testing


public class CategoryTest {

    Category category;

    @Before
    public void setUp() {
        category = new Category();
    }

    @Test
    public void getId(){
        Long idValue = 4l;
        category.setId(4l);
        assertEquals(idValue, category.getId());
    }
}

# Usando Mockito

Clase a testear.

		@Controller
		public class indexController {

		    private final RecipeService recipeService;


		    public indexController(RecipeService recipeService) {
		        this.recipeService = recipeService;
		    }

		    @RequestMapping({"","/","/index"})
		    public String IndexPage(Model model) {
		        log.debug("Im in the controller");
		        model.addAttribute("recipes", recipeService.getRecipe());

		        return "index";
		    }
		}

Test de IndexPage


		public class indexControllerTest {

		    indexController indexController;
		    @Mock
		    RecipeService recipeService;
		    @Mock
		    Model model;
		    @Before
		    public void setUp() throws Exception {
		        MockitoAnnotations.initMocks(this);
		        indexController = new indexController(recipeService);
		    }

		    @Test
		    public void indexPage() {

		        // given
		        Set<Recipe> recipes = new HashSet<>();
		        Recipe recipe1 = new Recipe();
		        recipe1.setId(1l);
		        Recipe recipe2 = new Recipe();
		        recipe1.setId(2l);
		        Recipe recipe3 = new Recipe();
		        recipe1.setId(3l);
		        recipes.add(recipe1);
		        recipes.add(recipe2);
		        recipes.add(recipe3);

		        when(recipeService.getRecipe()).thenReturn(recipes);

		        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

		        //when
		        String viewName = indexController.IndexPage(model);

		        //then
		        assertEquals("index", viewName);
		        verify(recipeService, times(1)).getRecipe();
		        verify(model, times(1)).addAttribute(anyString(), argumentCaptor.capture());
		        Set<Recipe> indRecipes = argumentCaptor.getValue();
		        assertEquals(2, indRecipes.size());


		    }
		}		

# MockMVC

Brinda la capidad de testear spring Mvc controlers y actualmente hacerles unit test, este tendrá un mock servlet context, en tiempos pasados para poder probar un controller era incluso necesario un servidor de pruebas, ahora Mock MVC brinda la capacidad de un mock web server 

		    @Test
		    public void testMockMVC() throws Exception {
		        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

		        mockMvc.perform(get("/")).
		                andExpect(status().isOk())
		                .andExpect(view().name("index"));
		    }


# Integrations Tests

Para realizar Integration test contra la base de datos deberemos utilizar las anotaciones @DataJpaTest y @runWith

		@RunWith(SpringRunner.class)
		@DataJpaTest
		public class UnitOfMeasureRepositoryIT {

		    @Autowired
		    UnitOfMeasureRepository unitOfMeasureRepository;

		    @Before
		    public void setUp() throws Exception {

		    }

		    @Test
		    public void findByDescription() {
		        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Each");
		        assertEquals("Each",unitOfMeasureOptional.get().getDescription());
		    }

		    @Test
		    public void findByDescriptionCup() {
		        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Cup");
		        assertEquals("Cup",unitOfMeasureOptional.get().getDescription());
		    }

		}		    

# Add Maven Fail Safe
Maven failsafe será un plugin que nos permitira realizar la fase de test de maven tanto para pruebas unitarias como para pruebas de integracion, para poder utilizar este plugin deberemos añadir el siguiente codigo en nuestro archivo pom, en la seccion de pluggins.

			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-failsafe-plugin</artifactId>
				<version>2.20</version>
				<configuration>
					<includes>
						<include>**/*IT.java</include>
					</includes>
					<additionalClasspathElements>
						<additionalClasspathElement>${basedir}/target/classes</additionalClasspathElement>
					</additionalClasspathElements>
					<parallel>none</parallel>
				</configuration>
				<executions>
					<execution>
						<goals>
							<goal>integration-test</goal>
							<goal>verify</goal>
						</goals>
					</execution>
				</executions>
			</plugin>









