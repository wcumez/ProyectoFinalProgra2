/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VentanaPrincipal;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.HeadlessException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;



/**
 *
 * @author wcume
 */
public class OpcionesProducto {
    
int codigoProducto;
String nombreProducto;
float precioUnitario;
int cantidadProducto;

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    //Ingresar producto
    public void ingresarPalabra(JTextField campoCodigo,JTextField campoNombre,JTextField campoPrecio,JTextField campoCantidad){
    
        setCodigoProducto(Integer.parseInt(campoCodigo.getText()));
        setNombreProducto(campoNombre.getText());
        setPrecioUnitario(Integer.parseInt(campoPrecio.getText()));
        setCantidadProducto(Integer.parseInt(campoCantidad.getText()));
        
        CConexion objetoConexion = new CConexion();
        
        String consulta="insert into producto(codigoProducto,nombreProducto,precioUnitario,cantidadProducto)\n" +
        " values(?,?,?,?);";
        
        try{
            CallableStatement cs= objetoConexion.estableceConexion().prepareCall(consulta);
            
            cs.setString(1, String.valueOf(getCodigoProducto()));
            cs.setString(2,getNombreProducto());
            cs.setString(3, String.valueOf(getPrecioUnitario()));
            cs.setString(4, String.valueOf(getCantidadProducto()));
            cs.execute();
            JOptionPane.showMessageDialog(null, "El producto fue ingresada correctamente!!");
            
            
        }catch(HeadlessException | SQLException e){
            
            JOptionPane.showMessageDialog(null, "|x|Ell producto no fue ingresada");
        } 
    }
    
    //Mostrar Productos
    public  void mostrarWords(JTable totalProductos){
        
        CConexion objetoConexion = new CConexion();
        
        DefaultTableModel modelo = new DefaultTableModel();
        
       TableRowSorter<TableModel> ordenarTable = new TableRowSorter<TableModel>(modelo);
       totalProductos.setRowSorter(ordenarTable);
       
       String sql="";
       
       modelo.addColumn("Código");
       modelo.addColumn("Producto");
       modelo.addColumn("Precio");
       modelo.addColumn("Cantidad");
       
       
       totalProductos.setModel(modelo);
       
       sql="select * from producto;";
       
       String[] datos = new String[4];
       
       Statement st;
       
       try{
           st=objetoConexion.estableceConexion().createStatement();
           ResultSet rs = st.executeQuery(sql);
           
           while(rs.next()){
               datos[0]=rs.getString(1);
               datos[1]=rs.getString(2);
               datos[2]=rs.getString(3);
               datos[3]=rs.getString(4);
               
               modelo.addRow(datos);
           }
           
           totalProductos.setModel(modelo);
           
       }catch(SQLException e){
           
          JOptionPane.showMessageDialog(null, "Los datos no se pueden mostrar");
       }
       
       
       
    }
    
    
    //Modificar productos
   
