package tech.ada.bootcamp.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ada.bootcamp.cafe.entidades.Combo;

import java.util.Optional;

@Repository
public interface ComboRepository extends JpaRepository<Combo, Long> {
    boolean existsByDescricao(String descricao);

    Optional<Combo> findByIdentificador(String identificador);
}