/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Metadata;
import java.sql.*;
import java.util.*;
/**
 *
 * @author Juan
 */
public class metadata {
    
    public static int numeroColunas(Connection cn, String nomeTabela){
        validation.invalidConnection(cn);
        validation.invalidTable(cn, nomeTabela);
        
        try(ResultSet result = cn.prepareStatement("SELECT * FROM %s;".formatted(nomeTabela)).executeQuery()){
            ResultSetMetaData metadata = result.getMetaData();
            int numColumns = metadata.getColumnCount();
            return numColumns;
        } catch (SQLException ex) {
            System.getLogger(metadata.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            throw new RuntimeException("Nao foi possivel extrair o numero de colunas da tabela " + nomeTabela
                 + ".");
        }
        
    }
    
    public static int numeroColunas(ResultSet result){
        validation.invalidResultSet(result);
        
        try {
            ResultSetMetaData metadata = result.getMetaData();
            int numColumns = metadata.getColumnCount();
            return numColumns;
        } catch (SQLException ex) {
            System.getLogger(metadata.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            throw new RuntimeException("Nao foi possivel extrair o numero de colunas da tabela.");
        }
        
    }
    
    public static String[] nomeColunas(Connection cn, String nomeTabela){
        validation.invalidConnection(cn);
        validation.invalidTable(cn, nomeTabela);
        
        String[] names = new String[numeroColunas(cn, nomeTabela)];
        
        try(ResultSet result = cn.prepareStatement("SELECT * FROM %s;".formatted(nomeTabela)).executeQuery()){
            ResultSetMetaData metadata = result.getMetaData();
            
            for(int i = 0; i < names.length; i++){
                names[i] = metadata.getColumnName(i + 1);
            }
            
            return names;
        } catch (SQLException ex) {
            System.getLogger(metadata.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            throw new RuntimeException("Nao foi possivel extrair o nome das colunas da tabela " + nomeTabela + ".");
        }
    }
    
    public static String[] nomeColunas(ResultSet result){
        validation.invalidResultSet(result);
        
        String[] names = new String[numeroColunas(result)];
        
        try{
            ResultSetMetaData metadata = result.getMetaData();
            
            for(int i = 0; i < names.length; i++){
                names[i] = metadata.getColumnName(i + 1);
            }

            return names;
        } catch (SQLException ex) {
            System.getLogger(metadata.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            throw new RuntimeException("Nao foi possivel extrair o nome das colunas da tabela.");
        }
    }

    public static String[] nomeTipoColunas(Connection cn, String nomeTabela) {
        validation.invalidConnection(cn);
        validation.invalidTable(cn, nomeTabela);
        
        String[] types = new String[numeroColunas(cn, nomeTabela)];

            try (ResultSet result = cn.prepareStatement("SELECT * FROM %s;".formatted(nomeTabela)).executeQuery()) {
                ResultSetMetaData metadata = result.getMetaData();

                for (int i = 0; i < types.length; i++) {
                    types[i] = metadata.getColumnTypeName(i + 1);
                }

                return types;
            } catch (SQLException ex) {
                System.getLogger(metadata.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                throw new RuntimeException("Nao foi possivel extrair o nome do tipo das colunas da tabela " + nomeTabela + ".");
            }
    }
    
    public static String[] nomeTipoColunas(ResultSet result) {
        validation.invalidResultSet(result);
        
        String[] types = new String[numeroColunas(result)];

            try {
                ResultSetMetaData metadata = result.getMetaData();

                for (int i = 0; i < types.length; i++) {
                    types[i] = metadata.getColumnTypeName(i + 1);
                }

                return types;
            } catch (SQLException ex) {
                System.getLogger(metadata.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                throw new RuntimeException("Nao foi possivel extrair o nome do tipo das colunas da tabela.");
            }
    }
    
    public static int[] tipoColunas(Connection cn, String nomeTabela) {
        validation.invalidConnection(cn);
        validation.invalidTable(cn, nomeTabela);
        
        int[] types = new int[numeroColunas(cn, nomeTabela)];

            try (ResultSet result = cn.prepareStatement("SELECT * FROM %s;".formatted(nomeTabela)).executeQuery()) {
                ResultSetMetaData metadata = result.getMetaData();

                for (int i = 0; i < types.length; i++) {
                    types[i] = metadata.getColumnType(i + 1);
                }

                return types;
            } catch (SQLException ex) {
                System.getLogger(metadata.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                throw new RuntimeException("Nao foi possivel extrair o tipo das colunas da tabela " + nomeTabela + ".");
            }
    }
    
    public static int[] tipoColunas(ResultSet result) {
        validation.invalidResultSet(result);
        
        int[] types = new int[numeroColunas(result)];

            try {
                ResultSetMetaData metadata = result.getMetaData();

                for (int i = 0; i < types.length; i++) {
                    types[i] = metadata.getColumnType(i + 1);
                }

                return types;
            } catch (SQLException ex) {
                System.getLogger(metadata.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                throw new RuntimeException("Nao foi possivel extrair o tipo das colunas da tabela.");
            }
    }
    
    public static Map FKs(Connection cn, String nomeTabela) {
        validation.invalidConnection(cn);
        validation.invalidTable(cn, nomeTabela);
        
        Map<String, String> fks = new LinkedHashMap();

        try (ResultSet result = cn.getMetaData().getImportedKeys(null, null, nomeTabela)) {
            while (result.next()) {
                fks.put(result.getString("PKTABLE_NAME"), result.getString("PKCOLUMN_NAME"));
            }

            return fks;
        } catch (SQLException ex) {
            System.getLogger(metadata.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            throw new RuntimeException("Nao foi possivel extrair as foreign keys da tabela " + nomeTabela + "."
                + "\n\nSe a coluna PKTABLE_NAME nao foi encontrada, isso significa que a tabela nao possui foreign keys.");
        }
    }
    
    //--------------------------------------------------------------------------
    private static class validation{
        
        public static boolean invalidConnection(Connection cn){
            boolean control = false;

            try {
                if (cn == null || cn.isClosed()) {
                    control = true;
                    throw new IllegalArgumentException("Conexao nula."
                            + "\n\nA conexao passada como parametro esta vazia, provavelmente por um erro ao se conectar com o banco de dados.");
                }
            } catch (SQLException ex) {
                System.getLogger(metadata.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }

            return control;
        }
        
        public static boolean invalidResultSet(ResultSet result){
            boolean control = false;
            
            try {
                if(result == null || result.isClosed()){
                    control = true;
                    throw new IllegalArgumentException("ResultSet nulo."
                            + "\n\nO ResultSet passado como parametro esta vazio.");
                }
            } catch (SQLException ex) {
                System.getLogger(metadata.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
            
            return control;
        }
        
        public static boolean invalidTable(Connection cn, String nomeTabela){
            boolean control = true;
            
            try{
                DatabaseMetaData db = cn.getMetaData();
                ResultSet result = db.getTables(null, null, null, new String[]{"TABLE"});
                
                while(result.next()){
                    if(result.getString("TABLE_NAME").equals(nomeTabela)) control = false;
                }
                
                result.close();
            } catch (SQLException ex) {
                System.getLogger(metadata.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
            
            if(control){
                throw new IllegalArgumentException("Tabela inexistente."
                            + "\n\nA tabela passada como parametro nao corresponde a nenhuma tabela no banco de dados.");
            }
            
             return control;
        }
        
    }
}
