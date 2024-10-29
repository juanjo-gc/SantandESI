package es.uca.santandesi.views.tarjetas;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;

import es.uca.santandesi.data.entity.Cuenta;
import es.uca.santandesi.data.entity.Tarjeta;
import es.uca.santandesi.data.service.TarjetaService;

public class TarjetaDetalleView extends VerticalLayout {
	
	HorizontalLayout layout = new HorizontalLayout();
	VerticalLayout colIzq = new VerticalLayout();
	VerticalLayout colDrch = new VerticalLayout();
	Button btnActivarDesactivar = new Button();
	
	public TarjetaDetalleView(TarjetaService tarjetaService) {
		
	Tarjeta tarjeta = VaadinSession.getCurrent().getAttribute(Tarjeta.class);
		
	layout.add(colIzq, colDrch);
	colIzq.add(	new H5("Número de tarjeta"),
				new Paragraph(tarjeta.getNumero_tarjeta()),
				new H5("PIN"),
				new Paragraph(tarjeta.getPin().toString())
				);
	
	colDrch.add(new H5("Código de seguridad"),
				new Paragraph(tarjeta.getCodigo_seguridad().toString()),
				new H5("Fecha de caducidad"),
				new Paragraph(tarjeta.getFecha_caducidad().toString()));
	
	layout.setSizeFull();
	
	if(tarjeta.isActivada()) {	
		btnActivarDesactivar.setText("Desactivar");
		btnActivarDesactivar.addThemeVariants(ButtonVariant.LUMO_ERROR);
	} else {
		btnActivarDesactivar.setText("Activar");
		btnActivarDesactivar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
	}
	
	btnActivarDesactivar.addClickListener(e -> {
		tarjetaService.activarDesactivar(tarjeta);
		UI.getCurrent().getPage().reload();
	});
	
	add(new H2("Detalles de la tarjeta"),
			layout, btnActivarDesactivar);
	}

}