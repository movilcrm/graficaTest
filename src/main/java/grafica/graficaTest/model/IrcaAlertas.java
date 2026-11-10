package grafica.graficaTest.model;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name = "tblIrcaAlertas")
public class IrcaAlertas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAlerta")
    private Integer idAlerta;

    @Column(name = "idLocal")
    private Integer idLocal;

    @Column(name = "idTanque")
    private Integer idTanque;

    @Column(name = "idSensor")
    private Integer idSensor;

    @Column(name = "fechaInicio")
    private Timestamp fechaInicio;

    @Column(name = "fechaFin")
    private Timestamp fechaFin;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "minimo")
    private Double minimo;

    @Column(name = "maximo")
    private Double maximo;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "estado")
    private String estado;

    @Column(name = "observacion")
    private String observacion;

    public Integer getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(Integer idAlerta) {
        this.idAlerta = idAlerta;
    }

    public Integer getIdLocal() {
        return idLocal;
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

    public Timestamp getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Timestamp fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Timestamp getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Timestamp fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getMinimo() {
        return minimo;
    }

    public void setMinimo(Double minimo) {
        this.minimo = minimo;
    }

    public Double getMaximo() {
        return maximo;
    }

    public void setMaximo(Double maximo) {
        this.maximo = maximo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}