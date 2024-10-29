package es.uca.santandesi.views.inicio;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import es.uca.santandesi.data.entity.Usuario;
import es.uca.santandesi.views.MainLayout;
import es.uca.santandesi.views.consultas.ConsultasView;
import es.uca.santandesi.views.cuentas.AgregarCuentaView;
import es.uca.santandesi.views.cuentas.CuentasView;
import es.uca.santandesi.views.movimientos.HistoricoMovimientosView;
import es.uca.santandesi.views.movimientos.MovimientosView;
import es.uca.santandesi.views.tarjetas.AgregarTarjetaView;
import es.uca.santandesi.views.tarjetas.HistoricoPagosView;
import es.uca.santandesi.views.usuarios.AgregarUsuarioView;
import es.uca.santandesi.views.usuarios.GestionUsuariosView;
import es.uca.santandesi.views.usuarios.VerPerfilView;

@PageTitle("Inicio")
@Route(value = "", layout = MainLayout.class)
public class InicioView extends VerticalLayout {
	
    public InicioView() {
    	Usuario usuario = VaadinSession.getCurrent().getAttribute(Usuario.class);
    	
        setSpacing(false);
        
        add(new H1("Hola " + usuario.getNombre()));

        add(new H2("Qué puedes hacer:"));
        
        HorizontalLayout hl = new HorizontalLayout();
        add(hl);

        if(usuario.getRol().compareTo("cliente") == 0) {
	        hl.add(new VerticalLayout(new Button("Ver tus cuentas y tarjetas", e -> { UI.getCurrent().navigate(CuentasView.class); }),
	        		new Button("Ver tu historial de transacciones", e -> { UI.getCurrent().navigate(HistoricoMovimientosView.class); }),
	        		new Button("Realizar una consulta", e -> { UI.getCurrent().navigate(ConsultasView.class); })
	        		));
	        hl.add(new VerticalLayout(new Button("Realizar un movimiento", e -> { UI.getCurrent().navigate(MovimientosView.class); }),
	        		new Button("Ver tu historial de pagos", e -> { UI.getCurrent().navigate(HistoricoPagosView.class); }),
	        		new Button("Ver tu perfil", e -> { UI.getCurrent().navigate(VerPerfilView.class); })
	        		));
        } else {
            hl.add(new VerticalLayout(new Button("Crear una cuenta", e -> { UI.getCurrent().navigate(AgregarCuentaView.class); }),
            		new Button("Crear un usuario", e -> { UI.getCurrent().navigate(AgregarUsuarioView.class); }),
            		new Button("Atender a una consulta", e -> { UI.getCurrent().navigate(ConsultasView.class); })
            		));
            hl.add(new VerticalLayout(new Button("Crear una tarjeta", e -> { UI.getCurrent().navigate(AgregarTarjetaView.class); }),
            		new Button("Gestionar los usuarios, cuentas y tarjetas", e -> { UI.getCurrent().navigate(GestionUsuariosView.class); }),
            		new Button("Ver tu perfil", e -> { UI.getCurrent().navigate(VerPerfilView.class); })
            		));
        }
        
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
