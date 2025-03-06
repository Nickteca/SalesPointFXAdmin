package com.salespointfx2.www.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfx2.www.model.Empresa;

public interface EmpresaRepo extends JpaRepository<Empresa, Short> {

}
