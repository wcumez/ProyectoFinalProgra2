
package LogIn;

import java.sql.*;

/**
 *
 * @author wcume
 */
public class LConexion {
    
    
    String user ="root";
    String password ="";
    String bd ="DBbecksportLogin";
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
