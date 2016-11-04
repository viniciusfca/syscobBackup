/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cobranca.dao;

import br.com.cobranca.entity.Divida;
import br.com.cobranca.entity.Historico;
import br.com.cobranca.entity.Pessoa;
import br.com.cobranca.util.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Vinicius
 */
public class HistoricoDAO {

    private final String INSERT = "INSERT INTO Historico (idDivida, idUsuario, observacao, datacadastro, dataultimacobranca, idAnexo)"
            + " VALUES (?,?,?,CURDATE(),?,?)";

    /**
     * Metodo que adiciona um novo historico
     *
     * @param historico
     */
    public int adicionarHistorico(Historico historico) {
        int retorno = 0;
        Conexao conexao = new Conexao();
        PreparedStatement ps = null;

        try {
            ps = conexao.conectar().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, historico.getDivida().getId());
            ps.setInt(2, Util.getUsuarioLogado().getId());
            ps.setString(3, historico.getObservacao());
            ps.setDate(4, new java.sql.Date(new Date().getTime()));
            ps.setInt(5, 0);

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                retorno = rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e);
        } finally {
            conexao.desconectar();
        }

        return retorno;
    }

    /**
     * Metodo que retorna uma lista de históricos da divída
     *
     * @param idDivida
     * @return
     */
    public List<Historico> listarHistorico(int idDivida) {
        Conexao conexao = new Conexao();
        Divida divida = new Divida();
        Pessoa usuario = new Pessoa();

        DividaDAO dividaDAO = new DividaDAO();
        PessoaDAO pessoaDAO = new PessoaDAO();

        List<Historico> historicos = new ArrayList<Historico>();

        PreparedStatement ps = null;

        String sql = "SELECT * FROM Historico WHERE idDivida =" + idDivida + " ORDER BY id desc ";

        try {
            ps = conexao.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Historico historico = new Historico();
                divida = dividaDAO.buscarDividaById(rs.getInt("idDivida"));
                usuario = pessoaDAO.get(rs.getInt("idUsuario"));

                historico.setId(rs.getInt("id"));
                historico.setDivida(divida);
                historico.setDataUltimaCobranca(rs.getDate("dataultimacobranca"));
                historico.setDataCadastro(rs.getDate("datacadastro"));
                historico.setObservacao(rs.getString("observacao"));
                historico.setPessoa(usuario);
                historico.setIdAnexo(rs.getInt("idAnexo"));

                historicos.add(historico);
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e);
        } finally {
            conexao.desconectar();
        }

        return historicos;
    }
}
