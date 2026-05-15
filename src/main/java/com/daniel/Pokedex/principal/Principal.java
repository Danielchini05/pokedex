package com.daniel.Pokedex.principal;

import com.daniel.Pokedex.model.*;
import com.daniel.Pokedex.service.ConsumoApi;
import com.daniel.Pokedex.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private final Scanner scanner = new Scanner(System.in);
    private final ConsumoApi consumo = new ConsumoApi();
    private final ConverteDados conversor = new ConverteDados();
    private final String URL_BASE = "https://pokeapi.co/api/v2/";


    private List<PokemonDetalhes> pokemonDetalhes = new ArrayList<>();
    private List<PokemonHabilidades> pokemonHabilidades = new ArrayList<PokemonHabilidades>();
    private List<TipoDetalhes> tiposPokemons = new ArrayList<TipoDetalhes>();
    private List<PokemonStatus> pokemonStatusList = new ArrayList<>();

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    ***POKEDEX***
                    1 - Buscar pokemon pelo nome
                    2 - Mostrar habilidades
                    3 - Mostrar tipos
                    4-  Mostrar status
                    0 - Sair
                    """;

            System.out.println(menu);
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    buscaPokemonPeloNome();
                    break;
                case 2:
                    buscaHabilidadesPokemon();
                    break;
                case 3:
                    mostraTiposExistentes();
                    buscaTipoPokemon();
                    break;
                case 4:
                    buscaStatusPokemon();
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void buscaPokemonPeloNome() {
        PokemonDetalhes pokemonDetalhes = getDadosPokemon();
        if (pokemonDetalhes == null) {
            return;
        }
        this.pokemonDetalhes.add(pokemonDetalhes);
        double alturaMetros = pokemonDetalhes.altura() / 10.0;
        double pesoKg = pokemonDetalhes.peso() / 10.0;

        System.out.println("Nome: " + pokemonDetalhes.nome());
        pokemonDetalhes.types().forEach(t ->
                System.out.println("Tipo: " + t.type().nome()));
        System.out.println("Altura: " + alturaMetros + " m");
        System.out.println("Peso: " + pesoKg + " kg\n");
    }

    private void buscaHabilidadesPokemon() {
        PokemonHabilidades pokemonHabilidades = getHabilidades();
        if (pokemonHabilidades == null) {
            return;
        }
        this.pokemonHabilidades.add(pokemonHabilidades);
        pokemonHabilidades.abilities().forEach(t ->
                System.out.println("Habilidades: " + t.ability().nome()));
    }

    private void buscaTipoPokemon() {
        TipoDetalhes tiposPokemon = getTiposPokemons();
        if (tiposPokemon == null) {
            return;
        }
        tiposPokemons.add(tiposPokemon);
        tiposPokemon.pokemon().forEach(p ->
                System.out.println("Pokemon: " + p.pokemon().nome()));
    }

    private void mostraTiposExistentes() {
        var json = consumo.obterDados(URL_BASE + "type");
        ListaTiposResposta dados = conversor.obterDados(json, ListaTiposResposta.class);
        dados.results().forEach(t ->
                System.out.println("Tipo: " + t.nome()));
    }

    private void buscaStatusPokemon() {
        PokemonStatus pokemonStatus = getPokemonStatus();
        if (pokemonStatus == null) {
            return;
        }
        pokemonStatusList.add(pokemonStatus);
        System.out.println("\nStatus do " + pokemonStatus.nome());
        pokemonStatus.stats().forEach(p ->
                System.out.println(p.stat().nome() + ": " + p.valor()));
    }

    private PokemonDetalhes getDadosPokemon() {
        System.out.println("Digite nome do pokemon para busca:");
        var nomePokemon = scanner.nextLine();
        var json = consumo.obterDados(URL_BASE + "pokemon/" + nomePokemon.toLowerCase());
        if (json.contains("Not Found")) {
            System.out.println("Pokemon não encontrado!");
            return null;
        }
        PokemonDetalhes pokemonDetalhes = conversor.obterDados(json, PokemonDetalhes.class);
        return pokemonDetalhes;
    }

    private PokemonHabilidades getHabilidades() {
        System.out.println("Digite o nome do pokemon para ver as habilidades:");
        var nomePokemon = scanner.nextLine();
        var json = consumo.obterDados(URL_BASE + "pokemon/" + nomePokemon.toLowerCase());
        if (json.contains("Not Found")) {
            System.out.println("Pokemon não encontrado!");
            return null;
        }
        PokemonHabilidades pokemonHabilidades = conversor.obterDados(json, PokemonHabilidades.class);
        return pokemonHabilidades;
    }

    private TipoDetalhes getTiposPokemons() {
        System.out.println("Digite o tipo para ver os pokemons existentes:");
        var tipoPokemon = scanner.nextLine();
        var json = consumo.obterDados(URL_BASE + "type/" + tipoPokemon.toLowerCase());
        if (json.contains("Not Found")) {
            System.out.println("Tipo não encontrado!");
            return null;
        }
        TipoDetalhes tiposPokemons = conversor.obterDados(json, TipoDetalhes.class);
        return tiposPokemons;
    }

    private PokemonStatus getPokemonStatus() {
        System.out.println("Digite o nome do pokemon que deseja ver os status:");
        var nomePokemon = scanner.nextLine();
        var json = consumo.obterDados(URL_BASE + "pokemon/" + nomePokemon.toLowerCase());
        if (json.contains("Not Found")) {
            System.out.println("Pokemon não encontrado");
            return null;
        }

        PokemonStatus pokemonStatus = conversor.obterDados(json, PokemonStatus.class);
        return pokemonStatus;
    }
}
