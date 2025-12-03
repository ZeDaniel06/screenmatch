package br.com.zedaniel.screenmatch.repository;

import br.com.zedaniel.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Serie, Long> {
}
