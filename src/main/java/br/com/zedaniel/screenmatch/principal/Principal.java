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


        System.out.println("Digite o nome da série: ");
        var nomeSerie = scanner.nextLine();
        nomeSerie = nomeSerie.replace(" ", "+");
        String url = ENDERECO_BASE + nomeSerie + API_KEY;

        var json = consumoApi.obterDados(url);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);

        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();


		for(int i = 1; i <= dados.totalTemporadas(); i++){
			url = ENDERECO_BASE + nomeSerie + "&season=" + i + API_KEY;
			json = consumoApi.obterDados(url);

			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}

		//temporadas.forEach(System.out::println);

//        temporadas.forEach(t -> t.episodios()
//                .forEach(e -> System.out.println(e.titulo())));



//        List<String> nomes = Arrays.asList("Jacque", "Iasmin", "Paulo", "Rodrigo", "Nico");
//        nomes.stream()
//                .sorted()
//                .limit(3)
//                .filter(n -> n.startsWith("N"))
//                .map(n -> n.toUpperCase())
//                .forEach(System.out::println);


        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

//        System.out.println("\n-----------\nTop 10 episódios");
//        dadosEpisodios.stream()
//                .filter(n -> !n.avaliacao().equals("N/A"))
//                .peek(e -> System.out.println("Primeiro filtro(N/A) " + e))
//                        .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//                .peek(e -> System.out.println("Ordenação " + e))
//                                .limit(10)
//                .peek(e -> System.out.println("Limitando " + e))
//                .map(e -> e.titulo().toUpperCase())
//                .peek(e -> System.out.println("Mapeamento " + e))
//                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d)))
                .collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("Digite sua busca de episódio: ");
        var trechoTitulo = scanner.nextLine();
        Optional<Episodio> busca = episodios.stream()
                .filter(e -> e.getTitulo()
                        .toUpperCase()
                        .contains(trechoTitulo.toUpperCase()))
                .findFirst();

        if(busca.isPresent()){
            System.out.println("Episódio encontrado!");
            System.out.println("Temporada: " + busca.get().getTemporada());
        }else{
            System.out.println("Episódio não encontrado!");
        }

//        episodios.stream()
//                .sorted(Comparator.comparing(Episodio::getAvaliacao).reversed())
//                .limit(5)
//                .forEach(System.out::println);
//
//        System.out.println("Exibir episódios a partir de que ano?");
//        var ano = scanner.nextInt();
//        scanner.nextLine();
//
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e -> e.getDataLancamento()!= null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println("Temporada: " +
//                        e.getTemporada() + ", Episódio: " + e.getTitulo() +
//                        ", Data de lançamento: " + e.getDataLancamento().format(formatter)));
    }

}


