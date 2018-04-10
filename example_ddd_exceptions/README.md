# Exceptions

El manejo de excepciones es importante a la hora de construir una aplicacion, Spring nos brinda diferentes herramientas como @ResponseStatus
o ExceptionHandler que nos permitiran el manejo adecuado de las Excepciones

## ResponseStatus

Esta anotacion se encargará de traducir una excepcion del controlador a una mensaje de error de http.

Para esto solo deberemos crear una clase que extienda de RuntimeException y usando la etiqueda de la siguiente manera.

		package guru.springframework.exceptions;

		import org.springframework.http.HttpStatus;
		import org.springframework.web.bind.annotation.ResponseStatus;

		@ResponseStatus(HttpStatus.NOT_FOUND)
		public class NotFoundException extends RuntimeException {

			public NotFoundException() {
				super();
			}

			public NotFoundException(String message) {
				super(message);
			}

			public NotFoundException(String message, Throwable cause) {
				super(message, cause);
			}
		}
		
## Exception Handler

La funcionalidad de Exception Handler es brindar una visualizacion más entendible para el usuario por medio de una vista,
para esto es necesario crear un nuevo metodo que esta anotado con @ExceptionHandler(Clase de Excepcion).

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(){

        log.error("Handling not found exception");

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("404error");

        return modelAndView;
    }
	
	
Con el metodo anterior, en el instante que se ejecute un llamado a la clase NotFoundException este nos retornará la vista 
a la cual se hace uso en el metodo 	ModelAndView.
		
		