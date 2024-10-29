package es.uca.santandesi.views.login;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import es.uca.santandesi.data.entity.Usuario;
import es.uca.santandesi.data.service.UsuarioService;
import es.uca.santandesi.data.service.UsuarioService.DateException;
import es.uca.santandesi.data.service.autenticacionService.AutenticacionService;

@PageTitle("Iniciar sesión")
@Route(value = "login")
@CssImport("themes/santandesi/views/login-view.css")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
	
    private final AutenticacionService authService;
    public static final String LOGIN_URL = "/";

	TextField dni = new TextField("DNI");
	PasswordField clave = new PasswordField("Clave de usuario");
	Button button;
	
	public LoginView(AutenticacionService authenticationService, UsuarioService usuarioService) throws DateException {
		authService = authenticationService;
		
		button = new Button("Iniciar sesión");
		button.addClickListener(event -> {
			try {
				authService.autenticar(dni.getValue(), clave.getValue());
				button.setDisableOnClick(true);
				UI.getCurrent().navigate(LOGIN_URL);			
			} catch(AutenticacionService.AuthException e) {
				Notification.show("Credenciales incorrectos");
			}
		});
		button.addClickShortcut(Key.ENTER);
		
		add(new H1("SantandESI"),
				dni,
				clave,
				button
		);
        
		addClassName("login-view");
		setSizeFull();
		setAlignItems(Alignment.CENTER); 
		setJustifyContentMode(JustifyContentMode.CENTER);
		getStyle().set("text-align", "center");
	}

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
    	if (VaadinSession.getCurrent().getAttribute(Usuario.class) != null)
            event.forwardTo(LOGIN_URL);
    }
}
