package es.uca.santandesi.views.tarjetas;

import java.util.List;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;

import es.uca.santandesi.data.entity.Cuenta;
import es.uca.santandesi.data.entity.Tarjeta;
import es.uca.santandesi.data.entity.Usuario;
import es.uca.santandesi.data.service.CuentaService;
import es.uca.santandesi.data.service.TarjetaService;
import es.uca.santandesi.views.cuentas.CuentaDetalleView;
import es.uca.santandesi.views.inicio.InicioView;

@PageTitle("Tus tarjetas")
public class TarjetasView extends VerticalLayout {

	private Grid<Tarjeta> grid = new Grid<>(Tarjeta.class);

	public TarjetasView(TarjetaService tarjetaService, CuentaService cuentaService) {
		if (VaadinSession.getCurrent().getAttribute(String.class) == null)
			UI.getCurrent().navigate(InicioView.class);
	
		Cuenta cuenta = cuentaService.getCuentaIban(VaadinSession.getCurrent().getAttribute(String.class));
	
		grid.setColumns("numero_tarjeta", "fecha_caducidad");
        grid.getColumnByKey("numero_tarjeta").setHeader("Numero de tarjeta");
        grid.getColumnByKey("fecha_caducidad").setHeader("Fecha de caducidad");

		grid.addColumn(
				new ComponentRenderer<>(Button::new, (button, tarjeta) -> {
					button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
					button.setText("Ver detalles");
					button.addClickListener(e -> {
						VaadinSession.getCurrent().setAttribute(Tarjeta.class, tarjeta);
						UI.getCurrent().navigate(TarjetaDetalleView.class);					
					});
				}));
		grid.addColumn(
				new ComponentRenderer<>(Button::new, (button, tarjeta) -> {
					if(tarjeta.isActivada()) {						
						button.addThemeVariants(ButtonVariant.LUMO_ERROR);
						button.setText("Desactivar");
					} else {
						button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
						button.setText("Activar");
					}
					button.addClickListener(e -> {
						tarjetaService.activarDesactivar(tarjeta);
						UI.getCurrent().getPage().reload();					
					});
				}));
		grid.setItems(cuenta.getTarjetas());
		
		add(new H2("Tarjetas de la cuenta " + cuenta.getIban()), grid);
	}
	
}

/*
 * String ibanCuenta = VaadinSession.getCurrent().getAttribute(String.class);
		VaadinSession.getCurrent().setAttribute(String.class, null); // Borrado del atributo de la sesion
		List<Tarjeta> tarjetas = tarjetaService
				.getTarjetasUsuario(VaadinSession.getCurrent().getAttribute(Usuario.class).getId());

		setSpacing(false);

		if (tarjetas.isEmpty()) {
			add(new H2("No existe ninguna tarjeta que pertenezca a la cuenta con IBAN " + ibanCuenta));
		} else {
			for (Tarjeta tarjeta : tarjetas) {

				Span numero = new Span("Numero de tarjeta: " + tarjeta.getNumero_tarjeta());
				Span codigoSeguridad = new Span("Código de seguridad: " + tarjeta.getCodigo_seguridad());
				Span fechaCaducidad = new Span("Fecha de caducidad: " + tarjeta.getFecha_caducidad().toString());
				Span pin = new Span("PIN: " + tarjeta.getPin());
				VerticalLayout tarjetaLayout = new VerticalLayout();
				tarjetaLayout.add(numero, codigoSeguridad, pin, fechaCaducidad);
				Button activarDesactivar = new Button("Desactivar", e -> {
					tarjetaService.activarDesactivar(tarjeta);
					if (!tarjeta.isActivada()) {
						Notification.show("Tarjeta desactivada");
						e.getSource().setText("Activar");
					} else {
						Notification.show("Tarjeta activada");
						e.getSource().setText("Desactivar");
					}
				});
				if (tarjeta.isActivada()) {
					activarDesactivar.setText("Desactivar");
				} else {
					activarDesactivar.setText("Activar");
				}
				tarjetaLayout.add(activarDesactivar);
				tarjetaLayout.setSpacing(false);
				tarjetaLayout.setPadding(false);
		
				accordionTarjetas.add("Cuenta asociada: " + ibanCuenta, tarjetaLayout);
			}
			add(accordionTarjetas);
			setSizeFull();
			setJustifyContentMode(JustifyContentMode.CENTER);
			setDefaultHorizontalComponentAlignment(Alignment.CENTER);
			getStyle().set("text-align", "center");
		}
		*/
