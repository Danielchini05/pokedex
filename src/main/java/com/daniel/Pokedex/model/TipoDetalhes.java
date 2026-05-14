package com.daniel.Pokedex.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record TipoDetalhes(
        List<PokemonPorTipo> pokemon
) {
}
