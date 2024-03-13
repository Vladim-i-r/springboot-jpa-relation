package com.vladimir.curso.springboot.jpa.relation.springbootjparelation.repositories;

import org.springframework.data.repository.CrudRepository;

import com.vladimir.curso.springboot.jpa.relation.springbootjparelation.entities.Invoice;

public interface InvoiceRepository extends CrudRepository<Invoice, Long>{  // No se le agrega @Component ya que el CrudRepo es uno 

}
