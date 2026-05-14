package com.daniel.Pokedex.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TipoResumo(
        @JsonAlias("name") String nome
) {
}
