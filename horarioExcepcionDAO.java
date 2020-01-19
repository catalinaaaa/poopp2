package dao;
import conexiones.ConexionSQL;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.HorarioExcepcion;

/**
 @author Ariel Rodriguez, Daniel Salzar, Bryan Berrocal
 */

public class horarioExcepcionDAO {
  public static ArrayList<HorarioExcepcion> listaHorarioExcepcion = new ArrayList<HorarioExcepcion>(); 
  public static HorarioExcepcion horarioExcepcion1;
  public static ResultSet res; 
  
  
  /**
  *Metodo que permite registrar un nuevo horario de excepcion
  * @param pHorarioExcepcion recibe el objeto de tipo horario excepcion
  * @return verdadero si se registra correctamete
  * @throws java.sql.SQLException psoble error de sql
  */
  public boolean registrarHorarioE(HorarioExcepcion pHorarioExcepcion) throws SQLException{
    if(!pHorarioExcepcion.equals(null)){
      listaHorarioExcepcion.add(pHorarioExcepcion);
      ingresarHorarioExtra(pHorarioExcepcion);
      JOptionPane.showMessageDialog(null, "Se registro un nuevo horario de escepcion");
     }
    else{
      JOptionPane.showMessageDialog(null, "ERROR");
      return false;
    }
    return true;    
  }
  
  
  /**
  *Metodo imprime a los horarios que existan en la lista  
  * @return la lista de horarios
  */
  public ArrayList<HorarioExcepcion> cargarListaHorarioE(){
    if (listaHorarioExcepcion != null){
      for (int i = 0; i < listaHorarioExcepcion.size(); i++){
        System.out.println("Horario excepcion: " + listaHorarioExcepcion.get(i).toString() + "\n");
      }
    }
    else{
      JOptionPane.showMessageDialog(null, "La lista de excepciones de horario se encuentra vacia");
    }
    return listaHorarioExcepcion;
  }
  
  
  /**
   * Metodo para guardar los horarios extraordinarios en la base de datos
   * @param horario recibe el objeto de tipo horario
   * @throws java.sql.SQLException psoble error de sql
   */
  public static void ingresarHorarioExtra(HorarioExcepcion horario) throws SQLException{
    CallableStatement entrada = ConexionSQL.getConnection().prepareCall
       ("{call agregarHorarioExtra(?, ?, ?)}");
    entrada.setString(1,horario.getDia());
    entrada.setString(2,horario.getHoraInicio());
    entrada.setString(3,horario.getHoraFin());
    entrada.execute(); 
  }  
  
  
  /**
   * Metodo para obtener los horarios extraordinarios de la base de datos
   * @throws java.sql.SQLException psoble error de sql
   */
  public void obtenerDatos() throws SQLException{
    CallableStatement salida = ConexionSQL.getConnection().prepareCall
       ("{call mostrarHorariosExtras}");
    res = salida.executeQuery();
    try{
      while(res.next()){
        HorarioExcepcion horario = new HorarioExcepcion();
        horario.setDia(res.getString(2));
        horario.setHoraInicio(res.getString(3));
        horario.setHoraFin(res.getString(4));
        listaHorarioExcepcion.add(horario);
      }
    }
    catch (SQLException e){
    }
  } 
}