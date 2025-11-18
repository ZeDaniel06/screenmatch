package br.com.zedaniel.screenmatch;

import br.com.zedaniel.screenmatch.model.DadosSerie;
import br.com.zedaniel.screenmatch.service.ConsumoApi;
import br.com.zedaniel.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoApi();
		var url = "https://www.omdbapi.com/?t=breaking+bad&apikey=93684a76";
		var json = consumoApi.obterDados(url);
		System.out.println(json);
		var conversor = new ConverteDados();
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);
//		url = "https://coffee.alexflipnote.dev/random.json";
//		json = consumoApi.obterDados(url);
//		System.out.println(json);
	}
}
