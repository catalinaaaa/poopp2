package dao;
import conexiones.ConexionSQL;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Recurso;

/**
 @author Ariel Rodriguez, Daniel Salzar, Bryan Berrocal
 */

public class recursoDAO {
  public static ArrayList<Recurso> listaRecursos = new ArrayList<Recurso>(); 
  public static Recurso recurso1;
  public static ResultSet res;
  
  /**
  *Metodo que permite registrar un nuevo recurso
  * @param pRecurso recibe el objeto de tipo Recurso para agregarlo a la lista
  * @return verdadero si se cumple
  * @throws java.sql.SQLException psoble error de sql
  */
  public boolean registrarRecurso(Recurso pRecurso) throws SQLException{
    if(!pRecurso.equals(null)){
      listaRecursos.add(pRecurso);
      ingresarRecurso(pRecurso);
      JOptionPane.showMessageDialog(null, "Se registro un nuevo recurso exitosamente");
     }else{
      JOptionPane.showMessageDialog(null, "ERROR");
      return false;
    }
    return true;    
  }
  
  
  /**
  *Metodo imprime a los recursos que existan en la lista  
  * @return la lista de recursos
  */
  public ArrayList<Recurso> cargarListaRecursos(){
    if(listaRecursos != null){
      for (int i = 0; i < listaRecursos.size(); i++){
        System.out.println("Recurso: " + listaRecursos.get(i).toString() + "\n");
      }
    }
    else{
      JOptionPane.showMessageDialog(null, "La lista de recursos se encuentra vacia");
    }
      return listaRecursos;
  }   
  
  
  /**
  *Método que busca un recurso en la lista si tiene el mismo nombre
  * @param pNombreRecurso recibe el nombre del recurso a comparar en la lista
  * @return el recurso que encuentra
  */
  public Recurso buscarRecurso(String pNombreRecurso){
    if(listaRecursos != null){
      for(int a = 0; a < listaRecursos.size(); a++){
        if(listaRecursos.get(a).getNombreRecurso() == pNombreRecurso){
          System.out.println("Se encontro el recurso: " + listaRecursos.get(a).toString() + "\n");
        }
        else{
          JOptionPane.showMessageDialog(null, "No existe ningun recurso con ese nombre");
        }
      }
    }
    else{
      JOptionPane.showMessageDialog(null, "La lista de recursos se encuentra vacia");
    }
    return recurso1;
  }
  
  
  //Métodos de SQL
  /**
   * Metodo para ingresar un recurso a la base de datos
   * @param recurso recibe el objeto de tipo recurso
   * @throws java.sql.SQLException psoble error de sql
   */
  public static void ingresarRecurso(Recurso recurso) throws SQLException{
    CallableStatement entrada = ConexionSQL.getConnection().prepareCall
       ("{call agregarRecurso(?, ?)}");
    entrada.setString(1,recurso.getNombreRecurso());
    entrada.setInt(2,recurso.getCantidadUtilizada());
    entrada.execute(); 
  }  
  
  
  /**
   * Funcion para relacionar un recurso con una sala
   * @param nombre recibe l nombre del recurso
   * @param id recibe el id de la sala
   * @throws java.sql.SQLException psoble error de sql
   */
  public static void unirRecursosSala(String nombre, String id) throws SQLException{
   CallableStatement entrada = ConexionSQL.getConnection().prepareCall
       ("{call agregarRecursosSala(?, ?)}");
   entrada.setString(1, id);
   entrada.setString(2, nombre);
   entrada.execute();
  }
  
  
  /**
   * Metodo para enviar los recursos de una lista al metodo unirRecursosSala
   * @param recursos recibe la lista de recursos
   * @param id recibe el id de la sala
   * @throws java.sql.SQLException psoble error de sql
   */
  public static void agregarLista(ArrayList<Recurso> recursos,String id) throws SQLException{
    for(int i = 0; i<recursos.size(); i++){
      Recurso recursoAgregado;
      recursoAgregado=recursos.get(i);
      String nombre = recursoAgregado.getNombreRecurso();
      unirRecursosSala(nombre, id);
    }   
  }
  
  
  /**
   * Metodo que une los recursos con la reserva
   *@param nombre recibe el nombre del recurso
   * @param id recibe el id de la reserva
   * @throws java.sql.SQLException psoble error de sql
   */
  public static void unirRecursosReserva(String nombre, String id) throws SQLException{
    CallableStatement entrada = ConexionSQL.getConnection().prepareCall
       ("{call agregarRecursosReserva(?, ?)}");
    entrada.setString(1, id);
    entrada.setString(2, nombre);
    entrada.execute();
  }
  
  
  /**
   * Metodo que agrega los recursos con la reserva
   *@param recursos recibe la lista de recursos
   * @param id recibe el id de la reserva
   * @throws java.sql.SQLException psoble error de sql
   */
  public static void agregarListaReserva(ArrayList<Recurso> recursos, String id) throws SQLException{
    for(int i = 0; i < recursos.size(); i++){
      Recurso recursoAgregado;
      recursoAgregado=recursos.get(i);
      String nombre = recursoAgregado.getNombreRecurso();
      unirRecursosReserva(nombre, id);      
    }
  }
  
  
  /**
  * Metodo para obtener los recursos guardados en la base de datos
  * @throws java.sql.SQLException psoble error de sql
   */
  public void obtenerDatos() throws SQLException{
    CallableStatement salida = ConexionSQL.getConnection().prepareCall("{call mostrarRecursos}");
    res = salida.executeQuery();        
    try{
      while(res.next()){
        Recurso recurso = new Recurso();
        recurso.setNombreRecurso(res.getString(2));
        recurso.setCantidadUtilizada(res.getInt(3));
        listaRecursos.add(recurso);
      }
    }
    catch(SQLException e){
    }
  }
  
  
  /**
   * Metodo para obtener de la base de datos, los recursos que se solicitaron en cada sala
   * @param idSala recibe el id la sala
   * @return la lista de recursos
   * @throws java.sql.SQLException psoble error de sql
   */
  public static ArrayList obtenerRecursosSala(String idSala) throws SQLException{
    CallableStatement salida = ConexionSQL.getConnection().prepareCall("{call mostrarSalaRecursos}");
    res = salida.executeQuery();
    ArrayList<Recurso> recursos = new ArrayList<Recurso>();
    try{
      while(res.next()){
        if(res.getString(1).equals(idSala)){
          Recurso recursoSala = new Recurso();
          recursoSala.setNombreRecurso(res.getString(3));
          recursoSala.setCantidadUtilizada(res.getInt(4));
          recursos.add(recursoSala);
        }
      }
    }
    catch (SQLException e){
    }
    return recursos;
  }
  
  
  /**
   * Metodo para obtener de la base de datos los recursos de cada reserva
   * @param idReserva obtiene la lista de las reservas con recurosos
   * @return la lista de reservas con recursos
   * @throws java.sql.SQLException psoble error de sql
   */
  public static ArrayList obtenerRecursosReserva(String idReserva) throws SQLException{
    CallableStatement salida = ConexionSQL.getConnection().prepareCall
       ("{call mostrarRecursosReserva}");
    res = salida.executeQuery();
    ArrayList<Recurso> recursos = new ArrayList<Recurso>();
    try{
      while(res.next()){
        if(res.getString(1).equals(idReserva)){
          Recurso recursoReserva = new Recurso();
          recursoReserva.setNombreRecurso(res.getString(3));
          recursoReserva.setCantidadUtilizada(res.getInt(4));
          recursos.add(recursoReserva);
        }
      }
    }
    catch (SQLException e){
    }
    return recursos;
  } 
}