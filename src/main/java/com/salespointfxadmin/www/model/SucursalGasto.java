package com.salespointfxadmin.www.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SucursalGasto implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idSucursalGasto;

	@Column(nullable = false)
	private float montoGasto;

	@Column(nullable = false, length = 20)
	private String contrato;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@JoinColumn(name = "gasto", referencedColumnName = "idGasto")
	@ManyToOne(optional = false)
	private Gasto gasto;

	@JoinColumn(name = "sucursal", referencedColumnName = "idSucursal")
	@ManyToOne(optional = false)
	private Sucursal sucursal;

}
