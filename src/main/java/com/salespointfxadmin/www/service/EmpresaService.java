package com.salespointfxadmin.www.service;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.repositorie.EmpresaRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpresaService {
	private final EmpresaRepo er;

}
