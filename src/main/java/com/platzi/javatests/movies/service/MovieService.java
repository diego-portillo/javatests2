package com.platzi.javatests.movies.service;

import com.platzi.javatests.movies.data.MovieRepository;
import com.platzi.javatests.movies.model.Genre;
import com.platzi.javatests.movies.model.Movie;
import org.jcp.xml.dsig.internal.dom.Utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class MovieService {

    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Collection<Movie> findMoviesByGenre(Genre genre) {

        return movieRepository.findAll().stream()
                .filter(movie -> movie.getGenre() == genre).collect(Collectors.toList());
    }

    public Collection<Movie> findMoviesByLength(int length) {

        return movieRepository.findAll().stream()
                .filter(movie -> movie.getMinutes() <= length).collect(Collectors.toList());
    }
    public Collection<Movie> findMoviesByName(String name){
        return movieRepository.findAll().stream()
                .filter(movie -> movie.getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
    }
    public Collection<Movie> findMoviesByDirector(String director){
        return movieRepository.findAll().stream()
                .filter(movie -> movie.getDirector().toLowerCase().contains(director.toLowerCase())).collect(Collectors.toList());
    }
    public Collection<Movie> findMoviesByTemplate(Movie template){
        //Continuar desde aca
        boolean[] filters = {};
        if (template.getDirector()!=null){
            Utils.arrayConcat(filters);
        }
        return movieRepository.findAll().stream()
                .filter(movie -> movie.getMinutes()!=null);
        return null;
    }
}
