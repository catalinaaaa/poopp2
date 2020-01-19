package dao;
import conexiones.ConexionSQL;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Participante;

/**
 @author Ariel Rodriguez, Daniel Salzar, Bryan Berrocal
 */

public class participanteDAO {
  public static ArrayList<Participante> listaParticipantes = new ArrayList<Participante>(); 
  public static Participante participante1;
  public static ResultSet res; 
  
  
  /**
  *Metodo que permite registrar un nuevo participante
  * @param pParticipante recibe el objeto de tipo usuario para agregarlo a la lista
  * @return verdadero si funciona
  * @throws java.sql.SQLException psoble error de sql
  */
  public boolean registrarParticipante(Participante pParticipante) throws SQLException{
    if(!pParticipante.equals(null)){
      listaParticipantes.add(pParticipante);
      ingresarParticipante(pParticipante);
      JOptionPane.showMessageDialog(null, "El participante ha sido registrado correctamente");
    }
    else{
      JOptionPane.showMessageDialog(null, "ERROR");
      return false;
    }
    return true;    
  }
  
  
  /**
  *Metodo imprime a los participantes que existan en la lista de participantes 
  * @return la lista de participantes
  */
  public ArrayList<Participante> cargarListaParticipantes(){
    if(listaParticipantes != null){
      for (int i = 0; i < listaParticipantes.size(); i++){
        System.out.println("Participante: " + listaParticipantes.get(i).toString() + "\n");
      }
    }
    else{
      JOptionPane.showMessageDialog(null, "La lista de participantes se encuentra vacia");
    }
    return listaParticipantes;
  }
  
  
  /**
  *Método que busca un participante en la lista si tiene el mismo nombre
  * @param pNombre recibe el nombre del participante a comparar en la lista
  * @return el participante
  */
  public Participante buscarParticipante(String pNombre){
    if(listaParticipantes != null){
      for(int a = 0; a < listaParticipantes.size(); a++){
        if(listaParticipantes.get(a).getNombreParticipante() == pNombre){
          System.out.println("Se encontro el participante: " + listaParticipantes.get(a).toString() 
             + "\n");
        }
      }
    }
    return participante1;
  }
  
  
  /**
  *Método que busca un participante en la lista si tiene la misma cedula
  * @param pCedula recibe la cedula del participante a comparar en la lista
  * @return el participante
  */
  public Participante buscarParticipantePorCedula(int pCedula){
    if(listaParticipantes != null){
      for(int a = 0; a < listaParticipantes.size(); a++){
        if(listaParticipantes.get(a).getCedulaParticipante() == pCedula){
          System.out.println("Se encontro el participante: " + listaParticipantes.get(a).toString() 
             + "\n");
        }
      }
    }
    return participante1;
  }
  
  
  //Metodos de SQL
  /**
   * Metodo que ingresa el participante en la bd
   *@param participante recibe el participante
   * @throws java.sql.SQLException psoble error de sql
   */
  public static void ingresarParticipante(Participante participante) throws SQLException{
    CallableStatement entrada = ConexionSQL.getConnection().prepareCall
       ("{call agregarParticipante(?, ?, ?)}");
    entrada.setInt(1,participante.getCedulaParticipante());
    entrada.setString(2,participante.getNombreParticipante());
    entrada.setString(3,participante.getEmail());
    entrada.execute(); 
  }  
  
  
  /**
   * Metodo que agrega en la tabla reserva--participante en sql
   * @param idReserva recibe el ide la reserva
   * @param cedula recibe la cedula del participante
   * @throws java.sql.SQLException psoble error de sql
   */
  public static void agregarParticipantesReserva(String idReserva, int cedula) throws SQLException{
    CallableStatement entrada = ConexionSQL.getConnection().prepareCall
       ("{call agregarParticipanteReserva(?,?)}");
    entrada.setString(1, idReserva);
    entrada.setInt(2, cedula);
    entrada.execute();
  }
  
  
  /**
   * Metodo para enviar los participantes de una reserva al metodo de ingresarParticipante
   * @param participantes recibe la lista de participantes
   * @param idReserva recibe el ide la reserva
   * @throws java.sql.SQLException psoble error de sql
   */
  public static void agregarLista(ArrayList<Participante> participantes, String idReserva)
     throws SQLException{
    for(int i = 0; i < participantes.size(); i++){
      Participante participanteAgregado;
      participanteAgregado = participantes.get(i);
      agregarParticipantesReserva(idReserva, participanteAgregado.getCedulaParticipante());
    }  
  }
  
  
  /**
   * Metodo para obtener los participantes de la base de datos
   * @throws java.sql.SQLException psoble error de sql
   */
  public void obtenerDatos() throws SQLException{
    CallableStatement salida = ConexionSQL.getConnection().prepareCall
       ("{call mostrarParticipantes()}");
    res = salida.executeQuery();
    try{
      while(res.next()){
        Participante participante = new Participante();
        participante.setNombreParticipante(res.getString(2));
        participante.setEmail(res.getString(3));
        listaParticipantes.add(participante);
      }
    }
    catch(SQLException e){
    }
  } 
  
  
  /**
   * Metodo para ingresar los participantes de cada reserva en el arraylist de la reserva
   * @param idReserva obtiene la lista de participantes
   * @return ArrayList de los partcipantes de cada reserva
   * @throws java.sql.SQLException psoble error de sql
   */
  public static ArrayList obtenerParticipantesReserva(String idReserva) throws SQLException{
    CallableStatement salida = ConexionSQL.getConnection().prepareCall
       ("{call mostrarParticipanteReserva}");
    res = salida.executeQuery();
    ArrayList<Participante> participantes = new ArrayList<Participante>();
    try{
      while(res.next()){
        if(res.getString(1).equals(idReserva)){
          Participante participanteReserva = new Participante();
          participanteReserva.setCedulaParticipante(res.getInt(2));
          participanteReserva.setNombreParticipante(res.getString(3));
          participanteReserva.setEmail(res.getString(4));
          participantes.add(participanteReserva);
        }
      }
    }
    catch(SQLException e){
    }
    return participantes;
  }   
}