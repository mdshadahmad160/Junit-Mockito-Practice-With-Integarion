package org.jsp.Nursury_PlantApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.jsp.Nursury_PlantApp.entity.Movie;
import org.jsp.Nursury_PlantApp.service.MovieService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * @author Shad Ahmad
 */

@WebMvcTest(controllers = MovieController.class)
public class MovieControllerTests {
    @MockBean
    private MovieService movieService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Movie avatarMovie;

    private Movie titanicMovie;

    @Test
    @DisplayName("Should be Created New Movie and return new Movie Object ")
    public void ShouldCreateNewMovie() throws Exception {
        avatarMovie = new Movie();
        avatarMovie.setId(1L);
        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 22));

        when(movieService.save(any(Movie.class))).thenReturn(avatarMovie);
        this.mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(avatarMovie)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",
                        CoreMatchers.is(avatarMovie.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genera",
                        CoreMatchers.is(avatarMovie.getGenera())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.releaseDate",
                        CoreMatchers.is(avatarMovie.getReleaseDate().toString())))
                .andDo(print());
    }

    @Test
    @DisplayName("Should be fetch All Movie List with Size 2")
    public void shouldFetchAllMovies() throws Exception {
        avatarMovie = new Movie();
        avatarMovie.setId(1L);
        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 22));


        titanicMovie = new Movie();
        titanicMovie.setId(2L);
        titanicMovie.setName("Titanic");
        titanicMovie.setGenera("Romance");
        titanicMovie.setReleaseDate(LocalDate.of(1999, Month.MAY, 20));

        List<Movie> list = new ArrayList<>();
        list.add(avatarMovie);
        list.add(titanicMovie);

        when(movieService.getAllMovies()).thenReturn(list);
        this.mockMvc.perform(get("/movies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                        CoreMatchers.is(list.size())))
                .andDo(print());

    }

    @Test
    @DisplayName("Should return Movie Object by Id ")
    public void ShouldFetchOneMovieById() throws Exception {
        avatarMovie = new Movie();
        
        avatarMovie.setId(1L);
        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 22));
        when(movieService.getMovieById(anyLong())).thenReturn(avatarMovie);

        this.mockMvc.perform(get("/movies/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(avatarMovie)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",
                        CoreMatchers.is(avatarMovie.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genera",
                        CoreMatchers.is(avatarMovie.getGenera())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.releaseDate",
                        CoreMatchers.is(avatarMovie.getReleaseDate().toString())))
                .andDo(print());

    }

    @Test
    @DisplayName("Should Deleted Movie Object By Id ")
    public void ShouldDeleteMovieById() throws Exception {
        avatarMovie = new Movie();
        avatarMovie.setId(1L);
        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 22));
        doNothing().when(movieService).deleteMovie(anyLong());

        this.mockMvc.perform(delete("/movies/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(avatarMovie)))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(print());

    }
    @Test
    @DisplayName("Should be Updated Movie Object By it's Id ")
    public void ShouldUpdateMovieById() throws Exception{
        avatarMovie = new Movie();
        avatarMovie.setId(1L);
        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 22));
        when(movieService.updateMovie(any(Movie.class), anyLong())).thenReturn(avatarMovie);

        this.mockMvc.perform(put("/movies/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(avatarMovie)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",
                        CoreMatchers.is(avatarMovie.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genera",
                        CoreMatchers.is(avatarMovie.getGenera())))
                .andDo(print());


    }


}
