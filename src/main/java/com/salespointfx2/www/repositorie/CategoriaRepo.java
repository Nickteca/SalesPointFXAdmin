package com.salespointfx2.www.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfx2.www.model.Categoria;

public interface CategoriaRepo extends JpaRepository<Categoria, Short> {

}
