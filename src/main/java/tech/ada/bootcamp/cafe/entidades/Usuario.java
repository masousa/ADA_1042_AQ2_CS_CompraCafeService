package tech.ada.bootcamp.cafe.entidades;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    private String cpf;
    private String nome;
    private double desconto;
    private String identificador;

}
