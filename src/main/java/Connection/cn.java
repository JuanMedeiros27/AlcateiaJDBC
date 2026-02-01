package Connection;


import java.sql.*;

public class cn {
    
    public static Connection connect(String provedor, String nomedb, String user, String senha){
        try {
            Connection connection = DriverManager.getConnection(formarURL(provedor, nomedb), user, senha);
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException ex) {
            System.getLogger(cn.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return null;
    }
    
    public static Connection connect(String provedor,String host, String nomedb, String user, String senha){
        try {
            Connection connection = DriverManager.getConnection(formarURL(provedor, host, nomedb), user, senha);
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException ex) {
            System.getLogger(cn.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return null;
    }
    
    private static String formarURL(String provedor, String nomedb){
        switch(provedor){
            case "postgres":
                return "jdbc:%s://localhost:5432/%s".formatted(provedor,nomedb);
            case "mysql":
                return "jdbc:%s://localhost:33060/%s".formatted(provedor,nomedb);
            default:
                throw new IllegalArgumentException("Provedor nao suportado." + "\n\nProvedores que a biblioteca possui suporte: postgres, mysql.");
        }
    }
    
    private static String formarURL(String provedor, String host, String nomedb){
        if(provedor.equals("postgres") || provedor.equals("mysql")){
            return "jdbc:%s://%s/%s".formatted(provedor, host, nomedb);
        } else {
            throw new IllegalArgumentException("Provedor nao suportado." + "\n\nProvedores que a biblioteca possui suporte: postgres, mysql.");
        }
    }
    
}
