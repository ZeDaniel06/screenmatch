package br.com.zedaniel.screenmatch.principal;

import br.com.zedaniel.screenmatch.model.*;
import br.com.zedaniel.screenmatch.repository.SerieRepository;
import br.com.zedaniel.screenmatch.service.ConsumoApi;
import br.com.zedaniel.screenmatch.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;

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
    private List<DadosSerie> dadosSeries = new ArrayList<>();

    private List<Serie> series = new ArrayList<>();

    private SerieRepository repositorio;

    public Principal(SerieRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu(){
        var opcao = 5;
        while (opcao != 0) {


            System.out.println("""
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Lista séries buscadas
                    
                    0 - Sair
                    """);

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção Inválida!");
                    break;
            }
        }

    }

    private void buscarSerieWeb(){
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        //dadosSeries.add(dados);
        repositorio.save(serie);
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie(){
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = scanner.nextLine();
        nomeSerie = nomeSerie.replace(" ", "+");
        String url = ENDERECO_BASE + nomeSerie + API_KEY;
        var json = consumoApi.obterDados(url);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(url);
        return dados;

    }

    private void buscarEpisodioPorSerie(){
        listarSeriesBuscadas();
        System.out.println("Escolha uma série pelo nome: ");
        var nomeSerie = scanner.nextLine();

        Optional<Serie> busca = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nomeSerie.toLowerCase()))
                .findFirst();

        if (busca.isPresent()) {

            var serie = busca.get();

            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serie.getTotalTemporadas(); i++) {
                String titulo = serie.getTitulo().toLowerCase().replace(" ", "+");
                String url = ENDERECO_BASE + titulo + "&season=" + i + API_KEY;
                var json = consumoApi.obterDados(url);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());

            serie.setEpisodios(episodios);
            repositorio.save(serie);
        }else {
            System.out.println("Série não encontrada!");
        }
    }

    private void listarSeriesBuscadas(){

        //series = dadosSeries.stream()
          //              .map(d -> new Serie(d))
            //                    .collect(Collectors.toList());

        series = repositorio.findAll();

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

}





