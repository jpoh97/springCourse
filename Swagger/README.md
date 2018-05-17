# Swagger

The goal of Swagger is to define a standart, languague-agnostic interface to Rest APIs which allows both hummans and computers to discover and understan the capabilities of the service without access to source, code, documentation , or through network traffic inspection.

Swagger for REST is equivalent to WSDL in SOAP but is not formal.

## What is Swagger?

	1. Swagger is more than documentation

	2. Swagger is a specification.

	3. Json for metadata.

	4. Json API definition

	5. JSON for schema for the model specification.

	6. The Swagger Specification is machine readable.

	7. Swagger is supported for most popular server side and cliente side languages.

## Swagger Eco System.

	1. Swagger UI - HTML, Javascript and CSS components to dynamically generate documentation from a swagger compilant API.

	2. Swagger Editor - Edit API Specification in YAML and preview documentation real time.

	2. Swagger Codegen - Create client libraries and server stubs from swagger definition.	

## Configuring Swagger

First of all in our pom.xml file we must to add the next dependencys.

		<properties>
			<springfox-swagger2.version>2.7.0</springfox-swagger2.version>
		</properties>

		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger2</artifactId>
			<version>${springfox-swagger2.version}</version>
		</dependency>
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger-ui</artifactId>
			<version>${springfox-swagger2.version}</version>
		</dependency>

Create config swagger class.

		@EnableSwagger2
		@Configuration
		public class SwaggerConfig {
		    }
		}

If you can see swagger-ui or you are not using Spring Boot try with the next config class.

@EnableSwagger2
@Configuration
public class SwaggerConfig { //} extends WebMvcConfigurationSupport {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/");
    }

		@Override
		protected void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry.addResourceHandler("swagger-ui.html")
		    .addResourceLocations("classpath:/META-INF/resources/");

			registry.addResourceHandler("/webjars/**")
		    .addResourceLocations("classpath:/META-INF/resources/webjars/");
		   }
		}

You can check if you swagger is working in the url

		http://localhost:8080/swagger-ui.html.				

Where  http://localhost:8080 is the ip of your server.

## Configuring Meta-DATA

We must change our config class. With the changes our config class will look like this.

		@EnableSwagger2
		@Configuration
		public class SwaggerConfig {

		    @Bean
		    public Docket api() {
		        return new Docket(DocumentationType.SWAGGER_2)
		                .select().apis(RequestHandlerSelectors.any())
		                .paths(PathSelectors.any())
		                .build()
		                .pathMapping("/")
		                .apiInfo(metaData());
		    }

		    private ApiInfo metaData() {
		        Contact contact = new Contact("Andres Montoya", "http://learning-swagger/about", "andres@spring.com");
		        return new ApiInfo(
		                "Spring FrameWork Learning !!",
		                "Spring FrameWork 5: APIs Rest",
		                "1.0",
		                "Terms of Services blhaaaaaaaaa",
		                contact,
		                "Apache License v2",
		                "https://wwww.apache.org/license/",
		                new ArrayList<>()
		        );
		    }
		}

## Customizing Endpoints Documentations

	1. Customize Controller Class.

	In each controller class we can add a extra documentation, explain what is this controller, and this way in our swagger-ui this information will be show and will be easier to understand.

		import io.swagger.annotations.Api;

		@Api(description = "This is my Vendor controller")
		@RestController
		@RequestMapping("/shop/vendors/")
		public class VendorController {
		}

	2. Customize Endpoints.

	In each endpoint or service exposed, we can define a brief explication about what this services does.

		import io.swagger.annotations.ApiOperation;

		@ApiOperation(value = "Get all the vendors", notes = "Here there are some notes.")
	    @GetMapping
	    @ResponseStatus(HttpStatus.OK)
	    public VendorListDTO getAllVendor() {
	        return new VendorListDTO(vendorService.getAllVendors());
	    }
