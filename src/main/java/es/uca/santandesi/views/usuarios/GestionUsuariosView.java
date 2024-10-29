package es.uca.santandesi.views.usuarios;

import java.util.UUID;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.server.VaadinSession;

import es.uca.santandesi.data.entity.Usuario;
import es.uca.santandesi.data.service.UsuarioService;
import es.uca.santandesi.views.cuentas.GestionCuentasView;

public class GestionUsuariosView extends VerticalLayout {
	
	private Grid<Usuario> grid = new Grid<>(Usuario.class);
	private TextField filtro = new TextField("Buscar por DNI");
	
	public GestionUsuariosView(UsuarioService usuarioService) {
		
		if(VaadinSession.getCurrent().getAttribute("tarjetaCreada") != null) {
			Notification.show("Se ha creado la tarjeta satisfactoriamente.");
			VaadinSession.getCurrent().setAttribute("tarjetaCreada", null);
		}
		if(VaadinSession.getCurrent().getAttribute("cuentaCreada") != null) {
			Notification.show("Se ha creado la cuenta satisfactoriamente.");
			VaadinSession.getCurrent().setAttribute("cuentaCreada", null);
		}

		grid.setColumns("DNI", "nombre", "apellidos", "email");
		grid.addColumn(
				new ComponentRenderer<>(Button::new, (button, usuario) -> {
					button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
					button.setText("Gestionar cuentas");
					button.addClickListener(e -> {
						VaadinSession.getCurrent().setAttribute(UUID.class, usuario.getId());
						UI.getCurrent().navigate(GestionCuentasView.class);					
					});
				}));
		grid.addColumn(
				new ComponentRenderer<>(Button::new, (button, usuario) -> {
					button.addThemeVariants(ButtonVariant.LUMO_ERROR);
					button.setText("Eliminar usuario");
					button.addClickListener(e -> {
						if(usuario.getId().compareTo(VaadinSession.getCurrent().getAttribute(Usuario.class).getId()) != 0) {							
							usuarioService.eliminar(usuario);
							grid.setItems(usuarioService.getUsuariosFiltro(null));
							Notification.show("Usuario borrado correctamente");
						} else {
							Notification.show("No puede eliminar su propio usuario");
						}
					});
				}));
		grid.setItems(usuarioService.getUsuariosFiltro(null));
		configurarFiltro();
		actualizarLista(usuarioService);
		
		add(new H2("Gestionar usuarios"), filtro, grid);
	}
	
	private void configurarFiltro() {
		filtro.setPlaceholder("Introduce un DNI...");
		filtro.setClearButtonVisible(true);
		filtro.setValueChangeMode(ValueChangeMode.LAZY);
	}
	
	private void actualizarLista(UsuarioService usuarioService) {
		filtro.addValueChangeListener(e -> 
			grid.setItems(usuarioService.getUsuariosFiltro(filtro.getValue()))
				);
	}
}
