package grafica.graficaTest.DTO;

import java.util.List;

public class SensoresRequest {

    private List<MedicionRequest> sensores;

    public List<MedicionRequest> getSensores() {
        return sensores;
    }

    public void setSensores(List<MedicionRequest> sensores) {
        this.sensores = sensores;
    }
}