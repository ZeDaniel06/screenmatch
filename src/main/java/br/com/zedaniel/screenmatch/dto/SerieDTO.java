package br.com.zedaniel.screenmatch.dto;

import br.com.zedaniel.screenmatch.model.Categoria;


public record SerieDTO(Long id,
                        String titulo,
                       Integer totalTemporadas,
                       Double avaliacao,
                       Categoria genero,
                       String atores,
                       String poster,
                       String sinopse) {
}
