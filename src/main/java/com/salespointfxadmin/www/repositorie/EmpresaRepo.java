package com.salespointfxadmin.www.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfxadmin.www.model.Empresa;

public interface EmpresaRepo extends JpaRepository<Empresa, Short> {

}
