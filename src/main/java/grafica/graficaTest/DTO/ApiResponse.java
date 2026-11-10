package grafica.graficaTest.DTO;

public class ApiResponse {

    private boolean estado;
    private int codigo;
    private String mensaje;
    private String detalle;

    public ApiResponse() {
    }

    public ApiResponse(boolean estado, int codigo, String mensaje) {
        this.estado = estado;
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public ApiResponse(boolean estado, int codigo, String mensaje, String detalle) {
        this.estado = estado;
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.detalle = detalle;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

}