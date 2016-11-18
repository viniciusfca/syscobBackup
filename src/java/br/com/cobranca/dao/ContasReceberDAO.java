/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cobranca.dao;

import br.com.cobranca.entity.ContasReceber;
import br.com.cobranca.util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

/**
 *
 * @author Vinicius
 */
public class ContasReceberDAO {
    
    /**
     * Metodo que inclui contas a rebecer
     * @param idCliente
     * @param Valor
     * @return 
     */
    public int inserirContasReceber(int idDivida, double Valor, String nossonumero, Date dataVencimento){
        Conexao conexao = new Conexao();
        PreparedStatement ps = null;
        int retorno = 0;
        
        String sql ="INSERT INTO receber (idDivida,valor,status,nossonumero,datavencimento) VALUES(?,?,?,?,?)";
        try{
            
            ps = conexao.conectar().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idDivida);
            ps.setDouble(2, Valor);
            ps.setString(3, "Aguardando Pagamento");
            ps.setString(4, nossonumero);
            ps.setDate(5, new java.sql.Date(dataVencimento.getTime()));
            ps.execute();
            
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                retorno = rs.getInt(1);
            }
            
        }catch(Exception e){
            System.out.println("Erro: "+ e.getMessage());
        }finally{
            conexao.desconectar();
        }
        
        return retorno;
    }
    
    /**
     * Metodo que retorna contasReceber pelo nossoNumero
     * @param nossoNumero
     * @return 
     */
    public ContasReceber buscarContasReceber(String nossoNumero){
        Conexao conexao = new Conexao();
        ContasReceber contasReceber = new ContasReceber();
        PreparedStatement ps = null;
        
        String sql = "SELECT * FROM receber WHERE nossonumero = ? ORDER BY id desc";
        
        try{
            
            ps = conexao.conectar().prepareStatement(sql);
            ps.setString(1, nossoNumero);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                contasReceber.setId(rs.getInt("id"));
                contasReceber.setValor(rs.getDouble("valor"));
                contasReceber.setDataPagamento(rs.getDate("datapagamento"));
                contasReceber.setDataVencimento(rs.getDate("datavencimento"));
                contasReceber.setIdDivida(rs.getInt("idDivida"));
                contasReceber.setStatus(rs.getString("status"));
                contasReceber.setNossoNumero(rs.getString("nossonumero"));
            }
            
        }catch(Exception e){
            System.out.println("Erro: " + e.getMessage());
        }finally{
            conexao.desconectar();
        }
        
        return contasReceber;
    }
    
    /**
     * Metodo que faz a baixa de uma conta a receber
     * @param contasReceber
     * @return 
     */
    public String baixarReceber(ContasReceber contasReceber){
        ContasPagarDAO contasPagarDAO = new ContasPagarDAO();
        Conexao conexao = new Conexao();
        PreparedStatement ps = null;
        String sql = "UPDATE receber SET datapagamento = ?, status = ? WHERE id = ?";
        String retorno = "";
        try{
            
            ps = conexao.conectar().prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(new Date().getTime() ));
            ps.setString(2, "Pago");
            ps.setInt(3, contasReceber.getId());
            ps.execute();
            
            retorno = "OK";
            
            contasPagarDAO.inserirContasPagar(contasReceber);
            
        }catch(Exception e){
            System.out.println("Erro: "+ e.getMessage());
        }finally{
            conexao.desconectar();
        }
        
        return retorno;
    }
    
}
