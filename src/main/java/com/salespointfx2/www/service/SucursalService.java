package com.salespointfx2.www.service;

import org.springframework.stereotype.Service;

import com.salespointfx2.www.repositorie.SucursalRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SucursalService {
	private final SucursalRepo sr;
}
