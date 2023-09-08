package tech.ada.bootcamp.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ada.bootcamp.cafe.entidades.Compra;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
}