
package VentanaPrincipal;

import java.sql.*;

/**
 *
 * @author wcume
 */
public class CConexion {
    
    
    String user ="root";
    String password ="6584";
    String bd ="BDBecksport";
    String ip ="localhost";
    String puerto ="3306";
    
    String cadena = "jdbc:mysql://"+ip+":"+puerto+"/"+bd;
    
    public Connection estableceConexion(){
        
        Connection con= null;
        
        try{
           con=DriverManager.getConnection(cadena,user,password);
            
        }catch(SQLException e){
        }
        return con;
    }
    
    
    
    
}
