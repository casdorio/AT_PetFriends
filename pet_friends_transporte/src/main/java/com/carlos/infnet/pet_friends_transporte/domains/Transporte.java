package com.carlos.infnet.pet_friends_transporte.domains;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "transportes")
public class Transporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pedido_id", nullable = false)
    private Long pedidoId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data")
    private Date dataEnvio = new Date();

    @Embedded
    private Endereco enderecoTransporte;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusTransporte status = StatusTransporte.PENDENTE;

    public Transporte(Long pedidoId, Endereco enderecoTransporte) {
        this.pedidoId = pedidoId;
        this.enderecoTransporte = enderecoTransporte;
    }
}
