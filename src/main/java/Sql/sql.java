package Sql;

import java.sql.*;
import java.util.*;
import java.util.Map.Entry;

public class sql {

    /*public static void teste(String nomeTabela, String[] colunas) {
        System.out.println(generate.update(nomeTabela, colunas));
    }*/

    //--------------------------------------------------------------------------
    public static void insert(Connection cn, String nomeTabela, Object[] valores) {
        validation.invalidConnection(cn);
        validation.invalidTable(cn, nomeTabela);
        validation.invalidValues(cn, nomeTabela, valores);
        
        try (PreparedStatement stmt = cn.prepareStatement(generate.insert(cn, nomeTabela, valores.length));
                ResultSet result = generate.resultSet(cn, generate.selectAll(nomeTabela))) {
            int[] types = metadata.columnsType(result);
            
            for(int i = 0; i < valores.length; i++){
                stmt.setObject(i + 1, valores[i], types[i + 1]);
            }
            
            stmt.executeUpdate();
            System.out.println("Insert realizado com sucesso!\n O sql gerado foi: "
                    + generate.insert(cn, nomeTabela, valores.length));

            cn.commit();
        } catch (SQLException ex) {
            System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            try {
                cn.rollback();
            } catch (SQLException ex1) {
                System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex1);
            }
        } finally {
            try {
                cn.close();
            } catch (SQLException ex) {
                System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
    }

    public static void delete(Connection cn, String nomeTabela, int id) {
        validation.invalidId(id);
        validation.invalidConnection(cn);
        validation.invalidTable(cn, nomeTabela);
        
        try (PreparedStatement stmt = cn.prepareStatement(generate.delete(nomeTabela, id))) {
            stmt.executeUpdate();
            System.out.println("Delete feito com sucesso!\n O sql gerado foi: "
                    + generate.delete(nomeTabela, id));

            cn.commit();
        } catch (SQLException ex) {
            System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            System.out.println("Falha ao realizar o delete!\n O sql gerado foi:"
                    + generate.delete(nomeTabela, id));
            try {
                cn.rollback();
            } catch (SQLException ex1) {
                System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex1);
            } finally {
                try {
                    cn.close();
                } catch (SQLException ex1) {
                    System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex1);
                }
            }
        }
    }

    public static List select(Connection cn, String nomeTabela) {
        validation.invalidConnection(cn);
        validation.invalidTable(cn, nomeTabela);
        
        List<Object[]> data = new ArrayList();

        try (ResultSet result = generate.resultSet(cn, generate.orderById(generate.selectAll(nomeTabela)))) {
            Map<String, Integer> typeName = metadata.typeName(result);
            int columnsNum = metadata.columnsNum(result);

            while (result.next()) {
                Object[] row = new Object[columnsNum];
                int i = 0;
                for (Entry<String, Integer> entry : typeName.entrySet()) {
                    Object value = translate.types(result, entry.getKey(), entry.getValue());
                    row[i] = value;
                    i++;
                }
                
                data.add(row);
            }

            return data;
        } catch (SQLException ex) {
            System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            try {
                cn.close();
            } catch (SQLException ex) {
                System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
        
        return null;
    }

    public static List select(Connection cn, String nomeTabela, String colunas) {
        validation.invalidConnection(cn);
        validation.invalidTable(cn, nomeTabela);
        String[] vColumns = colunas.trim().split(",");
        validation.invalidColumns(cn, nomeTabela, vColumns);
        
        List<Object[]> data = new ArrayList();
        System.out.println(generate.orderById(generate.select(nomeTabela, colunas)));

        try (ResultSet result = generate.resultSet(cn, generate.orderById(generate.select(nomeTabela, colunas)))) {
            Map<String, Integer> typeName = metadata.typeName(result);
            int columnsNum = metadata.columnsNum(result);

            while (result.next()) {
                Object[] row = new Object[columnsNum];
                int i = 0;
                for (Entry<String, Integer> entry : typeName.entrySet()) {
                    Object value = translate.types(result, entry.getKey(), entry.getValue());
                    row[i] = value;
                    i++;
                }
                data.add(row);
            }

            return data;
        } catch (SQLException ex) {
            System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            try {
                cn.close();
            } catch (SQLException ex) {
                System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
        
        return null;
    }

    public static List select(Connection cn, String nomeTabela, String colunas, String orderBy) {
        validation.invalidConnection(cn);
        validation.invalidTable(cn, nomeTabela);
        String[] vColumns = colunas.trim().split(",");
        validation.invalidColumns(cn, nomeTabela, vColumns);
        
        
        List<Object[]> data = new ArrayList();

        try (ResultSet result = generate.resultSet(cn, generate.orderBy(generate.select(nomeTabela, colunas), orderBy))) {
            Map<String, Integer> typeName = metadata.typeName(result);
            int columnsNum = metadata.columnsNum(result);

            while (result.next()) {
                Object[] row = new Object[columnsNum];
                int i = 0;
                for (Entry<String, Integer> entry : typeName.entrySet()) {
                    Object value = translate.types(result, entry.getKey(), entry.getValue());
                    row[i] = value;
                    i++;
                }
                data.add(row);
            }

            return data;
        } catch (SQLException ex) {
            System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            try {
                cn.close();
            } catch (SQLException ex) {
                System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }

        return null;
    }
    
    public static List selectWhere(Connection cn, String nomeTabela, String where) {
        validation.invalidConnection(cn);
        validation.invalidTable(cn, nomeTabela);
        
        List<Object[]> data = new ArrayList();

        try (ResultSet result = generate.resultSet(cn,
                generate.orderById(generate.selectWhere(nomeTabela, "*", where)))) {
            Map<String, Integer> typeName = metadata.typeName(result);
            int columnsNum = metadata.columnsNum(result);

            while (result.next()) {
                Object[] row = new Object[columnsNum];
                int i = 0;
                for (Entry<String, Integer> entry : typeName.entrySet()) {
                    Object value = translate.types(result, entry.getKey(), entry.getValue());
                    row[i] = value;
                    i++;
                }
                data.add(row);
            }

            return data;
        } catch (SQLException ex) {
            System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            try {
                cn.close();
            } catch (SQLException ex) {
                System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
        
        return null;
    }

    public static List selectWhere(Connection cn, String nomeTabela, String colunas, String where) {
        validation.invalidConnection(cn);
        validation.invalidTable(cn, nomeTabela);
        String[] vColumns = colunas.trim().split(",");
        validation.invalidColumns(cn, nomeTabela, vColumns);
        
        List<Object[]> data = new ArrayList();

        try (ResultSet result = generate.resultSet(cn,
                generate.orderById(generate.selectWhere(nomeTabela, colunas, where)))) {
            Map<String, Integer> typeName = metadata.typeName(result);
            int columnsNum = metadata.columnsNum(result);

            while (result.next()) {
                Object[] row = new Object[columnsNum];
                int i = 0;
                for (Entry<String, Integer> entry : typeName.entrySet()) {
                    Object value = translate.types(result, entry.getKey(), entry.getValue());
                    row[i] = value;
                    i++;
                }
                data.add(row);
            }

            return data;
        } catch (SQLException ex) {
            System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            try {
                cn.close();
            } catch (SQLException ex) {
                System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
        
        return null;
    }

    public static List selectWhere(Connection cn, String nomeTabela, String colunas, String where, String orderBy) {
        validation.invalidConnection(cn);
        validation.invalidTable(cn, nomeTabela);
        String[] vColumns = colunas.trim().split(",");
        validation.invalidColumns(cn, nomeTabela, vColumns);
        
        List<Object[]> data = new ArrayList();

        try (ResultSet result = generate.resultSet(cn,
                generate.orderBy(generate.selectWhere(nomeTabela, colunas, where), orderBy))) {
            Map<String, Integer> typeName = metadata.typeName(result);
            int columnsNum = metadata.columnsNum(result);

            while (result.next()) {
                Object[] row = new Object[columnsNum];
                int i = 0;
                for (Entry<String, Integer> entry : typeName.entrySet()) {
                    Object value = translate.types(result, entry.getKey(), entry.getValue());
                    row[i] = value;
                    i++;
                }
                data.add(row);
            }

            return data;
        } catch (SQLException ex) {
            System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            try {
                cn.close();
            } catch (SQLException ex) {
                System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
        
        return null;
    }

    public static void update(Connection cn, String nomeTabela, Object[] valores, int id) {
        validation.invalidId(id);
        validation.invalidConnection(cn);
        validation.invalidTable(cn, nomeTabela);
        validation.invalidValues(cn, nomeTabela, valores);
        
        try (PreparedStatement stmt = cn.prepareStatement(generate.update(cn, nomeTabela));
                ResultSet result = generate.resultSet(cn, generate.selectAll(nomeTabela))) {
            int[] types = metadata.columnsType(result);
            
            for(int i = 0; i < valores.length; i++){
                stmt.setObject(i + 1, valores[i], types[i + 1]);
            }
            
            stmt.setObject(types.length, id, Types.INTEGER);
            
            stmt.executeUpdate();
            System.out.println("Update feito com sucesso!\n O sql gerado foi: "
                    + generate.update(cn, nomeTabela));

            cn.commit();
        } catch (SQLException ex) {
            System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            try {
                cn.rollback();
            } catch (SQLException ex1) {
                System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex1);
            }
        } finally {
            try {
                cn.close();
            } catch (SQLException ex) {
                System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
    }

    public static void update(Connection cn, String nomeTabela, String[] colunas, Object[] valores, int id) {
        validation.invalidId(id);
        validation.invalidConnection(cn);
        validation.invalidTable(cn, nomeTabela);
        validation.invalidColumns(cn, nomeTabela, colunas);
        validation.invalidValues(colunas, valores);
        
        try (PreparedStatement stmt = cn.prepareStatement(generate.update(nomeTabela, colunas))) {
            for(int i = 0; i < colunas.length; i++){
                stmt.setObject(i + 1, valores[i]);
            }
            
            stmt.setObject(colunas.length + 1, id);
            
            stmt.executeUpdate();
            System.out.println("Update feito com sucesso!\n O sql gerado foi: "
                    + generate.update(nomeTabela, colunas));

            cn.commit();
        } catch (SQLException ex) {
            System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            try {
                cn.rollback();
            } catch (SQLException ex1) {
                System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex1);
            }
        } finally {
            try {
                cn.close();
            } catch (SQLException ex) {
                System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
    }

    public static List innerJoin(Connection cn, String colunas, String TabelaEsquerda, String TabelaDireita, String orderBy) {
        validation.invalidConnection(cn);
        validation.invalidTable(cn, TabelaEsquerda);
        validation.invalidTable(cn, TabelaDireita);
        
        List<Object[]> data = new ArrayList();
        //System.out.println(generate.orderById(generate.simpleInnerJoin(cn, colunas, TabelaEsquerda, TabelaDireita)));

        if (orderBy == null) {
            try (ResultSet result = generate.resultSet(cn, generate.simpleInnerJoin(cn, colunas, TabelaEsquerda, TabelaDireita))) {
                Map<String, Integer> typeName = metadata.typeName(result);
                int columnsNum = metadata.columnsNum(result);

                while (result.next()) {
                    Object[] row = new Object[columnsNum];
                    int i = 0;
                    for (Entry<String, Integer> entry : typeName.entrySet()) {
                        Object value = translate.types(result, entry.getKey(), entry.getValue());
                        row[i] = value;
                        i++;
                    }
                    data.add(row);
                }

                return data;
            } catch (SQLException ex) {
                System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                return null;
            } finally {
                try {
                    cn.close();
                } catch (SQLException ex) {
                    System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }
        } else {
            try (ResultSet result = generate.resultSet(cn, generate.orderBy(generate.simpleInnerJoin(cn, colunas, TabelaEsquerda, TabelaDireita), orderBy))) {
                Map<String, Integer> typeName = metadata.typeName(result);
                int columnsNum = metadata.columnsNum(result);

                while (result.next()) {
                    Object[] row = new Object[columnsNum];
                    int i = 0;
                    for (Entry<String, Integer> entry : typeName.entrySet()) {
                        Object value = translate.types(result, entry.getKey(), entry.getValue());
                        row[i] = value;
                        i++;
                    }
                    data.add(row);
                }

                return data;
            } catch (SQLException ex) {
                System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                return null;
            } finally {
                try {
                    cn.close();
                } catch (SQLException ex) {
                    System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }
        }

    }

    public static List innerJoin(Connection cn, String colunas, String Tabela1, String Tabela2, String Tabela3, String orderBy) {
        validation.invalidConnection(cn);
        validation.invalidTable(cn, Tabela1);
        validation.invalidTable(cn, Tabela2);
        validation.invalidTable(cn, Tabela3);
        
        List<Object[]> data = new ArrayList();
        //System.out.println(generate.orderById(generate.multiInnerJoin(cn, colunas, Tabela1, Tabela2, Tabela3)));

        if (orderBy == null) {
            try (ResultSet result = generate.resultSet(cn, generate.multiInnerJoin(cn, colunas, Tabela1, Tabela2, Tabela3))) {
                Map<String, Integer> typeName = metadata.typeName(result);
                int columnsNum = metadata.columnsNum(result);

                while (result.next()) {
                    Object[] row = new Object[columnsNum];
                    int i = 0;
                    for (Entry<String, Integer> entry : typeName.entrySet()) {
                        Object value = translate.types(result, entry.getKey(), entry.getValue());
                        row[i] = value;
                        i++;
                    }
                    data.add(row);
                }

                return data;
            } catch (SQLException ex) {
                System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                return null;
            } finally {
                try {
                    cn.close();
                } catch (SQLException ex) {
                    System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }
        } else {
            try (ResultSet result = generate.resultSet(cn, generate.orderBy(generate.multiInnerJoin(cn, colunas, Tabela1, Tabela2, Tabela3), orderBy))) {
                Map<String, Integer> typeName = metadata.typeName(result);
                int columnsNum = metadata.columnsNum(result);

                while (result.next()) {
                    Object[] row = new Object[columnsNum];
                    int i = 0;
                    for (Entry<String, Integer> entry : typeName.entrySet()) {
                        Object value = translate.types(result, entry.getKey(), entry.getValue());
                        row[i] = value;
                        i++;
                    }
                    data.add(row);
                }

                return data;
            } catch (SQLException ex) {
                System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                return null;
            } finally {
                try {
                    cn.close();
                } catch (SQLException ex) {
                    System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }
        }

    }

    //--------------------------------------------------------------------------
    private static class generate {

        public static String values(Object[] valores, ResultSet result) {
            String values = "";

            if (valores != null) {
                //para values de insert (NÃO É MAIS UTILIZADO)
                for (int i = 0; i < valores.length; i++) {
                    if (valores[i] instanceof String) {
                        valores[i] = "'" + valores[i] + "'";
                    }
                }

                values = Arrays.toString(valores)
                        .replace("[", "(")
                        .replace("]", ")");
            } else {
                //para colunas das tabelas (sem o id)
                List<String> names = new ArrayList(
                        Arrays.asList(metadata.columnsName(result)));
                names.removeFirst();
                values = Arrays.toString(names.toArray())
                        .replace("[", "(")
                        .replace("]", ")");
            }

            return values;
        }

        public static String selectAll(String nomeTabela) {
            return "SELECT * FROM %s".formatted(nomeTabela.trim());
        }

        public static String select(String nomeTabela, String colunas) {
            return "SELECT %s FROM %s".formatted(colunas, nomeTabela.trim());
        }
               
        public static String selectWhere(String nomeTabela, String colunas, String where) {
            return "SELECT %s FROM %s WHERE %s".formatted(colunas, nomeTabela, where);
        }

        public static String insert(Connection cn, String nomeTabela, int numParameter) {
            try (PreparedStatement stmt = cn.prepareStatement(selectAll(nomeTabela)); ResultSet result = stmt.executeQuery()) {
                return "INSERT INTO %s %s VALUES %s;".formatted(nomeTabela.trim(),
                        values(null, result),
                        parameterInsert(numParameter));
            } catch (SQLException ex) {
                System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                return "";
            }
        }
        
        public static String parameterInsert(int numParameter){
            String parameter = "";
       
            if (numParameter > 1) {
                for (int i = 0; i < numParameter; i++) {
                    if (i == 0) {
                        parameter += "(?,";
                    } else if (i == numParameter - 1) {
                        parameter += "?)";
                    } else {
                        parameter += "?,";
                    }
                }
            } else {
                parameter = "(?)";
            }
            return parameter;
        }

        public static String delete(String nomeTabela, int id) {
            return "DELETE FROM %s WHERE ID = %d".formatted(nomeTabela.trim(), id);
        }

        public static String update(Connection cn, String nomeTabela) {
            String sql = "UPDATE %s SET ".formatted(nomeTabela.trim());

            try (ResultSet result = resultSet(cn, selectAll(nomeTabela.trim()))) {
                List<String> columns = new ArrayList(Arrays.asList(metadata.columnsName(result)));
                columns.removeFirst();
                
                if(columns.size() == 1){
                    return "UPDATE %s SET %s = ? WHERE id = ?;".formatted(nomeTabela, columns.get(0));
                }

                for (int i = 0; i < columns.size(); i++) {
                    if(i == columns.size() - 1){
                        sql += parameterUpdate(columns.get(i), true);
                    } else{
                        sql += parameterUpdate(columns.get(i), false);
                    }
                }

                sql += "WHERE id = ?;";

                return sql;
            } catch (SQLException ex) {
                System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
            
            return "";
        }

        public static String update(String nomeTabela, String[] colunas) {
            String sql = "UPDATE %s SET ".formatted(nomeTabela.trim());
            
            if(colunas.length == 1){
                    return "UPDATE %s SET %s = ? WHERE id = ?;".formatted(nomeTabela, colunas[0]);
                }
            
            for (int i = 0; i < colunas.length; i++) {
                if (i == colunas.length - 1) {
                    sql += parameterUpdate(colunas[i], true);
                } else {
                    sql += parameterUpdate(colunas[i], false);
                }
            }
            
            sql += "WHERE id = ?;";

            return sql;
        }
        
        public static String parameterUpdate(String column, boolean isLast){
            if(!isLast){
                return column + " = ?, ";
            } else{
                return column + " = ? ";
            }
        }

        //até que serve pra algo, mas precisa ter cuidado pra fechar esse resultset em algum momento
        public static ResultSet resultSet(Connection cn, String sql) {
            try {
                ResultSet result = cn.prepareStatement(sql).executeQuery();
                return result;
            } catch (SQLException ex) {
                System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                return null;
            }

        }

        public static String simpleInnerJoin(Connection cn, String colunas, String TabelaEsquerda, String TabelaDireita) {
            return "SELECT %s FROM %s JOIN %s ON %s"
                    .formatted(colunas, TabelaEsquerda, TabelaDireita, correctFK(cn, TabelaEsquerda, TabelaDireita));
        }
        
        public static String multiInnerJoin(Connection cn, String colunas, String Tabela1, String Tabela2, String Tabela3){
            String on1 = correctFK(cn, Tabela1, Tabela2);
            String on2 = correctFK(cn, Tabela1, Tabela3);
            
            return "SELECT %s FROM %s JOIN %s ON %s JOIN %s ON %s".formatted(colunas, Tabela1, Tabela2, on1, Tabela3, on2);
        }

        public static String orderBy(String select, String orderBy) {
            return "%s ORDER BY %s".formatted(select, orderBy);
        }
        
        public static String orderById(String select) {
            return "%s ORDER BY id ASC".formatted(select);
        }
        
        public static String correctFK(Connection cn, String tableLeft, String tableRight) {
            Map<String, String> fKs = metadata.foreignKeys(cn, tableRight);
            String on = "";
            boolean control = false;

            for (Entry<String, String> entry : fKs.entrySet()) {
                if (entry.getKey().equals(tableLeft)) {
                    on = tableLeft + "." + entry.getValue();
                    control = true;
                }
            }
            
            if(!control) throw new IllegalArgumentException("Relaçao inexistente." 
                + "\n\nUma (ou mais) das tabelas passadas como parametro nao possui relaçao de chaves com a tabela de referencia.");

            try (ResultSet result = cn.getMetaData().getExportedKeys(null, null, tableRight)) {
                result.next();
                on += " = " + tableRight + "." + result.getString("PKCOLUMN_NAME");
                System.out.println(fKs);
                return on;
            } catch (SQLException ex) {
                System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
            
            return null;
        }

    }

    //--------------------------------------------------------------------------
    private static class metadata {

        public static int columnsNum(ResultSet result) {

            try {
                ResultSetMetaData metaData = result.getMetaData();
                int numColumns = metaData.getColumnCount();
                return numColumns;
            } catch (SQLException ex) {
                System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                System.out.println("Erro ao puxar o número de colunas!");
            }
            
            return -1;
        }

        public static String[] columnsName(ResultSet result) {
            int numColumns = columnsNum(result);

            //se for igual a -1 é pq deu erro
            if (numColumns != -1) {
                try {
                    ResultSetMetaData metaData = result.getMetaData();
                    String[] names = new String[numColumns];

                    for (int i = 0; i < names.length; i++) {
                        names[i] = metaData.getColumnName(i + 1);
                    }
                    return names;
                } catch (SQLException ex) {
                    System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    System.out.println("Erro ao puxar os nomes das colunas!");
                }
            }

            return new String[]{""};
        }

        public static int[] columnsType(ResultSet result) {
            int numColumns = columnsNum(result);

            if (numColumns != -1) {
                try {
                    ResultSetMetaData metadata = result.getMetaData();
                    int[] types = new int[numColumns];

                    for (int i = 0; i < types.length; i++) {
                        types[i] = metadata.getColumnType(i + 1);
                    }

                    return types;
                } catch (SQLException ex) {
                    System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    System.out.println("Erro ao puxar os tipos das colunas!");
                }
            }

            return new int[]{-1};
        }

        public static Map typeName(ResultSet result) {
            String[] names = columnsName(result);
            int[] types = columnsType(result);

            Map<String, Integer> typeName = new LinkedHashMap();

            for (int i = 0; i < names.length; i++) {
                typeName.put(names[i], types[i]);
            }

            return typeName;
        }

        public static Map foreignKeys(Connection cn, String table) {
            try (ResultSet result = cn.getMetaData().getExportedKeys(null, null, table)) {
                Map<String, String> fKs = new LinkedHashMap();

                while (result.next()) {
                    fKs.put(result.getString("FKTABLE_NAME"), result.getString("FKCOLUMN_NAME"));
                }

                return fKs;
            } catch (SQLException ex) {
                System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                return null;
            }
        }

    }

    //--------------------------------------------------------------------------
    private static class translate {

        public static Object types(ResultSet result, String columnName, int type) {
            Object valueT = null;
            System.out.println("Coluna: " + columnName + "\nTipo: " + type);
            
            if (columnName.equals("id")) {
                try {
                    valueT = result.getLong(columnName);
                    return valueT;
                } catch (SQLException ex) {
                    System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }
            
            switch (type) {
                case Types.INTEGER:
                case Types.SMALLINT:
                case Types.TINYINT:
                    try {
                        valueT = result.getInt(columnName);
                    } catch (SQLException ex) {
                        System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                    break;
                case Types.LONGNVARCHAR:
                case Types.VARCHAR:
                case Types.LONGVARCHAR:
                    try {
                        valueT = result.getString(columnName);
                    } catch (SQLException ex) {
                        System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                    break;
                case Types.DOUBLE:
                case Types.NUMERIC:
                case Types.FLOAT:
                case Types.REAL:
                    try {
                        valueT = result.getDouble(columnName);
                    } catch (SQLException ex) {
                        System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                    break;
                default:
                    try {
                        valueT = result.getObject(columnName);
                    } catch (SQLException ex) {
                        System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
            }

            return valueT;
        }

    }
    
    //--------------------------------------------------------------------------
    private static class validation{
        
        public static boolean invalidValues(Connection cn, String nomeTabela, Object[] values){
            boolean control = false;
            
            try(PreparedStatement stmt = cn.prepareStatement(generate.selectAll(nomeTabela));
                    ResultSet result = stmt.executeQuery()){
                String[] colunas = metadata.columnsName(result);
                
                if(values.length != colunas.length - 1){
                    System.out.println("Numero de colunas: " + (colunas.length - 1) 
                            + "\nNumero de values: " + values.length);
                    control = true;
                }
                
            } catch (SQLException ex) {
                System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
            
            if(control){
                throw new IllegalArgumentException("Array menor ou maior que o numero de colunas."
                            + "\n\nO Array passado como parametro tem mais (ou menos) valores do que o numero de colunas possiveis para se inserir.");
            }
            
            return control;
        }
        
        public static boolean invalidValues(String[] columns, Object[] values) {
            boolean control = false;

            if (values.length != columns.length) {
                control = true;
            }
            
            if (control) {
                throw new IllegalArgumentException("Array menor ou maior que o numero de colunas."
                        + "\n\nO Array passado como parametro tem mais (ou menos) valores do que o numero de colunas possiveis para se inserir.");
            }

            return control;
        }
        
        public static boolean invalidTable(Connection cn, String nomeTabela){
            boolean control = true;
            
            try {
                DatabaseMetaData db = cn.getMetaData();
                ResultSet result = db.getTables(null, null, null, new String[]{"TABLE"});
                
                while(result.next()){
                    if(result.getString("TABLE_NAME").equals(nomeTabela)) control = false;
                }
                
                result.close();
            } catch (SQLException ex) {
                System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
            
            if(control){
                throw new IllegalArgumentException("Tabela inexistente."
                            + "\n\nUma (ou mais) das tabelas passadas como parametro nao corresponde a nenhuma tabela no banco de dados.");
            }
            
            return control;
        }
        
        public static boolean invalidColumns(Connection cn, String nomeTabela, String[] colunas){
            try(PreparedStatement stmt = cn.prepareStatement(generate.selectAll(nomeTabela));
                    ResultSet result = stmt.executeQuery()){
                String[] names = metadata.columnsName(result);
                
                for(String coluna: colunas){
                    boolean control = true;
                    
                    for(String name : names){
                        if(coluna.equals(name)) control = false;
                    }
                    
                    if(control) throw new IllegalArgumentException("Coluna inexistente."
                        + "\n\nUma ou mais das colunas passadas como parametro nao existem nessa tabela.");
                }
                
            } catch (SQLException ex) {
                System.getLogger(sql.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
            
            return false;
        }
     
        public static boolean invalidConnection(Connection cn) {
            boolean control = false;

            if (cn == null) {
                control = true;
                throw new IllegalArgumentException("Conexao nula."
                        + "\n\nA conexao passada como parametro esta vazia, provavelmente por um erro ao se conectar com o banco de dados.");
            }

            return control;

        }

        public static boolean invalidId(int id) {
            boolean control = false;

            if (id < 0) {
                control = true;
                throw new IllegalArgumentException("Id invalido."
                        + "\n\nO id passado como parametro e menor que zero.\nDica: evite utilizar 0 como id.");
            }

            return control;
        }
        
    }
}
