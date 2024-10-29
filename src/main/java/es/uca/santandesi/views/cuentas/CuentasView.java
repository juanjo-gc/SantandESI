package es.uca.santandesi.views.cuentas;

import java.util.List;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import es.uca.santandesi.data.entity.Cuenta;
import es.uca.santandesi.data.entity.Usuario;
import es.uca.santandesi.data.service.CuentaService;
import es.uca.santandesi.views.tarjetas.TarjetasView;

@PageTitle("Cuentas")
public class CuentasView extends VerticalLayout {
	
	private Grid<Cuenta> grid = new Grid<>(Cuenta.class);
	
	public CuentasView(CuentaService cuentaService) {

		grid.setColumns("iban", "importe");
        grid.getColumnByKey("iban").setHeader("IBAN");
        grid.getColumnByKey("importe").setHeader("Importe");

		grid.addColumn(
				new ComponentRenderer<>(Button::new, (button, cuenta) -> {
					button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
					button.setText("Ver detalles");
					button.addClickListener(e -> {
						VaadinSession.getCurrent().setAttribute(Cuenta.class, cuenta);
						UI.getCurrent().navigate(CuentaDetalleView.class);					
					});
				}));
		grid.addColumn(
				new ComponentRenderer<>(Button::new, (button, cuenta) -> {
					button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
					button.setText("Ver tarjetas");
					button.addClickListener(e -> {
						VaadinSession.getCurrent().setAttribute(String.class, cuenta.getIban());
						UI.getCurrent().navigate(TarjetasView.class);					
					});
				}));
		grid.setItems(VaadinSession.getCurrent().getAttribute(Usuario.class).getCuentas());
		
		add(new H2("Tus cuentas"), grid);
	}

}

/*
 * 
public class CuentasView extends VerticalLayout {

	private Accordion accordion = new Accordion();
	public CuentasView(UsuarioService usuarioService, CuentaService cuentaService) {
		
		Usuario u =  VaadinSession.getCurrent().getAttribute(Usuario.class);
		List<Cuenta> cuentas = u.getCuentas();// cs.cuentasUsuario(VaadinSession.getCurrent().getAttribute(Usuario.class).getId());
		
		if (cuentas.isEmpty()) {
			add(new H2("No existe ninguna cuenta"), new H2(u.getNombre()));
		} else {
			for (Cuenta cuenta : cuentas) {

				Span importe = new Span("Importe: " + cuenta.getImporte() + "€");
				Span tipo = new Span("Tipo de cuenta: " + cuenta.getTipo());
				Span fecha = new Span("Fecha de creación: " + cuenta.getFecha_creacion().toString()); 
				Span comision = new Span("Comisión: " + cuenta.getComision() + "% anual");
				Button tarjetas = new Button("Ver tarjetas");
				tarjetas.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
				tarjetas.addClickListener(clickEvent -> {
					VaadinSession.getCurrent().setAttribute(String.class, cuenta.getIban());
					UI.getCurrent().navigate(TarjetasView.class);
				});
						
				VerticalLayout cuentaLayout = new VerticalLayout(importe, tipo, fecha, comision, tarjetas);
				cuentaLayout.setSpacing(false);
				cuentaLayout.setPadding(false);
				accordion.add(cuenta.getIban(), cuentaLayout);
			}
			add(new H2("Tus cuentas"), accordion);
			setSizeFull();
	        setJustifyContentMode(JustifyContentMode.CENTER);
	        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
	        getStyle().set("text-align", "center");
		}
	}
}



 */