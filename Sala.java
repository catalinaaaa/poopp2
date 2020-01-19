package modelo;
import java.util.ArrayList;
import modelo.Recurso;
import modelo.Horario;
import modelo.HorarioExcepcion;

/**
 @author Ariel Rodriguez, Daniel Salzar, Bryan Berrocal
 */

public class Sala {
  private String idSala;
  private String ubicacion;
  private int capMax;
  private String estado;
  private int calificacion;
  private static int cantSalas;
  private ArrayList<Recurso> listaRecursos;
  private Horario pHorarios;
  private HorarioExcepcion pHorarioExcepcion;
  

  public String getIdSala() {
    return idSala;
  }

  public void setIdSala(String idSala) {
    this.idSala = idSala;
  }

  public String getUbicacion() {
    return ubicacion;
  }

  public void setUbicacion(String ubicacion) {
    this.ubicacion = ubicacion;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public int getCalificacion() {
    return calificacion;
  }

  public void setCalificacion(int calificacion) {
    this.calificacion = calificacion;
  }

  public ArrayList<Recurso> getListaRecursos() {
    return listaRecursos;
  }

  public void setListaRecursos(ArrayList<Recurso> listaRecursos) {
     this.listaRecursos = listaRecursos;
  }

  public int getCapMax() {
    return capMax;
  }

  public void setCapMax(int capMax) {
    this.capMax = capMax;
  }

  public Horario getpHorarios() {
    return pHorarios;
  }

  public void setpHorarios(Horario pHorarios) {
    this.pHorarios = pHorarios;
  }

  public HorarioExcepcion getpHorarioExcepcion() {
    return pHorarioExcepcion;
  }

  public void setpHorarioExcepcion(HorarioExcepcion pHorarioExcepcion) {
    this.pHorarioExcepcion = pHorarioExcepcion;
  }

  
  
  

  
  public Sala(String idSala, String ubicacion, String estado, int calificacion, int capMax) {
    this.listaRecursos=new ArrayList<Recurso>();
    this.idSala = idSala;
    this.ubicacion = ubicacion;
    this.estado = estado;
    this.calificacion = calificacion;
    this.capMax=capMax;
    this.cantSalas++;
  }

  public Sala(String idSala, String ubicacion, String estado, int calificacion, int capMax,ArrayList<Recurso> listaRecursos) {
    this.listaRecursos=new ArrayList<Recurso>();
    this.idSala = idSala;
    this.ubicacion = ubicacion;
    this.estado = estado;
    this.calificacion = calificacion;
    this.capMax=capMax;
    this.listaRecursos = listaRecursos;
    this.cantSalas++;
  }
  
  public Sala(){
    this.listaRecursos=new ArrayList<Recurso>();
  }
  
  public Sala(String pIdSala, Horario pHorario){
    this.idSala = pIdSala;
    this.pHorarios = pHorario;
  }
  
  public Sala(String pIdSala, HorarioExcepcion pHorarioExcepcion){
    this.idSala = pIdSala;
    this.pHorarioExcepcion = pHorarioExcepcion;
  }
  
 
  
  
   public String toString(){
    String msg;
    msg="\n";
    msg+="Id Sala: "+getIdSala()+"\n";
    msg+="Ubicacion: "+getUbicacion()+"\n";
    msg+="Estado: " + getEstado() + "\n";
    msg+="Calificacion: " + getCalificacion() + "\n";
    msg+="Capacidad maxima : " + getCapMax() + "\n";
    return msg;
   }
    
}
