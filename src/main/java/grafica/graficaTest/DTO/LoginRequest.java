package grafica.graficaTest.DTO;

public class LoginRequest {

    private Integer idUsuario;
    private String razonSocial;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getClave() {
        return razonSocial;
    }

    public void setClave(String clave) {
        this.razonSocial = clave;
    }

}