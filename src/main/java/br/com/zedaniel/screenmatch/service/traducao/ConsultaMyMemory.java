package br.com.zedaniel.screenmatch.service.traducao;

import br.com.zedaniel.screenmatch.service.ConsumoApi;
import br.com.zedaniel.screenmatch.service.ConverteDados;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLEncoder;

public class ConsultaMyMemory {
    public static String obterTraducao(String text) {
        ConverteDados conversor = new ConverteDados();
        ConsumoApi consumo = new ConsumoApi();

        String texto = URLEncoder.encode(text);
        String langpair = URLEncoder.encode("autodetect|pt-br");

        String url = "https://api.mymemory.translated.net/get?q=" + texto + "&langpair=" + langpair;

        String json = consumo.obterDados(url);

        DadosTraducao traducao = conversor.obterDados(json, DadosTraducao.class);


        return traducao.dadosResposta().textoTraduzido();
    }
}