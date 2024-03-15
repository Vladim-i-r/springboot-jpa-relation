package com.vladimir.curso.springboot.jpa.relation.springbootjparelation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.vladimir.curso.springboot.jpa.relation.springbootjparelation.entities.Address;
import com.vladimir.curso.springboot.jpa.relation.springbootjparelation.entities.Client;
import com.vladimir.curso.springboot.jpa.relation.springbootjparelation.entities.ClientDetails;
import com.vladimir.curso.springboot.jpa.relation.springbootjparelation.entities.Invoice;
import com.vladimir.curso.springboot.jpa.relation.springbootjparelation.repositories.ClientDetailsRepository;
import com.vladimir.curso.springboot.jpa.relation.springbootjparelation.repositories.ClientRepository;
import com.vladimir.curso.springboot.jpa.relation.springbootjparelation.repositories.InvoiceRepository;

@SpringBootApplication
public class SpringbootJpaRelationApplication implements CommandLineRunner{

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private ClientDetailsRepository clientDetailsRepository;

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
		oneToOneBidireccionalFindById();
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
