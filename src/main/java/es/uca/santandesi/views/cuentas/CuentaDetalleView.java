package es.uca.santandesi.views.cuentas;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;

import es.uca.santandesi.data.entity.Cuenta;
import es.uca.santandesi.views.tarjetas.TarjetasView;

public class CuentaDetalleView extends HorizontalLayout {
	
	Button btnTarjetas = new Button("Ver tarjetas");
	
	public CuentaDetalleView() {
		Cuenta cuenta = VaadinSession.getCurrent().getAttribute(Cuenta.class);
		VaadinSession.getCurrent().setAttribute(Cuenta.class, null);
		
		btnTarjetas.addClickListener(e ->  {
			VaadinSession.getCurrent().setAttribute(String.class, cuenta.getIban());
			UI.getCurrent().navigate(TarjetasView.class);
		});
				
		VerticalLayout colIzq = new VerticalLayout();
		VerticalLayout colDrch = new VerticalLayout();
		colIzq.add(	new H5("IBAN"),
					new Paragraph(cuenta.getIban()),
					new H5("Importe"),
					new Paragraph(cuenta.getImporte().toString()),
					btnTarjetas);
		
		colDrch.add(new H5("Comisión"),
					new Paragraph(cuenta.getComision().toString() + "% anual"),
					new H5("Fecha de creación"),
					new Paragraph(cuenta.getFecha_creacion().toString()));
		
		add(colIzq, colDrch);
	}
	
}
