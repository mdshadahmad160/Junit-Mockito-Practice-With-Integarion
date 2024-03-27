package org.jsp.Nursury_PlantApp.repository;

import org.jsp.Nursury_PlantApp.entity.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MovieRepositoryTests {

    @Autowired
    private MovieRepository movieRepository;

    private Movie avatarMovie;
    private Movie titanicMovie;

    @BeforeEach
    public void init() {
        avatarMovie = new Movie();
        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 22));



        titanicMovie = new Movie();
        titanicMovie.setName("Titanic");
        titanicMovie.setGenera("Romance");
        titanicMovie.setReleaseDate(LocalDate.of(1999, Month.MAY, 20));


    }


    @Test
    @DisplayName("It should save the movie to the database")
    public void save() {
        /**
         * Every Junit test cases are divided into three parts
         */
        // 1. Arrange: Which is nothing but Setting Up the data that required for test cases
        //2.  Act: Act which is nothing but calling a method/Unit that is belongs to tested
        //3.  Assert: Assert which is nothing but Verifying the data whether the expected result
        // is correct or not


        //Arrange
       /* Movie avatarMovie = new Movie();
        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 22));
        */
        //Act
        Movie newMovie = movieRepository.save(avatarMovie);

        //Assert
        assertThat(newMovie).isNotNull();
        assertThat(newMovie.getId()).isNotEqualTo(null);


    }

    @Test
    @DisplayName(("It should return the  List of movies with the Size of 2"))
    public void getAllMovieMovies() {
       /* Movie avatarMovie = new Movie();

        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 22));*/
        movieRepository.save(avatarMovie);

       /* Movie titanicMovie = new Movie();

        titanicMovie.setName("Titanic");
        titanicMovie.setGenera("Romance");
        titanicMovie.setReleaseDate(LocalDate.of(1999, Month.MAY, 20));*/
        movieRepository.save(titanicMovie);


        List<Movie> list = movieRepository.findAll();

        assertNotNull(list);
        assertThat(list).isNotNull();
        assertEquals(2, list.size());


    }

    @Test
    @DisplayName("It should be return movie by  its Id")
    public void getMovieById() {
       /* Movie avatarMovie = new Movie();

        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 22));*/
        movieRepository.save(avatarMovie);


        Movie existingMovie = movieRepository.findById(avatarMovie.getId()).get();

        assertNotNull(existingMovie);
        assertEquals("Action", existingMovie.getGenera());
        assertThat(avatarMovie.getReleaseDate()).isBefore(LocalDate.of(2000, Month.APRIL, 23));

    }

    @Test
    @DisplayName("It should update the movie with genera FANTACY")
    public void updateMovie() {

        /*Movie avatarMovie = new Movie();

        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 22));*/
        movieRepository.save(avatarMovie);
        Movie existingMovie = movieRepository.findById(avatarMovie.getId()).get();

        existingMovie.setGenera("Fantacy");
        Movie newMovie = movieRepository.save(existingMovie);


        assertEquals("Fantacy", newMovie.getGenera());
        assertEquals("Avatar", newMovie.getName());


    }

    @Test
    @DisplayName("It should delete the existing movie")
    public void deleteMovie() {
       /* Movie avatarMovie = new Movie();

        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 22));*/
        movieRepository.save(avatarMovie);

        Long id = avatarMovie.getId();

       /* Movie titanicMovie = new Movie();
        titanicMovie.setName("Titanic");
        titanicMovie.setGenera("Romance");
        titanicMovie.setReleaseDate(LocalDate.of(1999, Month.MAY, 20));*/
        movieRepository.save(titanicMovie);
        movieRepository.delete(avatarMovie);
        Optional<Movie> existingMovie = movieRepository.findById(id);
        List<Movie> list = movieRepository.findAll();
        assertEquals(1, list.size());
        assertThat(existingMovie).isEmpty();


    }

    @Test
    @DisplayName("It should return the Movies list with genera ROMANCE")
    public void getMoviesByGenera() {
        /*Movie avatarMovie = new Movie();

        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 22));*/
        movieRepository.save(avatarMovie);

       /* Movie titanicMovie = new Movie();
        titanicMovie.setName("Titanic");
        titanicMovie.setGenera("Romance");
        titanicMovie.setReleaseDate(LocalDate.of(1999, Month.MAY, 20));*/
        movieRepository.save(titanicMovie);
        List<Movie> list = movieRepository.findByGenera("Romance");
        assertNotNull(list);
        assertThat(list.size()).isEqualTo(1);


    }


}
