package com.carlos.infnet.pet_friends_pedidos.domains;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.carlos.infnet.pet_friends_pedidos.repositories.PedidoStatusTypeConverter;
import com.carlos.infnet.pet_friends_pedidos.repositories.PrecoTypeConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedidos")
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "ORDER_DATE")
    @Temporal(TemporalType.DATE)
    private Date orderDate = new Date();

    @JsonIgnoreProperties(value = "orderId")
    @OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL)
    private List<ItemPedido> itemList = new ArrayList<>();

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Convert(converter = PedidoStatusTypeConverter.class)
    @Column(name = "STATUS")
    private PedidoStatus status = PedidoStatus.PENDENTE;

    @Convert(converter = PrecoTypeConverter.class)
    @Column(name = "VALOR_TOTAL")
    private Preco total = new Preco(BigDecimal.ZERO);

    public Pedido(Long id) {
        this.id = id;
    }

    public Pedido(Long id, Long customerId) {
        this.id = id;
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", customerId=" + customerId +
                ", status=" + status +
                ", valorTotal=" + total +
                '}';
    }

    public void adicionarItem(Long productId, int quantidade, double unitValue) {
        if (productId == null) {
            throw new IllegalArgumentException("Produto não existe");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade precisa ser positiva");
        }
        if (this.status != PedidoStatus.PENDENTE) {
            throw new IllegalStateException("Não é possível inserir itens em um pedido em andamento");
        }
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setOrderId(this);
        itemPedido.setProductId(productId);
        itemPedido.setQuantity(quantidade);
        itemPedido.setTotal(new Preco(new BigDecimal(unitValue * quantidade)));

        this.itemList.add(itemPedido);
        this.setTotal(this.total.somar(itemPedido.getTotal()));
    }

    public void fecharPedido() {
        if (this.status != PedidoStatus.PENDENTE) {
            throw new IllegalStateException("Não é possível fechar um pedido que não é novo");
        }
        if (this.itemList.isEmpty()) {
            throw new IllegalStateException("Não é possível fechar um pedido vazio");
        }
        this.status = PedidoStatus.FINALIZADO;
    }

    public void cancelarPedido() {
        if (this.status != PedidoStatus.FINALIZADO) {
            throw new IllegalStateException("Não é possível cancelar um pedido que não esteja fechado");
        }
        this.status = PedidoStatus.ANULADO;
    }

    public void enviarPedido() {
        if (this.status != PedidoStatus.FINALIZADO) {
            throw new IllegalStateException("Não é possível enviar um pedido que não esteja fechado");
        }
        this.status = PedidoStatus.EM_PREPARACAO;
    }
}
