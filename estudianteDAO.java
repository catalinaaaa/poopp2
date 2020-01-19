package dao;

import conexiones.ConexionSQL;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Estudiante;

/**
 *
 * @author catas
 */
public class estudianteDAO {
  public static ArrayList<Estudiante> listaEstudiantes = new ArrayList<Estudiante>(); 
  public static Estudiante estudiante1;
  public static ResultSet res; 
  
  
  /**
  *Metodo que permite registrar un nuevo estudiante al sistema, 
  * valida que no existan 2 estudiantes con el mismo carnet ***************************************************
  * @param pEstudiante recibe el objeto de tipo Estudiante para agregarlo a la lista
  * @return verdadero si el metodo funciona
  * @throws java.sql.SQLException psoble error de sql
  */
  public void registrarEstudiante(Estudiante pEstudiante) throws SQLException{
    listaEstudiantes.add(pEstudiante);
    ingresarEstudiante(pEstudiante);
  }
  
  
  /**
  *Metodo imprime a los estudiantes que existan en la lista  
  * @return la lista de estudiantes
  */
  public ArrayList<Estudiante> cargarListaEstudiantes(){
    if(listaEstudiantes != null){
      for(int i = 0; i < listaEstudiantes.size(); i++){
        System.out.println("Usuario: " + listaEstudiantes.get(i).toString() + "\n");
      }
    }
    else{
      JOptionPane.showMessageDialog(null,"La lista de estudiantes se encuentra vacia");
    }
    return listaEstudiantes;
  }   
  
  
  /**
  *Método que busca un recurso en la lista si tiene el mismo nombre
  * @param pCarnet recibe el carnet del estudiante que se va a comparar
  * @return el estudiante que se consulto
  */
  public Estudiante consultarEstudiante(String pCarnet){
    if(listaEstudiantes != null){
      for(int a = 0; a < listaEstudiantes.size(); a++){
        if(listaEstudiantes.get(a).getCarnet().equals(pCarnet)){
          System.out.println("Se encontro el estudiante: " + listaEstudiantes.get(a).toString()
             + "\n");
          estudiante1 = listaEstudiantes.get(a);
        }
      }
    }
    else{
      JOptionPane.showMessageDialog(null, "La lista de estudiantes se encuentra vacia");
    }
    return estudiante1;
  }
  
    
  /**
  *Método que busca un recurso en la lista si tiene el mismo nombre
  * @param pCarnet recibe el carnet del estudiante que se va a comparar
  * @return verdadero en caso de que se encuentre el estudiante
  */
  public boolean consultarEstudianteB(String pCarnet){
    if(listaEstudiantes != null){
      for(int a = 0; a < listaEstudiantes.size(); a++){
        if(listaEstudiantes.get(a).getCarnet().equals(pCarnet)){
          return true;
        }
      }
    }
    else{
      return false;
    }
    return false;
  }
  
  
  /**
  *Método que permite validar si ya existe un estudiante registrado con el mismo carnet 
  *@param pEstudiante recibe el objeto estudiante para comparar
  *@return encontrado variable si encontro o no un objeto con el mismo carnet
  */
  public boolean validarEstudiante(Estudiante pEstudiante){
    if(listaEstudiantes!=null){
       for(int a = 0; a < listaEstudiantes.size(); a++){
        if(listaEstudiantes.get(a).getCarnet().equals(pEstudiante.getCarnet())){
          System.out.println("Ya existe un estudiante registrado con ese carnet");
          return true;
        }
        else{
          return false;
        }
      }
    }
    return false;
  }
  
  
  //Métodos en SQL
  /**
   * Metodo para agregar un estudiante a la base de datos
   * @param estudiante recibe el estudiante que se va a almacenar
   * @throws java.sql.SQLException psoble error de sql
   */
  public static void ingresarEstudiante(Estudiante estudiante) throws SQLException{
    CallableStatement entrada = ConexionSQL.getConnection().prepareCall
       ("{call anadirEstudiante(?, ?, ?, ?, ?, ?, ?, ?)}");
    entrada.setString(1,estudiante.getCarnet());
    entrada.setString(2,estudiante.getNombre());
    entrada.setString(3,estudiante.getCarrera());
    entrada.setString(4,estudiante.getEmail());
    entrada.setInt(5,estudiante.getCalificacion());
    entrada.setInt(6,estudiante.getNumTel());
    entrada.setInt(7,estudiante.getLimReservas());
    entrada.setInt(8,estudiante.getCantIncidentes());
    entrada.execute(); 
  }
  
   
  /**
   * Metodo para obtener los datos de los estudiantes guardados en la base de datos
   * @throws java.sql.SQLException psoble error de sql
   */  
  public void obtenerDatos() throws SQLException{
    CallableStatement salida = ConexionSQL.getConnection().prepareCall("{call mostrarEstudiantes}");
    res = salida.executeQuery();
    try{
      while(res.next()){
        Estudiante estudiante = new Estudiante();
        estudiante.setCarnet(res.getString(1));
        estudiante.setNombre(res.getString(2));
        estudiante.setCarrera(res.getString(3));
        estudiante.setEmail(res.getString(4));
        estudiante.setCalificacion(res.getInt(5));
        estudiante.setNumTel(res.getInt(6));
        estudiante.setLimReservas(res.getInt(7));
        estudiante.setCantIncidentes(res.getInt(8));
        listaEstudiantes.add(estudiante);
      }
    }
    catch (SQLException e){    
    }
  }
  
  
  /***
   * Metodo que obtiene el estudiante q realiza una reserva
   * @param idReserva recibe el id de la reserva a comparar
   * @return el estudiante encontrado
   * @throws java.sql.SQLException psoble error de sql
  */
  public static Estudiante obtenerEstudianteReserva(String idReserva) throws SQLException{
    CallableStatement salida = ConexionSQL.getConnection().prepareCall
       ("{call mostarReservaEstudiante}");
    res = salida.executeQuery();
    Estudiante estudiante = new Estudiante();
    try{
      while(res.next()){
        if(res.getString(1).equals(idReserva)){
          estudiante.setCarnet(res.getString(2));
          estudiante.setNombre(res.getString(3));
          estudiante.setCarrera(res.getString(4));
          estudiante.setEmail(res.getString(5));
          estudiante.setCalificacion(res.getInt(6));
          estudiante.setNumTel(res.getInt(7));
          estudiante.setLimReservas(res.getInt(8));
          estudiante.setCantIncidentes(res.getInt(9));          
        }
      }
    }
    catch (SQLException e){
    }
    return estudiante;
  }    
}