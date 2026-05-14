package com.daniel.Pokedex.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PokemonDetalhes(
        @JsonAlias("name") String nome,
        @JsonAlias("height") Integer altura,
        @JsonAlias("weight") Integer peso,
        List<TipoDoPokemon> types
) {
}
