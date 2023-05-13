package com.ejerciciosmesa.libros.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



import com.ejerciciosmesa.libros.models.entities.Libro;

public interface LibroService {
	
	public List<Libro> findAll();
	public Page<Libro> findAll(Pageable pageable);
	public Libro findOne(Long id);
	public void save(Libro libro);
	public void remove(Long id);
	public Long count();
	
	
	
}



/*** Duende Code Generator Jose Manuel Rosado ejerciciosmesa.com 2023 ***/

