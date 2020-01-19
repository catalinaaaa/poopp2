package modelo;

/**
 @author Ariel Rodriguez, Daniel Salzar, Bryan Berrocal
 */
public class Incidente {
  private String tipoIncidente;
  private int puntajeIncidente;
  private static int cantIncidentes;

  //Setters and getters
  public String getTipoIncidente() {
    return tipoIncidente;
  }

  public void setTipoIncidente(String tipoIncidente) {
    this.tipoIncidente = tipoIncidente;
  }

  public int getPuntajeIncidente() {
    return puntajeIncidente;
  }

  public void setPuntajeIncidente(int puntajeIncidente) {
    this.puntajeIncidente = puntajeIncidente;
  }
  
  //constructor
  public Incidente(String tipoIncidente, int puntajeIncidente) {
    this.tipoIncidente = tipoIncidente;
    this.puntajeIncidente = puntajeIncidente;
    this.cantIncidentes++;
  }
  
  public Incidente(){
  }
  
  @Override
  public String toString() {
    return "Incidente{" + "tipoIncidente=" + tipoIncidente + ", puntajeIncidente=" + puntajeIncidente + '}';
  }
   
   
   
}
