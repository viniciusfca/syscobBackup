/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cobranca.bean;

import br.com.cobranca.dao.PessoaDAO;
import br.com.cobranca.entity.Pessoa;
import br.com.cobranca.util.Util;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author vinicius
 */
@ManagedBean(name = "LoginMB")
@ViewScoped
public class LoginMB {

    private String username;
    private String senha;

    /**
     * Construtor
     */
    public LoginMB() {

    }

    /**
     * Metodo que realiza o login
     */
    public void doLogin() {
        PessoaDAO pessoaDAO = new PessoaDAO();
        try {
            Pessoa pessoa = pessoaDAO.pessoaLogin(username, senha);
            if (pessoa.getId() != null) {
                Util.colocarUsuarioSessao(pessoa);
                Util.redirecionar(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath());
            } else {
                Util.mostrarMensagemErro("Informação", "Usuario ou senha incorreta!");
            }

        } catch (Exception ex) {
            Util.mostrarMensagemErro("Informação", ex.getMessage());
        }
    }

    /**
     * Metodo que realiza o logout
     */
    public void doLogout() {
        Util.retirarUsuarioSessao();
        Util.redirecionar(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath());
    }

    /**
     * Metodo qure retorna o usuario logado
     *
     * @return
     */
    public Pessoa retornarUsuarioLogado() {
        return Util.getUsuarioLogado();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    

}
