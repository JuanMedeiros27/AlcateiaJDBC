# AlcateiaJDBC
Uma biblioteca Java com métodos úteis para quem quer desenvolver com JDBC.

Esse é um Trabalho de Conclusão de Curso para o curso de Técnico de Informática para Internet.

Versão: 1.0

Instalação: 
    Será feita através do JitPack.

	Método connect:
	    — — — — — — — — — — — — — — — — — — — — —
		Assinatura:
	            (String sistema, String nomedb, String user, String senha)
		— — — — — — — — — — — — — — — — — — — — —
		Parâmetros:
	            String sistema —> Uma String contendo o sistema de gerenciamento que você utiliza.
	            Sistemas Suportados: postgresql e mysql.
	            String nomedb —> Uma String contendo o nome do banco de dados que se deseja conectar.
	            String user/senha —> Uma String contendo o seu nome de usuário e senha do sistema.
		— — — — — — — — — — — — — — — — — — — — —
		Exceptions:
	            IllegalArgumentException:
	                Quando o sistema passado como parâmetro não for suportado pela biblioteca.


Métodos: — — —> sql

    Insert: — — —> Void
            — — — — — — — — — — — — — — — — — — — — —
            Assinatura:
                (Connection cn, String nomeTabela, Object[] valores).
            — — — — — — — — — — — — — — — — — — — — —
            Parâmetros:
                Connection cn —> O objeto Connection contendo a conexão com um banco de dados. (Objeto não pode ser nulo e nem ter sido fechado)
                String nomeTabela —> Uma String que contenha o nome da tabela que se deseja realizar o insert.
                Object[] valores —> Um Array de Objetos contendo os valores que serão inseridos, os valores devem estar NA ORDEM das colunas da respectiva tabela.
            — — — — — — — — — — — — — — — — — — — — —
            Exceptions:
                IllegalArgumentException:
                    Quando a conexão for nula ou estiver fechada;
                    Quando o nome da tabela não existir no banco de dados referenciado;
                    Quando tiver mais (ou menos) valores no “Object[] valores” do que colunas para se inserir.
            — — — — — — — — — — — — — — — — — — — — —
            Obs: caso tenha problemas tentando inserir algum valor, tente converter ele para uma String antes de chamar o método.


    Delete: — — —> Void
            — — — — — — — — — — — — — — — — — — — — —
            Assinatura:
                (Connection cn, String nomeTabela, int id);
            — — — — — — — — — — — — — — — — — — — — —
            Parâmetros:
                Connection cn —> O objeto Connection contendo a conexão com um banco de dados. (Objeto não pode ser nulo e nem ter sido fechado)
                String nomeTabela —> Uma String que contenha o nome da tabela que se deseja realizar o delete.
                int id —> O id do registro específico que você deseja deletar.
            — — — — — — — — — — — — — — — — — — — — —
            Exceptions:
                IllegalArgumentException:
                    Quando a conexão for nula ou estiver fechada;
                    Quando o nome da tabela não existir no banco de dados referenciado;
                    Quando o “int id” for menor que zero.


    Update: — — —> Void
            — — — — — — — — — — — — — — — — — — — — —
            Assinaturas:
                (Connection cn, String nomeTabela, Object[] valores, int id)
                (Connection cn, String nomeTabela, String[] colunas, Object[] valores, int id)
            — — — — — — — — — — — — — — — — — — — — —
            Parâmetros:
                Connection cn —> O objeto Connection contendo a conexão com um banco de dados. (Não pode ser nulo e nem ter sido fechado)
                String nomeTabela —> Uma String que contenha o nome da tabela que se deseja realizar o update.
                String[] colunas —> Um Array de String contendo o nome das colunas que se deseja atualizar.
                Object[] valores —> Um Array de Objetos contendo os valores que serão atualizados, os valores devem estar NA ORDEM das colunas da respectiva tabela ou das colunas passadas como parâmetro.
                int id —> O id do registro específico que você deseja atualizar.
            — — — — — — — — — — — — — — — — — — — — —
            Exceptions:
                IllegalArgumentException:
                    Quando a conexão for nula ou estiver fechada;
                    Quando o nome da tabela não existir no banco de dados referenciado;
                    Quando tiver mais (ou menos) valores no “Object[] valores” do que colunas para se inserir;
                    Quando uma (ou mais) das colunas passadas como parâmetro não existir no banco de dados referenciado;
                    Quando o “int id” for menor que zero.


    Select: — — —> List<Object[]>
            — — — — — — — — — — — — — — — — — — — — —
            Assinaturas:
                (Connection cn, String nomeTabela)
                (Connection cn, String nomeTabela, String colunas)
                (Connection cn, String nomeTabela, String colunas, String orderBy)
            — — — — — — — — — — — — — — — — — — — — —
            Parâmetros:
                Connection cn —> O objeto Connection contendo a conexão com um banco de dados. (Não pode ser nulo e nem ter sido fechado)
                String nomeTabela —> Uma String que contenha o nome da tabela que se deseja realizar o select.
                String colunas —> Uma String contendo o nome das colunas que se deseja selecionar, a sintaxe deve ser igual a utilizada em SQL. Ex: String colunas =  “coluna1, coluna2…”;
                String orderBy —> Uma String que contenha a forma como o select irá ordenar o resultado, a sintaxe deve ser igual a utilizada em SQL. Ex: String orderBy = “id ASC”; 
            — — — — — — — — — — — — — — — — — — — — —
            Exceptions:
                IllegalArgumentException:
                    Quando a conexão for nula ou estiver fechada;
                    Quando o nome da tabela não existir no banco de dados referenciado;
                    Quando uma (ou mais) das colunas passadas como parâmetro não existir no banco de dados referenciado.


    SelectWhere: — — —> List<Object[]>
            — — — — — — — — — — — — — — — — — — — — —
            Assinaturas:
                (Connection cn, String nomeTabela, String where)
                (Connection cn, String nomeTabela, String colunas, String where)
                (Connection cn, String nomeTabela, String colunas, String where, String orderBy)
            — — — — — — — — — — — — — — — — — — — — —
            Parâmetros:
                Connection cn —> O objeto Connection contendo a conexão com um banco de dados. (Não pode ser nulo e nem ter sido fechado)
                String nomeTabela —> Uma String que contenha o nome da tabela que se deseja realizar o select.
                String colunas —> Uma String contendo o nome das colunas que se deseja selecionar, a sintaxe deve ser igual a utilizada em SQL. 
                Ex: String colunas =  “coluna1, coluna2…”;
                String where —> Uma String contendo a forma como o conteúdo será  filtrado, a sintaxe deve ser igual a utilizada em SQL. Ex: 
                String where =  “nome = ‘fulano’”;
                String orderBy —> Uma String que contenha a forma como o select irá ordenar o resultado, a sintaxe deve ser igual a utilizada em SQL. Ex: String orderBy = “id ASC”; 
            — — — — — — — — — — — — — — — — — — — — —
            Exceptions:
                IllegalArgumentException:
                    Quando a conexão for nula ou estiver fechada;
                    Quando o nome da tabela não existir no banco de dados referenciado;
                    Quando uma (ou mais) das colunas passadas como parâmetro não existir no banco de dados referenciado.


    innerJoin: — — —> List<Object[]>
            — — — — — — — — — — — — — — — — — — — — —
            Assinaturas:
                (Connection cn, String colunas, String TabelaEsquerda, String TabelaDireita, String orderBy)
                (Connection cn, String colunas, String Tabela1, String Tabela2, String Tabela3, String orderBy)
            — — — — — — — — — — — — — — — — — — — — —
            Parâmetros:
                Connection cn —> O objeto Connection contendo a conexão com um banco de dados. (Objeto não pode ser nulo e nem ter sido fechado)
                String colunas —> Uma String contendo o nome das colunas que se deseja selecionar, a sintaxe deve ser igual a utilizada em SQL. Ex: String colunas =  “Tabela1.coluna1, Tabela2.coluna2…”;
                String TabelaEsquerda/TabelaDireita —> Utilizada para relações 1 para n com 2 tabelas, a TabelaEsquerda(n) é a tabela que possui a Foreign Key, enquanto a TabelaDireita(1) é a tabela referenciada.
                String Tabela1/Tabela2/Tabela3 —> Utilizada para relações n para n com 2 tabelas, a Tabela1 é a tabela que possui as Foreign Keys, enquanto as Tabela2/Tabela3 são as tabelas referenciadas.
                String orderBy —> Uma String que contenha a forma como o select irá ordenar o resultado, a sintaxe deve ser igual a utilizada em SQL. Ex: String orderBy = “id ASC”; 
            — — — — — — — — — — — — — — — — — — — — —
            Exceptions:
                IllegalArgumentException:
                    Quando a conexão for nula ou estiver fechada;
                    Quando o nome de uma ou mais tabelas não existir no banco de dados referenciado;

	Objeto TabelaMetaData:
	     — — — — — — — — — — — — — — — — — — — — —
		Construtor:
	            (Connection cn, String nomeTabela)
		— — — — — — — — — — — — — — — — — — — — —
		Parâmetros:
	            Connection cn —> O objeto Connection contendo a conexão com um banco de dados. (Objeto não pode ser nulo e nem ter sido fechado)
	            String nomeTabela —> Uma String que contenha o nome da tabela que se deseja obter o metadata.
		— — — — — — — — — — — — — — — — — — — — —
		Atributos:
	            int numColunas —> Um número inteiro que representa o número de colunas que a tabela possui.
	            String[] nomeColunas —> Um Array de Strings que representa o nome das colunas que a tabela possui. (As suas posições seguem a mesma ordem de como estão dispostos na 					tabela)
	            String[] nomeTipoColunas —> Um Array de Strings que representa o nome do tipo das colunas que a tabela possui. (As suas posições seguem a mesma ordem de como estão 					dispostos na tabela)
	            int[] tipoColunas —> Um Array de Int que representa o tipo das colunas que a tabela possui, os valores correspondem ao java.sql.Types. (As suas posições seguem a mesma 				ordem de como estão dispostos na tabela)
	            Map<String, String> FKs —> Um Map que representa as tabelas e as colunas que são referenciadas pelas Foreign Keys da tabela passada como parâmetro.
		— — — — — — — — — — — — — — — — — — — — —
		Exceptions:
	            IllegalArgumentException:
	                Quando a conexão for nula ou estiver fechada;
	                Quando o nome da tabela não existir no banco de dados referenciado;
		— — — — — — — — — — — — — — — — — — — — —
		Obs: você pode obter esses valores de forma separada utilizando a classe estática metadata, passando como parâmetro a conexão e o nome da tabela ou passando um ResultSet.
