package es.uca.santandesi.views.tarjetas;

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
import es.uca.santandesi.data.entity.Tarjeta;
import es.uca.santandesi.data.entity.Usuario;
import es.uca.santandesi.data.service.CuentaService;
import es.uca.santandesi.data.service.TarjetaService;
import es.uca.santandesi.data.service.UsuarioService;
import es.uca.santandesi.views.usuarios.GestionUsuariosView;

public class GestionTarjetasView extends VerticalLayout implements BeforeEnterObserver, BeforeLeaveObserver {

	private Grid<Tarjeta> grid = new Grid<>(Tarjeta.class);
	//private TextField filtro = new TextField("Buscar por número de tarjeta");
	private Button agregarTarjeta = new Button("Agregar una tarjeta a esta cuenta");

	public GestionTarjetasView(TarjetaService tarjetaService, CuentaService cuentaService, UsuarioService usuarioService) {

		Usuario usuario = usuarioService.getByDNI(VaadinSession.getCurrent().getAttribute("dniCliente").toString());
		String idCuentaString = VaadinSession.getCurrent().getAttribute("idCuenta").toString();
		UUID idCuenta = UUID.fromString(idCuentaString);
		Cuenta cuenta = cuentaService.get(idCuenta).get();
		// List<Cuenta> cuentas = usuario.getCuentas();

		agregarTarjeta.addClickListener(e -> {
			VaadinSession.getCurrent().setAttribute("dniCliente", usuario.getDNI());
			VaadinSession.getCurrent().setAttribute("ibanCuenta", cuenta.getIban());
			UI.getCurrent().navigate(AgregarTarjetaView.class);
		});

		grid.setColumns("numero_tarjeta", "fecha_caducidad");
		grid.addColumn(new ComponentRenderer<>(Button::new, (button, tarjeta) -> {
			button.addThemeVariants(ButtonVariant.LUMO_ERROR);
			button.setText("Eliminar tarjeta");
			button.addClickListener(e -> {
				tarjetaService.eliminar(tarjeta, cuenta);
				UI.getCurrent().getPage().reload();
				// grid.setItems(usuario.getCuentas());
				Notification.show("Tarjeta borrada correctamente");
			});
		}));
		grid.setItems(cuenta.getTarjetas());

		add(new H2("Tarjetas de la cuenta con IBAN " + cuenta.getIban()), agregarTarjeta, grid);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if (VaadinSession.getCurrent().getAttribute(UUID.class).equals(null))
			UI.getCurrent().navigate(GestionUsuariosView.class);

	}

	@Override
	public void beforeLeave(BeforeLeaveEvent event) {
		VaadinSession.getCurrent().setAttribute(UUID.class, null);

	}
}
