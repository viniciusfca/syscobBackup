/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cobranca.bean;

import br.com.cobranca.dao.PessoaDAO;
import br.com.cobranca.entity.Pessoa;
import br.com.cobranca.util.Util;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "PessoaMB", eager = true)
@ViewScoped
public class PessoaMB {

    private String confirmarSenha;
    private String confirmarEmail;

    private String msg;

    private String tipoConsulta;
    private String valorConsulta;

    private Date valorMaximoDataNascimento;

    private boolean habilitarBotaoAlterar;

    private final PessoaDAO dao;
    private Pessoa pessoa;

    public ArrayList<Pessoa> pessoas;
    private String senhaBackup;

    public PessoaMB() {
        this.dao = new PessoaDAO();

        limparTela();
    }

    public void limparTela() {

        pessoa = new Pessoa();
        this.pessoas = new ArrayList<>();

        valorMaximoDataNascimento = new Date();

        habilitarBotaoAlterar = false;
        valorConsulta = "";

        confirmarEmail = "";
        senhaBackup = "";
    }
    
    
    
     /**
     * Metodo que retorna devedor pelo cpf
     */
    public void buscarPessoaByCpf() {

        if (Util.isCPF(pessoa.getCpf())) {
            pessoa = dao.getByCpf(pessoa.getCpf());

            if (pessoa.getId() > 0) {
                Util.mostrarMensagemSucesso("Informação", "Pessoa já cadastrado");
            }
        } else {
            Util.mostrarMensagemErro("Informação", "CPF Inválido");
        }

    }
    
    private boolean validarPessoa() {

        msg = "";

        if (habilitarBotaoAlterar) {

            if ((confirmarSenha == null || confirmarSenha.isEmpty())
                    && (pessoa.getSenha() == null || pessoa.getSenha().isEmpty())) {
                confirmarSenha = senhaBackup;
                pessoa.setSenha(senhaBackup);
            }

        }

        if (pessoa.getNome() == null || pessoa.getNome().isEmpty()) {
            msg = "Nome inválido!";
        } else if (pessoa.getCpf() == null || !Util.isCPF(pessoa.getCpf())) {
            msg = "CPF inválido!";
        } else if (pessoa.getRg() == null || pessoa.getRg().trim().length() < 9) {
            msg = "RG inválido!";
        } else if (pessoa.getDataNascimento() == null || pessoa.getDataNascimento().getTime() >= (new Date()).getTime()) {
            msg = "Data de Nascimento inválida!";
        } else if (pessoa.getSexo() == null || (!pessoa.getSexo().trim().equals("F") && !pessoa.getSexo().trim().equals("M"))) {
            msg = "Sexo inválido!";
        } else if (pessoa.getEndereco() == null || pessoa.getEndereco().isEmpty()) {
            msg = "Endereço inválido!";
        } else if (pessoa.getNumero() == null || pessoa.getNumero().equals(0l)) {
            msg = "Número inválido!";
        } else if (pessoa.getBairro() == null || pessoa.getBairro().isEmpty()) {
            msg = "Bairro inválido!";
        } else if (pessoa.getCidade() == null || pessoa.getCidade().isEmpty()) {
            msg = "Cidade inválida!";
        } else if (pessoa.getUf() == null) {
            msg = "Estado inválido!";
        } else if (pessoa.getTelefone() == null || pessoa.getTelefone().trim().length() < 10) {
            msg = "Número de telefone inválido!";
        } else if (pessoa.getCelular() == null || pessoa.getCelular().trim().length() < 10) {
            msg = "Número de celular inválido!";
        } else if (pessoa.getEmail() == null || !Util.isEmailValido(pessoa.getEmail())) {
            msg = "E-mail inválido!";
        } else if (confirmarEmail == null || !confirmarEmail.equals(pessoa.getEmail())) {
            msg = "Confirmação de e-mail inválida!";
        } else if (pessoa.getUsername() == null || pessoa.getUsername().isEmpty()) {
            msg = "Usuário inválido!";
        } else if (pessoa.getSenha() == null || pessoa.getSenha().isEmpty()) {
            msg = "Senha inválida!";
        } else if (confirmarSenha == null || !confirmarSenha.equals(pessoa.getSenha())) {
            msg = "Confirmação de senha inválida!";
        }

        if (msg.equals("")) {
            return true;
        }
        return false;
    }

    public void atualizarPessoa() {

        try {

            if (validarPessoa()) {

                boolean retorno = dao.put(pessoa);

                if (retorno) {
                    msg = "Alteração realizada com sucesso!";
                    limparTela();
                } else {
                    msg = "Alteração não efetuada!";
                }
            }

        } catch (Exception ex) {
            msg = "Erro ao efetuar a alteração: " + ex.getMessage();
            //Logger.getLogger(PessoaMB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Util.mostrarMensagemSucesso("Informação", msg);
        }
    }

    public void inserirPessoa(String tipo) {

        try {

            if (validarPessoa()) {

                pessoa.setTipo(tipo);
                pessoa.setDataCadastro(new Date());

                int id = dao.post(pessoa);

                if (id > 0) {
                    msg = "Inclusão realizada com sucesso!";
                    limparTela();
                } else {
                    msg = "Inclusão não efetuada!";
                }
            }
        } catch (Exception ex) {
            msg = "Erro ao efetuar a inclusão: " + ex.getMessage();
            //Logger.getLogger(PessoaMB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Util.mostrarMensagemSucesso("Informação", msg);
        }

    }

    public void consultarPessoas(String tipo) {

        if (valorConsulta != null && !valorConsulta.isEmpty()) {

            valorConsulta = valorConsulta.replace("'", "");

            if (pessoas != null) {
                pessoas.clear();
            }

            try {
                pessoas = dao.get(tipoConsulta, valorConsulta, tipo);
            } catch (Exception ex) {
                Logger.getLogger(PessoaMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //
    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;

        pessoas.clear();
        valorConsulta = "";
        habilitarBotaoAlterar = true;

        confirmarEmail = pessoa.getEmail();
        senhaBackup = pessoa.getSenha();
    }

    public ArrayList<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(ArrayList<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public String getValorConsulta() {
        return valorConsulta;
    }

    public void setValorConsulta(String valorConsulta) {
        this.valorConsulta = valorConsulta;
    }

    public boolean isHabilitarBotaoAlterar() {
        return habilitarBotaoAlterar;
    }

    public void setHabilitarBotaoAlterar(boolean habilitarBotaoAlterar) {
        this.habilitarBotaoAlterar = habilitarBotaoAlterar;
    }

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }

    public String getConfirmarEmail() {
        return confirmarEmail;
    }

    public void setConfirmarEmail(String confirmarEmail) {
        this.confirmarEmail = confirmarEmail;
    }

    public Date getValorMaximoDataNascimento() {
        return valorMaximoDataNascimento;
    }

    public void setValorMaximoDataNascimento(Date valorMaximoDataNascimento) {
        this.valorMaximoDataNascimento = valorMaximoDataNascimento;
    }

}
