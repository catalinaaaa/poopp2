package bean;
import com.sun.jmx.snmp.BerDecoder;
import dao.estudianteDAO;
import dao.horarioDAO;
import dao.horarioExcepcionDAO;
import dao.incidenteDAO;
import dao.participanteDAO;
import dao.recursoDAO;
import dao.reservaDAO;
import dao.salaDAO;
import java.io.Serializable;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author Daniel Salazar
 */

@ManagedBean (name="inicio", eager=true)
@SessionScoped
public class inicioBean implements Serializable{
  public static estudianteDAO rEstudiante = new estudianteDAO();  
  public static horarioDAO rHorario = new horarioDAO();
  public static horarioExcepcionDAO rHorarioExcepcion = new horarioExcepcionDAO();
  public static incidenteDAO rIncidente = new incidenteDAO();
  public static participanteDAO rParticipante = new participanteDAO();
  public static recursoDAO rRecurso = new recursoDAO();
  public static reservaDAO rReserva = new reservaDAO();
  public static salaDAO rSala = new salaDAO();
  private String usuario;
  private String contrasena;

 
  public String getUsuario() {
    return usuario;
  }

  
  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  
  public String getContrasena() {
    return contrasena;
  }

  
  public void setContrasena(String contrasena) {
    this.contrasena = contrasena;
  }
  
  
  public void cargar() throws SQLException{
    rEstudiante.obtenerDatos();
    rHorario.obtenerDatos();
    rHorarioExcepcion.obtenerDatos();
    rIncidente.obtenerDatos();
    rParticipante.obtenerDatos();
    rRecurso.obtenerDatos();
    rReserva.obtenerDatos();
    rSala.obtenerDatos();
  }
  
  
  public String iniciarMenu() throws SQLException{
    cargar();
    String resultado="iniciarMenu";
    return resultado;
  }
  
  
  public String iniciarMenuEstudiante()throws SQLException{
    cargar();
    String resultado ="iniciarMenuEstudiante";
    return resultado;
  }
  
  
  public String abrirBuscarEstudiante() throws SQLException{
    cargar();
    String resultado = "abrirBuscarEstudiante";
    return resultado;
  }
}