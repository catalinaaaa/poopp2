package modelo;
import java.util.ArrayList;
import modelo.Estudiante;
import modelo.Participante;
import modelo.Recurso;

/**
 @author Ariel Rodriguez, Daniel Salzar, Bryan Berrocal
 */
public class Reserva {
  private String idReserva;
  private String asunto;
  private String estado;
  private String idSala;
  private String fecha;
  private String horaInicio;
  private String horaFin;
  private boolean hayIncidente;
  private int cantidad;
  private Estudiante estudiante;
  private ArrayList<Participante>listaParticipantes;
  private ArrayList<Recurso>listaRecursos;
  private ArrayList<Incidente>listaIncidentes;
  private String codigo; //*******************************************************************

  //Setters y getters
  public String getIdReserva() {
    return idReserva;
  }

  public void setIdReserva(String idReserva) {
    this.idReserva = idReserva;
  }

  public String getAsunto() {
    return asunto;
  }

  public void setAsunto(String asunto) {
    this.asunto = asunto;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getIdSala() {
    return idSala;
  }

  public void setIdSala(String idSala) {
    this.idSala = idSala;
  }

  public String getFecha() {
    return fecha;
  }

  public void setFecha(String fecha) {
    this.fecha = fecha;
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

  public boolean getHayIncidente() {
    return hayIncidente;
  }

  public void setHayIncidente(boolean hayIncidente) {
    this.hayIncidente = hayIncidente;
  }

  public int getCantidad() {
    return cantidad;
  }

  public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
  }

  public Estudiante getEstudiante() {
    return estudiante;
  }

  public void setEstudiante(Estudiante estudiante) {
    this.estudiante = estudiante;
  }

  public ArrayList<Participante> getListaParticipantes() {
    return listaParticipantes;
  }

  public void setListaParticipantes(ArrayList<Participante> listaParticipantes) {
    this.listaParticipantes = listaParticipantes;
  }

  public ArrayList<Recurso> getListaRecursos() {
    return listaRecursos;
  }

  public void setListaRecursos(ArrayList<Recurso> listaRecursos) {
    this.listaRecursos = listaRecursos;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }
  
  public String crearCod(){
   String codigoGenerado="";
   codigoGenerado = this.idSala+ this.idReserva+ this.estudiante.getCarnet();
   return codigoGenerado; 
  }

  public ArrayList<Incidente> getListaIncidentes() {
    return listaIncidentes;
  }

  public void setListaIncidentes(ArrayList<Incidente> listaIncidentes) {
    this.listaIncidentes = listaIncidentes;
  }
  
  
  
  
  //constructores
  public Reserva(String idReserva, String asunto, String estado, String idSala, String fecha, String horaInicio, String horaFin, boolean hayIncidente, int cantidad, Estudiante estudiante, ArrayList<Participante> listaParticipantes, ArrayList<Recurso> listaRecursos) {
    this.listaParticipantes=new ArrayList<Participante>();
    this.listaRecursos=new ArrayList<Recurso>();
    this.idReserva = idReserva;
    this.asunto = asunto;
    this.estado = estado;
    this.idSala = idSala;
    this.fecha = fecha;
    this.horaInicio = horaInicio;
    this.horaFin = horaFin;
    this.hayIncidente = hayIncidente;
    this.cantidad = cantidad;
    this.estudiante = estudiante;
    this.listaParticipantes = listaParticipantes;
    this.listaRecursos = listaRecursos;
    crearCod();
  }
  
   public Reserva(String idReserva, String asunto, String estado, String idSala, String fecha, String horaInicio, String horaFin, boolean hayIncidente, int cantidad, Estudiante estudiante, ArrayList<Participante> listaParticipantes) {
    this.listaParticipantes=new ArrayList<Participante>();
    this.listaRecursos=new ArrayList<Recurso>();
    this.idReserva = idReserva;
    this.asunto = asunto;
    this.estado = estado;
    this.idSala = idSala;
    this.fecha = fecha;
    this.horaInicio = horaInicio;
    this.horaFin = horaFin;
    this.hayIncidente = hayIncidente;
    this.cantidad = cantidad;
    this.estudiante = estudiante;
    this.listaParticipantes = listaParticipantes;
    crearCod();
  }
  
   public Reserva(String idReserva, String asunto, String estado, String idSala, String fecha, String horaInicio, String horaFin, boolean hayIncidente, int cantidad, Estudiante estudiante) {
    this.listaParticipantes=new ArrayList<Participante>();
    this.listaRecursos=new ArrayList<Recurso>();       
    this.idReserva = idReserva;
    this.asunto = asunto;
    this.estado = estado;
    this.idSala = idSala;
    this.fecha = fecha;
    this.horaInicio = horaInicio;
    this.horaFin = horaFin;
    this.hayIncidente = hayIncidente;
    this.cantidad = cantidad;
    this.estudiante = estudiante;
    crearCod();
  }
   
 
  public Reserva(){
    this.listaParticipantes=new ArrayList<Participante>();
    this.listaRecursos=new ArrayList<Recurso>();  
    this.estudiante = new Estudiante();
    this.listaIncidentes = new ArrayList<Incidente>();
  }
  
  
  /**
  *Método para imprimir en patalla
  */
  public String toString(){
    String msg;
    msg="\n";
    msg+="idReserva: "+getIdReserva()+"\n";
    msg+="Asunto: "+getAsunto()+"\n";
    msg+="Estado: " + getEstado() + "\n";
    msg+="Id sala: " + getIdSala() + "\n";
    msg+="Fecha reserva: " + getFecha() + "\n";
    msg+="Hora de inicio: " + getHoraInicio() + "\n";
    msg+="Hora de cierre: " + getHoraFin() + "\n";
    msg+="Presnta incidente: " + getHayIncidente() + "\n";
    msg+="Cantidad de personas: " + getCantidad() + "\n";
    msg+="Estudiante que realiza la reserva: " + getEstudiante().getCarnet() + "\n";
    msg+="Codigo de reserva desde el constructor: " + crearCod() + "\n";
    msg+="Codigo de reserva a partir del metodo: " + getCodigo() + "\n";
    //msg+="Lista participantes: " + getListaParticipantes().toString() + "\n";
    return msg;
  }
}
