package dao;
import conexiones.ConexionSQL;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Horario;

/**
 @author Ariel Rodriguez, Daniel Salzar, Bryan Berrocal
 */

public class horarioDAO {
  public static ArrayList<Horario> listaHorario = new ArrayList<Horario>(); 
  public static Horario horario1;
  public static ResultSet res; 
  
  
  /**
  *Metodo que permite registrar un nuevo horario
  * @param pHorario recibe el objeto de tipo usuario para agregarlo a la lista
  * @return retorna verdadero si se registra
  * @throws java.sql.SQLException psoble error de sql
  */
  public boolean registrarHorario(Horario pHorario) throws SQLException{
    if(!pHorario.equals(null)){
      listaHorario.add(pHorario);
      ingresarHorario(pHorario);
      JOptionPane.showMessageDialog(null, "Se ha registrado un nuevo horario correctamente");
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
  public ArrayList<Horario> cargarListaHorario(){
    if (listaHorario != null){
      for (int i=0; i<listaHorario.size();i++){
        System.out.println("Horario: " + listaHorario.get(i).toString() + "\n");
      }
    }
    else{
      JOptionPane.showMessageDialog(null,"La lista de horarios se encuentra vacia");
    }
    return listaHorario;
  }
  
  
  /**
   * Metodo para ingresar los horarios a la base de datos
   * @param horario recibe el objeto de tipo horario
   * @throws java.sql.SQLException psoble error de sql
   */
  public static void ingresarHorario(Horario horario) throws SQLException{
    CallableStatement entrada = ConexionSQL.getConnection().prepareCall
       ("{call agregarHorario(?,?,?)}");
    entrada.setString(1,horario.getDia());
    entrada.setString(2,horario.getHoraInicio());
    entrada.setString(3,horario.getHoraFin());
    entrada.execute(); 
  }  
  
  
  /**
   *Metodo para obtener los horarios de la base de datos
   * @throws java.sql.SQLException psoble error de sql
   */
  public void obtenerDatos() throws SQLException{
    CallableStatement salida = ConexionSQL.getConnection().prepareCall("{call mostrarHorarios}");
    res = salida.executeQuery();
    try{
      while(res.next()){
        Horario horario = new Horario();
        horario.setDia(res.getString(2));
        horario.setHoraInicio(res.getString(3));
        horario.setHoraFin(res.getString(4));
        listaHorario.add(horario);
      }
    }
    catch (SQLException e){
    }
  }
}