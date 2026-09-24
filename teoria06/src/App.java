import java.sql.*;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        String url;
        url = "jdbc:postgresql://aws-0-us-west-2.pooler.supabase.com:6543/postgres?user=postgres.kmgceisasvvupvssughl&password=HS74@#hs28101997";

        Connection conexao;
        conexao = ConnectionFactory.getConnection(url);

        ContaDao dao;
        dao = new ContaDao(conexao);
        dao.criar(new Conta(300, 9.99));


    }
}
