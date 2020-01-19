package dao;
import conexiones.ConexionSQL;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Recurso;
import modelo.Sala;

/**
 @author Ariel Rodriguez, Daniel Salzar, Bryan Berrocal
 */

public class salaDAO {
  public static ArrayList<Sala> listaSalas = new ArrayList<Sala>(); 
  public static Sala sala1;
  public static ResultSet res;
  
  
  /**
  *Metodo que permite registrar una nueva Sala al sistema, 
  * valida que no existan 2 Salas con el mismo id 
  * @param pSala recibe el objeto de tipo sala para agregarlo a la lista
  * @return verdadero si registra la sala
  * @throws java.sql.SQLException psoble error de sql
  */
  public boolean registrarSala(Sala pSala) throws SQLException{
    if(!pSala.equals(null)){
      if(!validarSala(pSala)){
        listaSalas.add(pSala);
        if(pSala.getListaRecursos().isEmpty()){
          ingresarSalaSinRecursos(pSala);
        }
        else{
          ingresarSala(pSala);
        }
        JOptionPane.showMessageDialog(null, "Se registro una nueva sala exitosamente");
      }
      else{
        JOptionPane.showMessageDialog(null, "ERROR, la sala que desea agregar ya existe");
      }      
    }
    else{
      JOptionPane.showMessageDialog(null, "ERROR");
      return false;
    }   
    return true;
  }
  
  
  /**
  *Metodo imprime a las Salas que existan en la lista  
  * @return la lista de Salas
  */
  public ArrayList<Sala> cargarListaSalas(){
    if(listaSalas != null){
      for(int i = 0; i < listaSalas.size(); i++){
        System.out.println("Sala: " + listaSalas.get(i).toString() + "\n");
      }
    }
    else{
      JOptionPane.showMessageDialog(null, "La lista de salas se encuentra vacia");
    }
    return listaSalas;
  }   
  
  
  /**
  *Metodo que permite obtener a las Salas que existan en la lista  
  * @return la lista de Salas
  */
  public ArrayList<Sala> obtenerListaSalas(){
    if(listaSalas != null){
      for(int i = 0; i < listaSalas.size(); i++){
        listaSalas.get(i).toString();
      }
    }
    else{
      JOptionPane.showMessageDialog(null, "La lista de salas se encuentra vacia");
    }
    return listaSalas;
  }   
  
  
  /**
  *Método que busca una Sala en la lista si tiene el mismo id
  * @param pIdSala recibe el carnet del estudiante que se va a comparar
  * @return la sala que se consulto
  */
  public Sala consultarSala(String pIdSala){
    if(listaSalas != null){
      for(int a = 0; a < listaSalas.size(); a++){
        if(listaSalas.get(a).getIdSala().equals(pIdSala)){
          System.out.println("Se encontro la sala: " + listaSalas.get(a).toString() + "\n");
        }
      }
    }
    else{
      JOptionPane.showMessageDialog(null, "La lista de salas se encuentra vacia");
    }
    return sala1;
  }    
  
  
  /**
  *Método que permite modificar los atributos de la sala
  * @param pIdSala recibe el id de la sala
  * @param pUbicacion recibe la nueva ubicacion de la sala
  * @param pEstado recibe el nuevo estado de la sala
  * @param pCalificacion recibe la nueva calificacion
  * @param pCapMax recibe la nueva capacidad maxima de la sala
  * @return los datos actualizados de la sala
  * @throws java.sql.SQLException psoble error de sql
  */
  public Sala modificarSala(String pIdSala, String pUbicacion, String pEstado, int pCalificacion,
     int pCapMax) throws SQLException{
    if(!listaSalas.isEmpty()){
      for(int a = 0; a < listaSalas.size(); a++){
        if(listaSalas.get(a).getIdSala().equals(pIdSala)){
          //actualiza todos los datos de la sala
          listaSalas.get(a).setUbicacion(pUbicacion);
          listaSalas.get(a).setEstado(pEstado);
          listaSalas.get(a).setCalificacion(pCalificacion);
          listaSalas.get(a).setCapMax(pCapMax);
          System.out.println("AQUIIIIIIIIIIII");
          actualizarSala(pIdSala, pUbicacion, pEstado, pCalificacion, pCapMax);
          System.out.println("Datos de la sala modificados: " + listaSalas.get(a).toString() + "\n");
          JOptionPane.showMessageDialog(null, "Datos de la sala modificados");
        }
      }
    }
    else{
      JOptionPane.showMessageDialog(null, "La lista de Salas se encuentra vacia");
    }
    return sala1;
  }    
  
  
  /**
   *Método que permite validar el estado de la sala 
   *@param pIdSala recibe el id de la sala a buscar
   *@return el estado actual de la sala
   */
  public String verEstadoSala(String pIdSala){
    String estadoActual = "";
    if(listaSalas != null){
      for(int a = 0; a < listaSalas.size(); a++){
        if(listaSalas.get(a).getIdSala().equals(pIdSala)){
          estadoActual = listaSalas.get(a).getEstado();
          JOptionPane.showMessageDialog(null, "El estado actual de la sala es: " + estadoActual);
        }
        else{
          JOptionPane.showMessageDialog(null, "NO hay ninguna sala con ese ID");
        }
      }
    }
    else{
      JOptionPane.showMessageDialog(null, "La lista de salas se encuentra vacia");
    }
    return estadoActual;
  }
  
    
  /**
   *Método que permite cambiar el estado de la sala 
   *@param pIdSala recibe el id de la sala a buscar
   *@return el cambio del estado de la sala
   * @throws java.sql.SQLException psoble error de sql
   */
  public String cambiarEstadoSala(String pIdSala) throws SQLException{
    String estadoActual = "";
    if(listaSalas != null){
      for(int a = 0; a < listaSalas.size(); a++){
        if(listaSalas.get(a).getIdSala().equals(pIdSala)){
          if(listaSalas.get(a).getEstado().equals("Libre")){
            estadoActual = "Ocupado";
            listaSalas.get(a).setEstado(estadoActual);
            actualizarEstado(pIdSala, estadoActual);
            JOptionPane.showMessageDialog(null, "Se ha cambiado el estado de la sala a: " 
               + estadoActual);
          }
          else{
            estadoActual = "Libre";
            listaSalas.get(a).setEstado(estadoActual);
            JOptionPane.showMessageDialog(null, "Se ha cambiado el estado de la sala a: " 
               + estadoActual);
          }    
        }
        else{
          JOptionPane.showMessageDialog(null, "No existe un sala con ese id");
        }
      }
    }
    else{
      JOptionPane.showMessageDialog(null, "La lista de Salas se encuentra vacia");
   }
  return estadoActual;
  }
  
  
  /**
  *Método que permite validar si ya existe una sala registrada con el mismo id
  *@param pSala recibe el objeto sala para comparar
  *@return encontrado variable si encontro o no un objeto con el mismo id
  */
  public boolean validarSala(Sala pSala){
    boolean encontrado = false;
    if(listaSalas != null){
      for(int a = 0; a < listaSalas.size(); a++){
        if(listaSalas.get(a).getIdSala().equals(pSala.getIdSala())){
          System.out.println("Ya existe una sala registrada con ese id");
          encontrado = true;
          return encontrado;
        }
        else{
          JOptionPane.showMessageDialog(null, "No existe ninguna sala con ese id");
          return encontrado;
        }
      }
    }
    else{
      JOptionPane.showMessageDialog(null, "La lista de sala se encuentra vacia");
    }
    return encontrado;
  }
  
  
  //Métodos de SQL
  /**
   * Metodo para ingresar una sala a la base de datos
   * @param sala recibe el objeto de tipo sala
   * @throws java.sql.SQLException posible error de sql
   */
  public static void ingresarSala(Sala sala) throws SQLException{
    CallableStatement entrada = ConexionSQL.getConnection().prepareCall("{call agregarSala(?, ?, ?)}");
    entrada.setString(1, sala.getIdSala());
    entrada.setString(2, sala.getUbicacion());
    entrada.setInt(3, sala.getCapMax());
    entrada.execute(); 
    ArrayList<Recurso>recursos = sala.getListaRecursos();
    recursoDAO.agregarLista(recursos, sala.getIdSala());
  }
  
  
  /**
   * Metodo para ingresar una sala sin recursos a la base de datos
   * @param sala recibe el objeto de tipo sala
   * @throws java.sql.SQLException posible error de sql
   */
  public static void ingresarSalaSinRecursos(Sala sala) throws SQLException{
    CallableStatement entrada = ConexionSQL.getConnection().prepareCall("{call agregarSala(?, ?, ?)}");
    entrada.setString(1, sala.getIdSala());
    entrada.setString(2, sala.getUbicacion());
    entrada.setInt(3, sala.getCapMax());
    entrada.execute(); 
  }  
  
  
  /**
   * Metodo para relacionar una sala y un horario
   * @param idSala recibe el id de la sala
   * @param dia recibe el dia de la semana
   * @param horaInicio recibe la fecha de inicio
   * @param horaFin recibe la fecha de cierre
   * @throws java.sql.SQLException posible error de sql
   */
  public static void agregarHorario(String idSala, String dia, String horaInicio, String horaFin) 
     throws SQLException{
    CallableStatement entrada = ConexionSQL.getConnection().prepareCall
       ("{call agregarSalaHorario(?, ?, ?, ?)}");
    entrada.setString(1,idSala);
    entrada.setString(2,dia);
    entrada.setString(3, horaInicio);
    entrada.setString(4, horaFin);
    entrada.execute();
  }
  
  
  /**
   * Metodo para relacionar una sala y un horario excepcion
   * Metodo para relacionar una sala y un horario
   * @param idSala recibe el id de la sala
   * @param dia recibe el dia de la semana
   * @param horaInicio recibe la fecha de inicio
   * @param horaFin recibe la fecha de cierre
   * @throws java.sql.SQLException posible error de sql
   */
  public static void agregarHorarioExcepcion(String idSala, String dia, String horaInicio, 
     String horaFin) throws SQLException{
    CallableStatement entrada = ConexionSQL.getConnection().prepareCall
       ("{call agregarSalaHorarioExcep(?, ?, ?, ?)}");
    entrada.setString(1,idSala);
    entrada.setString(2,dia);
    entrada.setString(3, horaInicio);
    entrada.setString(4, horaFin);
    entrada.execute();
  }  
  
  
  /**
   * Metodo para obtener las salas guardas en la base de datos
   * @throws java.sql.SQLException posible error de sql
   */
  public void obtenerDatos() throws SQLException{
    CallableStatement salida = ConexionSQL.getConnection().prepareCall("{call mostrarSalas}");
    res = salida.executeQuery();
    try{
      while(res.next()){
        Sala sala = new Sala();
        sala.setIdSala(res.getString(1));
        sala.setUbicacion(res.getString(2));
        sala.setCapMax(res.getInt(3));
        sala.setEstado(res.getString(4));
        sala.setCalificacion(res.getInt(5));
        ArrayList<Recurso> recursos = new ArrayList<Recurso>();
        recursos = recursoDAO.obtenerRecursosSala(res.getString(1));
        sala.setListaRecursos(recursos);
        listaSalas.add(sala);
      }
    }
    catch(SQLException e){
    }
  }
  
  
  /**
   * Metodo para actualizar los datos de una sala
   * @param idSala recibe el id a comparar
   * @param ubicacion recibe la nueva ubicacion
   * @param estado recibe el nuevo estado
   * @param calificacion recibe la nueva calificacion
   * @param capacidad recibe la nueva capacidad
   * @throws java.sql.SQLException posible error de sql
   */
  public static void actualizarSala(String idSala, String ubicacion, String estado, 
     int calificacion, int capacidad) throws SQLException{
    CallableStatement entrada = ConexionSQL.getConnection().prepareCall
       ("{call actualizarSala(?, ?, ?, ?, ?)}");
    entrada.setString(1, idSala);
    entrada.setString(2, ubicacion);
    entrada.setString(3, estado);
    entrada.setInt(4, calificacion);
    entrada.setInt(5, capacidad);
    entrada.execute(); 
  }  
  
  
  /**
   * Metodo para actualizar la calificacion de una nota
   * @param idSala el id de la sala a acyializar
   * @param calificacion la nueva calificacion
   * @throws java.sql.SQLException posible error de sql
   */
  public static void actualizarCalificacion(String idSala, int calificacion) throws SQLException{
    CallableStatement entrada = ConexionSQL.getConnection().prepareCall
       ("{call actualizarCalificacion(?, ?)}");
    entrada.setString(1, idSala);
    entrada.setInt(2, calificacion);
    entrada.execute();
  }
  
  
  /**
   * Metodo para actualizar el estado de una sala
   * @param idSala recibe el id de la sala
   * @param estado recibe el nuevo estado
   * @throws java.sql.SQLException posible error de sql
   */
  public static void actualizarEstado(String idSala, String estado) throws SQLException{
    CallableStatement entrada = ConexionSQL.getConnection().prepareCall
       ("{call actualizarEstado(?, ?)}");
    entrada.setString(1, idSala);
    entrada.setString(2, estado);
    entrada.execute();
  }   
}