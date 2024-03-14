package com.vladimir.curso.springboot.jpa.relation.springbootjparelation.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Long total;

    @ManyToOne                     //* Muchas facturas hacia un cliente, se configura el foreign id como client_id por defecto, pero se le puede cambiar con JoinColumn 
    @JoinColumn(name = "id_client_temp")    //* PERSONALIZANDO FOREIGN KEY       por defecto es client_id */
    private Client client;

    public Invoice() {
    }

    
    public Invoice(String description, Long total) {
        this.description = description;
        this.total = total;
    }

    public Invoice(Long id, String description, Long total) {
        this.id = id;
        this.description = description;
        this.total = total;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Long getTotal() {
        return total;
    }
    public void setTotal(Long total) {
        this.total = total;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    } 

    @Override
    public String toString() {
        return "{id=" + id + ", description=" + description + ", total=" + total + ", client=" + client + "}";
    }

   

    
}
