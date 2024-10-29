package es.uca.santandesi.data.service.autenticacionService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinSession;

import es.uca.santandesi.data.entity.Usuario;
import es.uca.santandesi.data.service.UsuarioRepository;
import es.uca.santandesi.views.MainLayout;
import es.uca.santandesi.views.consultas.ConsultasView;
import es.uca.santandesi.views.consultas.MostrarConsultasView;
import es.uca.santandesi.views.cuentas.AgregarCuentaView;
import es.uca.santandesi.views.cuentas.CuentaDetalleView;
import es.uca.santandesi.views.cuentas.CuentasView;
import es.uca.santandesi.views.cuentas.GestionCuentasView;
import es.uca.santandesi.views.movimientos.HistoricoMovimientosView;
import es.uca.santandesi.views.movimientos.MovimientosView;
import es.uca.santandesi.views.noticias.AgregarNoticiaView;
import es.uca.santandesi.views.noticias.GestionarNoticiasView;
import es.uca.santandesi.views.noticias.MostrarNoticiasView;
import es.uca.santandesi.views.noticias.NoticiaDetalleView;
import es.uca.santandesi.views.tarjetas.AgregarTarjetaView;
import es.uca.santandesi.views.tarjetas.GestionTarjetasView;
import es.uca.santandesi.views.tarjetas.HistoricoPagosView;
import es.uca.santandesi.views.tarjetas.TarjetaDetalleView;
import es.uca.santandesi.views.tarjetas.TarjetasView;
import es.uca.santandesi.views.usuarios.AgregarUsuarioView;
import es.uca.santandesi.views.usuarios.GestionUsuariosView;
import es.uca.santandesi.views.usuarios.VerPerfilView;

@Service
public class AutenticacionService {

	public class AuthException extends Exception {}
	public record RutasAutorizadas(String ruta, String nombre, Class<? extends Component> vista) {}
	
	private final UsuarioRepository _usuarioRepository;
	
    @Autowired
    public AutenticacionService(UsuarioRepository usuarioRepository) {
        _usuarioRepository = usuarioRepository;
    }
    
	public void autenticar(String dni, String pass) throws AuthException{
		
		Usuario usuario = _usuarioRepository.findByDNI(dni);
		
		if(usuario != null && usuario.checkContrasenna(pass)) {
			VaadinSession.getCurrent().setAttribute(Usuario.class, usuario);
			createRoutes(usuario.getRol());
		} else {
			throw new AuthException();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void createRoutes(String rol) {
		getRutasAutorizadas(rol).stream()
			.forEach(ruta ->
					RouteConfiguration.forSessionScope().setRoute(ruta.ruta, ruta.vista, MainLayout.class));
	}
	
	public List<RutasAutorizadas> getRutasAutorizadas(String rol) {
		
		var rutas = new ArrayList<RutasAutorizadas>();
		if(rol.equals("cliente")) {
			rutas.add(new RutasAutorizadas("perfil", "Tu perfil", VerPerfilView.class));
			rutas.add(new RutasAutorizadas("cuentas", "Cuentas", CuentasView.class));
			rutas.add(new RutasAutorizadas("movimientos", "Movimientos", MovimientosView.class));
			rutas.add(new RutasAutorizadas("tarjetas", "Tarjetas", TarjetasView.class));
			rutas.add(new RutasAutorizadas("consulta", "Realizar consulta", ConsultasView.class));
			rutas.add(new RutasAutorizadas("mostrarConsultas", "Tus consultas", MostrarConsultasView.class));
			rutas.add(new RutasAutorizadas("historicoMovimientos", "Historico de movimiento", HistoricoMovimientosView.class));
			rutas.add(new RutasAutorizadas("historicoPagos", "Historico de pagos", HistoricoPagosView.class));
			rutas.add(new RutasAutorizadas("noticias", "Noticias", MostrarNoticiasView.class));
			rutas.add(new RutasAutorizadas("detalleNoticia", "Detalles de la noticia", NoticiaDetalleView.class));
			rutas.add(new RutasAutorizadas("detalleCuenta", "Detalles de la cuenta", CuentaDetalleView.class));
			rutas.add(new RutasAutorizadas("detalleTarjeta", "Detalles de la tarjeta",TarjetaDetalleView.class));

		} else if(rol.equals("operador")){
			rutas.add(new RutasAutorizadas("perfil", "Tu perfil", VerPerfilView.class));
			rutas.add(new RutasAutorizadas("agregarUsuario", "Registrar usuario", AgregarUsuarioView.class));
			rutas.add(new RutasAutorizadas("agregarCuenta", "Agregar Cuenta", AgregarCuentaView.class));
			rutas.add(new RutasAutorizadas("agregarTarjeta", "Agregar tarjeta", AgregarTarjetaView.class));
			rutas.add(new RutasAutorizadas("responderConsultas", "Responder consultas", MostrarConsultasView.class));
			rutas.add(new RutasAutorizadas("gestionarUsuarios", "Gestionar usuarios", GestionUsuariosView.class));
			rutas.add(new RutasAutorizadas("gestionarCuentas", "Gestionar cuentas", GestionCuentasView.class));
			rutas.add(new RutasAutorizadas("gestionarTarjetas", "Gestionar tarjetas", GestionTarjetasView.class));
			rutas.add(new RutasAutorizadas("gestionarNoticias", "Gestionar noticias", GestionarNoticiasView.class));
			rutas.add(new RutasAutorizadas("agregarNoticia", "Agregar noticia", AgregarNoticiaView.class));
		}
		
		return rutas;
	}
	
    public void logout() {
    	String rol = VaadinSession.getCurrent().getAttribute(Usuario.class).getRol();
    	if(rol.equals("cliente")) {
    		RouteConfiguration.forSessionScope().removeRoute("perfil");
        	RouteConfiguration.forSessionScope().removeRoute("cuentas");
        	RouteConfiguration.forSessionScope().removeRoute("movimientos");
        	RouteConfiguration.forSessionScope().removeRoute("tarjetas");
        	RouteConfiguration.forSessionScope().removeRoute("consulta");
        	RouteConfiguration.forSessionScope().removeRoute("mostrarConsultas");
        	RouteConfiguration.forSessionScope().removeRoute("historicoMovimientos");
        	RouteConfiguration.forSessionScope().removeRoute("historicoPagos");
        	RouteConfiguration.forSessionScope().removeRoute("noticias");
        	RouteConfiguration.forSessionScope().removeRoute("detalleNoticia");
        	RouteConfiguration.forSessionScope().removeRoute("detalleCuenta");
        	RouteConfiguration.forSessionScope().removeRoute("detalleTarjeta");
    	} else {
    		RouteConfiguration.forSessionScope().removeRoute("perfil");
        	RouteConfiguration.forSessionScope().removeRoute("agregarUsuario");
        	RouteConfiguration.forSessionScope().removeRoute("agregarCuenta");
        	RouteConfiguration.forSessionScope().removeRoute("agregarTarjeta");
        	RouteConfiguration.forSessionScope().removeRoute("responderConsultas");
        	RouteConfiguration.forSessionScope().removeRoute("gestionarUsuarios");
        	RouteConfiguration.forSessionScope().removeRoute("gestionarCuentas");
        	RouteConfiguration.forSessionScope().removeRoute("gestionarTarjetas");
        	RouteConfiguration.forSessionScope().removeRoute("gestionarNoticias");
        	RouteConfiguration.forSessionScope().removeRoute("agregarNoticia");
    	}
    	VaadinSession.getCurrent().setAttribute(Usuario.class, null);
        UI.getCurrent().getPage().setLocation(MainLayout.LOGOUT_URL);
    }

}
