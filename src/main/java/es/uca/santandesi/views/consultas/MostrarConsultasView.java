package es.uca.santandesi.views.consultas;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.messages.MessageInputI18n;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.server.VaadinSession;

import es.uca.santandesi.data.entity.Consulta;
import es.uca.santandesi.data.entity.Mensaje;
import es.uca.santandesi.data.entity.Usuario;
import es.uca.santandesi.data.service.ConsultaService;
import es.uca.santandesi.data.service.MensajeService;
import es.uca.santandesi.data.service.UsuarioService;

public class MostrarConsultasView extends VerticalLayout {
	
		Grid<Button> gridConsultas = new Grid<Button>();
		Consulta consulta;
	
	public MostrarConsultasView(ConsultaService consultaService, UsuarioService usuarioService, MensajeService mensajeService) {
		Usuario usuario = VaadinSession.getCurrent().getAttribute(Usuario.class);
		List<Consulta> consultasUsuario = new ArrayList<>();
		
		if(usuario.getRol().compareTo("cliente") == 0)
			consultasUsuario = consultaService.getConsultasCliente(usuario.getId());
		else
			consultasUsuario = consultaService.getConsultasGestor(usuario.getId());
		
		HorizontalLayout hl = new HorizontalLayout();
		VerticalLayout vl = new VerticalLayout();
		MessageList chat = new MessageList();
		MessageInput messageInput = new MessageInput();
		messageInput.setI18n(new MessageInputI18n().setMessage("Mensaje").setSend("Enviar"));
		messageInput.setVisible(false);
		
		List<MessageListItem> mliList = new ArrayList<>();
		
		messageInput.addSubmitListener(submitEvent -> {
			Mensaje mensaje = mensajeService.update(new Mensaje(consulta, submitEvent.getValue(), usuario));
			consulta.addMensaje(mensaje);
	    	consultaService.update(consulta);
	    	
			MessageListItem message = new MessageListItem(
					submitEvent.getValue(), LocalDateTime.now().toInstant(ZoneOffset.UTC), usuario.getNombre());
			mliList.add(message);
			chat.setItems(mliList);
		});

		Button cerrarConsulta = new Button("Cerrar la consulta", e -> {
			consulta.setFecha_cierre(LocalDateTime.now());
			consultaService.update(consulta);
			UI.getCurrent().getPage().reload();
		});
		cerrarConsulta.setVisible(false);
		
		ListBox<Consulta> listBox = new ListBox<Consulta>();
		listBox.setItems(consultasUsuario);
        listBox.setRenderer(new ComponentRenderer<VerticalLayout, Consulta>(consultaRend -> {
        	Paragraph paragraph = new Paragraph("Consulta abierta");
        	if(consultaRend.getFecha_cierre() != null) {        		
        		paragraph.setText("Consulta cerrada");
        		
        	}
        	return new VerticalLayout(new Button(consultaRend.getAsunto(), e -> {
        		consulta = consultaRend;
        		mliList.clear();

            	if(consultaRend.getFecha_cierre() != null) {
            		messageInput.setVisible(false);
            		cerrarConsulta.setVisible(false);
            	}
        		else {
        			messageInput.setVisible(true);
            		cerrarConsulta.setVisible(true);
        		}
        			
        		for(Mensaje mensaje: consultaRend.getMensajes()) {
        			MessageListItem message = new MessageListItem(mensaje.getTexto(), mensaje.getFecha_envio().toInstant(ZoneOffset.UTC), mensaje.getUsuario().getNombre());
        			mliList.add(message);
        		}
        		chat.setItems(mliList);
        	}), paragraph);
        }));
        
        vl.add(cerrarConsulta, chat, messageInput);
		hl.add(new VerticalLayout(new H3("Tus consultas"), listBox), vl);
		add(hl);
	}

}
