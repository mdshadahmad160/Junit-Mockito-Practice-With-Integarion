package org.jsp.Nursury_PlantApp.repository;

import org.jsp.Nursury_PlantApp.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MovieRepository extends JpaRepository<Movie,Long> {


     List<Movie> findByGenera(String genera);
}
