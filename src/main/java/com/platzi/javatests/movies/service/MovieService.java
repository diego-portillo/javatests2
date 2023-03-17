package com.platzi.javatests.movies.service;

import com.platzi.javatests.movies.data.MovieRepository;
import com.platzi.javatests.movies.model.Genre;
import com.platzi.javatests.movies.model.Movie;
import org.jcp.xml.dsig.internal.dom.Utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public Collection<Movie> findMoviesByTemplate(Movie template) {

        Stream<Movie> movies = movieRepository.findAll().stream();
        if (template.getMinutes() < 0){
            throw new IllegalArgumentException();
        }
        else if(template.getId() != null){
            return movies.filter(movie -> Objects.equals(movie.getId(), template.getId())).collect(Collectors.toList());
        }
        else {
            if (template.getDirector() != null) {
                movies = movies.filter(movie -> movie.getDirector().toLowerCase().contains(template.getDirector().toLowerCase()));
            }
            if (template.getMinutes() != null) {
                movies = movies.filter(movie -> movie.getMinutes() <= template.getMinutes());
            }
            if (template.getName() != null) {
                movies = movies.filter(movie -> movie.getName().toLowerCase().contains(template.getName().toLowerCase()));
            }
            if (template.getGenre() != null) {
                movies = movies.filter(movie -> movie.getGenre().equals(template.getGenre()));
            }
            return movies.collect(Collectors.toList());
        }
    }
}
