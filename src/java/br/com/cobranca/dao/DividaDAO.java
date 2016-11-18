/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cobranca.dao;

import br.com.cobranca.entity.Devedor;
import br.com.cobranca.entity.Divida;
import br.com.cobranca.entity.Pessoa;
import br.com.cobranca.util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Vinicius
 */
public class DividaDAO {

    private final String INSERT = "INSERT INTO Divida (idDevedor,IdCliente,valordivida,status,observacao,datacadastro,datacobranca) VALUES(?,?,?,?,?,CURDATE(),?)";

    /**
     * Metodo para cadastrar uma nova divida
     *
     * @param divida
     */
    public Divida CadastrarDivida(Divida divida) {
        Conexao conexao = new Conexao();
        PreparedStatement ps = null;

        Date dataCob = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dataCob);
        c.add(Calendar.DATE, +1);
        dataCob = c.getTime();

        try {
            ps = conexao.conectar().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, divida.getDevedor().getId());
            ps.setInt(2, divida.getCliente().getId());
            ps.setDouble(3, divida.getValorDivida());
            ps.setString(4, divida.getStatus());
            ps.setString(5, divida.getObservacao());
            ps.setDate(6, new java.sql.Date(dataCob.getTime()));

            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                divida.setId(rs.getInt(1));
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e);
        } finally {
            conexao.desconectar();
        }

        return divida;
    }

    public List<Divida> dividasDia() {
        Conexao conexao = new Conexao();
        Devedor devedor = new Devedor();
        Pessoa pessoa = new Pessoa();
        PessoaDAO pessoaDAO = new PessoaDAO();
        DevedorDAO devedorDAO = new DevedorDAO();
        PreparedStatement ps = null;
        List<Divida> dividas = new ArrayList<Divida>();

        String sql = "SELECT * FROM DIVIDA WHERE datacobranca = ?";
        try {
            ps = conexao.conectar().prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(new Date().getTime()));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Divida divida = new Divida();
                divida.setId(rs.getInt("id"));

                devedor = devedorDAO.getById(rs.getInt("idDevedor"));
                pessoa = pessoaDAO.get(rs.getInt("idCliente"));
                divida.setCliente(pessoa);
                divida.setDevedor(devedor);

                divida.setStatus(rs.getString("status"));
                divida.setObservacao(rs.getString("observacao"));
                divida.setDataCadastro(rs.getDate("datacadastro"));
                divida.setDataCobranca(rs.getDate("datacobranca"));
                divida.setValorDivida(rs.getDouble("valordivida"));

                dividas.add(divida);
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e);
        } finally {
            conexao.desconectar();
        }

        return dividas;
    }
    
    
    public List<Divida> dividasCliente(int idCliente) {
        Conexao conexao = new Conexao();
        Devedor devedor = new Devedor();
        Pessoa pessoa = new Pessoa();
        PessoaDAO pessoaDAO = new PessoaDAO();
        DevedorDAO devedorDAO = new DevedorDAO();
        PreparedStatement ps = null;
        List<Divida> dividas = new ArrayList<Divida>();

        String sql = "SELECT * FROM DIVIDA WHERE idCliente = ?";
        try {
            ps = conexao.conectar().prepareStatement(sql);
            ps.setInt(1, idCliente);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Divida divida = new Divida();
                divida.setId(rs.getInt("id"));

                devedor = devedorDAO.getById(rs.getInt("idDevedor"));
                pessoa = pessoaDAO.get(rs.getInt("idCliente"));
                divida.setCliente(pessoa);
                divida.setDevedor(devedor);

                divida.setStatus(rs.getString("status"));
                divida.setObservacao(rs.getString("observacao"));
                divida.setDataCadastro(rs.getDate("datacadastro"));
                divida.setDataCobranca(rs.getDate("datacobranca"));
                divida.setValorDivida(rs.getDouble("valordivida"));

                dividas.add(divida);
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e);
        } finally {
            conexao.desconectar();
        }

        return dividas;
    }
    
    public List<Divida> dividasDevedor(int idDevedor) {
        Conexao conexao = new Conexao();
        Devedor devedor = new Devedor();
        Pessoa pessoa = new Pessoa();
        PessoaDAO pessoaDAO = new PessoaDAO();
        DevedorDAO devedorDAO = new DevedorDAO();
        PreparedStatement ps = null;
        List<Divida> dividas = new ArrayList<Divida>();

        String sql = "SELECT * FROM DIVIDA WHERE idDevedor = ?";
        try {
            ps = conexao.conectar().prepareStatement(sql);
            ps.setInt(1, idDevedor);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Divida divida = new Divida();
                divida.setId(rs.getInt("id"));

                devedor = devedorDAO.getById(rs.getInt("idDevedor"));
                pessoa = pessoaDAO.get(rs.getInt("idCliente"));
                divida.setCliente(pessoa);
                divida.setDevedor(devedor);

                divida.setStatus(rs.getString("status"));
                divida.setObservacao(rs.getString("observacao"));
                divida.setDataCadastro(rs.getDate("datacadastro"));
                divida.setDataCobranca(rs.getDate("datacobranca"));
                divida.setValorDivida(rs.getDouble("valordivida"));

                dividas.add(divida);
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e);
        } finally {
            conexao.desconectar();
        }

        return dividas;
    }

    public Divida buscarDividaById(int id) {
        Conexao conexao = new Conexao();
        Divida divida = new Divida();
        Devedor devedor = new Devedor();
        Pessoa pessoa = new Pessoa();
        
        DevedorDAO devedorDAO = new DevedorDAO();
        PessoaDAO pessoaDAO = new PessoaDAO();

        String sql = "SELECT * FROM Divida WHERE id = " + id +" AND status <> 'Finalizado' ";

        PreparedStatement ps = null;

        try {
            ps = conexao.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                divida.setId(rs.getInt("id"));

                devedor = devedorDAO.getById(rs.getInt("idDevedor"));
                pessoa = pessoaDAO.get(rs.getInt("idCliente"));
                divida.setCliente(pessoa);
                divida.setDevedor(devedor);

                divida.setStatus(rs.getString("status"));
                divida.setObservacao(rs.getString("observacao"));
                divida.setDataCadastro(rs.getDate("datacadastro"));
                divida.setDataCobranca(rs.getDate("datacobranca"));
                divida.setValorDivida(rs.getDouble("valordivida"));
            }

        } catch (Exception e) {
            System.out.println("Erro: "+ e);
        } finally {
            conexao.desconectar();
        }

        return divida;
    }
    
    /**
     * Metodo que atualiza status da divida
     * @param divida
     * @return 
     */
    public Divida atualizarDivida(Divida divida){
        Conexao conexao = new Conexao();
        PreparedStatement ps = null;
        String sql = "Update Divida SET status = ?, datacobranca = ?  WHERE id = ?";
        
        Date dataCob = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dataCob);
        c.add(Calendar.DATE, +1);
        dataCob = c.getTime();
        
        
        try{
            ps = conexao.conectar().prepareStatement(sql);
            ps.setString(1, divida.getStatus());
            ps.setDate(2, new java.sql.Date(dataCob.getTime()));
            ps.setInt(3, divida.getId());
            
            ps.execute();
            
        }catch(Exception e ){
            System.out.println("Erro: " + e);
        }finally{
            conexao.desconectar();
        }
        
        return divida;
    }

}
