package modelo;

/**
 @author Ariel Rodriguez, Daniel Salzar, Bryan Berrocal
 */
public class Horario {
  private String dia;
  private String horaInicio;
  private String horaFin;

  
  //Setters and getters
  public String getDia() {
    return dia;
  }

  public void setDia(String dia) {
    this.dia = dia;
  }

  public String getHoraInicio() {
    return horaInicio;
  }

  public void setHoraInicio(String horaInicio) {
    this.horaInicio = horaInicio;
  }

  public String getHoraFin() {
    return horaFin;
  }

  public void setHoraFin(String horaFin) {
    this.horaFin = horaFin;
  }
  
  //Constructor
  public Horario(String dia, String horaInicio, String horaFin) {
    this.dia = dia;
    this.horaInicio = horaInicio;
    this.horaFin = horaFin;
  }
  
  public Horario() {
  }
  
  //toString 
  @Override
  public String toString() {
    return "Horario{" + "dia=" + dia + ", horaInicio=" + horaInicio + ", horaFin=" + horaFin + '}';
  }
  
  
}
