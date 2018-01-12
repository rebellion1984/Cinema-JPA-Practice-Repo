package com.qa.cinema.business.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.*;
import com.qa.cinema.persistence.Movie;
import com.qa.cinema.util.JSONUtil;


@Transactional(Transactional.TxType.SUPPORTS)
public class MovieServiceDBImpl {

	@PersistenceContext(unitName = "primary")
	 private EntityManager entityManager;
	private JSONUtil util = new JSONUtil();
	
	@Transactional(Transactional.TxType.REQUIRED)
	public Movie createMovie(Movie movie) {
		entityManager.persist(movie);
		return movie;
	}
	
	@Transactional(Transactional.TxType.REQUIRED)
	public String createStringMovie(String movie) {
		Movie aMovie = util.getObjectForJSON(movie, Movie.class);
		 entityManager.persist(aMovie);
		 return "Success";
	}
	
	@Transactional(Transactional.TxType.REQUIRED)
	public String updateMovie(String movieDetails) {
		Movie aMovie = util.getObjectForJSON(movieDetails, Movie.class);
		entityManager.merge(aMovie);
		return "Success";
	}
	
	public Movie updateMovieOnDB(Movie movieDetails) {
		return entityManager.merge(movieDetails); 
	}
	
	public Movie findAMovie(long id){
		return entityManager.find(Movie.class, id);
		 
	}
	
	@Transactional(Transactional.TxType.REQUIRED)
	public void deleteMovie(long id){
		 entityManager.remove(findAMovie(id));
	}
	
	public List<Movie> findAllMovies(){
		TypedQuery<Movie> query = entityManager.createQuery("SELECT m FROM Movie m ORDER BY m.title DESC", Movie.class);
        return query.getResultList(); 
	}
}
