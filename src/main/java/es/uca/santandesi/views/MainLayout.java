package es.uca.santandesi.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HighlightActions;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.Lumo;

import es.uca.santandesi.data.entity.Usuario;
import es.uca.santandesi.data.service.autenticacionService.AutenticacionService;
import es.uca.santandesi.views.usuarios.VerPerfilView;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout implements BeforeEnterObserver {

	private final AutenticacionService authSer;
    public static final String LOGOUT_URL = "login";

    public MainLayout(AutenticacionService AuthService) {
    	authSer = AuthService;
        addToNavbar(createHeaderContent());
    }

    private Component createHeaderContent() {
    	Usuario usuario = VaadinSession.getCurrent().getAttribute(Usuario.class);
    	if(usuario == null)
    		usuario = new Usuario();
    	RouteConfiguration rutas = RouteConfiguration.forSessionScope();
    	
    	Header header = new Header();
        header.addClassNames("box-border", "flex", "flex-col", "w-full");

        Image logo = new Image("images/logo.png", "SantandESI");
        logo.setHeight("50px");
        RouterLink linkLogo = new RouterLink();
        if(!rutas.getRoute("").isEmpty())
        	linkLogo.setRoute(rutas.getRoute("").get());
        
        linkLogo.addClassNames("m-xs");
        linkLogo.add(logo);
        linkLogo.setHighlightAction(HighlightActions.none());

        Button modoOscuro = new Button("Activar modo oscuro", click -> {
            ThemeList themeList = UI.getCurrent().getElement().getThemeList();

            if (themeList.contains(Lumo.DARK)) { 
                themeList.remove(Lumo.DARK);
                logo.setSrc("images/logo.png");
                click.getSource().setText("Activar modo oscuro");
            } else {
                themeList.add(Lumo.DARK);
                logo.setSrc("images/logoDark.png");
                click.getSource().setText("Desactivar modo oscuro");
            }
        });

        Button logout = new Button("Cerrar sesión", e -> authSer.logout());
        logout.setIcon(new LineAwesomeIcon("las la-2x la-sign-out-alt"));
        logout.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        logout.setIconAfterText(true);
        Div espacio = new Div();

        RouterLink nombre = new RouterLink();
        nombre.setText(usuario.getNombre());
        if(!rutas.getRoute("perfil").isEmpty())
        	nombre.setRoute(VerPerfilView.class);
        Paragraph bienvenida = new Paragraph(new Span("Hola "), nombre);
        
        HorizontalLayout barraSuperior = new HorizontalLayout(linkLogo, modoOscuro, espacio, bienvenida, logout);
        barraSuperior.addClassNames("flex", "items-center", "px-l");
        barraSuperior.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        barraSuperior.expand(espacio);

        Nav barraInferior = new Nav();
        barraInferior.addClassNames("flex", "overflow-auto", "px-m", "py-xs");

        // Wrap the links in a list; improves accessibility
        UnorderedList listaBotones = new UnorderedList();
        listaBotones.addClassNames("flex", "gap-s", "list-none", "m-0", "p-0");
        barraInferior.add(listaBotones);

        if(!rutas.getRoute("").isEmpty())
        	listaBotones.add(new MenuItemInfo("Inicio", "", "las la-home", rutas.getRoute("").get()));
        
        if(!rutas.getRoute("cuentas").isEmpty())
        	listaBotones.add(new MenuItemInfo("Tus cuentas", "", "las la-wallet", rutas.getRoute("cuentas").get()));
        if(!rutas.getRoute("movimientos").isEmpty())
        	listaBotones.add(new MenuItemInfo("Movimientos", "", "las la-exchange-alt", rutas.getRoute("movimientos").get()));
        if(!rutas.getRoute("consulta").isEmpty())
        	listaBotones.add(new MenuItemInfo("Consultas", "", "las la-headset", rutas.getRoute("consulta").get()));        
        if(!rutas.getRoute("responderConsultas").isEmpty())
            	listaBotones.add(new MenuItemInfo("Responder consultas", "", "las la-headset", rutas.getRoute("responderConsultas").get()));
        if(!rutas.getRoute("historicoMovimientos").isEmpty())
        	listaBotones.add(new MenuItemInfo("Historico de movimientos", "", "las la-money-check", rutas.getRoute("historicoMovimientos").get()));
        if(!rutas.getRoute("historicoPagos").isEmpty())
        	listaBotones.add(new MenuItemInfo("Historico de pagos", "", "las la-credit-card", rutas.getRoute("historicoPagos").get()));
        if(!rutas.getRoute("noticias").isEmpty())
        	listaBotones.add(new MenuItemInfo("Noticias", "", "las la-credit-card", rutas.getRoute("noticias").get()));

        if(!rutas.getRoute("agregarUsuario").isEmpty())
        	listaBotones.add(new MenuItemInfo("Registrar usuario", "", "las la-user-plus", rutas.getRoute("agregarUsuario").get()));
        if(!rutas.getRoute("gestionarUsuarios").isEmpty())
        	listaBotones.add(new MenuItemInfo("Gestión de usuarios, cuentas y tarjetas", "", "las la-th-list", rutas.getRoute("gestionarUsuarios").get()));
        if(!rutas.getRoute("agregarCuenta").isEmpty())
        	listaBotones.add(new MenuItemInfo("Agregar cuenta", "", "las la-money-check", rutas.getRoute("agregarCuenta").get()));
        if(!rutas.getRoute("agregarTarjeta").isEmpty())
        	listaBotones.add(new MenuItemInfo("Agregar tarjeta", "", "las la-money-check", rutas.getRoute("agregarTarjeta").get()));
        if(!rutas.getRoute("gestionarNoticias").isEmpty())
        	listaBotones.add(new MenuItemInfo("Gestionar noticias", "", "las la-credit-card", rutas.getRoute("gestionarNoticias").get()));

        header.add(barraSuperior, barraInferior);
        return header;
    }

    /**
     * A simple navigation item component, based on ListItem element.
     */
    public static class MenuItemInfo extends ListItem {

        private final Class<? extends Component> view;

        public MenuItemInfo(String menuTitle, String linkClass, String iconClass, Class<? extends Component> view) {
            this.view = view;
            RouterLink link = new RouterLink();
            // Use Lumo classnames for various styling
            link.addClassNames("flex", "gap-xs", "h-m", "items-center", "px-s", "text-body");
            if (!linkClass.isEmpty()) {
                addClassNames(linkClass);
            }
            link.setRoute(view);

            Span text = new Span(menuTitle);
            // Use Lumo classnames for various styling
            text.addClassNames("font-medium", "text-m", "whitespace-nowrap");

            link.add(new LineAwesomeIcon(iconClass), text);
            add(link);
        }

        public Class<?> getView() {
            return view;
        }


    }
    /**
     * Simple wrapper to create icons using LineAwesome iconset. See
     * https://icons8.com/line-awesome
     */
    @NpmPackage(value = "line-awesome", version = "1.3.0")
    public static class LineAwesomeIcon extends Span {
    	public LineAwesomeIcon(String lineawesomeClassnames) {
    		// Use Lumo classnames for suitable font styling
    		addClassNames("text-l", "text-secondary");
    		if (!lineawesomeClassnames.isEmpty()) {
    			addClassNames(lineawesomeClassnames);
    		}
    	}
    }
    
	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if (VaadinSession.getCurrent().getAttribute(Usuario.class) == null)
			event.forwardTo(LOGOUT_URL);
	}
}
