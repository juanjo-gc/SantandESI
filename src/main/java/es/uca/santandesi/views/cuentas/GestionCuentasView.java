package es.uca.santandesi.views.cuentas;

import java.math.BigDecimal;
import java.util.UUID;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.server.VaadinSession;

import es.uca.santandesi.data.entity.Cuenta;
import es.uca.santandesi.data.entity.Usuario;
import es.uca.santandesi.data.service.CuentaService;
import es.uca.santandesi.data.service.UsuarioService;
import es.uca.santandesi.views.tarjetas.GestionTarjetasView;
import es.uca.santandesi.views.usuarios.GestionUsuariosView;

public class GestionCuentasView extends VerticalLayout implements BeforeEnterObserver, BeforeLeaveObserver {

	private Grid<Cuenta> grid = new Grid<>(Cuenta.class);
	//private TextField filtro = new TextField("Buscar por IBAN");
	private Button agregarCuenta = new Button("Agregar una cuenta a este usuario");
	
	public GestionCuentasView(CuentaService cuentaService, UsuarioService usuarioService) {
		
		UUID idUsuario = VaadinSession.getCurrent().getAttribute(UUID.class);
		Usuario usuario = usuarioService.get(idUsuario).get();
		//List<Cuenta> cuentas = usuario.getCuentas();
		
		agregarCuenta.addClickListener(e -> {
			VaadinSession.getCurrent().setAttribute("dniCliente", usuario.getDNI());
			UI.getCurrent().navigate(AgregarCuentaView.class);
		});
		
		grid.setColumns("iban", "importe", "tipo");
		grid.addColumn(
				new ComponentRenderer<>(Button::new, (button, cuenta) -> {
					button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
					button.setText("Gestionar tarjetas");
					button.addClickListener(e -> {
						VaadinSession.getCurrent().setAttribute("idCuenta", cuenta.getId());
						VaadinSession.getCurrent().setAttribute("dniCliente", usuario.getDNI());
						UI.getCurrent().navigate(GestionTarjetasView.class);	
					});
				}));
		grid.addColumn(
				new ComponentRenderer<>(Button::new, (button, cuenta) -> {
					button.addThemeVariants(ButtonVariant.LUMO_ERROR);
					button.setText("Eliminar cuenta");
					button.addClickListener(e -> {
						if(cuenta.getImporte().compareTo(BigDecimal.ZERO) == 0) {
							cuentaService.eliminar(cuenta, usuario);
							UI.getCurrent().getPage().reload();
							//grid.setItems(usuario.getCuentas());
							Notification.show("Cuenta borrada correctamente");
						} else {
							Notification.show("No se puede borrar una cuenta con un balance distinto a 0.");
						}
					});
				}));
		grid.setItems(usuario.getCuentas());
		
		add(new H2("Cuentas de " + usuario.getNombre() + " " + usuario.getApellidos()), agregarCuenta, grid);
	}
	

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if(VaadinSession.getCurrent().getAttribute(UUID.class).equals(null))
			UI.getCurrent().navigate(GestionUsuariosView.class);
		
	}

	@Override
	public void beforeLeave(BeforeLeaveEvent event) {
		//VaadinSession.getCurrent().setAttribute(UUID.class, null);
		
	}
}
