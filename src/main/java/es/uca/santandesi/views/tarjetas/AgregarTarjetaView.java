package es.uca.santandesi.views.tarjetas;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import es.uca.santandesi.data.service.CuentaService;
import es.uca.santandesi.data.service.TarjetaService;
import es.uca.santandesi.views.usuarios.GestionUsuariosView;

@PageTitle("Agregar tarjeta")
public class AgregarTarjetaView extends VerticalLayout {
	

	TextField dniCliente = new TextField("DNI del cliente:");
	TextField ibanCuenta = new TextField("IBAN de la cuenta");
	Div tarjetaFormView = new Div();
	Div formDiv = new Div();
	
	public AgregarTarjetaView(TarjetaService tarjetaService) {
		
		tarjetaFormView.setWidth("65%");
        tarjetaFormView.addClassNames("flex-col", "justify-center", "content-start");
        add(tarjetaFormView);
        
        if(VaadinSession.getCurrent().getAttribute("dniCliente") != null) {
        	dniCliente.setValue(VaadinSession.getCurrent().getAttribute("dniCliente").toString());
        }
        
        if(VaadinSession.getCurrent().getAttribute("ibanCuenta") != null) {
        	ibanCuenta.setValue(VaadinSession.getCurrent().getAttribute("ibanCuenta").toString());
        }
        
        Button btnContinuar = new Button("Continuar", e -> { 
        	try {
        		tarjetaService.addTarjetaCuenta(ibanCuenta.getValue(), dniCliente.getValue());
        		VaadinSession.getCurrent().setAttribute("tarjetaCreada", true);
        		UI.getCurrent().navigate(GestionUsuariosView.class);
        	} catch (TarjetaService.CardCreationException ex) {
        		Notification.show(ex.getMotivo());
        	} catch (CuentaService.AddCardException ex) {
        		Notification.show(ex.getMotivo());
        	}
        });
        
        tarjetaFormView.add(new H1("Agregar una tarjeta a una cuenta"), formDiv);
        
        FormLayout form = new FormLayout();
        //formDiv.add(form);
        form.setResponsiveSteps(new ResponsiveStep("0", 2));        
        
        btnContinuar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        
        form.add(dniCliente, ibanCuenta);
        form.setColspan(ibanCuenta, 2);
        form.setColspan(dniCliente, 2);
        formDiv.add(form, btnContinuar);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
	}

}
