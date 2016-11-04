/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cobranca.dao;

import br.com.cobranca.entity.Devedor;
import br.com.cobranca.util.Conexao;
import br.com.cobranca.util.Util;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Vinicius
 */
public class DevedorDAO {
    
    
    
    public ArrayList<Devedor> get() throws Exception {

        Conexao conexao = new Conexao();
        ArrayList<Devedor> devedores = new ArrayList<Devedor>();

        try {

            String strSql = "SELECT * FROM DEVEDOR";
            PreparedStatement ps = conexao.conectar().prepareStatement(strSql);

          

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Devedor p = Util.atribuirValores(Devedor.class, rs);
                devedores.add(p);

            }

            rs.close();
            ps.close();

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            conexao.desconectar();
        }

        return devedores;
    }
    
    
    
    public ArrayList<Devedor> get(String tipoConsulta, String valorConsulta) throws Exception {

        Conexao conexao = new Conexao();
        ArrayList<Devedor> devedores = new ArrayList<>();

        try {

            String strSql = "SELECT * FROM DEVEDOR WHERE " + tipoConsulta;

            if (tipoConsulta.equals("CPF")) {
                strSql = strSql + " = ?";
            } else {
                strSql = strSql + " LIKE '%" + valorConsulta + "%'";
            }
           
            PreparedStatement ps = conexao.conectar().prepareStatement(strSql);

            

            if (tipoConsulta.equals("CPF")) {
                ps.setString(1, valorConsulta);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Devedor p = Util.atribuirValores(Devedor.class, rs);
                devedores.add(p);

            }

            rs.close();
            ps.close();

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            conexao.desconectar();
        }

        return devedores;
    }

    
     public int post(Devedor devedor) throws Exception {

        Conexao conexao = new Conexao();
        int id = 0;

        try {
            
            devedor.setCpf(Util.retirarMascara(devedor.getCpf()));
            devedor.setCelular(Util.retirarMascara(devedor.getCelular()));
            devedor.setTelefone(Util.retirarMascara(devedor.getTelefone()));
            
            id = Util.inserirRegistro(devedor, conexao.conectar());

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            conexao.desconectar();
        }

        return id;
    }
     
     public boolean put(Devedor devedor) throws Exception {

        Conexao conexao = new Conexao();
        try {

            String strSQL = "UPDATE DEVEDOR SET NOME = ? WHERE ID = ? "; // Falta finalizar
            PreparedStatement ps = conexao.conectar().prepareStatement(strSQL);
            
            //Set
            ps.setString(1, devedor.getNome());
            
            //Where
            ps.setInt(2, devedor.getId());
           
            
            int qtdLinhasAfetadas = ps.executeUpdate();

            ps.close();
            
            if (qtdLinhasAfetadas > 0) {
                return true;
            }
            
            else{
                return false;
            }
            
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            conexao.desconectar();
        }

    }
     
     /**
      * Metodo que retorna devedor pelo CPF
      * @param cpf
      * @return 
      */
     public Devedor getByCpf(String cpf){
         Conexao conexao = new Conexao();
         Devedor devedor = new Devedor();
         
         String sql = "SELECT * FROM DEVEDOR WHERE cpf = '"+cpf+"'";
         
         PreparedStatement ps;
         
         try{
             ps = conexao.conectar().prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             
             if(rs.next()){
                 devedor.setId(rs.getInt("id"));
                 devedor.setNome(rs.getString("nome"));
                 devedor.setSexo(rs.getString("sexo"));
                 devedor.setCpf(rs.getString("cpf"));
                 devedor.setRg(rs.getString("rg"));
                 devedor.setDataNascimento(rs.getDate("datanascimento"));
                 devedor.setDataCadastro(rs.getDate("datacadastro"));
                 devedor.setEmail(rs.getString("email"));
                 devedor.setTelefone(rs.getString("telefone"));
                 devedor.setCelular(rs.getString("celular"));
                 devedor.setEndereco(rs.getString("endereco"));
                 devedor.setNumero(rs.getString("numero"));
                 devedor.setBairro(rs.getString("bairro"));
                 devedor.setComplemento(rs.getString("complemento"));
                 devedor.setCidade(rs.getString("cidade"));
                 devedor.setUf(rs.getString("uf"));
             }
             
         }catch(Exception e){
             System.out.println("Erro: "+ e);
         }finally{
             conexao.desconectar();
         }
         
         return devedor;
     }
     
     /**
      * Metodo que retorna devedor pelo CPF
      * @param id
      * @return 
      */
     public Devedor getById(int id){
         Conexao conexao = new Conexao();
         Devedor devedor = new Devedor();
         
         String sql = "SELECT * FROM DEVEDOR WHERE id = '"+id+"'";
         
         PreparedStatement ps;
         
         try{
             ps = conexao.conectar().prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             
             if(rs.next()){
                 devedor.setId(rs.getInt("id"));
                 devedor.setNome(rs.getString("nome"));
                 devedor.setSexo(rs.getString("sexo"));
                 devedor.setCpf(rs.getString("cpf"));
                 devedor.setRg(rs.getString("rg"));
                 devedor.setDataNascimento(rs.getDate("datanascimento"));
                 devedor.setDataCadastro(rs.getDate("datacadastro"));
                 devedor.setEmail(rs.getString("email"));
                 devedor.setTelefone(rs.getString("telefone"));
                 devedor.setCelular(rs.getString("celular"));
                 devedor.setEndereco(rs.getString("endereco"));
                 devedor.setNumero(rs.getString("numero"));
                 devedor.setBairro(rs.getString("bairro"));
                 devedor.setComplemento(rs.getString("complemento"));
                 devedor.setCidade(rs.getString("cidade"));
                 devedor.setUf(rs.getString("uf"));
             }
             
         }catch(Exception e){
             System.out.println("Erro: "+ e);
         }finally{
             conexao.desconectar();
         }
         
         return devedor;
     }
     
}
