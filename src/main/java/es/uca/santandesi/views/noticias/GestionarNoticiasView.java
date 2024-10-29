package es.uca.santandesi.views.noticias;

import java.util.UUID;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.component.html.H2;

import com.vaadin.flow.server.VaadinSession;

import es.uca.santandesi.data.entity.Noticia;
import es.uca.santandesi.data.service.NoticiaService;

public class GestionarNoticiasView extends VerticalLayout {
	
	private Grid<Noticia> grid = new Grid<>(Noticia.class);
	private Button agregarNoticia = new Button("Agregar una noticia");
	
	public GestionarNoticiasView(NoticiaService noticiaService) {

		grid.setColumns("titulo", "fecha_publicacion");
        grid.getColumnByKey("titulo").setHeader("Título");
        grid.getColumnByKey("fecha_publicacion").setHeader("Fecha de publicación");
        
        agregarNoticia.addClickListener(e -> {
        	UI.getCurrent().navigate(AgregarNoticiaView.class);
        });

        grid.addColumn(
				new ComponentRenderer<>(Button::new, (button, noticia) -> {
					button.addThemeVariants(ButtonVariant.LUMO_ERROR);
					button.setText("Eliminar noticia");
					button.addClickListener(e -> {
						noticiaService.delete(noticia.getId());
						UI.getCurrent().getPage().reload();
						Notification.show("Noticia borrada correctamente");
					});
				}));
		grid.setItems(noticiaService.getNoticias());
		
		add(new H2("Noticias"), agregarNoticia, grid);
	}

}
