/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cobranca.dao;

import br.com.cobranca.entity.Pessoa;
import br.com.cobranca.util.Conexao;
import java.sql.PreparedStatement;

/**
 *
 * @author Vinicius
 */
public class ContasPagarDAO {
    
    public Pessoa buscarCliente(int id){
        Pessoa cliente = new Pessoa();
        Conexao conexao  = new Conexao();
        
        PreparedStatement ps;
        
        try{
            
        }catch(Exception e){
            
        }finally{
            
        }
        
        return cliente;
    }
}
