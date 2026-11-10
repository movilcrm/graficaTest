package grafica.graficaTest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblIrcaSensores")
public class IrcaSensores {

    @Id
    @Column(name = "idSensor")
    private Integer idSensor;

    @Column(name = "nombreSensor")
    private String nombreSensor;

    @Column(name = "nombreMedida")
    private String nombreMedida;

    @Column(name = "minimoRango")
    private Double minimoRango;

    @Column(name = "maximoRango")
    private Double maximoRango;

    public Integer getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(Integer idSensor) {
        this.idSensor = idSensor;
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

}