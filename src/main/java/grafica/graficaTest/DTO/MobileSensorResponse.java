package grafica.graficaTest.DTO;

public class MobileSensorResponse {

    private Integer idSensor;
    private String endpoint;
    private String nombreSensor;
    private String nombreMedida;
    private Double minimoRango;
    private Double maximoRango;
	public Integer getIdSensor() {
		return idSensor;
	}
	public void setIdSensor(Integer idSensor) {
		this.idSensor = idSensor;
	}
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public String getNombreSensor() {
		return nombreSensor;
	}
	public void setNombreSensor(String nombreSensor) {
		this.nombreSensor = nombreSensor;
	}
	public String getNombreMedida() {
		return nombreMedida;
	}
	public void setNombreMedida(String nombreMedida) {
		this.nombreMedida = nombreMedida;
	}
	public Double getMinimoRango() {
		return minimoRango;
	}
	public void setMinimoRango(Double minimoRango) {
		this.minimoRango = minimoRango;
	}
	public Double getMaximoRango() {
		return maximoRango;
	}
	public void setMaximoRango(Double maximoRango) {
		this.maximoRango = maximoRango;
	}

    // getters y setters
}