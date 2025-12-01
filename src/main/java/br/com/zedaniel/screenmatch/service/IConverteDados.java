package br.com.zedaniel.screenmatch.service;

import java.util.List;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);


}
