package org.jsp.Nursury_PlantApp.service;

import org.jsp.Nursury_PlantApp.entity.Movie;
import org.jsp.Nursury_PlantApp.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


/**
 * @author Shad Ahmad
 */
@ExtendWith(MockitoExtension.class)
public class MovieServiceTests {

    @InjectMocks
    private MovieService movieService;

    @Mock
    private MovieRepository movieRepository;


    private Movie avatarMovie;
    private Movie titanicMovie;

    @BeforeEach
    public void init() {
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
    }

    @Test
    @DisplayName("Should save the movie object to the database ")
    public void save() {
           /* Movie avatarMovie = new Movie();
            avatarMovie.setId(1L);
            avatarMovie.setName("Avatar");
            avatarMovie.setGenera("Action");

        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 22));
*/
        when(movieRepository.save(any(Movie.class))).thenReturn(avatarMovie);

        Movie newMovie = movieService.save(avatarMovie);

        assertNotNull(newMovie);
        assertThat(newMovie.getName()).isEqualTo("Avatar");
    }

    @Test
    @DisplayName("Should be return List of  movies with size 2  ")
    public void getAllMovies() {
      /*  Movie avatarMovie = new Movie();
        avatarMovie.setId(1L);
        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 22));

        Movie titanicMovie = new Movie();
        titanicMovie.setId(2L);
        titanicMovie.setName("Titanic");
        titanicMovie.setGenera("Romance");
        titanicMovie.setReleaseDate(LocalDate.of(1999, Month.MAY, 20));
*/
        List<Movie> list = new ArrayList<>();
        list.add(titanicMovie);
        list.add(avatarMovie);

        when(movieRepository.findAll()).thenReturn(list);
        List<Movie> movieList = movieService.getAllMovies();

        assertEquals(2, movieList.size());
        assertNotNull(movieList);


    }

    @Test
    @DisplayName("Should be return Movie Object ")
    public void getMovieById() {
        /*Movie avatarMovie = new Movie();
        avatarMovie.setId(1L);
        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 22));*/
        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(avatarMovie));
        Movie existingMovie = movieService.getMovieById(1L);

        assertNotNull(existingMovie);
        assertThat(existingMovie.getId()).isEqualTo(1L);
    }

    /**
     * Junit Test cases for Handling the exception
     */
    @Test
    @DisplayName("Should throw the exception ")
    public void getMovieByIdException() {
   /* Movie avatarMovie = new Movie();
    avatarMovie.setId(1L);
    avatarMovie.setName("Avatar");
    avatarMovie.setGenera("Action");
    avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 22));
*/
        when(movieRepository.findById(1L)).thenReturn(Optional.of(avatarMovie));

        assertThrows(RuntimeException.class, () -> {
            movieService.getMovieById(2L);
        });


    }

    @Test
    @DisplayName("Should Update the movie in to the database ")
    public void updateMovie() {
       /* Movie avatarMovie = new Movie();
        avatarMovie.setId(1L);
        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 22));*/
        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(avatarMovie));
        when(movieRepository.save(any(Movie.class))).thenReturn(avatarMovie);
        avatarMovie.setGenera("Fantasy");
        Movie updatedMovie = movieService.updateMovie(avatarMovie, 1L);

        assertNotNull(updatedMovie);
        assertEquals("Fantasy", updatedMovie.getGenera());

    }

    @Test
    @DisplayName("Should Delete the movie from the database")
    public void deleteMovie() {
       /* Movie avatarMovie = new Movie();
        avatarMovie.setId(1L);
        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 22));
*/
        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(avatarMovie));

        doNothing().when(movieRepository).delete(any(Movie.class));

        movieService.deleteMovie(1L);

        verify(movieRepository, timeout(1)).delete(avatarMovie);
    }


}
