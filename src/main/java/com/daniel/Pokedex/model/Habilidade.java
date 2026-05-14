package com.daniel.Pokedex.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Habilidade(
        @JsonAlias("name") String nome
) {
}