     public void modificarWords(JTextField campoCodigo,JTextField campoNombre,JTextField campoPrecio,JTextField campoCantidad){
        
        setCodigoProducto(Integer.parseInt(campoCodigo.getText()));
        setNombreProducto(campoNombre.getText());
        setPrecioUnitario((float) Double.parseDouble(campoPrecio.getText())); 
        setCantidadProducto(Integer.parseInt(campoCantidad.getText()));

         CConexion objetoConexion = new CConexion();
         
         String consulta= "update producto set producto.nombreProducto=?,producto.precioUnitario=?,producto.cantidadProducto=? where producto.codigoProducto=?;";
         
         try{
             
           CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);

            cs.setString(1,getNombreProducto());
            cs.setFloat(2, getPrecioUnitario()); 
            cs.setInt(3, getCantidadProducto()); 
            cs.setInt(4,getCodigoProducto());
            cs.execute();

             JOptionPane.showMessageDialog(null,"Actualización procesada!!");
             
         }catch(SQLException e){
             JOptionPane.showMessageDialog(null,"|x|Actualización no procesada");  
         }
         
        }
     
     //buscar por codigo
     
     public void buscarPorCodigo(JTextField campoCodigo,JTextField campoNombre,JTextField campoPrecio,JTextField campoCantidad){
     
     String consulta="select codigoProducto,nombreProducto,precioUnitario,cantidadProducto from producto where producto.codigoProducto=?";
         
      CConexion objetoConexion = new CConexion();
      try{
                   CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
                   cs.setString(1,campoCodigo.getText());
                   cs.execute();
                   
                   ResultSet rs = cs.executeQuery();
                   
                   
                   if(rs.next()){
                   
                   
                   JOptionPane.showMessageDialog(null,"Registro encontrado");
                   campoCodigo.setText(rs.getString("codigoProducto"));
                   campoNombre.setText(rs.getString("nombreProducto"));
                    campoPrecio.setText(rs.getString("precioUnitario"));
                    campoCantidad.setText(rs.getString("cantidadProducto"));
                   
                   
                   }else{
                   
                   
                      campoCodigo.setText(rs.getString(""));
                   campoNombre.setText(rs.getString(""));
                    campoPrecio.setText(rs.getString(""));
                    campoCantidad.setText(rs.getString(""));
                   
                   }
                   
      }catch(SQLException ex){
      
        JOptionPane.showMessageDialog(null,"Registro no encontrado");
      
      }
     
     }
     
    
     
     
     //buscar por nombre
     
     public void buscarPorNombre(JTextField campoCodigo,JTextField campoNombre,JTextField campoPrecio,JTextField campoCantidad){
     
     String consulta="select codigoProducto,nombreProducto,precioUnitario,cantidadProducto from producto where producto.nombreProducto=?";
         
      CConexion objetoConexion = new CConexion();
      try{
                   CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
                   cs.setString(1,campoNombre.getText());
                   cs.execute();
                   
                   ResultSet rs = cs.executeQuery();
                   
                   
                   if(rs.next()){
                   
                   
                   JOptionPane.showMessageDialog(null,"Registro encontrado");
                   campoCodigo.setText(rs.getString("codigoProducto"));
                   campoNombre.setText(rs.getString("nombreProducto"));
                    campoPrecio.setText(rs.getString("precioUnitario"));
                    campoCantidad.setText(rs.getString("cantidadProducto"));
                   
                   
                   }else{
                   
                   
                      campoCodigo.setText(rs.getString(""));
                   campoNombre.setText(rs.getString(""));
                    campoPrecio.setText(rs.getString(""));
                    campoCantidad.setText(rs.getString(""));
                   
                   }
                   
      }catch(SQLException ex){
      
          
      JOptionPane.showMessageDialog(null,"Registro no encontrado");
      }
     
     }
 
     
     //Eliminar producto
     public void EliminarPalabra(JTextField campoCodigo){
     
         setCodigoProducto(Integer.parseInt(campoCodigo.getText()));
         
         CConexion objetoConexion = new CConexion();
         
         String consulta= "delete from producto where producto.codigoProducto=?;";
         
         try{
             
             CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
             cs.setInt(1, getCodigoProducto());
             
             cs.execute();
             
             JOptionPane.showMessageDialog(null, "Palabra eleminada correctamente!!");
         
         }catch(SQLException e ){
         
         
         JOptionPane.showMessageDialog(null, "|x|La palabra no fue eliminada, intente nuevamente.");
         }
         
         
         
         
     }
     
     //Generar reporte
     public void reporteria (){

     Document documento= new Document();
     CConexion objetoConexion = new CConexion();
        try{
            PdfWriter.getInstance(documento, new FileOutputStream("Reporte_Inventario.pdf"));
            documento.open();
            
            String sql="";
    
       PdfPTable tabla= new PdfPTable(4);
            tabla.addCell("Codigo");
            tabla.addCell("Producto");
            tabla.addCell("Precio Unitario");
            tabla.addCell("Cantidad");
       Statement st;
       
       try{
           sql="select * from producto;";
           st=objetoConexion.estableceConexion().createStatement();
           ResultSet rs = st.executeQuery(sql);
            
           if(rs.next()){
           
                 do  {
                tabla.addCell(rs.getString(1));
                tabla.addCell(rs.getString(2));
                tabla.addCell(rs.getString(3));
                tabla.addCell(rs.getString(4));
            }while (rs.next());

            // Agregar la tabla al documento
            documento.add(tabla);
            
           }
           
          
           
         
                
            }catch(DocumentException | SQLException e){
            
                
            }
            documento.close();
            JOptionPane.showMessageDialog(null, "Reporte generado");
        
        }catch(DocumentException | HeadlessException | FileNotFoundException e){
        
        }

     }
     
  
     
        
     
     
     
     
     
     
     
     
     
     



 
    
}
