package com.pokemonreview.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.pokemonreview.api.dto.PokemonDto;
import com.pokemonreview.api.dto.PokemonResponse;
import com.pokemonreview.api.models.Pokemon;
import com.pokemonreview.api.models.Review;
import com.pokemonreview.api.repository.PokemonRepository;
import com.pokemonreview.api.service.impl.PokemonServiceImpl;
import org.springframework.data.domain.Pageable;

@RunWith(MockitoJUnitRunner.class)
public class PokemonServiceTests {
    @Mock
    private PokemonRepository pokemonRepository;
    
    @InjectMocks
    private PokemonServiceImpl pokemonService;


    @Test
    public void PokemonService_CreatePokemon_ReturnsPokemonDto() {
        // Arrange
        Pokemon pokemon = Pokemon.builder()
                .name("Pikachu")
                .type("Electric")
                .build();

        PokemonDto pokemonDto = PokemonDto.builder()
                .name("Pikachu")
                .type("Electric")
                .build();

        when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);

        // Act
        PokemonDto resultDto = pokemonService.createPokemon(pokemonDto);
        
        // Assert

        assertEquals(pokemonDto, resultDto);
    } 

    @Test
    public void PokemonService_GetAllPokemon_ReturnsResponseDto() {
        // Arrange
        List<Pokemon> pokemonList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Pokemon pokemon = Pokemon.builder()
                    .name("Pikachu")
                    .type("Electric")
                    .build();
            pokemonList.add(pokemon);
        }
   
        Page<Pokemon> pagedResponse = new PageImpl<>(pokemonList);
        when(pokemonRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pagedResponse);
    

        // Act
        PokemonResponse result = pokemonService.getAllPokemon(1, 10);

        // Assert
        assertThat(result.getContent().size()).isEqualTo(10);

    }
    @Test
    public void PokemonService_FindById_ReturnPokemonDto() {
        // Arrange
        int pokemonId = 1;
        Pokemon pokemon = Pokemon.builder()
                .id(pokemonId)
                .name("Pikachu")
                .type("Electric")
                .build();
        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.of(pokemon));

        // Act
        PokemonDto result = pokemonService.getPokemonById(pokemonId);

        // Assert

        assertNotNull(result);
    }

    @Test 
    public void PokemonService_UpdatePokemon_ReturnPokemonDto() {
        // Arrange
        int pokemonId = 1;
        Pokemon pokemon = Pokemon.builder()
                .id(pokemonId)
                .name("Pikachu")
                .type("Electric")
                .build();

        PokemonDto pokemonDto = PokemonDto.builder()
                .id(pokemonId)
                .name("Pikachu")
                .type("Electric")
                .build();

        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.ofNullable(pokemon));
        when(pokemonRepository.save(Mockito.eq(pokemon))).thenReturn(pokemon);

        // Act

        PokemonDto result = pokemonService.updatePokemon(pokemonDto, pokemonId);

        // Assert

        assertEquals(pokemonDto, result);
    }

    @Test
    public void PokemonService_DeletePokemonById_ReturnVoid() {
        // Arrange
        int pokemonId = 1;
        Pokemon pokemon = Pokemon.builder()
                .id(pokemonId)
                .name("Pikachu")
                .type("Electric")
                .build();

        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.ofNullable(pokemon));
        doNothing().when(pokemonRepository).delete(pokemon);



        assertAll(() -> {
            // Act
            pokemonService.deletePokemonId(pokemonId);
        });
    
    }
}
