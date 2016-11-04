/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cobranca.bean;

import br.com.cobranca.dao.DevedorDAO;
import br.com.cobranca.dao.DividaDAO;
import br.com.cobranca.dao.PessoaDAO;
import br.com.cobranca.entity.Devedor;
import br.com.cobranca.entity.Divida;
import br.com.cobranca.entity.Pessoa;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import br.com.cobranca.util.Util;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vinicius
 */
@ManagedBean(name = "DividaMB")
@ViewScoped
public class DividaMB {

    private String msg;
    private String tipoConsulta;
    private String valorConsulta;
    private String valorConsultaCliente;
    private String tipoConsultaCliente;

    private boolean habilitarBtnDividaNovo;
    private boolean habilitarBtnDividaSalvar;
    
    
    private Devedor devedor;
    private Divida divida;

    private DevedorDAO devedorDAO;
    private PessoaDAO pessoaDAO;
    private DividaDAO dividaDAO;
    
    public ArrayList<Pessoa> pessoas;
    public ArrayList<Devedor> devedores;

    public DividaMB() {
        this.devedor = new Devedor();
        this.divida = new Divida();
        this.devedorDAO = new DevedorDAO();
        this.pessoaDAO = new PessoaDAO();
        this.dividaDAO = new DividaDAO();
        
        habilitarBtnDividaNovo = true;
        habilitarBtnDividaSalvar = true;
        
        divida.setCliente(new Pessoa());
    }

    public void limparTela() {
        devedor = new Devedor();
        divida = new Divida();
        habilitarBtnDividaNovo = true;
        habilitarBtnDividaSalvar = true;
    }

    /**
     * Metodo que retorna devedor pelo cpf
     */
    public void buscarDevedorByCpf() {
        String cpf = devedor.getCpf();
        if (Util.isCPF(devedor.getCpf())) {
            
            devedor = devedorDAO.getByCpf(devedor.getCpf());

            if (devedor.getId() > 0) {
                Util.mostrarMensagemSucesso("Informação", "Devedor já cadastrado");
                habilitarBtnDividaNovo = false;
            }else{
                devedor.setCpf(cpf);
            }
        } else {
            Util.mostrarMensagemErro("Informação", "CPF Inválido");
        }

    }
    
    /**
     * Metodo para incluir nova divida
     */
    public void novaDivida(){
        divida = new Divida();
        divida.setCliente(new Pessoa());
        divida.setDevedor(devedor);
        habilitarBtnDividaSalvar = false;
    }
    
    public void salvarDivida(){
        
        divida =  dividaDAO.CadastrarDivida(divida);
        
        if(divida.getId() > 0){
            Util.mostrarMensagemSucesso("Informação", "Divida cadastrada com sucesso!");
        }else{
            Util.mostrarMensagemErro("Informação", "Falha ao cadastrar a divida.");
        }
    }
    
