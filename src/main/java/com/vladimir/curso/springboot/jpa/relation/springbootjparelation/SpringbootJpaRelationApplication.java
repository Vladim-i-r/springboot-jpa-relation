package com.vladimir.curso.springboot.jpa.relation.springbootjparelation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.vladimir.curso.springboot.jpa.relation.springbootjparelation.entities.Client;
import com.vladimir.curso.springboot.jpa.relation.springbootjparelation.entities.Invoice;
import com.vladimir.curso.springboot.jpa.relation.springbootjparelation.repositories.ClientRepository;
import com.vladimir.curso.springboot.jpa.relation.springbootjparelation.repositories.InvoiceRepository;

@SpringBootApplication
public class SpringbootJpaRelationApplication implements CommandLineRunner{

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaRelationApplication.class, args);
	}

	@Override													//* Este metodo se debe crear al implementar el CommandLineRunner */
	public void run(String... args) throws Exception {
		//manyToOne();											//? Al correrlo por 1ra vez marcara error porque no existen aun las foreign keys que trata de eliminar
		manyToOneFindByIdClient();
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
