package br.com.zedaniel.screenmatch.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);

}
