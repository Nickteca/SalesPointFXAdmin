package com.salespointfxadmin.www.model;

import java.io.Serializable;

import jakarta.persistence.Basic;
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
@Table(name = "folio", uniqueConstraints = { @UniqueConstraint(columnNames = "idFolio") })
public class Folio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(nullable = false)
	private Short idFolio;

	@Basic(optional = false)
	@Column(nullable = false, length = 4)
	private String acronimoFolio;

	@Basic(optional = false)
	@Column(nullable = false)
	private int numeroFolio;

	@Basic(optional = false)
	@Column(nullable = false, length = 1)
	private Character naturalezaFolio;

	@JoinColumn(name = "sucursal", referencedColumnName = "idSucursal")
	@ManyToOne(optional = false)
	private Sucursal sucursal;

	public Folio(String acronimoFolio, int numeroFolio, Character naturalezaFolio, Sucursal sucursal) {
		super();
		this.acronimoFolio = acronimoFolio;
		this.numeroFolio = numeroFolio;
		this.naturalezaFolio = naturalezaFolio;
		this.sucursal = sucursal;
	}

}