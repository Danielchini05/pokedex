package com.daniel.Pokedex.principal;

import com.daniel.Pokedex.model.DadosHabilidade;
import com.daniel.Pokedex.model.DadosListaTipos;
import com.daniel.Pokedex.model.DadosPokemon;
import com.daniel.Pokedex.model.DadosTipo;
import com.daniel.Pokedex.service.ConsumoApi;
import com.daniel.Pokedex.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal{
    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private List<DadosPokemon> dadosPokemons = new ArrayList<>();
    private List<DadosHabilidade> dadosHabilidades = new ArrayList<DadosHabilidade>();
    private List<DadosTipo> tiposPokemons = new ArrayList<DadosTipo>();
    private final String URL_BASE = "https://pokeapi.co/api/v2/";
    public void exibeMenu(){
        var opcao = -1;
        while (opcao != 0){
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
            }


        }
    }

    private void buscaPokemonPeloNome(){
        DadosPokemon dadosPokemon = getDadosPokemon();
        dadosPokemons.add(dadosPokemon);
        double alturaMetros = dadosPokemon.altura() / 10.0;
        double pesoKg = dadosPokemon.peso() / 10.0;

        System.out.println("Nome: " + dadosPokemon.nome());
        dadosPokemon.types().forEach(t ->
                System.out.println("Tipo: " + t.type().nome()));
        System.out.println("Altura: " + alturaMetros + " m");
        System.out.println("Peso: " + pesoKg + " kg\n");
    }

    private void buscaHabilidadesPokemon(){
        DadosHabilidade dadosHabilidade = getHabilidades();
        dadosHabilidades.add(dadosHabilidade);
        dadosHabilidade.abilities().forEach(t ->
                System.out.println("Habilidades: " + t.ability().nome()));
    }

    private void buscaTipoPokemon(){
        DadosTipo tiposPokemon = getTiposPokemons();
        tiposPokemons.add(tiposPokemon);
        tiposPokemon.pokemon().forEach(p ->
                System.out.println("Pokemon: " + p.pokemon().nome()));
    }

    private void mostraTiposExistentes(){
        var json = consumo.obterDados(URL_BASE + "type");
        DadosListaTipos dados = conversor.obterDados(json, DadosListaTipos.class);
        dados.results().forEach(t ->
                System.out.println("Tipo: " + t.nome()));
    }

    private DadosPokemon getDadosPokemon(){
        System.out.println("Digite nome do pokemon para busca:");
        var nomePokemon = scanner.nextLine();
        var json = consumo.obterDados(URL_BASE + "pokemon/" + nomePokemon.toLowerCase());
        DadosPokemon dadosPokemon = conversor.obterDados(json, DadosPokemon.class);
        return dadosPokemon;
    }

    private DadosHabilidade getHabilidades(){
        System.out.println("Digite o nome do pokemon para ver as habilidades:");
        var nomePokemon = scanner.nextLine();
        var json = consumo.obterDados(URL_BASE + "pokemon/" + nomePokemon.toLowerCase());
        DadosHabilidade dadosHabilidade = conversor.obterDados(json, DadosHabilidade.class);
        return dadosHabilidade;
    }

    private DadosTipo getTiposPokemons(){
        System.out.println("Digite o tipo para ver os pokemons existentes:");
        var tipoPokemon = scanner.nextLine();
        var json = consumo.obterDados(URL_BASE + "type/" + tipoPokemon.toLowerCase());

        DadosTipo tiposPokemons = conversor.obterDados(json, DadosTipo.class);
        return tiposPokemons;
    }
}
