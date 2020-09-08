/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import lata.classes.Acesso;

/**
 *
 * @author Gabriel Lopes
 */
public class AcessoDAO {

    Connection con;

    public AcessoDAO() throws SQLException {
        con = ConnectionFactory.getConnection();
    }

    public void create(Acesso u) {

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tbacesso (nome,sexo,dataAni,celular,email,senha) VALUES (?,?,?,?,?,?)");
            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getSexo());
            stmt.setString(3, u.getDataAni());
            stmt.setString(4, u.getCelular());
            stmt.setString(5, u.getEmail());
            stmt.setString(6, u.getSenha());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Usuário Salvo com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }

    public boolean checkLogin(String email, String senha) {

        PreparedStatement stmt = null;
        ResultSet rs = null;

        boolean check = false;

        try {

            stmt = con.prepareStatement("SELECT * FROM tbacesso WHERE email = ? and senha = ?");
            stmt.setString(1, email);
            stmt.setString(2, senha);

            rs = stmt.executeQuery();

            if (rs.next()) {

                check = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(AcessoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return check;

    }

    public List<Acesso> read() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Acesso> acessos = new ArrayList<>();
        try {
            stmt = con.prepareStatement( "SELECT * FROM tbacesso");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Acesso acesso = new Acesso();
                acesso.setId(rs.getInt("id"));
                acesso.setNome(rs.getString("nome"));
                acesso.setSexo(rs.getString("sexo"));
                acesso.setDataAni(rs.getString("dataAni"));
                acesso.setCelular(rs.getString("celular"));
                acesso.setEmail(rs.getString("email"));
                acesso.setSenha(rs.getString("senha"));

                acessos.add(acesso);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcessoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return acessos;
    }
    public List <Acesso> readForDesc(String desc) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List <Acesso> produtos = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM tbacesso WHERE nome LIKE ?");
            stmt.setString(1, "%"+desc +"%");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Acesso acesso = new Acesso();
                acesso.setId(rs.getInt("id"));
                acesso.setNome(rs.getString("nome"));
                acesso.setSexo(rs.getString("sexo"));
                acesso.setDataAni(rs.getString("dataAni"));
                acesso.setCelular(rs.getString("celular"));                                                
                acesso.setEmail(rs.getString("email"));
                acesso.setSenha(rs.getString("senha"));
                produtos.add(acesso);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcessoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return produtos;

    }

    public void update(Acesso a) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement( "UPDATE tbacesso SET nome =  ?, sexo =  ?, dataAni =  ?, celular = ?, email = ?, senha = ? WHERE  id =  ?");
            stmt.setString(1, a.getNome());
            stmt.setString(2, a.getSexo());
            stmt.setString(3, a.getDataAni());
            stmt.setString(4, a.getCelular());
            stmt.setString(5, a.getEmail());
            stmt.setString(6, a.getSenha());
            stmt.setInt(7, a.getId());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null,"Atualizado com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,  "Erro ao atualizar: "+ex);
    } finally {
    ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void delete(Acesso a) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM tbacesso WHERE id =  ?");
            stmt.setInt(1, a.getId());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null,  "Excluido com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir: " +ex
          
            );
    } finally {
    ConnectionFactory.closeConnection(con, stmt);
        }
    }

}
