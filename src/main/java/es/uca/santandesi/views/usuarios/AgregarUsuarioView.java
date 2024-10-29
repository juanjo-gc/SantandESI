package es.uca.santandesi.views.usuarios;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import es.uca.santandesi.data.service.UsuarioService;

@PageTitle("Registrar usuario")
public class AgregarUsuarioView extends VerticalLayout {

	private TextField nombre = new TextField("Nombre");
	private TextField apellidos = new TextField("Apellidos");
	// private TextField contrasenna = new TextField("Contraseña");
	private DatePicker fechaNacimiento = new DatePicker("Fecha de nacimiento");
	private TextField dni = new TextField("DNI");
	private EmailField email = new EmailField("Dirección de correo electrónico");
	private Select<String> rolUsuario = new Select<>();

	private Div usuarioFormView = new Div();
	private Div formDiv = new Div();

	public AgregarUsuarioView(UsuarioService usuarioService) {
		usuarioFormView.setWidth("65%");
		usuarioFormView.addClassNames("flex-col", "justify-center", "content-start");
		add(usuarioFormView);

		rolUsuario.setLabel("Tipo de usuario");
		rolUsuario.setValue("Cliente");
		rolUsuario.setItems("Cliente", "Operador");

		fechaNacimiento.setHelperText("Formato: DD/MM/AAAA");

		dni.setPattern("^[0-9]{8}[A-Z]{1}$");
		dni.setHelperText("Número seguido de la letra, sin guión");

		apellidos.setHelperText("2 apellidos separados por un espacio");

		Button btnContinuar = new Button("Continuar", e -> {
			try {
				usuarioService.addUsuario(nombre.getValue(), apellidos.getValue(), email.getValue(),
						fechaNacimiento.getValue(), dni.getValue(), rolUsuario.getValue());
				Notification.show("Usuario creado correctamente");
			} catch (UsuarioService.DateException ex) {
				Notification.show("El usuario debe ser mayor de 18 años. Revisa la fecha de nacimiento");
			} catch (UsuarioService.DuplicateDNIException ex) {
				Notification.show("Ya existe un usuario con ese DNI registrado en el sistema");
			}
		});

		/*
		 * public Usuario(@NotNull String nombre, @NotNull String apellidos, @NotNull
		 * String contrasenna_hash,
		 * 
		 * @NotNull LocalDate fecha_nacimiento, @NotNull String dNI, String rol)
		 */

		usuarioFormView.add(new H1("Agregar un nuevo usuario"), formDiv);

		FormLayout form = new FormLayout();
		// formDiv.add(form);
		form.setResponsiveSteps(new ResponsiveStep("0", 4));

		btnContinuar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		form.add(nombre, apellidos, email, fechaNacimiento, dni, rolUsuario);
		form.setColspan(nombre, 2);
		form.setColspan(apellidos, 2);
		form.setColspan(email, 3);
		form.setColspan(fechaNacimiento, 1);
		form.setColspan(dni, 3);
		form.setColspan(rolUsuario, 1);
		// form.setColspan(contrasenna, 4);
		formDiv.add(form, btnContinuar);

		setSizeFull();
		setJustifyContentMode(JustifyContentMode.START);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		getStyle().set("text-align", "center");

	}
	/*
	 * private TextField firstName = new TextField("First name"); private TextField
	 * lastName = new TextField("Last name"); private EmailField email = new
	 * EmailField("Email address"); private DatePicker dateOfBirth = new
	 * DatePicker("Birthday"); private PhoneNumberField phone = new
	 * PhoneNumberField("Phone number"); private TextField occupation = new
	 * TextField("Occupation");
	 * 
	 * private Button cancel = new Button("Cancel"); private Button save = new
	 * Button("Save");
	 * 
	 * private Binder<SamplePerson> binder = new Binder<>(SamplePerson.class);
	 * 
	 * public RegistrarseView(SamplePersonService personService) {
	 * addClassName("registrarse-view");
	 * 
	 * add(createTitle()); add(createFormLayout()); add(createButtonLayout());
	 * 
	 * binder.bindInstanceFields(this); clearForm();
	 * 
	 * cancel.addClickListener(e -> clearForm()); save.addClickListener(e -> {
	 * personService.update(binder.getBean());
	 * Notification.show(binder.getBean().getClass().getSimpleName() +
	 * " details stored."); clearForm(); }); }
	 * 
	 * private void clearForm() { binder.setBean(new SamplePerson()); }
	 * 
	 * private Component createTitle() { return new H3("Personal information"); }
	 * 
	 * private Component createFormLayout() { FormLayout formLayout = new
	 * FormLayout(); email.setErrorMessage("Please enter a valid email address");
	 * formLayout.add(firstName, lastName, dateOfBirth, phone, email, occupation);
	 * return formLayout; }
	 * 
	 * private Component createButtonLayout() { HorizontalLayout buttonLayout = new
	 * HorizontalLayout(); buttonLayout.addClassName("button-layout");
	 * save.addThemeVariants(ButtonVariant.LUMO_PRIMARY); buttonLayout.add(save);
	 * buttonLayout.add(cancel); return buttonLayout; }
	 * 
	 * private static class PhoneNumberField extends CustomField<String> { private
	 * ComboBox<String> countryCode = new ComboBox<>(); private TextField number =
	 * new TextField();
	 * 
	 * public PhoneNumberField(String label) { setLabel(label);
	 * countryCode.setWidth("120px"); countryCode.setPlaceholder("Country");
	 * countryCode.setPattern("\\+\\d*"); countryCode.setPreventInvalidInput(true);
	 * countryCode.setItems("+354", "+91", "+62", "+98", "+964", "+353", "+44",
	 * "+972", "+39", "+225"); countryCode.addCustomValueSetListener(e ->
	 * countryCode.setValue(e.getDetail())); number.setPattern("\\d*");
	 * number.setPreventInvalidInput(true); HorizontalLayout layout = new
	 * HorizontalLayout(countryCode, number); layout.setFlexGrow(1.0, number);
	 * add(layout); }
	 * 
	 * @Override protected String generateModelValue() { if (countryCode.getValue()
	 * != null && number.getValue() != null) { String s = countryCode.getValue() +
	 * " " + number.getValue(); return s; } return ""; }
	 * 
	 * @Override protected void setPresentationValue(String phoneNumber) { String[]
	 * parts = phoneNumber != null ? phoneNumber.split(" ", 2) : new String[0]; if
	 * (parts.length == 1) { countryCode.clear(); number.setValue(parts[0]); } else
	 * if (parts.length == 2) { countryCode.setValue(parts[0]);
	 * number.setValue(parts[1]); } else { countryCode.clear(); number.clear(); } }
	 * }
	 */
}