    /**
     * Metodo de consulta de pessoas
     * @param tipo 
     */
     public void consultarPessoas(String tipo) {

        if (valorConsultaCliente != null && !valorConsultaCliente.isEmpty()) {

            valorConsultaCliente = valorConsultaCliente.replace("'", "");

            if (pessoas != null) {
                pessoas.clear();
            }

            try {
                pessoas = pessoaDAO.get(tipoConsultaCliente, valorConsultaCliente, tipo);
            } catch (Exception ex) {
                Logger.getLogger(PessoaMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
     
      /**
     * Metodo de consulta de devedores 
     */
     public void consultarDevedores() {

        if (valorConsulta != null && !valorConsulta.isEmpty()) {

            valorConsulta = valorConsulta.replace("'", "");

            if (devedores != null) {
                devedores.clear();
            }

            try {
                devedores = devedorDAO.get(tipoConsulta, valorConsulta);
            } catch (Exception ex) {
                Logger.getLogger(PessoaMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo para validar campos do devedor
     *
     * @return
     */
    private boolean validarDevedor() {

        msg = "";

        if (devedor.getNome() == null || devedor.getNome().isEmpty()) {
            msg = "Nome inválido!";
        } else if (devedor.getCpf() == null || !Util.isCPF(devedor.getCpf())) {
            msg = "CPF inválido!";
        } else if (devedor.getRg() == null || devedor.getRg().trim().length() < 9) {
            msg = "RG inválido!";
        } else if (devedor.getDataNascimento() == null || devedor.getDataNascimento().getTime() >= (new Date()).getTime()) {
            msg = "Data de Nascimento inválida!";
        } else if (devedor.getSexo() == null || (!devedor.getSexo().trim().equals("F") && !devedor.getSexo().trim().equals("M"))) {
            msg = "Sexo inválido!";
        } else if (devedor.getEndereco() == null || devedor.getEndereco().isEmpty()) {
            msg = "Endereço inválido!";
        } else if (devedor.getNumero() == null || devedor.getNumero().equals(0l)) {
            msg = "Número inválido!";
        } else if (devedor.getBairro() == null || devedor.getBairro().isEmpty()) {
            msg = "Bairro inválido!";
        } else if (devedor.getCidade() == null || devedor.getCidade().isEmpty()) {
            msg = "Cidade inválida!";
        } else if (devedor.getUf() == null) {
            msg = "Estado inválido!";
        } else if (devedor.getTelefone() == null || devedor.getTelefone().trim().length() < 10) {
            msg = "Número de telefone inválido!";
        } else if (devedor.getCelular() == null || devedor.getCelular().trim().length() < 10) {
            msg = "Número de celular inválido!";
        } else if (devedor.getEmail() == null || !Util.isEmailValido(devedor.getEmail())) {
            msg = "E-mail inválido!";
        }

        if (msg.equals("")) {
            return true;
        }
        return false;
    }

    /**
     * Metodo que insere um novo devedor
     */
    public void inserirDevedor() {

        try {

            if (validarDevedor()) {
                devedor.setDataCadastro(new Date());
                devedor.setId(devedorDAO.post(devedor));

                if (devedor.getId() > 0) {
                    msg = "Inclusão realizada com sucesso!";
                    habilitarBtnDividaNovo = false;
                } else {
                    msg = "Inclusão não efetuada!";
                }
            }
        } catch (Exception ex) {
            msg = "Erro ao efetuar a inclusão: " + ex.getMessage();
        } finally {
            Util.mostrarMensagemSucesso("Informação", msg);
        }

    }

    /**
     * Metodo que atualiza os dados do devedor
     */
    public void atualizarPessoa() {

        try {

            if (validarDevedor()) {

                boolean retorno = devedorDAO.put(devedor);

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

    public Devedor getDevedor() {
        return devedor;
    }

    public void setDevedor(Devedor devedor) {
        this.devedor = devedor;
        valorConsulta = "";
        tipoConsulta = "";
        habilitarBtnDividaNovo = false;
        devedores.clear();
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

    public Divida getDivida() {
        return divida;
    }

    public void setDivida(Divida divida) {
        this.divida = divida;
        
    }

    public ArrayList<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(ArrayList<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    public boolean isHabilitarBtnDividaNovo() {
        return habilitarBtnDividaNovo;
    }

    public void setHabilitarBtnDividaNovo(boolean habilitarBtnDividaNovo) {
        this.habilitarBtnDividaNovo = habilitarBtnDividaNovo;
    }

    public boolean isHabilitarBtnDividaSalvar() {
        return habilitarBtnDividaSalvar;
    }

    public void setHabilitarBtnDividaSalvar(boolean habilitarBtnDividaSalvar) {
        this.habilitarBtnDividaSalvar = habilitarBtnDividaSalvar;
    }

    public ArrayList<Devedor> getDevedores() {
        return devedores;
    }

    public void setDevedores(ArrayList<Devedor> devedores) {
        this.devedores = devedores;
    }

    public String getValorConsultaCliente() {
        return valorConsultaCliente;
    }

    public void setValorConsultaCliente(String valorConsultaCliente) {
        this.valorConsultaCliente = valorConsultaCliente;
    }

    public String getTipoConsultaCliente() {
        return tipoConsultaCliente;
    }

    public void setTipoConsultaCliente(String tipoConsultaCliente) {
        this.tipoConsultaCliente = tipoConsultaCliente;
    }

    
    
    
    

}
