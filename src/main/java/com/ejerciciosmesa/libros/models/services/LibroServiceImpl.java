package com.ejerciciosmesa.libros.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.ejerciciosmesa.libros.models.dao.LibroDAO;
import com.ejerciciosmesa.libros.models.entities.Libro;


@Service
public class LibroServiceImpl implements LibroService {

	private final LibroDAO libroDAO;
	
	
	
	public LibroServiceImpl(
			
			LibroDAO libroDAO
			) {
		
		this.libroDAO = libroDAO;
	}

	@Transactional(readOnly=true)
	@Override
	public List<Libro> findAll() {
		return (List<Libro>) libroDAO.findAll();
	}
	
	@Transactional(readOnly=true)
	@Override
	public Page<Libro> findAll(Pageable pageable) {
		return libroDAO.findAll(pageable);
	}

	@Transactional(readOnly=true)
	@Override
	public Libro findOne(Long id) {
		return libroDAO.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public void save(Libro libro) {
		libroDAO.save(libro);
	}

	@Transactional
	@Override
	public void remove(Long id) {
		libroDAO.deleteById(id);
	}
	
	@Transactional(readOnly=true)
	@Override
	public Long count() {
		return libroDAO.count();
	}
	
	
	
	
}



/*** Duende Code Generator Jose Manuel Rosado ejerciciosmesa.com 2023 ***/

