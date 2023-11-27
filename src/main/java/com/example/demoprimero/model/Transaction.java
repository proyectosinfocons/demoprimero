package com.example.demoprimero.model;

import com.example.demoprimero.audit.Auditable;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "transaction")
public class Transaction extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_transaccion", nullable = false)
    private String tipoTransaccion;

    private Double monto;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    private String detalles;
}
