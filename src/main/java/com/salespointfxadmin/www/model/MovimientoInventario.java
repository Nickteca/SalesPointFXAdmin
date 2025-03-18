package com.salespointfxadmin.www.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoInventario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column()
	private Integer idMovimientoInventario;

	@Column(nullable = false, length = 17, unique = true)
	private String folio;

	@Column(nullable = false)
	private TipoMovimiento tipoMovimiento;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = true, length = 50)
	private String decripcion;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@JoinColumn(name = "sucursal", referencedColumnName = "idSucursal")
	@ManyToOne(optional = false)
	private Sucursal sucursal;

	@JoinColumn(name = "sucursalDestino", referencedColumnName = "idSucursal")
	@ManyToOne(optional = true)
	private Sucursal sucursalDestino;

	@OneToMany(mappedBy = "movimientoInventario", cascade = CascadeType.ALL)
	private List<MovimientoInventarioDetalle> listMovimientoInventarioDetalle;

	public enum TipoMovimiento {
		E, S
	}

}
