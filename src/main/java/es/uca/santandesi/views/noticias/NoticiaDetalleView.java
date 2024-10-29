package es.uca.santandesi.views.noticias;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import es.uca.santandesi.data.entity.Noticia;
import es.uca.santandesi.data.entity.Usuario;
import es.uca.santandesi.views.MainLayout;

public class NoticiaDetalleView extends VerticalLayout implements BeforeLeaveObserver {
	
    public NoticiaDetalleView() {
    	
        Noticia noticia = VaadinSession.getCurrent().getAttribute(Noticia.class);
    	VaadinSession.getCurrent().setAttribute(Noticia.class, null);

        setSpacing(false);
        
        add(new H1(noticia.getTitulo()));
        add(new H3(noticia.getEncabezado()));

        /*
         * 
        Image img = new Image("images/" + noticia.getImagen(), noticia.getImagen());

        img.setWidth("200px");
        add(img);
         */
        add(new Paragraph(noticia.getCuerpo()));

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    /*
     * 
	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if(VaadinSession.getCurrent().getAttribute(Noticia.class).equals(null))
			UI.getCurrent().navigate(MostrarNoticiasView.class);
		
	}
     */

	@Override
	public void beforeLeave(BeforeLeaveEvent event) {
		VaadinSession.getCurrent().setAttribute(Noticia.class, null);
    }
}
