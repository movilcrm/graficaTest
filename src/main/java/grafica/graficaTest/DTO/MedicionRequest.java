package grafica.graficaTest.DTO;

import java.sql.Timestamp;

public class MedicionRequest {

    private Integer idLocal;

    private Integer idTanque;

    private Integer idSensor;
    
    private String fechaHora;

    private Double valor;

    public Integer getIdLocal() {
        return idLocal;
    }

    public String getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(String fechaHora) {
		this.fechaHora = fechaHora;
	}

	public void setIdLocal(Integer idLocal) {
        this.idLocal = idLocal;
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


    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

}