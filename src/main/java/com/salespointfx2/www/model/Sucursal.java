package com.salespointfx2.www.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "idSucursal", "nombreSucursal" }))
public class Sucursal implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Integer idSucursal;

	@Column(length = 30, nullable = false)
	private String nombreSucursal;

	@Column(length = 70, nullable = false)
	private String calleSucursal;

	@Column(length = 50, nullable = false)
	private String ciudadSucursal;

	@Column(length = 30, nullable = false)
	private String estadosucursal;

	@Column(length = 15, nullable = false)
	private String telefonoSucursal;

	@Column(nullable = false)
	private Boolean estatusSucursal;

	@Column(nullable = false)
	private LocalDateTime cretaedAt;

	@JoinColumn(name = "empresa", referencedColumnName = "idEmpresa")
	@ManyToOne(optional = false)
	private Empresa empresa;

	public Sucursal(Integer idSucursal, String nombreSucursal, String calleSucursal, String ciudadSucursal, String estadosucursal, String telefonoSucursal, Boolean estatusSucursal, Empresa empresa) {
		super();
		this.idSucursal = idSucursal;
		this.nombreSucursal = nombreSucursal;
		this.calleSucursal = calleSucursal;
		this.ciudadSucursal = ciudadSucursal;
		this.estadosucursal = estadosucursal;
		this.telefonoSucursal = telefonoSucursal;
		this.estatusSucursal = estatusSucursal;
		this.cretaedAt = LocalDateTime.now();
		this.empresa = empresa;
	}

}
