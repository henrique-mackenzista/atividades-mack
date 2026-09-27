import java.util.*;
import java.sql.*;

public class TitularDao implements ITitularDao {

    private PreparedStatement pstmCreate;
    private PreparedStatement pstmRead;
    private PreparedStatement pstmReadByNumero;
    private PreparedStatement pstmUpdateNome;
    private PreparedStatement pstmUpdateRG;
    private PreparedStatement pstmUpdateCPF;
    private PreparedStatement pstmDelete;

    public TitularDao(Connection conexao) throws SQLException {
        pstmCreate = conexao.prepareStatement("INSERT INTO titulares VALUES (?,?,?,?)");
        pstmRead = conexao.prepareStatement("SELECT * FROM titulares");
        pstmReadByNumero = conexao.prepareStatement("SELECT * FROM titulares WHERE nro_titular=?");
        pstmUpdateNome = conexao.prepareStatement("UPDATE titulares SET nome=? WHERE nro_titular=?");
        pstmUpdateRG = conexao.prepareStatement("UPDATE titulares SET rg=? WHERE nro_titular=?");
        pstmUpdateCPF = conexao.prepareStatement("UPDATE titulares SET cpf=? WHERE nro_titular=?");
        pstmDelete = conexao.prepareStatement("DELETE FROM titulares WHERE nro_titular=?");
    }

    @Override
    public boolean criar(Titular t) {
        boolean resposta = false;
        try {
            pstmCreate.setLong(1, t.nro_titular());
            pstmCreate.setString(2, t.nome());
            pstmCreate.setString(3, t.rg());
            pstmCreate.setString(4, t.cpf());
            int ret = pstmCreate.executeUpdate();
            resposta = (ret == 1);
        } catch(SQLException ex) {
            IO.println("Erro ao cadastrar um novo titular!");
        }
        return resposta;
    }

    @Override
    public List<Titular> lerTodos() {
        List<Titular> titulares = new ArrayList<>();
        try {
            ResultSet rs = pstmRead.executeQuery();
            while (rs.next()) {
                long nro = rs.getLong("nro_titular");
                String nome = rs.getString("nome");
                String rg = rs.getString("rg");
                String cpf = rs.getString("cpf");
                Titular t = new Titular(nro, nome, rg, cpf);
                titulares.add(t);
            }
        } catch (SQLException ex) {
            IO.println("Erro ao ler titulares!");
        }
        return titulares;
    }

    @Override
    public Titular buscarPeloNumeroTitular(long numero) {
        Titular t = null;
        try {
            pstmReadByNumero.setLong(1, numero);
            ResultSet rs = pstmReadByNumero.executeQuery();
            if (rs.next()) {
                long nro = rs.getLong("nro_titular");
                String nome = rs.getString("nome");
                String rg = rs.getString("rg");
                String cpf = rs.getString("cpf");
                t = new Titular(nro, nome, rg, cpf);
            }
        } catch (SQLException ex) {
            IO.println("Erro ao buscar um titular!");
        }
        return t;
    }

    @Override
    public boolean atualizarTitularNome(Titular t) {
        boolean resposta = false;
        try {
            pstmUpdateNome.setString(1, t.nome());
            pstmUpdateNome.setLong(2, t.nro_titular());
            int ret = pstmUpdateNome.executeUpdate();
            resposta = (ret == 1);
        } catch(SQLException ex) {
            IO.println("Erro ao atualizar o nome do titular!");
        }
        return resposta;
    }

    @Override
    public boolean atualizarTitularRG(Titular t) {
        boolean resposta = false;
        try {
            pstmUpdateRG.setString(1, t.rg());
            pstmUpdateRG.setLong(2, t.nro_titular());
            int ret = pstmUpdateRG.executeUpdate();
            resposta = (ret == 1);
        } catch(SQLException ex) {
            IO.println("Erro ao atualizar o RG do titular!");
        }
        return resposta;
    }

    @Override
    public boolean atualizarTitularCPF(Titular t) {
        boolean resposta = false;
        try {
            pstmUpdateCPF.setString(1, t.cpf());
            pstmUpdateCPF.setLong(2, t.nro_titular());
            int ret = pstmUpdateCPF.executeUpdate();
            resposta = (ret == 1);
        } catch(SQLException ex) {
            IO.println("Erro ao atualizar o CPF do titular!");
        }
        return resposta;
    }

    @Override
    public boolean apagarTitular(Titular t) {
        boolean resposta = false;
        try {
            pstmDelete.setLong(1, t.nro_titular());
            int ret = pstmDelete.executeUpdate();
            resposta = (ret == 1);
        } catch(SQLException ex) {
            IO.println("Erro ao apagar titular!");
        }
        return resposta;
    }
}