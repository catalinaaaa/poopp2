package dao;
import conexiones.ConexionSQL;
import conexiones.EmailSender;
import static dao.estudianteDAO.listaEstudiantes;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Reserva;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Participante;
import modelo.Recurso;
import static dao.participanteDAO.listaParticipantes;
import java.sql.PreparedStatement;
import modelo.Estudiante;
import modelo.Incidente;

/**
 @author Ariel Rodriguez, Daniel Salzar, Bryan Berrocal
 */

public class reservaDAO {
  public static ArrayList<Reserva> listaReservas = new ArrayList<Reserva>(); 
  public static Reserva reserva1;
  public static ResultSet res;
  public static ArrayList<String> listaCodigos = new ArrayList<String>(); 
  
  
  /**
  *Metodo que permite registrar una nueva Reserva al sistema, 
  * valida que no existan 2 Salas con el mismo id 
  * @param pReserva  recibe el objeto de tipo reserva para agregarlo a la lista
  * @return verdadero si se registra
  * @throws java.sql.SQLException psoble error de sql
  */
  public boolean registrarReserva(Reserva pReserva) throws SQLException{
    if(!pReserva.equals(null)){
      if(!listaReservas.equals(pReserva)){
        //validaciones de los requisitos del estudiante
        if(pReserva.getEstudiante().getCalificacion()>70){
          //agrega la reserva a la lista de reservas
          listaReservas.add(pReserva);
          if(pReserva.getListaRecursos().isEmpty() && pReserva.getListaParticipantes().isEmpty()){
            ingresarSoloReserva(pReserva);
            generarCodigo(pReserva.getIdReserva(), pReserva.getIdSala(), 
               pReserva.getEstudiante().getCarnet());
          }
          else if(pReserva.getListaParticipantes().isEmpty()){
            ingresarReservaRecursos(pReserva);
            generarCodigo(pReserva.getIdReserva(), pReserva.getIdSala(), 
               pReserva.getEstudiante().getCarnet());
          }
          else if(pReserva.getListaRecursos().isEmpty()){
            ingresarReservaParticipantes(pReserva);
            generarCodigo(pReserva.getIdReserva(), pReserva.getIdSala(), 
               pReserva.getEstudiante().getCarnet());
            notificarParticipantes();
          }
          else{
            ingresarReserva(pReserva);
            generarCodigo(pReserva.getIdReserva(), pReserva.getIdSala(), 
               pReserva.getEstudiante().getCarnet());
            notificarParticipantes();
          }
          JOptionPane.showMessageDialog(null, "Se registro una Reserva sala exitosamente");
        }
      } 
    }
    else{
      JOptionPane.showMessageDialog(null, "ERROR");
      return false;
    }   
    return true;
  }
  
  
  /**
  *Metodo imprime a las Reservas que existan en la lista  
  * @return la lista de Reservas
  */
  public ArrayList<Reserva> cargarListaReservas(){
    if(listaReservas != null){
      for(int i = 0; i < listaReservas.size(); i++){
        System.out.println("Reserva: " + listaReservas.get(i).toString() + "\n");
      }
    }
    else{
      JOptionPane.showMessageDialog(null, "La lista de Reservas se encuentra vacia");
    }
    return listaReservas;
  } 
  
  
  /**
  *Método que busca una Reserva en la lista si tiene el mismo id
  * @param pIdReserva recibe id de la reserva  que se va a comparar
  * @return la reserva que encuentra
  */
  public Reserva consultarReserva(String pIdReserva){
    if(listaReservas != null){
      for(int a = 0; a < listaReservas.size(); a++){
        if(listaReservas.get(a).getIdReserva().equals(pIdReserva)){
          System.out.println("Se encontro la reserva: " + listaReservas.get(a).toString() + "\n");
        }
      }
    }
    else{
      JOptionPane.showMessageDialog(null, "La lista de Reservas se encuentra vacia");
    }
    return reserva1;
  }  
  
  
  /**
  *Método que busca una Reserva en la lista 
  * @param pCarnet recibe carnet del estudiante que se va a comparar
  * @return la reserva consultada por el carnet del estudiante
  */
  public ArrayList consultarReservaPorEstudiante(String pCarnet){
    ArrayList<Reserva> reservaLista = new ArrayList<Reserva>();
    if(listaReservas != null){
      for(int a = 0; a < listaReservas.size(); a++){
        if(listaReservas.get(a).getEstudiante().getCarnet().equals(pCarnet)){
          reservaLista.add(listaReservas.get(a));
        }
      }
    }
    else{
      return reservaLista;
    }
    return reservaLista;
  }  
  
  
  /**
  *Método que genere un codigo automatico para las calificaciones 
  *@param pCarnet recibe el carnet del estudiante 
  *@param pIdSala recibe el id de la sala
  *@param pIdReserva recibe el id de la reserva
  *@return el codigo que genera
  */
  public String generarCodigo(String pIdReserva, String pIdSala, String pCarnet){
    String codigo = "";
    if(listaReservas != null){
      for(int a = 0; a < listaReservas.size(); a++){
        if(listaReservas.get(a).getIdReserva() == pIdReserva){
          if(listaReservas.get(a).getIdSala() == pIdSala){
            if(listaReservas.get(a).getEstudiante().getCarnet() == pCarnet){
              codigo = pIdSala + pIdReserva + pCarnet;
              System.out.println("El codigo generado es: " + codigo);
              listaReservas.get(a).setCodigo(codigo);
              JOptionPane.showMessageDialog(null, "El codigo generado es: " +
                 listaReservas.get(a).getCodigo());
              System.out.println(listaReservas.get(a).toString());
              //genera el correo para enviarlo al estudiante
              EmailSender email = new EmailSender();
              email.SendEmail(listaReservas.get(a).getEstudiante().getEmail(), "Codigo de reserva: "
                 + codigo);
              
             //agrega a la lista de codigos el codigo creado
              listaCodigos.add(codigo);
            }
            else{
              JOptionPane.showMessageDialog(null, "El estudiante ingresado no realizado reservas");
            }
          }
          else{
            JOptionPane.showMessageDialog(null, "No existe ninguna reserva de esa sala");
          }
        }
      }   
    }
    else{
      JOptionPane.showMessageDialog(null, "La lista de Reservas se encuentra vacia");
    }
    return codigo;
  }
  
  
  /**
  *Método que permite notificar a los participantes de una reserva
  * @return el mensaje del correo
  */
  public String notificarParticipantes(){
    String mensaje = "";
    if(listaReservas != null){
      for(int a = 0; a < listaReservas.size(); a++){
        if(listaReservas.get(a).getListaParticipantes() != null){
          for(int b = 0; b < listaReservas.get(a).getListaParticipantes().size(); b++){
            String emailUsuario = listaReservas.get(a).getListaParticipantes().get(b).getEmail();
            String hora =  "Recordatorio a las: " + listaReservas.get(a).getHoraInicio();
            String codigo = "Cod de la reserva: " + listaReservas.get(a).crearCod();
            String participante = "Participante: " + 
               listaReservas.get(a).getListaParticipantes().get(b).getNombreParticipante();
            mensaje = codigo +" "+ hora+" "+participante;
            System.out.println(listaReservas.get(a).toString()); 
            //genera el correo para enviarlo a los participantes
            EmailSender email = new EmailSender();
            email.SendEmail(emailUsuario, mensaje);
            break;
          }//termina el 2do for  
        }
        else{
          JOptionPane.showMessageDialog(null, "La lista de Reservas no cuenta con ningun "
             + "participante");
        }  
      }//termina el 1er for       
    }
    else{
      JOptionPane.showMessageDialog(null, "La lista de Reservas no cuenta con ningun participante");
    }
    return mensaje;
  }
  
    
  /**
  *Método que permite notificar a los participantes de una cancelacion reserva
  * @param pIdReserva recibe el id de la reserva
  */
  public void notificarParticipantesCancelacion(String pIdReserva){
    String mensaje = "";
    if(listaReservas != null){
      for(int a = 0; a < listaReservas.size(); a++){
        if(listaReservas.get(a).getIdReserva().equals(pIdReserva)){
          if(listaReservas.get(a).getListaParticipantes()!= null){
            for(int b = 0; b < listaReservas.get(a).getListaParticipantes().size(); b++){
              String emailUsuario = listaReservas.get(a).getListaParticipantes().get(b).getEmail();
              String hora =  "Recordatorio a las: " + listaReservas.get(a).getHoraInicio();
              String codigo = "Cod de la reserva: " + listaReservas.get(a).crearCod();
              String participante = "Participante: " 
                 + listaReservas.get(a).getListaParticipantes().get(b).getNombreParticipante();
              String cancel = "la reserva anterior ha sido cancelada";
              mensaje = codigo + " " + hora + " " + participante + " " + cancel;
              System.out.println(listaReservas.get(a).toString());
              //genera el correo para enviarlo a los participantes
              EmailSender email = new EmailSender();
              email.SendEmail(emailUsuario, mensaje);
              break;
            }//termina el 2do for  
          }
          else{
          JOptionPane.showMessageDialog(null, "La lista de Reservas no cuenta con ningun "
             + "participante");
          }  
        }  
      }//termina el 1er for       
    }
    else{
      JOptionPane.showMessageDialog(null, "La lista de Reservas no cuenta con ningun participante");
    }
  }
  
   
 /**
  *Método que busca una Reserva en la lista si tiene el mismo id y la cancela
  * @param pIdReserva recibe id de la reserva  que se va a comparar
  */
  public void cancelarReserva(String pIdReserva){
    if(listaReservas != null){
      for(int a = 0; a < listaReservas.size(); a++){
        if(listaReservas.get(a).getIdReserva().equals(pIdReserva)){
          //actualiza el valor del estado de la reserva
          listaReservas.get(a).setEstado("Cancelada");
          
          //baja en 1 el puntaje de la nota del estudiante que realizo la reserva
          int notaEstudianteActualizada = listaReservas.get(a).getEstudiante().getCalificacion();
          notaEstudianteActualizada = notaEstudianteActualizada - 1;
          listaReservas.get(a).getEstudiante().setCalificacion(notaEstudianteActualizada);
          System.out.println(listaReservas.get(a).getEstudiante().toString());
          
          //Busca la lista de participantes de la reserva para notificar que se cancelo
          if(listaReservas.get(a).getListaParticipantes() != null){
            for(int b = 0; b < listaReservas.get(a).getListaParticipantes().size(); b++){
              String mensaje = "La reserva con el codigo: " + listaReservas.get(a).crearCod() + 
                 " ha sido: " + listaReservas.get(a).getEstado();
              //genera el correo para enviarlo a los participantes
              EmailSender email = new EmailSender();
              email.SendEmail(listaParticipantes.get(b).getEmail(), mensaje);
            }//termina el 2do for  
          }
          else{
          JOptionPane.showMessageDialog(null, "La lista de Reservas no cuenta con ningún "
             + "participante");
          }             
        }//termina el 1er for
      }
    }
    else{
      JOptionPane.showMessageDialog(null, "La lista de Reservas se encuentra vacia");
    }
  }  
  
  
  /**
  *Método que permite agregar una lista de recursos a una reserva
  *@param pIdReserva recibe el ide la reserva a comparar
  *@param pListaR recibe la lista de los recursos
  */
  public void agregarListaRecursos (String pIdReserva, ArrayList<Recurso> pListaR){
    if(pListaR != null){
      for(int a = 0; a < listaReservas.size(); a++){
        if(listaReservas.get(a).getIdReserva().equals(pIdReserva)){
          listaReservas.get(a).setListaRecursos(pListaR);
          JOptionPane.showMessageDialog(null, "Se ha agregado a lista de recursos a la reserva: " 
             + listaReservas.get(a).getIdReserva());
        }
      }//termina el for
    }
    else{
      JOptionPane.showMessageDialog(null, "La lista de recursos que desea agregar se encuentra vacia");
    }
  }
  
  
  /**
  *Método que permite agregar una lista de incidentes a una reserva
  *@param pIdReserva recibe el ide la reserva a comparar
  *@param pListaIncidente recibe la lista de los incidentes
  * @throws java.sql.SQLException psoble error de sql
  */
  public void agregarListaIncidentes (String pIdReserva, ArrayList<Incidente> pListaIncidente) 
     throws SQLException{
    if(pListaIncidente != null){
      for(int a = 0; a < listaReservas.size(); a++){
        if(listaReservas.get(a).getIdReserva().equals(pIdReserva)){
          listaReservas.get(a).setListaIncidentes(pListaIncidente);
            System.out.println(listaReservas.get(a).toString());
            JOptionPane.showMessageDialog(null, "Se ha agregado a lista de incidentes a la reserva: "
               + listaReservas.get(a).getIdReserva());
        }
      }//termina el for
    }
    else{
      JOptionPane.showMessageDialog(null, "La lista de incidentes que desea agregar se encuentra "
         + "vacia");
    }
  }
  
  
  /**
  *Método que baja los puntos de calificacion a un estudiante
  * @param  pPuntajeIncidente recibe el puntaje del incidente que rebajara la nota
  * @param pCarnet recibe el carnet del estudiante para compararle y asi poder rebajarle la nota
  * @throws java.sql.SQLException psoble error de sql
  */
  public void bajarPuntaje(int pPuntajeIncidente, String pCarnet) throws SQLException{
    int nota = 0;
    if(listaEstudiantes != null){
      for(int a = 0; a < listaEstudiantes.size(); a++){
        if(listaEstudiantes.get(a).getCarnet().equals(pCarnet)){
          nota = listaEstudiantes.get(a).getCalificacion() - pPuntajeIncidente; //realiza la resta del incidente cometido
          listaEstudiantes.get(a).setCalificacion(nota);//actualiza el valor de la calificacion
            
          int aumentarContadorIncidentes = listaEstudiantes.get(a).getCantIncidentes();//obtiene el contador de incidentes del estudiante
          listaEstudiantes.get(a).setCantIncidentes(aumentarContadorIncidentes++);//actualiza el contador de incidentes en 1
          cambiarNotaIncidenteSQL(pCarnet, nota);//actualiza la nota en la bd 
          actualizarContadorIncidentesSQL(pCarnet);
          System.out.println("Se actualizo la nota del estudiante: " + 
             listaEstudiantes.get(a).getCalificacion() + ", cantidad de incidentes cometidos: "+
                aumentarContadorIncidentes + "\n");
          }
        }
      }
    else{
      JOptionPane.showMessageDialog(null, "La lista de estudiantes se encuentra vacia");
    }
  }    
  
  
  //Método de SQL
  /**
   * Metodo para guardar una reserva en la base de datos
   * @param reserva recibe el objeto de tipo reseva
   * @throws java.sql.SQLException psoble error de sql
   */
  public static void ingresarReserva(Reserva reserva) throws SQLException{
    CallableStatement entrada = ConexionSQL.getConnection().prepareCall
       ("{call crearReserva(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
    entrada.setString(1, reserva.getIdReserva());
    entrada.setString(2, reserva.getAsunto());
    entrada.setString(3, reserva.getEstado());
    entrada.setString(4, reserva.getIdSala());
    entrada.setString(5, reserva.getFecha());
    entrada.setString(6, reserva.getHoraInicio());
    entrada.setString(7, reserva.getHoraFin());
    entrada.setBoolean(8 ,reserva.getHayIncidente());
    entrada.setInt(9, reserva.getCantidad());
    entrada.setString(10, reserva.getEstudiante().getCarnet());
    entrada.execute();     
    
    ArrayList<Participante>participantes = reserva.getListaParticipantes();
    participanteDAO.agregarLista(participantes, reserva.getIdReserva());
    
    ArrayList<Recurso> recursos = reserva.getListaRecursos();
    recursoDAO.agregarListaReserva(recursos, reserva.getIdReserva());
  }
  
  
  /**
   * Metodo para guardar una reserva que no posee una lista de participantes
   * @param reserva recibe el objeto de tipo reserva
   * @throws java.sql.SQLException psoble error de sql
   */
  public static void ingresarReservaRecursos(Reserva reserva) throws SQLException{
    CallableStatement entrada = ConexionSQL.getConnection().prepareCall
       ("{call crearReserva(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
    entrada.setString(1, reserva.getIdReserva());
    entrada.setString(2, reserva.getAsunto());
    entrada.setString(3, reserva.getEstado());
    entrada.setString(4, reserva.getIdSala());
    entrada.setString(5, reserva.getFecha());
    entrada.setString(6, reserva.getHoraInicio());
    entrada.setString(7, reserva.getHoraFin());
    entrada.setBoolean(8, reserva.getHayIncidente());
    entrada.setInt(9, reserva.getCantidad());
    entrada.setString(10, reserva.getEstudiante().getCarnet());    
    entrada.execute();     
    
    ArrayList<Recurso> recursos = reserva.getListaRecursos();
    recursoDAO.agregarListaReserva(recursos, reserva.getIdReserva());
  }  
  
  
  /**
   * Metodo para guardar una reserva que no posee una lista de recursos
   * @param reserva recibe el objeto de tipo reserva
   * @throws java.sql.SQLException psoble error de sql
   */
  public static void ingresarReservaParticipantes(Reserva reserva) throws SQLException{
    CallableStatement entrada = ConexionSQL.getConnection().prepareCall
       ("{call crearReserva(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
    entrada.setString(1, reserva.getIdReserva());
    entrada.setString(2, reserva.getAsunto());
    entrada.setString(3, reserva.getEstado());
    entrada.setString(4, reserva.getIdSala());
    entrada.setString(5, reserva.getFecha());
    entrada.setString(6, reserva.getHoraInicio());
    entrada.setString(7, reserva.getHoraFin());
    entrada.setBoolean(8, reserva.getHayIncidente());
    entrada.setInt(9, reserva.getCantidad());
    entrada.setString(10, reserva.getEstudiante().getCarnet());    
    entrada.execute();     
    
    ArrayList<Participante>participantes = reserva.getListaParticipantes();
    participanteDAO.agregarLista(participantes, reserva.getIdReserva());
  }  
  
  
  /**
   * Metodo para guarar una reserva que no posee lista de recursos ni de participantes
   * @param reserva recibe el objeto de tipo reserva
   * @throws java.sql.SQLException psoble error de sql
   */
  public static void ingresarSoloReserva(Reserva reserva) throws SQLException{
    CallableStatement entrada = ConexionSQL.getConnection().prepareCall
       ("{call crearReserva(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
    entrada.setString(1, reserva.getIdReserva());
    entrada.setString(2, reserva.getAsunto());
    entrada.setString(3, reserva.getEstado());
    entrada.setString(4, reserva.getIdSala());
    entrada.setString(5, reserva.getFecha());
    entrada.setString(6, reserva.getHoraInicio());
    entrada.setString(7, reserva.getHoraFin());
    entrada.setBoolean(8, reserva.getHayIncidente());
    entrada.setInt(9, reserva.getCantidad());
    entrada.setString(10, reserva.getEstudiante().getCarnet());    
    entrada.execute(); 
  }  
  
  
  /**
   * Metodo para registrar un incidente en una reserva
   * @param idReserva recibe el id de la reserva
   * @param tipoIncidente recibe el tipo de incidente
   * @throws java.sql.SQLException psoble error de sql
   */
  public static void agregarIncidenteReserva(String idReserva, String tipoIncidente) 
     throws SQLException{
    CallableStatement entrada = ConexionSQL.getConnection().prepareCall
       ("{call agregarReservaIncidente(?, ?)}");
    entrada.setString(1, idReserva);
    entrada.setString(2, tipoIncidente);
    entrada.execute();
  }
  
  
  /**
   * Metodo para obtener las reservas de la base de datos
   * @throws java.sql.SQLException psoble error de sql
   */
  public void obtenerDatos() throws SQLException{
    CallableStatement salida = ConexionSQL.getConnection().prepareCall("{call mostrarReservas}");
    res = salida.executeQuery();
    try{
      while(res.next()){
        Reserva reserva = new Reserva();
        reserva.setIdReserva(res.getString(1));
        reserva.setAsunto(res.getString(2));
        reserva.setEstado(res.getString(3));
        reserva.setIdSala(res.getString(4));
        reserva.setFecha(res.getString(5));
        reserva.setHoraInicio(res.getString(6));
        reserva.setHoraFin(res.getString(7));
        reserva.setHayIncidente(res.getBoolean(8));
        reserva.setCantidad(res.getInt(9));
        Estudiante estudiante = new Estudiante();
        estudiante = estudianteDAO.obtenerEstudianteReserva(res.getString(1));
        reserva.setEstudiante(estudiante);
        
        ArrayList<Recurso> recursos = new ArrayList<Recurso>();
        recursos = recursoDAO.obtenerRecursosReserva(reserva.getIdReserva());
        reserva.setListaRecursos(recursos);
        
        ArrayList<Participante> participantes = new ArrayList<Participante>();
        participantes = participanteDAO.obtenerParticipantesReserva(reserva.getIdReserva());
        reserva.setListaParticipantes(participantes);
        listaReservas.add(reserva);
      }
    }
    catch (SQLException e){
    }
  }   
   
    
  /**
  *Método que permite actulizar la nota del estudiante que realiza un incidente
  * @param pCarnet recibe el carnet del estudiante a comparar
  * @param nota recibe la nota que se actualiza
  * @throws java.sql.SQLException psoble error de sql
  */
  public void cambiarNotaIncidenteSQL(String pCarnet, int nota) throws SQLException{ 
    String sql = ("update estudiante set calificacion = calificacion - " + nota + "where carnet = " 
       + "'" + pCarnet + "'");
    PreparedStatement pps = conexiones.ConexionSQL.getConnection().prepareStatement(sql);
    pps.executeUpdate();
    JOptionPane.showMessageDialog(null, "Datos actualizados");
    //obtine la info
    pps.executeUpdate();
    JOptionPane.showMessageDialog(null, "Los datos han sido modificados");           
  }
  
  
   /**
  *Método que permite actulizar el cotador de incidentes del estudiante que realiza un incidente
  * @param pCarnet recibe el carnet del estudiante a comparar
  * @throws java.sql.SQLException psoble error de sql
  */
  public void actualizarContadorIncidentesSQL(String pCarnet) throws SQLException{ 
    String sql = ("update estudiante set cantidadIncidentes = cantidadIncidentes + 1 where carnet = "
       + "'" + pCarnet + "'");
    PreparedStatement pps = conexiones.ConexionSQL.getConnection().prepareStatement(sql);
    pps.executeUpdate();
    JOptionPane.showMessageDialog(null, "Datos actualizados");
    //obtine la info
    pps.executeUpdate();
    JOptionPane.showMessageDialog(null, "Los datos han sido modificados");           
  }
}