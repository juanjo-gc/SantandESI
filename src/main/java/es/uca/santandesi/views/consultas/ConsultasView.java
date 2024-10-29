package es.uca.santandesi.views.consultas;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;

import es.uca.santandesi.data.entity.Consulta;
import es.uca.santandesi.data.service.CategoriaService;
import es.uca.santandesi.data.service.ConsultaService;

@PageTitle("Realizar una consulta")
public class ConsultasView extends VerticalLayout {
    
	TextField asunto = new TextField("Asunto");
	Select<String> categoria = new Select<>();
	TextArea descripcion = new TextArea("Descripción");
	Button btnEnviar = new Button("Enviar");
	H1 titulo = new H1("Realizar una consulta");
	
	//Binder<Consulta> binder = new BeanValidationBinder<>(Consulta.class);
	
	private Div consultaFormView = new Div();
	private Div formDiv = new Div();
	
	public ConsultasView(ConsultaService consultaService, CategoriaService categoriaService) {
		
		consultaFormView.setWidth("65%");
		consultaFormView.addClassNames("flex-col", "justify-center", "content-start");
		
		Button consultas = new Button("Tus consultas", e -> {
			UI.getCurrent().navigate(MostrarConsultasView.class);
		});
		add(consultas);
		
        add(consultaFormView);
        
		btnEnviar.addClickListener(clickEvent -> {
			if(true) { // TODO validacion				
				consultaService.addConsulta(asunto.getValue(), categoria.getValue(), descripcion.getValue());
				UI.getCurrent().navigate(MostrarConsultasView.class);
				Notification.show("La consulta ha sido registrada.");
			}
		});
        
        categoria.setItems(categoriaService.getCategorias());
        categoria.setLabel("Categoría");
        
        FormLayout form = new FormLayout();
        form.setResponsiveSteps(new ResponsiveStep("0", 4));
        
        form.add(asunto, categoria, descripcion);
        
        //binder.bindInstanceFields(form);
        
        form.setColspan(asunto, 2);
        form.setColspan(categoria, 2);
        form.setColspan(descripcion, 4);
        formDiv.add(form, btnEnviar);
        add(titulo, formDiv);
        
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }
	
	boolean camposCorrectos(String asunto, String categoria, String descripcion) {
		if(asunto.isEmpty()){
			Notification.show("El asunto no puede estar vacío.");
			return false;
		}
		if(categoria.isEmpty()) {
			Notification.show("Introduzca una categoría.");
			return false;
		}
		if(descripcion.isEmpty()) {
			Notification.show("Introduzca una descripción.");
		}
		return true;
	}
}