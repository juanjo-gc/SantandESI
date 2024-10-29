package es.uca.santandesi.component;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import es.uca.santandesi.data.entity.Categoria;
import es.uca.santandesi.data.entity.Cliente;
import es.uca.santandesi.data.entity.Consulta;
import es.uca.santandesi.data.entity.Cuenta;
import es.uca.santandesi.data.entity.Mensaje;
import es.uca.santandesi.data.entity.Operador;
import es.uca.santandesi.data.service.CategoriaService;
import es.uca.santandesi.data.service.ConsultaService;
import es.uca.santandesi.data.service.CuentaService;
import es.uca.santandesi.data.service.MensajeService;
import es.uca.santandesi.data.service.MovimientoService;
import es.uca.santandesi.data.service.MovimientoService.InvalidTransactionException;
//import es.uca.santandesi.data.service.PagoService;
//import es.uca.santandesi.data.service.TarjetaService;
import es.uca.santandesi.data.service.UsuarioService;

@Component
public class InicializacionBD{

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private CuentaService cuentaService;
	//@Autowired
	//private TarjetaService tarjetaService;
	@Autowired
	private MovimientoService movimientoService;
	//@Autowired
	//private PagoService pagoService;
	@Autowired
	private ConsultaService consultaService;
	@Autowired
	private MensajeService mensajeService;
    
	@EventListener(ApplicationReadyEvent.class)
	public void runAfterStartup() {
	    System.out.println("Inicializando base de datos...");
	    //Usuarios
	    Cliente cliente1 = new Cliente("David", "Martínez Orellana", "dmo@gmail.com", "123456", LocalDate.of(2000, 7, 17), "12345678A");
		cliente1 = (Cliente)usuarioService.update(cliente1);
		
		Cliente cliente2 = new Cliente("Juan José", "Gil Calle", "jjgc@gmail.com", "123456", LocalDate.of(2000, 7, 26), "12345678J");
		cliente2 = (Cliente)usuarioService.update(cliente2);
		
		Operador operador = new Operador("Diego", "Filgueira Campelo", "dfc@gmail.com", "123456", LocalDate.of(2000, 12, 31), "12345678D");
		operador = (Operador)usuarioService.update(operador);
		
		//Categorias
		categoriaService.update(new Categoria("Información general"));
		categoriaService.update(new Categoria("Error de transación"));
		categoriaService.update(new Categoria("Gestión de cuentas y tarjetas"));
		categoriaService.update(new Categoria("Robo de datos y seguridad"));
		categoriaService.update(new Categoria("Cancelación de movimientos"));
		
		//Cuentas
		Cuenta cuenta1 = new Cuenta("joven", new BigDecimal(3000.13));
		cuenta1 = cuentaService.update(cuenta1);
		usuarioService.addCuenta(cliente1, cuenta1);
	    cliente1 = (Cliente)usuarioService.update(cliente1);
	    
		Cuenta cuenta2 = new Cuenta("joven");
		cuenta2 = cuentaService.update(cuenta2);
		usuarioService.addCuenta(cliente2, cuenta2);
	    cliente2 = (Cliente)usuarioService.update(cliente2);
	    
		Cuenta cuenta3 = new Cuenta("estandar", new BigDecimal(100));
		cuenta3 = cuentaService.update(cuenta3);
		usuarioService.addCuenta(cliente2, cuenta3);
	    cliente2 = (Cliente)usuarioService.update(cliente2);
		
		//Tarjetas
		
		
		//Movimientos
		try {
			movimientoService.procesarTransacion(new BigDecimal(100.49), "Recepción de Transferencia de préstamo", cuenta1.getIban(), cuenta2.getIban());
			
			movimientoService.procesarTransacion(new BigDecimal(5), "Bizum de Juanjo", cuenta3.getIban(), cuenta1.getIban());
		} catch (InvalidTransactionException e) {
			e.printStackTrace();
		}
		
		//Pagos
		//Consultas
    	Consulta consulta = consultaService.update(new Consulta("Cancelar mi tarjeta", cliente2, operador, categoriaService.getCategoria("Gestión de cuentas y tarjetas")));
    	Mensaje mensaje = mensajeService.update(new Mensaje(consulta, "Hola buenas, me gustaría cancelar mi tarjeta de crédito", cliente2));
    	
    	consulta.addMensaje(mensaje);
    	consulta = consultaService.update(consulta);
		
	    
	    System.out.println("Finalizada la inicializacion de la base de datos.");
	}
}
