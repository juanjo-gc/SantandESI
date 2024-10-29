package es.uca.santandesi.views.tarjetas;

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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;

import es.uca.santandesi.data.entity.Cuenta;
import es.uca.santandesi.data.entity.Pago;
import es.uca.santandesi.data.entity.Tarjeta;
import es.uca.santandesi.data.entity.Usuario;
import es.uca.santandesi.data.service.PagoService;

@PageTitle("Histórico de pagos")
public class HistoricoPagosView extends VerticalLayout {
	
	private Accordion acordeon = new Accordion();
	
	public HistoricoPagosView(PagoService pagoService) {
		List<Cuenta> cuentas = VaadinSession.getCurrent().getAttribute(Usuario.class).getCuentas();
		List<Tarjeta> tarjetas = new ArrayList<Tarjeta>();
		List<Pago> pagos = new ArrayList<Pago>();
		
		for(Cuenta cuenta: cuentas) {
			tarjetas.addAll(cuenta.getTarjetas());
		}
		
		for(Tarjeta tarjeta: tarjetas) {
			pagos.addAll(pagoService.getPagosTarjeta(tarjeta));
		}
		
		Collections.sort(pagos, new Comparator<Pago>() {
			@Override
			public int compare(Pago p1, Pago p2) {
				return p1.getFecha().compareTo(p2.getFecha());
			}
		});

		add(new H2("Historial de pagos"));
		if(pagos.isEmpty()) {
			add(new H3("No hay pagos con tus tarjetas en el ultimo año."));
		} else {
			add(acordeon);
			for(Pago pago: pagos) {
				if(LocalDate.now().minusYears(1).compareTo(pago.getFecha()) <= 0) // Se muestran los pagos de hace 1 año
				{
					Span importe = new Span("Importe: " + pago.getImporte());
					Span fecha = new Span("Fecha: " + pago.getFecha());
					VerticalLayout pagoLayout = new VerticalLayout(importe, fecha);
					pagoLayout.setSpacing(false);
					pagoLayout.setPadding(false);
					acordeon.add("Pago envia a " + pago.getNombre_empresa(), pagoLayout);
				}
			}
		}
		
		setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
	}
}
