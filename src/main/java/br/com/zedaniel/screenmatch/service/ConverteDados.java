package br.com.zedaniel.screenmatch.service;

import br.com.zedaniel.screenmatch.model.DadosSerie;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.type.CollectionType;

import java.util.ArrayList;
import java.util.List;

public class ConverteDados implements IConverteDados{
    private ObjectMapper mapper = new ObjectMapper();



    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        return mapper.readValue(json, classe);

    }


}
