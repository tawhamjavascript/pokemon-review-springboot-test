package com.pokemonreview.api.dto;
import java.util.List;
import com.pokemonreview.api.models.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PokemonDto {
    private int id;
    private String name;
    private String type;
    private List<Review> reviews;
}
