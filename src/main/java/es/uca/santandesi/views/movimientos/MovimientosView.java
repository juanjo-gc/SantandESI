package es.uca.santandesi.views.movimientos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;

import es.uca.santandesi.data.entity.Cuenta;
import es.uca.santandesi.data.entity.Usuario;
import es.uca.santandesi.data.service.MovimientoService;

@PageTitle("Realizar un movimiento")
public class MovimientosView extends VerticalLayout {

	public MovimientosView(MovimientoService movimientoService) {
		
		List<String> listaIban = new ArrayList<>();
		Div movimientosView = new Div();
		movimientosView.setWidth("65%");
		movimientosView.addClassNames("flex-col", "justify-center", "content-start");
		add(movimientosView);

		H1 titulo = new H1("Realizar un movimiento");
		Div formDiv = new Div();

		movimientosView.add(titulo, formDiv);

		FormLayout form = new FormLayout();
		// formDiv.add(form);
		form.setResponsiveSteps(new ResponsiveStep("0", 2));
		
		/* 
		ComboBox<Cuenta> cuentaOrigen = new ComboBox<Cuenta>();
		cuentaOrigen.setLabel("Número de cuenta origen: ");
		cuentaOrigen.setItemLabelGenerator(Cuenta::getIban);
		cuentaOrigen.setItems(cuentas);
		 */
		Select<String> cuentaOrigen = new Select<String>();
		cuentaOrigen.setLabel("Número de la cuenta origen");
		
		for(Cuenta cuenta: VaadinSession.getCurrent().getAttribute(Usuario.class).getCuentas()) {
			listaIban.add(cuenta.getIban());
		}
		cuentaOrigen.setItems(listaIban);
		
		TextField cuentaDestino = new TextField("Número de cuenta destino:");

		NumberField importe = new NumberField("Importe:");
		importe.setSuffixComponent(new Paragraph("€"));
		TextField concepto = new TextField("Concepto:");

		Button btnContinuar = new Button("Continuar", e -> {
			try {
				movimientoService.procesarTransacion(BigDecimal.valueOf(importe.getValue()), concepto.getValue(),
						cuentaOrigen.getValue(), cuentaDestino.getValue());
			} catch (MovimientoService.InvalidTransactionException ex) {
				Notification.show(ex.getMessage());
			}
		});

		btnContinuar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		form.add(cuentaOrigen, cuentaDestino, importe, concepto);
		form.setColspan(cuentaOrigen, 2);
		form.setColspan(cuentaDestino, 2);
		form.setColspan(concepto, 2);
		form.setColspan(importe, 1);
		formDiv.add(form, btnContinuar);
		// seleccion de cuenta(informacion de cuenta ó numero de cuenta)
		// "Cuenta de origen: selector"

		// "Cuenta de destino:"
		// "Nombre del beneficiario: Número de cuenta:"
		// caja nombre caja numero

		// "Importe"
		// Caja dinero

		// "Concepto"
		// Caja texto

		// boton "Continuar"

		// nueva pagina resumen con el resumen de la operacion a realizar

		setSizeFull();
		setJustifyContentMode(JustifyContentMode.START);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		getStyle().set("text-align", "center");
	}
}