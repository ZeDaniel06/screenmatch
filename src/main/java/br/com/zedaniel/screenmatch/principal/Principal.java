package br.com.zedaniel.screenmatch.principal;

import br.com.zedaniel.screenmatch.model.DadosEpisodio;
import br.com.zedaniel.screenmatch.model.DadosSerie;
import br.com.zedaniel.screenmatch.model.DadosTemporada;
import br.com.zedaniel.screenmatch.model.Episodio;
import br.com.zedaniel.screenmatch.service.ConsumoApi;
import br.com.zedaniel.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner scanner = new Scanner(System.in);

    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=93684a76";
    public void exibeMenu(){

        System.out.println("""
                1 - Buscar séries
                2 - Buscar episódios
                
                0 - Sair
                """);

        var opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao){
            case 1:
                buscarSerieWeb();
                break;
            case 2:
                buscarEpisodioPorSerie();
                break;
            case 0:
                System.out.println("Saindo...");
                break;
            default:
                System.out.println("Opção Inválida!");
                break;
        }

    }

    private void buscarSerieWeb(){
        DadosSerie dados = getDadosSerie();
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie(){
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = scanner.nextLine();
        nomeSerie = nomeSerie.replace(" ", "+");
        String url = ENDERECO_BASE + nomeSerie + API_KEY;
        var json = consumoApi.obterDados(url);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;

    }

    private void buscarEpisodioPorSerie(){
        DadosSerie dadosSerie = getDadosSerie();
        List<DadosTemporada> temporadas = new ArrayList<>();

        for(int i = 1; i <= dadosSerie.totalTemporadas(); i++){
            String titulo = dadosSerie.titulo().toLowerCase().replace(" ", "+");
            String url = ENDERECO_BASE + titulo + "&season=" + i + API_KEY;
            var json = consumoApi.obterDados(url);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);
    }

}


