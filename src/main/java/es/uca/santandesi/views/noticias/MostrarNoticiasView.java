package es.uca.santandesi.views.noticias;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.component.html.H2;

import com.vaadin.flow.server.VaadinSession;

import es.uca.santandesi.data.entity.Noticia;
import es.uca.santandesi.data.service.NoticiaService;

public class MostrarNoticiasView extends VerticalLayout {
	
	private Grid<Noticia> grid = new Grid<>(Noticia.class);
	
	public MostrarNoticiasView(NoticiaService noticiaService) {

		grid.setColumns("titulo", "fecha_publicacion");
        grid.getColumnByKey("titulo").setHeader("Título");
        grid.getColumnByKey("fecha_publicacion").setHeader("Fecha de publicación");

		grid.addColumn(
				new ComponentRenderer<>(Button::new, (button, noticia) -> {
					button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
					button.setText("Ver noticia");
					button.addClickListener(e -> {
						VaadinSession.getCurrent().setAttribute(Noticia.class, noticia);
						UI.getCurrent().navigate(NoticiaDetalleView.class);					
					});
				}));
		grid.setItems(noticiaService.getNoticias());
		
		add(new H2("Noticias"), grid);
	}

}
