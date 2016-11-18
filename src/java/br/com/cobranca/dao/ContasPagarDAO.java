/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cobranca.dao;

import br.com.cobranca.entity.ContasPagar;
import br.com.cobranca.entity.ContasReceber;
import br.com.cobranca.entity.Divida;
import br.com.cobranca.entity.Pessoa;
import br.com.cobranca.entity.Usuario;
import br.com.cobranca.util.Conexao;
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
public class ContasPagarDAO {
    
    /**
     * Metodo que insere Contas a Pagar
     * @param contasReceber
     * @return 
     */
    public int inserirContasPagar(ContasReceber contasReceber){
        
        Conexao conexao = new Conexao();
        Divida divida = new Divida();
        DividaDAO dividaDAO = new DividaDAO();
        
        PreparedStatement ps = null;
        int retorno = 0;
        
        String sql = "INSERT INTO pagar (idCliente,valor,status,idDivida) VALUES(?,?,?,?)";
        
        try{
            
            ps = conexao.conectar().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            divida = dividaDAO.buscarDividaById(contasReceber.getIdDivida());
            ps.setInt(1, divida.getCliente().getId());
            ps.setDouble(2, contasReceber.getValor());
            ps.setString(3, "Aguardando Pagamento");
            ps.setInt(4, divida.getId());
            ps.execute();

            
             ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                retorno = rs.getInt(1);
            }
            
        }catch(Exception e ){
            System.out.println("Erro: "+ e.getMessage());
        }finally{
            conexao.desconectar();
        }
        
        return retorno;
    }
    
    /**
     * Metodo que retorna uma lista de contas a pagar
     * @param idCliente
     * @return 
     */
    public List<ContasPagar> listarContasPagar(int idCliente){
        Conexao conexao = new Conexao();
        PreparedStatement ps = null;
        List<ContasPagar> contasPagar = new ArrayList<ContasPagar>();
        String sql = "SELECT * FROM pagar WHERE idCliente = ?";
        
        try{
            ps = conexao.conectar().prepareStatement(sql);
            ps.setInt(1, idCliente);
           
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                ContasPagar contaPagar = new ContasPagar();
                contaPagar.setId(rs.getInt("id"));
                contaPagar.setIdCliente(rs.getInt("idCliente"));
                contaPagar.setIdDivida(rs.getInt("idDivida"));
                contaPagar.setStatus(rs.getString("status"));
                contaPagar.setValor(rs.getDouble("valor"));
                contaPagar.setDataPagamento(rs.getDate("datapagamento"));
                contasPagar.add(contaPagar);
            }
           
            
        }catch(Exception e){
            System.out.println("Erro: " + e.getMessage());
        }finally{
            conexao.desconectar();
        }
        
        return contasPagar;
    }
    
    /**
     * Metodo que quita conta pagar
     * @param contaPagar
     * @return 
     */
    public List<ContasPagar> quitarContasPagar(ContasPagar contaPagar){
        Conexao conexao = new Conexao();
        PreparedStatement ps = null;
        List<ContasPagar> contas = new ArrayList<ContasPagar>();
        String sql = "UPDATE pagar SET status = ?, datapagamento = ? WHERE id = ?";
        
        try{
            ps = conexao.conectar().prepareStatement(sql);
            ps.setString(1, "Pago");
            ps.setDate(2, new java.sql.Date(new Date().getTime()));
            ps.setInt(3, contaPagar.getId());
            ps.execute();
            
            contas = listarContasPagar(contaPagar.getIdCliente());
            
            
        }catch(Exception e ){
            System.out.println("Erro: " + e.getMessage());
        }finally{
            conexao.desconectar();
        }
        
        return contas;
    }
}
