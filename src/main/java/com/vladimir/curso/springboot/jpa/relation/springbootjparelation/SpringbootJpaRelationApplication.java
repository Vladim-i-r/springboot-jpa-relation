package com.vladimir.curso.springboot.jpa.relation.springbootjparelation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.vladimir.curso.springboot.jpa.relation.springbootjparelation.entities.Address;
import com.vladimir.curso.springboot.jpa.relation.springbootjparelation.entities.Client;
import com.vladimir.curso.springboot.jpa.relation.springbootjparelation.entities.ClientDetails;
import com.vladimir.curso.springboot.jpa.relation.springbootjparelation.entities.Course;
import com.vladimir.curso.springboot.jpa.relation.springbootjparelation.entities.Invoice;
import com.vladimir.curso.springboot.jpa.relation.springbootjparelation.entities.Student;
import com.vladimir.curso.springboot.jpa.relation.springbootjparelation.repositories.ClientDetailsRepository;
import com.vladimir.curso.springboot.jpa.relation.springbootjparelation.repositories.ClientRepository;
import com.vladimir.curso.springboot.jpa.relation.springbootjparelation.repositories.CourseRepository;
import com.vladimir.curso.springboot.jpa.relation.springbootjparelation.repositories.InvoiceRepository;
import com.vladimir.curso.springboot.jpa.relation.springbootjparelation.repositories.StudentRepository;

