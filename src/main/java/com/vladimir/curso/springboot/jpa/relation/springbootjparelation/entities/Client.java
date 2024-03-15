package com.vladimir.curso.springboot.jpa.relation.springbootjparelation.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
//import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name="clients")
public class Client {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;
    
    //@JoinColumn(name = "cli/ent_id")                                         //? Evitas tambien que se cree una tabla externa de client_address, se coloca el foreign key en esta tabla
                                                                                               // * para eliminar el id relacionado a las direcciones y no quede null
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)// fetch = FetchType.EAGER)                                // * cascade = Cuando se cree un cliente se elimina la direccion asi mismo cuando se crea 
    @JoinTable(                                                                                                         
        name = "tbl_clientes_to_direcciones", 
        joinColumns = @JoinColumn(name="id_cliente"),
        inverseJoinColumns = @JoinColumn(name="id_direcciones"), 
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_direcciones"}))   // * Con esto se crea una tabla con dicha relacion de cliente y direcciones
    private List<Address> addresses;                                     // * Un cliente para distintas direcciones 

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client")         //! El dueño de la relacion es quien tiene la foreign key, que la tiene el Invoice @JoinColumn
    private List<Invoice> invoices;                                                         //! y se indica el nombre del atributo "client" en mappedBy

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client")   //? COMO NO LLEVA ESTE EL FOREIGN, LLEVA LOS DETALLES DE ONETOONE
    private ClientDetails clientDetails;

    public Client() {
        addresses = new ArrayList<>();
        invoices = new ArrayList<>();
    }

    public Client(String name, String lastname) {
        this();                                     // * Manda llamar al constructor vacio que seria lo mismo que poner el addresses = new;
        this.name = name;
        this.lastname = lastname;
    }

    public Client(Long id, String name, String lastname) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public ClientDetails getClientDetails() {
        return clientDetails;
    }

    public void setClientDetails(ClientDetails clientDetails) {
        this.clientDetails = clientDetails;
        clientDetails.setClient(this);                              // * Se agrega esto para simplificar codigo, pero esta linea puede ir en el principal
    }       

    public void removeClientDetails(ClientDetails clientDetails) {   // * se crea un metodo remove
        clientDetails.setClient(null);
        this.clientDetails = null;
    }

    @Override
    public String toString() {
        return "{id=" + id + ", name=" + name + ", lastname=" + lastname + ", invoices" + invoices + ", addresses" + addresses + ", clientDetails" + clientDetails + "}";
    }

    public void removeInvoice(Invoice invoice) {
        this.getInvoices().remove(invoice);
        invoice.setClient(null);
    }

    

  

    

}
