package br.com.solutionsnote.note.repository;

import br.com.solutionsnote.note.model.Automovel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutomovelRepository extends JpaRepository<Automovel, Long> {
    Page<Automovel> findByTipoContainingIgnoreCase(String tipo, Pageable pageable);
}