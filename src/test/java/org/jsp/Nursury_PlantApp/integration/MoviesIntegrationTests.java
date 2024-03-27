package org.jsp.Nursury_PlantApp.integration;

import org.jsp.Nursury_PlantApp.entity.Movie;
import org.jsp.Nursury_PlantApp.repository.MovieRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Month;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Shad Ahmad
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MoviesIntegrationTests {

    @LocalServerPort
    private int port;

    private String baseURL = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private MovieRepository movieRepository;

    /**
     * @BeforeAll annotation run the method before running all the test cases
     */
    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    private Movie avatarMovie;

    private Movie titanicMovie;

    @BeforeEach
    public void beforeSetUp() {
        baseURL = baseURL + ":" + port + "/movies";

        avatarMovie = new Movie();
        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 22));


        titanicMovie = new Movie();
        titanicMovie.setName("Titanic");
        titanicMovie.setGenera("Romance");
        titanicMovie.setReleaseDate(LocalDate.of(1999, Month.MAY, 20));

        avatarMovie = movieRepository.save(avatarMovie);
        titanicMovie = movieRepository.save(titanicMovie);
    }

    @AfterEach
    public void afterSetUp() {
        movieRepository.deleteAll();
    }

    @Test
    public void ShouldCreateMovieTest() {
        avatarMovie = new Movie();
        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 22));

        Movie newMovie = restTemplate.postForObject(baseURL, avatarMovie, Movie.class);

        assertNotNull(newMovie);
        assertThat(newMovie.getId()).isNotNull();

    }

    @Test
    public void ShouldFetchMoviesTest() {
        List<Movie> list = restTemplate.getForObject(baseURL, List.class);

        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    public void ShouldFetchOneMovieByIdTest() {

        Movie existingMovie = restTemplate.getForObject(baseURL + "/" + avatarMovie.getId(), Movie.class);

        assertNotNull(existingMovie);
        assertEquals("Avatar", existingMovie.getName());

    }

    @Test
    public void ShouldDeleteMovieTest() {
        restTemplate.delete(baseURL + "/" + avatarMovie.getId());
        int count = movieRepository.findAll().size();
        assertEquals(1, count);

    }

    @Test
    public void ShouldUpdateMovieTest() {
        avatarMovie.setGenera("Comedy");
        restTemplate.put(baseURL + "/{id}", avatarMovie, avatarMovie.getId());
        Movie existingMovie = restTemplate.getForObject(baseURL + "/" + avatarMovie.getId(), Movie.class);
        assertNotNull(existingMovie);
        assertEquals("Comedy", existingMovie.getGenera());
    }


}
