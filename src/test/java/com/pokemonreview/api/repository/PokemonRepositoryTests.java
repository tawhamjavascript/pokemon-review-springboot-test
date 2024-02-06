package com.pokemonreview.api.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pokemonreview.api.models.Pokemon;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest
@RunWith(SpringRunner.class)
public class PokemonRepositoryTests {
    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    public void PokemonRepository_SaveAll_ReturnSavedPokemon() {
        // Arrange
        Pokemon pokemon = Pokemon.builder()
            .name("Pikachu")
            .type("Electric")
            .build();
        // Act
        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        // Assert
        Assertions.assertThat(savedPokemon).isNotNull();
        Assertions.assertThat(savedPokemon.getId()).isGreaterThan(0);
    }   

    @Test
    public void PokemonRepository_GetAll_ReturnMoreThenOnePokemon() {
        // Arrange
        Pokemon pokemon = Pokemon.builder()
            .name("Pikachu")
            .type("Electric")
            .build();
        
        Pokemon pokemon2 = Pokemon.builder()
            .name("Pikachu")
            .type("Electric")
            .build();

        pokemonRepository.saveAll(List.of(pokemon, pokemon2));

        // Act
        List<Pokemon> pokemons = pokemonRepository.findAll();

        // Assert
        Assertions.assertThat(pokemons).isNotNull();
        Assertions.assertThat(pokemons.size()).isEqualTo(2);
    
    }

    @Test
    public void PokemonRepository_FindById_ReturnPokemon() {
        // Arrange
        Pokemon pokemon = Pokemon.builder()
            .name("Pikachu")
            .type("Electric")
            .build();
        
        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        // Act
        Pokemon pokemonFound = pokemonRepository.findById(savedPokemon.getId()).get();

        // Assert
        Assertions.assertThat(pokemonFound).isNotNull();
        Assertions.assertThat(pokemonFound.getId()).isEqualTo(savedPokemon.getId());
    }

    @Test
    public void PokemonRepository_FindByType_ReturnPokemon() {
        // Arrange
        Pokemon pokemon = Pokemon.builder()
            .name("Pikachu")
            .type("Electric")
            .build();

        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        // Act
        Pokemon pokemonFound = pokemonRepository.findByType(savedPokemon.getType()).get();
        assertEquals("Electric", pokemonFound.getType());
    }

    @Test
    public void PokemonRepository_UpdatePokemon_ReturnPokemonNotNull() {
        // Arrange
        Pokemon pokemon = Pokemon.builder()
            .name("Pikachu")
            .type("electric")
            .build();

        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        // Act
        Pokemon pokemonFound = pokemonRepository.findById(savedPokemon.getId()).get();
        pokemonFound.setName("Raichu");
        pokemonFound.setType("Electric");

        Pokemon updatedPokemon = pokemonRepository.save(pokemonFound);

        // Assert
        assertEquals("Raichu", updatedPokemon.getName());
        assertEquals("Electric", updatedPokemon.getType());
    }

    @Test
    public void PokemonRepository_PokemonDelete_ReturnPokemonIsEmpty() {
        // Arrange
        Pokemon pokemon = Pokemon.builder()
            .name("Pikachu")
            .type("Electric")
            .build();

        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        // Act
        pokemonRepository.deleteById(savedPokemon.getId());
        Optional<Pokemon> pokemonFound = pokemonRepository.findById(savedPokemon.getId());

        // Assert
        Assertions.assertThat(pokemonFound).isEmpty();
    }
}
