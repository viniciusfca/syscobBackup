package br.com.cobranca.dao;

import br.com.cobranca.entity.Pessoa;
import br.com.cobranca.util.Conexao;
import br.com.cobranca.util.Util;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PessoaDAO {

    public Pessoa get(int id) throws Exception {

        Conexao conexao = new Conexao();
        Pessoa pessoa = new Pessoa();

        try {

            String strSql = "SELECT * FROM PESSOA WHERE ID = ?";
            PreparedStatement ps = conexao.conectar().prepareStatement(strSql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                pessoa = Util.atribuirValores(Pessoa.class, rs);
            }

            rs.close();
            ps.close();

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            conexao.desconectar();
        }

        return pessoa;
    }

    public ArrayList<Pessoa> get(String tipoConsulta, String valorConsulta, String tipoPessoa) throws Exception {

        Conexao conexao = new Conexao();
        ArrayList<Pessoa> pessoas = new ArrayList<>();

        try {

            String strSql = "SELECT * FROM PESSOA WHERE TIPO = ? AND " + tipoConsulta;

            if (tipoConsulta.equals("CPF")) {
                strSql = strSql + " = ?";
            } else {
                strSql = strSql + " LIKE '%" + valorConsulta + "%'";
            }

            PreparedStatement ps = conexao.conectar().prepareStatement(strSql);

            ps.setString(1, tipoPessoa);

            if (tipoConsulta.equals("CPF")) {
                ps.setString(2, valorConsulta);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Pessoa p = Util.atribuirValores(Pessoa.class, rs);
                pessoas.add(p);

            }

            rs.close();
            ps.close();

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            conexao.desconectar();
        }

        return pessoas;
    }

    public int post(Pessoa pessoa) throws Exception {

        Conexao conexao = new Conexao();
        int id = 0;

        try {
            if (validarPessoa(pessoa)) {
                id = Util.inserirRegistro(pessoa, conexao.conectar());
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            conexao.desconectar();
        }

        return id;
    }

    public boolean put(Pessoa pessoa) throws Exception {

        Conexao conexao = new Conexao();
        boolean retorno = false;

        try {

            if (validarPessoa(pessoa)) {
                String strWhere = "WHERE ID = " + pessoa.getId();
                retorno = Util.alterarRegistro(pessoa, Pessoa.class, conexao.conectar(), strWhere);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            conexao.desconectar();
        }

        return retorno;
    }

    public boolean delete(int id) throws Exception {

        Conexao conexao = new Conexao();
        try {

            String strSQL = "DELETE FROM PESSOA WHERE ID = ?";
            PreparedStatement ps = conexao.conectar().prepareStatement(strSQL);

            //Where
            ps.setInt(1, id);

            int qtdLinhasAfetadas = ps.executeUpdate();

            ps.close();

            return (qtdLinhasAfetadas > 0);

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            conexao.desconectar();
        }
    }

    private boolean validarPessoa(Pessoa pessoa) throws Exception {

        Conexao conexao = new Conexao();
        boolean retorno = false;

        try {
            String strSQL = "SELECT * FROM PESSOA WHERE TIPO = ? AND (CPF = ? OR USERNAME = ?)";
            
            if(pessoa.getId() != null && pessoa.getId() > 0){
                strSQL = strSQL + " AND ID <> ?";
            }
            
            PreparedStatement ps = conexao.conectar().prepareStatement(strSQL);

            //Where
            ps.setString(1, pessoa.getTipo());
            ps.setString(2, pessoa.getCpf());
            ps.setString(3, pessoa.getUsername());

            if(pessoa.getId() != null && pessoa.getId() > 0){
                ps.setInt(4, pessoa.getId());
            }
            
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                retorno = true;
            }

            rs.close();
            ps.close();

            return retorno;

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            conexao.desconectar();
        }

    }
    
    /**
      * Metodo que retorna pessoa pelo CPF
      * @param cpf
      * @return 
      */
     public Pessoa getByCpf(String cpf, String tipo){
         Conexao conexao = new Conexao();
         Pessoa pessoa = new Pessoa();
         
         String sql = "SELECT * FROM PESSOA WHERE tipo = '"+tipo+"' AND cpf = '"+cpf+"'";
         
         PreparedStatement ps;
         
         try{
             ps = conexao.conectar().prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             
             if(rs.next()){
                 pessoa.setId(rs.getInt("id"));
                 pessoa.setNome(rs.getString("nome"));
                 pessoa.setSexo(rs.getString("sexo"));
                 pessoa.setCpf(rs.getString("cpf"));
                 pessoa.setRg(rs.getString("rg"));
                 pessoa.setDataNascimento(rs.getDate("datanascimento"));
                 pessoa.setDataCadastro(rs.getDate("datacadastro"));
                 pessoa.setEmail(rs.getString("email"));
                 pessoa.setTelefone(rs.getString("telefone"));
                 pessoa.setCelular(rs.getString("celular"));
                 pessoa.setEndereco(rs.getString("endereco"));
                 pessoa.setNumero(rs.getLong("numero"));
                 pessoa.setBairro(rs.getString("bairro"));
                 pessoa.setComplemento(rs.getString("complemento"));
                 pessoa.setCidade(rs.getString("cidade"));
                 pessoa.setUf(rs.getString("uf"));
                 pessoa.setTipo(rs.getString("tipo"));
                 pessoa.setUsername(rs.getString("username"));
                 pessoa.setSenha(rs.getString("senha"));
             }
             
         }catch(Exception e){
             System.out.println("Erro: "+ e);
         }finally{
             conexao.desconectar();
         }
         
         return pessoa;
     }
     /**
      * Metodo que retorno pessoa pelo login e senha
      * @param username
      * @param password
      * @param tipo 
     * @return  
      */
     public Pessoa pessoaLogin(String username, String password){
         Conexao conexao = new Conexao();
         Pessoa pessoa = new Pessoa();
         PreparedStatement ps;
         
         String sql = "SELECT id,nome,email,tipo FROM pessoa WHERE username = ? and senha = ?";
         
         try{
             ps = conexao.conectar().prepareStatement(sql);
             ps.setString(1, username);
             ps.setString(2, password);
             
             
             ResultSet rs = ps.executeQuery();
             
             if(rs.next()){
                 pessoa.setId(rs.getInt("id"));
                 pessoa.setNome(rs.getString("nome"));
                 pessoa.setEmail(rs.getString("email"));
                 pessoa.setTipo(rs.getString("tipo"));
             }
             
         }catch(Exception e ){
             System.out.println("Erro: "+ e);
         }finally{
             conexao.desconectar();
         }
         
         return pessoa;
     }

}
