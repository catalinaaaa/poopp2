package modelo;

/**
 @author Ariel Rodriguez, Daniel Salzar, Bryan Berrocal
 */
public class Participante {
  private int cedulaParticipante;
  private String nombreParticipante;
  private String email;
  private static int cantParticipantes;
  
    
  public String getNombreParticipante() {
    return nombreParticipante;
  }

  public void setNombreParticipante(String nombreParticipante) {
    this.nombreParticipante = nombreParticipante;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getCedulaParticipante() {
    return cedulaParticipante;
  }

  public void setCedulaParticipante(int cedulaParticipante) {
    this.cedulaParticipante = cedulaParticipante;
  }

   
  public Participante(int cedulaParticipante, String nombreParticipante, String email) {
    this.cedulaParticipante = cedulaParticipante;
    this.nombreParticipante = nombreParticipante;
    this.email = email;
  }
  
  public Participante(){
  }

  
   @Override
   public String toString() {
    String msg;
    msg="\n";
    msg+="Cedula: "+ getCedulaParticipante()+"\n";
    msg+="Nombre: "+ getNombreParticipante()+"\n";
    msg+="Email: " + getEmail() + "\n";
    
    return msg;
   }
  

  
}
