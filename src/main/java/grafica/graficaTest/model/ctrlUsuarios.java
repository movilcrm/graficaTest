package grafica.graficaTest.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ctrlUsuarios")
public class ctrlUsuarios {

    @Id
    @Column(name = "idUsuario")
    private Integer idUsuario;

    @Column(name = "idLocal")
    private Integer idLocal;

    @Column(name = "nombreUsuario")
    private String nombreUsuario;

    @Column(name = "clave")
    private String clave;

    @Column(name = "idNivel")
    private Integer idNivel;

    @Column(name = "estado")
    private Integer estado;

    @Column(name = "email")
    private String email;

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Integer getIdNivel() {
		return idNivel;
	}

	public void setIdNivel(Integer idNivel) {
		this.idNivel = idNivel;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    
}