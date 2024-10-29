package es.uca.santandesi.views.usuarios;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import es.uca.santandesi.data.entity.Usuario;
import es.uca.santandesi.data.service.UsuarioService;

@PageTitle("Tu perfil")
public class VerPerfilView extends VerticalLayout {
	
	public VerPerfilView(UsuarioService usuarioService) {
		Usuario u = VaadinSession.getCurrent().getAttribute(Usuario.class);
		add(new H3("Nombre: " + u.getNombre()),
			new H3("DNI: " + u.getDNI()));
	}
}
