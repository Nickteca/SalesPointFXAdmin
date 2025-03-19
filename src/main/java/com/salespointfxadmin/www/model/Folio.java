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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
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

	@Column(nullable = false, length = 4)
	private String acronimoFolio;

	@Column(nullable = false)
	private int numeroFolio;

	@Column(nullable = false)
	private Naturaleza naturalezaFolio;

	@Column(nullable = false)
	private NombreFolio nombreFolio;

	@JoinColumn(name = "sucursal", referencedColumnName = "idSucursal")
	@ManyToOne(optional = false)
	private Sucursal sucursal;

	public Folio(String acronimoFolio, int numeroFolio, Naturaleza naturalezaFolio, Sucursal sucursal,NombreFolio nombreFolio) {
		super();
		this.acronimoFolio = acronimoFolio;
		this.numeroFolio = numeroFolio;
		this.naturalezaFolio = naturalezaFolio;
		this.sucursal = sucursal;
		this.nombreFolio=nombreFolio;
	}

	@Override
	public String toString() {
		return idFolio+" "+nombreFolio;
	}

	public enum Naturaleza {
		E, S
	}

	public enum NombreFolio {
		Ajuste_Entrada, Ajuste_salida, Traspaso_Entrada, Trspaso_Salida, Venta, Devolucion_Venta, Cancelacion_Venta
	}

}