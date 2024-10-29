package es.uca.santandesi.views.cuentas;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import es.uca.santandesi.data.service.CuentaService;
import es.uca.santandesi.views.MainLayout;
import es.uca.santandesi.views.usuarios.GestionUsuariosView;

@PageTitle("Agregar cuenta")
public class AgregarCuentaView extends VerticalLayout implements BeforeLeaveObserver {
	
	Select<String> tipoCuenta = new Select<>();
	TextField dniCliente = new TextField("DNI del cliente:");
	Div cuentaFormView = new Div();
	Div formDiv = new Div();
	
    
	public AgregarCuentaView(CuentaService cuentaService) {
        cuentaFormView.setWidth("65%");
        cuentaFormView.addClassNames("flex-col", "justify-center", "content-start");
        add(cuentaFormView);
        
        tipoCuenta.setLabel("Tipo de cuenta");
        tipoCuenta.setItems("Estándar", "Joven");
        tipoCuenta.setValue("Estándar");
        
        if(VaadinSession.getCurrent().getAttribute("dniCliente") != null) {
        	dniCliente.setValue(VaadinSession.getCurrent().getAttribute("dniCliente").toString());
        	
        }
        
        Button btnContinuar = new Button("Continuar", e -> { 
        	cuentaService.addCuenta(tipoCuenta.getValue(), dniCliente.getValue()); 
        	UI.getCurrent().navigate(GestionUsuariosView.class);
        });
        
        cuentaFormView.add(new H1("Agregar una cuenta nueva"), formDiv);
        
        FormLayout form = new FormLayout();
        //formDiv.add(form);
        form.setResponsiveSteps(new ResponsiveStep("0", 2));        
        
        btnContinuar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        
        form.add(dniCliente, tipoCuenta);
        form.setColspan(tipoCuenta, 4);
        form.setColspan(dniCliente, 2);
        formDiv.add(form, btnContinuar);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }


	@Override
	public void beforeLeave(BeforeLeaveEvent event) {
		if(VaadinSession.getCurrent().getAttribute("dniCliente") != null)
			VaadinSession.getCurrent().setAttribute("dniCliente", null);
		
	}
}