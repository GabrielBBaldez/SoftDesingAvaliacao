package com.desafioSoftDesing.demo.associado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long> {
    Optional<Object> findByCpf(String cpf);
}
