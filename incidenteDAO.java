package dao;
import conexiones.ConexionSQL;
import static dao.estudianteDAO.listaEstudiantes;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Incidente;

/**
 @author Ariel Rodriguez, Daniel Salzar, Bryan Berrocal
 */

public class incidenteDAO {
  public static ArrayList<Incidente> listaIncidentes = new ArrayList<Incidente>(); 
  public static Incidente incidente1;
  public static ResultSet res;
  
  
  /**
  *Metodo que permite registrar un nuevo Incidente
  * @param pIncidente recibe el objeto de tipo Incidente para agregarlo a la lista
  * @return verdadero si registra adecuadamente
  * @throws java.sql.SQLException psoble error de sql
  */
  public boolean registrarIncidente(Incidente pIncidente) throws SQLException{
    if(!pIncidente.equals(null)){
      listaIncidentes.add(pIncidente);
      ingresarIncidente(pIncidente);
      JOptionPane.showMessageDialog(null, "Se registro un nuevo Incidente exitosamente");
    }
    else{
      JOptionPane.showMessageDialog(null, "ERROR");
      return false;
    }
    return true;    
  }
  
  
  /**
  *Metodo imprime a los horarios que existan en la lista  
  * @return la lista de recursos
  */
  public ArrayList<Incidente> cargarListaIncidentes(){
    if (listaIncidentes != null){
      for(int i = 0; i < listaIncidentes.size(); i++){
        System.out.println("Incidente: " + listaIncidentes.get(i).toString() + "\n");
      }
    }
    else{
      JOptionPane.showMessageDialog(null, "La lista de Incidentes se encuentra vacia");
    }
    return listaIncidentes;
  }   
  
  
  /**
  *Método que busca un Incidente en la lista si tiene el mismo nombre
  * @param pTipoIncidente recibe el nombre del Incidente a comparar en la lista
  * @return el incidente consultado
  */
  public Incidente buscarIncidente(String pTipoIncidente){
    if(listaIncidentes != null){
      for(int a = 0; a < listaIncidentes.size(); a++){
        if(listaIncidentes.get(a).getTipoIncidente().equals(pTipoIncidente)){
          System.out.println("Se encontro el incidente: " + listaIncidentes.get(a).toString() 
             + "\n");
        }
      }
    }
    else{
      JOptionPane.showMessageDialog(null, "La lista de Incidentes se encuentra vacia");
    }
    return incidente1;
  }    
  
  
  /**
  *Método que baja los puntos de calificacion a un estudiante
  *@param  pPuntajeIncidente recibe el puntaje del incidente que rebajara la nota
  *@param pCarnet recibe el carnet del estudiante para compararle y asi poder rebajarle la nota
  * @throws java.sql.SQLException psoble error de sql
  */
  public void bajarPuntaje(int pPuntajeIncidente, String pCarnet) throws SQLException{
    int nota = 0;
    if(listaEstudiantes != null){
      for(int a = 0; a < listaEstudiantes.size(); a++){
        if(listaEstudiantes.get(a).getCarnet() ==  pCarnet){
          nota = listaEstudiantes.get(a).getCalificacion() - pPuntajeIncidente; //realiza la resta del incidente cometido
          listaEstudiantes.get(a).setCalificacion(nota);//actualiza el valor de la calificacion
          int aumentarContadorIncidentes=listaEstudiantes.get(a).getCantIncidentes();//obtiene el contador de incidentes del estudiante
          listaEstudiantes.get(a).setCantIncidentes(aumentarContadorIncidentes++);//actualiza el contador de incidentes en 1
          cambiarNotaSQL(pCarnet, pPuntajeIncidente);
          System.out.println("Se actualizo la nota del estudiante: "+listaEstudiantes.get(a).getCalificacion()+", cantidad de incidentes cometidos: "+
          aumentarContadorIncidentes+"\n");
        }
      }
    }
    else{
      JOptionPane.showMessageDialog(null, "La lista de estudiantes se encuentra vacia");
    }
  }    
  
  
  /**
   *Metodo para ingresar un incidente a la base de datos
   *@param incidente recibe el objeto de tipo incidente
   * @throws java.sql.SQLException psoble error de sql
   */
  public static void ingresarIncidente(Incidente incidente) throws SQLException{
    CallableStatement entrada = ConexionSQL.getConnection().prepareCall
       ("{call agregarIncidente(?,?)}");
    entrada.setString(1,incidente.getTipoIncidente());
    entrada.setInt(2,incidente.getPuntajeIncidente());
    entrada.execute(); 
  }  
  
  
  /**
   * Metodo para obtener los horarios de la base de datos
   * @throws java.sql.SQLException psoble error de sql
   */
  public void obtenerDatos() throws SQLException{
    CallableStatement salida = ConexionSQL.getConnection().prepareCall("{call mostrarIncidentes}");
    res = salida.executeQuery();
    try{
      while(res.next()){
        Incidente incidente = new Incidente();
        incidente.setTipoIncidente(res.getString(2));
        incidente.setPuntajeIncidente(res.getInt(3));
        listaIncidentes.add(incidente);
      }
    }
    catch (SQLException e){
    }
  }   
  
  
  /**
  *Método que permite actulizar la nota del estudiante que realiza un incidente
  * @param  pCarnet recibe el carnet del estudiante a comparar
  * @param nota recibe la nota a actualizar
  * @throws java.sql.SQLException psoble error de sql
  */
  public void cambiarNotaSQL(String pCarnet, int nota) throws SQLException{ 
    String sql = ("update estudiante set calificacion = calificacion - " + nota + " where carnet = "
       + "'" + pCarnet + "'");
    PreparedStatement pps = conexiones.ConexionSQL.getConnection().prepareStatement(sql);
    pps.executeUpdate();
    JOptionPane.showMessageDialog(null, "Datos actualizados");
    pps.executeUpdate();
    JOptionPane.showMessageDialog(null, "Los datos han sido modificados");           
  } 
}