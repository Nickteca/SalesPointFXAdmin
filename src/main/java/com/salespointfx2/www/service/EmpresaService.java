package com.salespointfx2.www.service;

import org.springframework.stereotype.Service;

import com.salespointfx2.www.repositorie.EmpresaRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpresaService {
	private final EmpresaRepo er;

}
