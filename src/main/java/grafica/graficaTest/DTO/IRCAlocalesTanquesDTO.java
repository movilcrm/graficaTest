package grafica.graficaTest.DTO;

public class IRCAlocalesTanquesDTO {

    private Integer idTanque;
    private String nombreTanque;

    public IRCAlocalesTanquesDTO(Integer idTanque, String nombreTanque) {
        this.idTanque = idTanque;
        this.nombreTanque = nombreTanque;
    }

    public Integer getIdTanque() {
        return idTanque;
    }

    public String getNombreTanque() {
        return nombreTanque;
    }
}