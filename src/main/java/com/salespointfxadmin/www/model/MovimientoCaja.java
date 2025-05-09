package com.salespointfxadmin.www.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = @Index(name = "creado", columnList = "createdAt"))
public class MovimientoCaja implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Integer idMovimientoCaja;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoMovimiento tipoMovimientoCaja;

	@Column(nullable = false)
	private float saldoAnterior;

	@Column(nullable = true)
	private float saldoFinal;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@JoinColumn(name = "sucursal", referencedColumnName = "idSucursal")
	@ManyToOne(optional = false)
	private Sucursal sucursal;

	@OneToOne(mappedBy = "movimientoCaja", cascade = CascadeType.ALL)
	private Corte corte;

	public enum TipoMovimiento {
		APERTURA, CIERRE
	}

}
