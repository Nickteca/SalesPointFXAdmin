package com.salespointfxadmin.www.service;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.repositorie.GastoRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GastoService {
	private final GastoRepo gr;

}