@SpringBootApplication
public class SpringbootJpaRelationApplication implements CommandLineRunner{

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private ClientDetailsRepository clientDetailsRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaRelationApplication.class, args);
	}

	@Override													//* Este metodo se debe crear al implementar el CommandLineRunner */
	public void run(String... args) throws Exception {
		//manyToOne();											//? Al correrlo por 1ra vez marcara error porque no existen aun las foreign keys que trata de eliminar
		//manyToOneFindByIdClient();
		//oneToMany();
		//oneToManyFindById();
		//removeAddress();
		// removeAddressFindById();
		//oneToManyInvoiceBidireccional();
		// oneToManyInvoiceBidireccionalFindById();
		//removeInvoiceBidireccionalFindById();
		// removeInvoiceBidireccional();
		//oneToOne();
		// oneToOneBidireccional();
		//oneToOneBidireccionalFindById();
		// manyToMany();
		//manyToManyFindById();
		//manyToManyRemoveFind();
		//manyToManyRemove();
		// manyToManyBidireccional();
		// manyToManyBidireccionalRemove();
		manyToManyBidireccionalFindById();
	}

	@SuppressWarnings("null")
	@Transactional
	public void manyToManyBidireccionalFindById(){

		Optional<Student> studentOpt1 = studentRepository.findById(1L);
		Optional<Student> studentOpt2 = studentRepository.findById(2L);

		Student student1 = studentOpt1.get();   // esto es para evitar el ifPresent()
		Student student2 = studentOpt2.get();

		Course course1 = courseRepository.findById(1L).get();
		Course course2 = courseRepository.findById(2L).get();

		student1.addCourse(course1);
		student1.addCourse(course2);
		student2.addCourse(course2);

		studentRepository.saveAll(Set.of(student1,student2));  // Set ss lo mismo que List

		System.out.println(student1);
		System.out.println(student2);
	}

	@SuppressWarnings("null")
	@Transactional
	public void manyToManyBidireccionalRemove(){

		Student student1 = new Student("Jano", "Puro");
		Student student2 = new Student("Alba", "Godin");

		Course course1 = new Course("Curso de Java master", "Andres");
		Course course2 = new Course("Curso de Spring master", "Andres");

		student1.addCourse(course1);
		student1.addCourse(course2);
		student2.addCourse(course2);

		studentRepository.saveAll(Set.of(student1,student2));  // Set ss lo mismo que List

		System.out.println(student1);
		System.out.println(student2);

		Optional<Student> studentOptDB = studentRepository.findOneWithCourses(3L);
		if(studentOptDB.isPresent()){
			Student studentDB = studentOptDB.get();
			Optional<Course> courseOptDB = courseRepository.findById(3L);
			if(courseOptDB.isPresent()){
				Course courseDB = courseOptDB.get();
				studentDB.removeCourse(courseDB);

				studentRepository.save(studentDB);
				System.out.println(studentDB);
			}
		}
	}

	@SuppressWarnings("null")
	@Transactional
	public void manyToManyBidireccional(){

		Student student1 = new Student("Jano", "Puro");
		Student student2 = new Student("Alba", "Godin");

		Course course1 = new Course("Curso de Java master", "Andres");
		Course course2 = new Course("Curso de Spring master", "Andres");

		student1.addCourse(course1);
		student1.addCourse(course2);
		student2.addCourse(course2);

		studentRepository.saveAll(Set.of(student1,student2));  // Set ss lo mismo que List

		System.out.println(student1);
		System.out.println(student2);

		
	}


	@SuppressWarnings("null")
	@Transactional
	public void manyToManyRemove(){

		Student student1 = new Student("Jano", "Puro");
		Student student2 = new Student("Alba", "Godin");

		Course course1 = new Course("Curso de Java master", "Andres");
		Course course2 = new Course("Curso de Spring master", "Andres");

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		studentRepository.saveAll(Set.of(student1,student2));  // Set ss lo mismo que List

		System.out.println(student1);
		System.out.println(student2);

		Optional<Student> studentOptDB = studentRepository.findOneWithCourses(3L);
		if(studentOptDB.isPresent()){
			Student studentDB = studentOptDB.get();
			Optional<Course> courseOptDB = courseRepository.findById(3L);
			if(courseOptDB.isPresent()){
				Course courseDB = courseOptDB.get();
				studentDB.getCourses().remove(courseDB);

				studentRepository.save(studentDB);
				System.out.println(studentDB);
			}
		}
	}

	@SuppressWarnings("null")
	@Transactional
	public void manyToManyRemoveFind(){

		Optional<Student> studentOpt1 = studentRepository.findById(1L);
		Optional<Student> studentOpt2 = studentRepository.findById(2L);

		Student student1 = studentOpt1.get();   // esto es para evitar el ifPresent()
		Student student2 = studentOpt2.get();

		Course course1 = courseRepository.findById(1L).get();
		Course course2 = courseRepository.findById(2L).get();

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		studentRepository.saveAll(Set.of(student1,student2));  // Set ss lo mismo que List

		System.out.println(student1);
		System.out.println(student2);

		Optional<Student> studentOptDB = studentRepository.findOneWithCourses(1L);
		if(studentOptDB.isPresent()){
			Student studentDB = studentOptDB.get();
			Optional<Course> courseOptDB = courseRepository.findById(2L);
			if(courseOptDB.isPresent()){
				Course courseDB = courseOptDB.get();
				studentDB.getCourses().remove(courseDB);

				studentRepository.save(studentDB);
				System.out.println(studentDB);
			}
		}
	}

	@SuppressWarnings("null")
	@Transactional
	public void manyToManyFindById(){

		Optional<Student> studentOpt1 = studentRepository.findById(1L);
		Optional<Student> studentOpt2 = studentRepository.findById(2L);

		Student student1 = studentOpt1.get();   // esto es para evitar el ifPresent()
		Student student2 = studentOpt2.get();

		Course course1 = courseRepository.findById(1L).get();
		Course course2 = courseRepository.findById(2L).get();

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		studentRepository.saveAll(Set.of(student1,student2));  // Set ss lo mismo que List

		System.out.println(student1);
		System.out.println(student2);
	}

	@SuppressWarnings("null")
	@Transactional
	public void manyToMany(){

		Student student1 = new Student("Jano", "Puro");
		Student student2 = new Student("Alba", "Godin");

		Course course1 = new Course("Curso de Java master", "Andres");
		Course course2 = new Course("Curso de Spring master", "Andres");

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		studentRepository.saveAll(Set.of(student1,student2));  // Set ss lo mismo que List

		System.out.println(student1);
		System.out.println(student2);
	}

	public void oneToOneBidireccionalFindById(){

		Optional<Client> optClient = clientRepository.findOne(2L);
		optClient.ifPresent(client -> {

			ClientDetails clientDetails = new ClientDetails(true,5000);
	
			client.setClientDetails(clientDetails);
			
			clientRepository.save(client);
	
			System.out.println(client);
		});

	}

	public void oneToOneBidireccional(){

		Client client = new Client("Erba", "Pura");
		ClientDetails clientDetails = new ClientDetails(true,5000);

		client.setClientDetails(clientDetails);
		
		clientRepository.save(client);

		System.out.println(client);

	}

	public void oneToOneById(){
		ClientDetails clientDetails = new ClientDetails(true,5000);
		clientDetailsRepository.save(clientDetails);

		Optional<Client> optClient = clientRepository.findOne(2L);  //Para que no de error con el lazily con findbyid
		optClient.ifPresent(client -> {
			
			client.setClientDetails(clientDetails);
			clientRepository.save(client);
	
			System.out.println(client);
		});

	}


	public void oneToOne(){
		ClientDetails clientDetails = new ClientDetails(true,5000);
		clientDetailsRepository.save(clientDetails);

		Client client = new Client("Erba", "Pura");
		client.setClientDetails(clientDetails);
		clientRepository.save(client);

		System.out.println(client);

	}

	@Transactional
	public void removeInvoiceBidireccional(){

		Client client = new Client("Fran", "Moras");
		
		Invoice invoice1 =  new Invoice("Compras de la casa", 5000L);
		Invoice invoice2 =  new Invoice("Compras del trabajo", 8000L);

		List<Invoice> invoices = new ArrayList<>();
		invoices.add(invoice1);
		invoices.add(invoice2);
		client.setInvoices(invoices);

		invoice1.setClient(client);		// ya que es bidireccional
		invoice2.setClient(client);

		clientRepository.save(client);  // se guarda el cliente ya que tiene varias facturas, cascade all, incluira las facturas
		System.out.println(client);
	
		Optional<Client> optClientDB = clientRepository.findOne(3L);
		optClientDB.ifPresent(clientDB -> {
			Optional<Invoice> optInvoice = invoiceRepository.findById(2L);
			optInvoice.ifPresent(invoice -> {
				//client.getInvoices().remove(invoice);
				clientDB.removeInvoice(invoice);			// se puede crear un metodo para ello 
				invoice.setClient(null);

				clientRepository.save(clientDB);
				System.out.println(clientDB);
			});
		});
	}

	@Transactional
	public void removeInvoiceBidireccionalFindById(){

		Optional<Client> optClient = clientRepository.findOne(1L);
		optClient.ifPresent(client -> {

			Invoice invoice1 =  new Invoice("Compras de la casa", 5000L);
			Invoice invoice2 =  new Invoice("Compras del trabajo", 8000L);
	
			List<Invoice> invoices = new ArrayList<>();
			invoices.add(invoice1);
			invoices.add(invoice2);

			client.setInvoices(invoices);
	
			invoice1.setClient(client);		// ya que es bidireccional
			invoice2.setClient(client);
	
			clientRepository.save(client);  // se guarda el cliente ya que tiene varias facturas, cascade all, incluira las facturas
			System.out.println(client);
		});
		Optional<Client> optClientDB = clientRepository.findOne(1L);
		optClientDB.ifPresent(client -> {
			Optional<Invoice> optInvoice = invoiceRepository.findById(2L);
			optInvoice.ifPresent(invoice -> {
				//client.getInvoices().remove(invoice);
				client.removeInvoice(invoice);			// se puede crear un metodo para ello 
				invoice.setClient(null);

				clientRepository.save(client);
				System.out.println(client);
			});
		});
	}

	@Transactional
	public void oneToManyInvoiceBidireccionalFindById(){

		Optional<Client> optClient = clientRepository.findOne(1L);
		optClient.ifPresent(client -> {

			Invoice invoice1 =  new Invoice("Compras de la casa", 5000L);
			Invoice invoice2 =  new Invoice("Compras del trabajo", 8000L);
	
			List<Invoice> invoices = new ArrayList<>();
			invoices.add(invoice1);
			invoices.add(invoice2);

			client.setInvoices(invoices);
	
			invoice1.setClient(client);		// ya que es bidireccional
			invoice2.setClient(client);
	
			clientRepository.save(client);  // se guarda el cliente ya que tiene varias facturas, cascade all, incluira las facturas
			System.out.println(client);
		});
	}

	@Transactional
	public void oneToManyInvoiceBidireccional(){

		Client client = new Client("Fran", "Moras");
		Invoice invoice1 =  new Invoice("Compras de la casa", 5000L);
		Invoice invoice2 =  new Invoice("Compras del trabajo", 8000L);

		List<Invoice> invoices = new ArrayList<>();
		invoices.add(invoice1);
		invoices.add(invoice2);
		client.setInvoices(invoices);

		invoice1.setClient(client);		// ya que es bidireccional
		invoice2.setClient(client);

		clientRepository.save(client);  // se guarda el cliente ya que tiene varias facturas, cascade all, incluira las facturas
		System.out.println(client);
	}

	@Transactional
	public void removeAddress(){								// Crea un cliente con distintas direcciones 

		Client client = new Client("Fran", "Moras");
		Address address1 = new Address("El Verjel", 1567);
		Address address2 = new Address("Vasco de Gama", 9875);

		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		clientRepository.save(client);						// * Con guardar el cliente se guardan las direcciones

		System.out.println(client);

		Optional<Client> optClient = clientRepository.findById(3L);
		optClient.ifPresent(c -> {
			c.getAddresses().remove(address1);						//Tiene que verificar si estan en la misma instancia y eso se hace desde el entity Address con Hashcode e equals.
			clientRepository.save(c);
			System.out.println(c);
		});
	}

	@Transactional
	public void removeAddressFindById(){						//Asignar direcciones a cliente existente

		Optional<Client> optClient = clientRepository.findById(2L);
		optClient.ifPresent(client ->{

			Address address1 = new Address("Patriotismo", 642);
			Address address2 = new Address("Vasco de Quiroga", 125);
	
			client.setAddresses(Arrays.asList(address1,address2));
	
			clientRepository.save(client);						// * Con guardar el cliente se guardan las direcciones
	
			System.out.println(client);
			
			// Optional<Client> optClient2 = clientRepository.findById(2L);
			Optional<Client> optClient2 = clientRepository.findOneWithAddresses(2L);
			optClient2.ifPresent(c -> {
				c.getAddresses().remove(address2);						//Tiene que verificar si estan en la misma instancia y eso se hace desde el entity Address con Hashcode e equals.
				clientRepository.save(c);
				System.out.println(c);
		});
		});

	}

	@Transactional
	public void oneToManyFindById(){						//Asignar direcciones a cliente existente

		Optional<Client> optClient = clientRepository.findById(2L);
		optClient.ifPresent(client ->{

			Address address1 = new Address("Patriotismo", 642);
			Address address2 = new Address("Vasco de Quiroga", 125);
	
			client.setAddresses(Arrays.asList(address1,address2));
	
			clientRepository.save(client);						// * Con guardar el cliente se guardan las direcciones
	
			System.out.println(client);
		});

	}

	@Transactional
	public void oneToMany(){								// Crea un cliente con distintas direcciones 

		Client client = new Client("Fran", "Moras");
		Address address1 = new Address("El Verjel", 1567);
		Address address2 = new Address("Vasco de Gama", 9875);

		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		clientRepository.save(client);						// * Con guardar el cliente se guardan las direcciones

		System.out.println(client);
	}

	@Transactional
	public void manyToOne(){

		Client client = new Client("John", "Doe");							//Persiste primero el cliente
		clientRepository.save(client);

		Invoice invoice = new Invoice("Compras de Oficina", 2000L);
		invoice.setClient(client);
		Invoice invoiceDB = invoiceRepository.save(invoice);								// Crea la factura con el cliente 
		System.out.println(invoiceDB);
	}

	@Transactional
	public void manyToOneFindByIdClient(){

		Optional<Client> optClient = clientRepository.findById(1L);							//Se busca al cliente en la base de datos para asignarlo
		if (optClient.isPresent()){
			Client client = optClient.orElseThrow();

			Invoice invoice = new Invoice("Compras de Oficina", 2000L);
			invoice.setClient(client);
			Invoice invoiceDB = invoiceRepository.save(invoice);								
			System.out.println(invoiceDB);
		}
	}

}
