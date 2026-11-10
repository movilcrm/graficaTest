package grafica.graficaTest.model;

import java.util.Date;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(LecturaSensorId.class)
@Table(name = "tblIrcaLocalesSensor")
public class LecturaSensorModel {

    @Id
    @Column(name = "IDLOCAL")
    private Integer IDLOCAL;

    @Id
    @Column(name = "idTanque")
    private Integer idTanque;

    @Id
    @Column(name = "idSensor")
    private Integer idSensor;

    @Id
    @Column(name = "fechaHora")
    private Timestamp fechaHora;

    @Column(name = "valor")
    private Double valor;

	public Integer getIDLOCAL() {
		return IDLOCAL;
	}

	public void setIDLOCAL(Integer iDLOCAL) {
		IDLOCAL = iDLOCAL;
	}

	public Integer getIdTanque() {
		return idTanque;
	}

	public void setIdTanque(Integer idTanque) {
		this.idTanque = idTanque;
	}

	public Integer getIdSensor() {
		return idSensor;
	}

	public void setIdSensor(Integer idSensor) {
		this.idSensor = idSensor;
	}

	public Timestamp getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Timestamp fechaHora) {
		this.fechaHora = fechaHora;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

    
}