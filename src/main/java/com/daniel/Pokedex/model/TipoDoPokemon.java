package com.daniel.Pokedex.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TipoDoPokemon(
        TipoReferencia type
) {
}
