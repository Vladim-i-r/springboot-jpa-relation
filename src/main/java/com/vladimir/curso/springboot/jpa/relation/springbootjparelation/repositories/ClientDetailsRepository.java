package com.vladimir.curso.springboot.jpa.relation.springbootjparelation.repositories;

import org.springframework.data.repository.CrudRepository;

import com.vladimir.curso.springboot.jpa.relation.springbootjparelation.entities.ClientDetails;

public interface ClientDetailsRepository extends CrudRepository<ClientDetails, Long>{

}
