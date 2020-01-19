package bean;
import static bean.inicioBean.rEstudiante;
import static bean.inicioBean.rHorario;
import static bean.inicioBean.rHorarioExcepcion;
import static bean.inicioBean.rIncidente;
import static bean.inicioBean.rParticipante;
import static bean.inicioBean.rRecurso;
import static bean.inicioBean.rReserva;
import static bean.inicioBean.rSala;
import com.sun.jmx.snmp.BerDecoder;
import java.io.Serializable;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.Estudiante;

/**
 * @author Daniel Salazar
 */


@ManagedBean (name="estudiante")
@SessionScoped

public class estudianteBean implements Serializable{
  private String nombre;
  private String carnet;
  private String carrera;
  private String email;
  private int calificacion;
  private int numTel;
  private int limReservas;
  private int cantIncidentes;
  private static int cantEstudiantes;

    
  public String getNombre() {
    return nombre;
  }

  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  
  public String getCarnet() {
        return carnet;
    }

  public void setCarnet(String carnet) {
    this.carnet = carnet;
  }

  
  public String getCarrera() {
    return carrera;
  }

  
  public void setCarrera(String carrera) {
    this.carrera = carrera;
  }

  
  public String getEmail() {
    return email;
  }

 
  public void setEmail(String email) {
    this.email = email;
  }

  
  public int getCalificacion() {
    return calificacion;
  }

 
  public void setCalificacion(int calificacion) {
    this.calificacion = calificacion;
  }

  
  public int getNumTel() {
    return numTel;
  }

  
  public void setNumTel(int numTel) {
    this.numTel = numTel;
  }

 
  public int getLimReservas() {
    return limReservas;
  }

  
  public void setLimReservas(int limReservas) {
    this.limReservas = limReservas;
  }

  
  public int getCantIncidentes() {
    return cantIncidentes;
  }

  
  public void setCantIncidentes(int cantIncidentes) {
    this.cantIncidentes = cantIncidentes;
  }

  
  public static int getCantEstudiantes() {
    return cantEstudiantes;
  }

  
  public static void setCantEstudiantes(int cantEstudiantes) {     
    estudianteBean.cantEstudiantes = cantEstudiantes;
  }
  
  
  public String mostrar() throws SQLException{
    FacesContext facesContext = FacesContext.getCurrentInstance();
    String result="";
    if(inicioBean.rEstudiante.consultarEstudianteB(carnet) == false){
      Estudiante estudiante = new Estudiante(nombre, carnet, carrera, email, calificacion, numTel,
         limReservas, cantIncidentes); 
      inicioBean.rEstudiante.registrarEstudiante(estudiante);
      result="verEstudiante";
      return result;
    }
    else{
      facesContext.addMessage("agregarEstudianteForm", new FacesMessage
         ("Ya existe un estudiante con este carnet"));
      return null;
    }
  }
  
 
  public String consultarEstudiante(){
    FacesContext facesContext = FacesContext.getCurrentInstance();
    String result="";
    if(inicioBean.rEstudiante.consultarEstudianteB(carnet) == true){
      Estudiante estudiante = inicioBean.rEstudiante.consultarEstudiante(carnet);  
          
      System.out.println("ESTUDIANTE ENCONTRADO");
      System.out.println(estudiante.toString());
          
      setNombre(estudiante.getNombre());
      setCarnet(estudiante.getCarnet());
      setCarrera(estudiante.getCarrera());
      setEmail(estudiante.getEmail());
      setCalificacion(estudiante.getCalificacion());
      setNumTel(estudiante.getNumTel());
      setLimReservas(estudiante.getLimReservas());
      setCantIncidentes(estudiante.getCantIncidentes());
      result="estudianteVer";
      return result;
    }
    else{
      facesContext.addMessage("consultaEstudianteForm", new FacesMessage
         ("El carnet de estudiante no existe"));
      return null;
    }
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
  
  
  public String abrirBuscarEstudiante() throws SQLException{
    cargar();
    String resultado = "abrirBuscarEstudiante";
    return resultado;
  }
}