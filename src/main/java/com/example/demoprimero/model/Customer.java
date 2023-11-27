package com.example.demoprimero.model;

import com.example.demoprimero.audit.Auditable;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "customer")
public class Customer extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String direccion;
    private String email;

    private String password;

    @Column(name = "is_active")
    private boolean isActive = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_registro")
    private Date fechaRegistro;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Account> cuentas;
}
