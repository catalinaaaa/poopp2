package modelo;

/**
 @author Ariel Rodriguez, Daniel Salzar, Bryan Berrocal
 */

public class Estudiante {
  private String nombre;
  private String carnet;
  private String carrera;
  private String email;
  private int calificacion = 100;
  private int numTel;
  private int limReservas;
  private int cantIncidentes;
  private static int cantEstudiantes;

  
  /**
   * métodos sets y gets
   */
  public String getNombre() {
    return nombre;
  }

  
  public void setNombre(String nombre) {
    this.nombre = nombre;
    System.out.println(nombre);
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
  
  /**
   * método constructor de la clase Estudiante
   * @param nombre
   * @param carnet
   * @param carrera
   * @param email
   * @param calificacion
   * @param numTel
   * @param limReservas
   * @param cantIncidentes 
   */
  public Estudiante(String nombre, String carnet, String carrera, String email, int calificacion, 
     int numTel, int limReservas, int cantIncidentes) {
    this.nombre = nombre;
    this.carnet = carnet;
    this.carrera = carrera;
    this.email = email;
    this.calificacion = calificacion;
    this.numTel = numTel;
    this.limReservas = limReservas;
    this.cantIncidentes = cantIncidentes;
    this.cantEstudiantes++;
  }
  
  
  /**
   * método constructor vacío de la clase Estudiante
   */
  public Estudiante(){
  }
  

  /**
   * método toString de la clase Estudiante
   * @return información del nombre del estudiante, su número de carnet, la carrera que cursa,
   * el email, calificación a nivel del sistema, número de teléfono de él, límite de reservas
   * semanales y la cantidad de incidentes
   */
  public String toString(){
    String msg;
    msg = "\n";
    msg += "Nombre: " + getNombre() + "\n";
    msg += "Carnet: " + getCarnet() + "\n";
    msg += "Carrera: " + getCarrera() + "\n";
    msg += "Email:" + getEmail() + "\n";
    msg += "Calificacion:" + getCalificacion() + "\n";
    msg += "Telefono:" + getNumTel() + "\n";
    msg += "Limite de reservas hechas:" + getLimReservas() + "\n";
    msg += "Cantidad de incidentes cometidos:" + getCantIncidentes() + "\n";
    return msg;
  }
}