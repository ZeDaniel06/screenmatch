package br.com.zedaniel.screenmatch.principal;

import br.com.zedaniel.screenmatch.model.DadosEpisodio;
import br.com.zedaniel.screenmatch.model.DadosSerie;
import br.com.zedaniel.screenmatch.model.DadosTemporada;
import br.com.zedaniel.screenmatch.service.ConsumoApi;
import br.com.zedaniel.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

		temporadas.forEach(System.out::println);

        temporadas.forEach(t -> t.episodios()
                .forEach(e -> System.out.println(e.titulo())));




    }

}


