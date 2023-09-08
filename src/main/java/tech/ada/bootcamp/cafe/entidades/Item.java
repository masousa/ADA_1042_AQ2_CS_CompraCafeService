package tech.ada.bootcamp.cafe.entidades;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String descricao;

    private double valorUnitario;

    private TipoUnidade tipoUnidade;

    private Long quantidadeUnidade;

    private String identificador;

}
