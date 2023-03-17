package com.platzi.javatests.movies.service;

import com.platzi.javatests.movies.data.MovieRepository;
import com.platzi.javatests.movies.model.Genre;
import com.platzi.javatests.movies.model.Movie;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class MovieServiceShould {

    private MovieService movieService;

    @Before
    public void setUp() throws Exception {

        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);

        Mockito.when(movieRepository.findAll()).thenReturn(
                Arrays.asList(
                        new Movie(1, "Dark Knight", 152, Genre.ACTION, "Nolan"),
                        new Movie(2, "Memento", 113, Genre.THRILLER, "Nolan"),
                        new Movie(3, "There's Something About Mary", 119, Genre.COMEDY, "Fulano"),
                        new Movie(4, "Super 8", 112, Genre.THRILLER, "Mengano"),
                        new Movie(5, "Scream", 111, Genre.HORROR, "Sultano"),
                        new Movie(6, "Home Alone", 103, Genre.COMEDY, "Juan"),
                        new Movie(7, "Matrix", 136, Genre.ACTION, "Jose")
                )
        );

        movieService = new MovieService(movieRepository);
    }

    @Test
    public void return_movies_by_genre() {
        Collection<Movie> movies = movieService.findMoviesByGenre(Genre.COMEDY);
        assertThat(getMovieIds(movies), CoreMatchers.is(Arrays.asList(3, 6)));
    }

    @Test
    public void return_movies_by_length() {
        Collection<Movie> movies = movieService.findMoviesByLength(119);
        assertThat(getMovieIds(movies), CoreMatchers.is(Arrays.asList(2, 3, 4, 5, 6)) );
    }

    @Test
    public void return_movies_by_name(){
        Collection<Movie> movies = movieService.findMoviesByName("m");
        assertThat(getMovieIds(movies), CoreMatchers.is(Arrays.asList(2, 3, 5, 6 ,7)));
    }
    @Test
    public void return_movies_by_director(){
        Collection<Movie> movies = movieService.findMoviesByDirector("n");
        assertThat(getMovieIds(movies), CoreMatchers.is(Arrays.asList(1, 2, 3, 4, 5, 6)));
    }
    @Test
    public void return_movies_by_length_and_genre(){
        assertThat(getMovieIds(movieService.findMoviesByTemplate(new Movie(null, 150, Genre.ACTION, null))), CoreMatchers.is(Arrays.asList(7)));
    }
    @Test
    public void return_movies_by_name_and_length(){
        assertThat(getMovieIds(movieService.findMoviesByTemplate(new Movie("M", 112, null, null))), CoreMatchers.is(Arrays.asList(5,6)));
    }
    @Test
    public void return_movies_by_name_genre_and_length(){
        assertThat(getMovieIds(movieService.findMoviesByTemplate(new Movie("M", 112, Genre.HORROR, null))), CoreMatchers.is(Arrays.asList(5)));
    }
    @Test
    public void return_movies_by_director_with_template(){
        assertThat(getMovieIds(movieService.findMoviesByTemplate(new Movie(null, null, null, "nol"))), CoreMatchers.is(Arrays.asList(1,2)));
    }
    @Test
    public void return_movies_by_id_with_template(){
        assertThat(getMovieIds(movieService.findMoviesByTemplate(new Movie(1,"z", null, null, "nol"))), CoreMatchers.is(Arrays.asList(1)));
    }
    @Test(expected = IllegalArgumentException.class)
    public void return_illegal_argument_exception_if_minutes_are_negative(){
        movieService.findMoviesByTemplate(new Movie(1,"z", -11, null, "nol"));
    }
    private List<Integer> getMovieIds(Collection<Movie> movies) {
        return movies.stream().map(Movie::getId).collect(Collectors.toList());
    }

}