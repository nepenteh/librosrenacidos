package com.ejerciciosmesa.libros.models.entities;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import javax.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@Entity
@Table(name="libro")
public class Libro implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name="portada")
private String portada;


@NotBlank
@Size(max=100)
@Column(name="titulo")
private String titulo;


@NotBlank
@Size(max=100)
@Column(name="autor")
private String autor;


@NotNull
@Column(name="precio")
private Float precio = 12.5f;


@DateTimeFormat(pattern = "yyyy-MM-dd")
@Column(name="fechaadquisicion")
private LocalDate fechaAdquisicion = LocalDate.now();


@Column(name="categoria")
private String categoria = "Misterio";


@Column(name="descuento")
private Float descuento;


@Lob
@Column(name="resumen")
private String resumen;



	
	public Libro() {}


	public Long getId() {
		return id;
	}


	public String getPortada() {
		return portada;
}
public void setPortada(String portada) {
	this.portada = portada;
}
public String getTitulo() {
		return titulo;
}
public void setTitulo(String titulo) {
	this.titulo = titulo;
}
public String getAutor() {
		return autor;
}
public void setAutor(String autor) {
	this.autor = autor;
}
public Float getPrecio() {
		return precio;
}
public void setPrecio(Float precio) {
	this.precio = precio;
}
public LocalDate getFechaAdquisicion() {
		return fechaAdquisicion;
}
public void setFechaAdquisicion(LocalDate fechaAdquisicion) {
	this.fechaAdquisicion = fechaAdquisicion;
}
public String getCategoria() {
		return categoria;
}
public void setCategoria(String categoria) {
	this.categoria = categoria;
}
public Float getDescuento() {
		return descuento;
}
public void setDescuento(Float descuento) {
	this.descuento = descuento;
}
public String getResumen() {
		return resumen;
}
public void setResumen(String resumen) {
	this.resumen = resumen;
}

	

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Libro other = (Libro) obj;
		return Objects.equals(id, other.id);
	}


	private static final long serialVersionUID = 1L;
	
}



/*** Duende Code Generator Jose Manuel Rosado ejerciciosmesa.com 2023 ***/

