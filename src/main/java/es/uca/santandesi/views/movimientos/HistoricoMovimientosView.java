package es.uca.santandesi.views.movimientos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;

import es.uca.santandesi.data.entity.Cuenta;
import es.uca.santandesi.data.entity.Movimiento;
import es.uca.santandesi.data.entity.Usuario;
import es.uca.santandesi.data.service.MovimientoService;

public class HistoricoMovimientosView extends VerticalLayout {
	
	private Accordion acordeon = new Accordion();
	
	public HistoricoMovimientosView(MovimientoService movimientoService) {
		List<Cuenta> cuentas = VaadinSession.getCurrent().getAttribute(Usuario.class).getCuentas();
		List<Movimiento> movimientos = new ArrayList<Movimiento>();
		
		for(Cuenta cuenta: cuentas) {
			movimientos.addAll(movimientoService.getMovimientosCuenta(cuenta));
		}
		
		Collections.sort(movimientos, new Comparator<Movimiento>() {
			@Override
			public int compare(Movimiento m1, Movimiento m2) {
				return m1.getFecha_realizacion().compareTo(m2.getFecha_realizacion());
			}
		});
		
		add(new H2("Historial de movimientos realizados"));
		if(movimientos.isEmpty()) {
			add(new H3("No hay movimientos en tus cuentas en el ultimo año."));
		} else {
			add(acordeon);
			for(Movimiento movimiento: movimientos) {
				if(LocalDate.now().minusYears(1).compareTo(movimiento.getFecha_realizacion()) <= 0) // Se muestran los movimientos de hace 1 año
				{
					Span importe = new Span("Importe: " + movimiento.getImporte());
					Span ibanBenef = new Span("IBAN de la cuenta destino: " + movimiento.getBeneficiario().getIban());
					Span ibanOrden = new Span("IBAN de la cuenta origen: " + movimiento.getOrdenante().getIban());
					Span fecha = new Span("Fecha: " + movimiento.getFecha_realizacion());
					
					VerticalLayout movimientoLayout = new VerticalLayout(importe, ibanBenef, ibanOrden, fecha);
					movimientoLayout.setSpacing(false);
					movimientoLayout.setPadding(false);
					acordeon.add("Concepto: " + movimiento.getConcepto(), movimientoLayout);
				}
			}
		}
		
		setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
		
	}

}
