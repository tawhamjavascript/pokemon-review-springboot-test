package com.pokemonreview.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.pokemonreview.api.dto.PokemonDto;
import com.pokemonreview.api.dto.ReviewDto;
import com.pokemonreview.api.models.Pokemon;
import com.pokemonreview.api.models.Review;
import com.pokemonreview.api.repository.PokemonRepository;
import com.pokemonreview.api.repository.ReviewRepository;
import com.pokemonreview.api.service.impl.ReviewServiceImpl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

@RunWith(MockitoJUnitRunner.class)
public class ReviewServiceTests {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private PokemonRepository pokemonRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;
    
    private Pokemon pokemon;
    private Review review;
    private ReviewDto reviewDto;
    private PokemonDto pokemonDto;

    @Before
    public void setUp() {
        pokemon = Pokemon.builder().name("pikachu").type("electric").build();

        review = Review.builder().title("title").content("content").stars(5).build();

        reviewDto = ReviewDto.builder().title("title").content("content").stars(5).build();

        pokemonDto = PokemonDto.builder().name("pickachu").type("electric").build();
    }

    @Test
    public void ReviewService_CreateReview_ReturnsReviewDto() {
        // Arrange
        when(pokemonRepository.findById(pokemon.getId())).thenReturn(Optional.of(pokemon));
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

        // Act
        ReviewDto resultDto = reviewService.createReview(pokemon.getId(), reviewDto);

        // Assert
        assertEquals(reviewDto, resultDto);
    }

    @Test
    public void ReviewService_GetReviewById_ReturnReviewDto() {
        //Arrange
        int reviewId = 1;
        int pokemonId = 1;
        review.setPokemon(pokemon);
        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.of(pokemon));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        //Act
        ReviewDto resultDto = reviewService.getReviewById(reviewId, pokemonId);

        //Assert
        assertEquals(reviewDto, resultDto);

    }

    @Test
    public void ReviewService_UpdatePokemon_ReturnReviewDto() {
        // Arrange
        int pokemonId = 1;
        int reviewId = 1;
        pokemon.setReviews(Arrays.asList(review));
        review.setPokemon(pokemon);
        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.of(pokemon));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        when(reviewRepository.save(Mockito.eq(review))).thenReturn(review);

        // Act
        ReviewDto resultDto = reviewService.updateReview(pokemonId, reviewId, reviewDto);

        // Assert
        assertEquals(reviewDto, resultDto);
    }

    @Test
    public void ReviewService_DeletePokemonById_ReturnVoid() {
        // Arrange
        int pokemonId = 1;
        int reviewId = 1;

        pokemon.setReviews(Arrays.asList(review));
        review.setPokemon(pokemon);
        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.of(pokemon));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        assertAll(() -> {
            reviewService.deleteReview(pokemonId, reviewId);
        });
    }
}
