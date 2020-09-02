package lata.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import lata.classes.Lata;

/**
 *
 * @author glope
 */
public class LataDAO {

    Connection con;

    public LataDAO() throws SQLException {
        con = ConnectionFactory.getConnection();
    }

    public void create(Lata l) {

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tblata (marca,volume,ano,pais,altura,diametro,descricao) VALUES (?,?,?,?,?,?,?)");
            stmt.setString(1, l.getMarca());
            stmt.setDouble(2, l.getVolume());
            stmt.setInt(3, l.getAno());
            stmt.setString(4, l.getPais());
            stmt.setDouble(5, l.getAltura());
            stmt.setDouble(6, l.getDiametro());
            stmt.setString(7, l.getDescricao());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Lata salva com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }

    public List<Lata> read() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Lata> latas = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM tblata");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Lata lata = new Lata();
                lata.setId(rs.getInt("id"));
                lata.setMarca(rs.getString("marca"));
                lata.setVolume(rs.getDouble("volume"));
                lata.setAno(rs.getInt("ano"));
                lata.setPais(rs.getString("pais"));
                lata.setAltura(rs.getDouble("altura"));
                lata.setDiametro(rs.getDouble("diametro"));
                lata.setDescricao(rs.getString("descricao"));

                latas.add(lata);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcessoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return latas;
    }

    public List<Lata> readForDesc(String desc) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Lata> produtos = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM tblata WHERE nome LIKE ?");
            stmt.setString(1, "%" + desc + "%");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Lata lata = new Lata();
              
                lata.setId(rs.getInt("id"));
                lata.setMarca(rs.getString("marca"));
                lata.setVolume(rs.getDouble("volume"));
                lata.setAno(rs.getInt("ano"));
                lata.setPais(rs.getString("pais"));
                lata.setAltura(rs.getDouble("altura"));
                lata.setDiametro(rs.getDouble("diametro"));
                lata.setDescricao(rs.getString("descricao"));
                
                produtos.add(lata);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcessoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return produtos;

    }

    public void update(Lata l) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE tblata SET marca =  ?, volume =  ?, ano =  ?, pais = ?, altura = ?, diametro = ?, descricao = ? WHERE  id =  ?");
            stmt.setString(1, l.getMarca());
            stmt.setDouble(2, l.getVolume());
            stmt.setInt(3, l.getAno());
            stmt.setString(4, l.getPais());
            stmt.setDouble(5, l.getAltura());
            stmt.setDouble(6, l.getDiametro());
            stmt.setString(7, l.getDescricao());
            stmt.setInt(8, l.getId());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void delete(Lata l) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM tblata WHERE id =  ?");
            stmt.setInt(1, l.getId());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Excluido com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir: " + ex
            );
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

}
