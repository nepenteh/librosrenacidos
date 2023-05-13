package com.ejerciciosmesa.libros.util.paginator;

public class PageItem {

	private int pageNumber;
	private boolean actual;
	
	public PageItem(int pageNumber, boolean actual) {
		this.pageNumber = pageNumber;
		this.actual = actual;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public boolean isActual() {
		return actual;
	}
	
}



/*** Duende Code Generator Jose Manuel Rosado ejerciciosmesa.com 2023 ***/

