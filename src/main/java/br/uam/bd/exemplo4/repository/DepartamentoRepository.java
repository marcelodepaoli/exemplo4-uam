package br.uam.bd.exemplo4.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.uam.bd.exemplo4.model.Departamento;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
    
}
