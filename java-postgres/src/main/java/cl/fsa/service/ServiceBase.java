package cl.fsa.service;

import cl.fsa.modelo.Dominio;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceBase implements Serializable {
    
    private static Connection connection = null;
    
    public static Connection getConexion() throws SQLException {
        //Si la instancia no ha sido creado aun, se crea
        if (ServiceBase.connection == null || ServiceBase.connection.isClosed()) {
            contruyendoConexion();
        }
        return ServiceBase.connection;
    }

    //Obtener las instancias de Conexion JDBC
    private static void contruyendoConexion() throws SQLException {
        
        String urlConexion = "jdbc:postgresql://localhost:5432/fondo-afp";
        String usuario = "postgres";
        String password = "postgres";
        String DRIVER = "org.postgresql.Driver";
        
        try {
            Class.forName(DRIVER).newInstance();
            ServiceBase.connection = DriverManager.getConnection(urlConexion, usuario, password);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            System.out.println("" + ex);
        }
    }
    
    public static void close() {
        try {
            if (ServiceBase.connection != null && !ServiceBase.connection.isClosed()) {
                ServiceBase.connection.close();
            }
        } catch (SQLException ex) {
            System.out.println("" + ex);
        }
    }
    
    public List<Dominio> getListaDominio(String dominio) throws SQLException {
        List<Dominio> result = new ArrayList<>();
        try {
            getConexion().setAutoCommit(false);//Con el Manejo de cursores es importante manejar el auto commit en falso.
            String sql = "{ call public.get_lst_parametros(?, ?, ?, ?, ?) }";
            CallableStatement pre = getConexion().prepareCall(sql);
            pre.setString(1, dominio);
            pre.setInt(2, 0);
            pre.registerOutParameter(3, java.sql.Types.INTEGER);
            pre.registerOutParameter(4, java.sql.Types.VARCHAR);
            pre.registerOutParameter(5, java.sql.Types.OTHER);
            
            pre.execute();
            
            ResultSet resSet = (ResultSet) pre.getObject(5);
            while (resSet.next()) {
                Dominio dm = new Dominio(resSet.getString(1), resSet.getString(2), resSet.getString(3));
                result.add(dm);
            }
            pre.close();
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            getConexion().commit();
            close();
        }
    }
}
