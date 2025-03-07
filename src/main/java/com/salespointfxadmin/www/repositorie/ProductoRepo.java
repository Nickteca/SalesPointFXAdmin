package com.salespointfxadmin.www.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfxadmin.www.model.Producto;

public interface ProductoRepo extends JpaRepository<Producto, Short> {

}
