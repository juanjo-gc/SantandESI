package es.uca.santandesi.views.noticias;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import es.uca.santandesi.data.entity.Cuenta;
import es.uca.santandesi.data.entity.Usuario;
import es.uca.santandesi.data.service.MovimientoService;
import es.uca.santandesi.data.service.NoticiaService;
import es.uca.santandesi.data.service.UsuarioService;
import es.uca.santandesi.views.MainLayout;

public class AgregarNoticiaView extends VerticalLayout {

	private TextField titulo = new TextField("Título de la noticia:");
	private TextArea cuerpo = new TextArea("Cuerpo");
	private TextField encabezado = new TextField("Encabezado");
	private MemoryBuffer buffer = new MemoryBuffer();
	private Upload imagen = new Upload();
	
	public AgregarNoticiaView(NoticiaService noticiaService) {

		Div noticiasView = new Div();
		noticiasView.setWidth("65%");
		noticiasView.addClassNames("flex-col", "justify-center", "content-start");
		add(noticiasView);

		H1 tituloPagina = new H1("Agregar una noticia");
		Div formDiv = new Div();

		noticiasView.add(tituloPagina, formDiv);

		FormLayout form = new FormLayout();
		// formDiv.add(form);
		form.setResponsiveSteps(new ResponsiveStep("0", 3));
		
		imagen.setAcceptedFileTypes("image/jpeg", "image/jpg", "image.png");
		/*
		 * 
		imagen.addSucceededListener(evento -> {
			InputStream inputStream = buffer.getInputStream();
			String nombreFichero = evento.getFileName();
			try {
				noticiaService.registrarNoticia(titulo.getValue(), cuerpo.getValue(), inputStream, nombreFichero);
			} catch (IOException e1) {
				Notification.show("La imagen no se pudo subir");
			}
		});
		 */
		Button btnContinuar = new Button("Continuar", e -> {
			noticiaService.registrarNoticia(titulo.getValue(), encabezado.getValue(), cuerpo.getValue());
		});

		btnContinuar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		form.add(titulo, encabezado, cuerpo);
		form.setColspan(titulo, 3);
		form.setColspan(cuerpo, 3);
		form.setColspan(imagen, 1);
		formDiv.add(form, btnContinuar);
	}

}