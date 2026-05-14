package com.daniel.Pokedex.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
