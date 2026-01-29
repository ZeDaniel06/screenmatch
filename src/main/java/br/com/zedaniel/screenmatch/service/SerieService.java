package br.com.zedaniel.screenmatch.service;

import br.com.zedaniel.screenmatch.dto.SerieDTO;
import br.com.zedaniel.screenmatch.model.Serie;
import br.com.zedaniel.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SerieService {
    @Autowired
    private SerieRepository repositorio;

    public List<SerieDTO> obterTodasAsSeries(){
        return converterDados(repositorio.findAll());
    }

    public List<SerieDTO> obterTop5Series() {
        return converterDados(repositorio.findTop5ByOrderByAvaliacaoDesc());
    }

    private List<SerieDTO> converterDados(List<Serie> series){
        return series.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(),
                        s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obterLancamentos() {
        return converterDados(repositorio.findTop5ByOrderByEpisodiosDataLancamentoDesc());
    }
}
