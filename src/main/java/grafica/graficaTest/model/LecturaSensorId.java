package grafica.graficaTest.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class LecturaSensorId implements Serializable {

    private Integer IDLOCAL;
    private Integer idTanque;
    private Integer idSensor;
    private Timestamp fechaHora;

    public LecturaSensorId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LecturaSensorId)) return false;

        LecturaSensorId that = (LecturaSensorId) o;

        return Objects.equals(IDLOCAL, that.IDLOCAL) &&
               Objects.equals(idTanque, that.idTanque) &&
               Objects.equals(idSensor, that.idSensor) &&
               Objects.equals(fechaHora, that.fechaHora);
    }

    @Override
    public int hashCode() {
        return Objects.hash(IDLOCAL, idTanque, idSensor, fechaHora);
    }
}