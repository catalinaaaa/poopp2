package modelo;

/**
 @author Ariel Rodriguez, Daniel Salzar, Bryan Berrocal
 */
public class Recurso {
  private String nombreRecurso;
  private int cantidadUtilizada;
  private static int contRecursos;

   
  public String getNombreRecurso() {
    return nombreRecurso;
  }

  public void setNombreRecurso(String nombreRecurso) {
    this.nombreRecurso = nombreRecurso;
  }

  public int getCantidadUtilizada() {
    return cantidadUtilizada;
  }

  public void setCantidadUtilizada(int cantidadUtilizada) {
    this.cantidadUtilizada = cantidadUtilizada;
  }


 
  public Recurso(String nombreRecurso, int cantidadUtilizada) {
    this.nombreRecurso = nombreRecurso;
    this.cantidadUtilizada = cantidadUtilizada;
    this.contRecursos++;
  }
  
  public Recurso(){
  }
  

  @Override
  public String toString() {
    return "Recurso{" + "nombreRecurso=" + nombreRecurso + ", cantidadUtilizada=" + cantidadUtilizada + '}';
  }
  
  
  
}
