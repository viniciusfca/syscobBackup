/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cobranca.bean;

import br.com.cobranca.dao.ContasPagarDAO;
import br.com.cobranca.entity.ContasPagar;
import br.com.cobranca.entity.Pessoa;
import br.com.cobranca.util.Util;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Vinicius
 */
@ManagedBean(name="contasPagarMB")
@ViewScoped
public class ContasPagarMB {
    
    
    
    private Pessoa pessoa;
    
    private ContasPagarDAO contasPagarDAO;
    
    
    private List<ContasPagar> contasPagar;
    /**
     * Construtor
     */
    public ContasPagarMB() {
        contasPagar = new ArrayList<ContasPagar>();
        contasPagarDAO = new ContasPagarDAO();
        pessoa = new Pessoa();
    }
    
    /**
     * Metodo que buscar uma lista de contas a pagar
     */
    public void ListarContasPagar(){
        contasPagar = contasPagarDAO.listarContasPagar(pessoa.getId());
    }
    
    /**
     * Metodo que quita conta a pagar
     * @param contaPagar 
     */
    public void quitarContasPagar(ContasPagar contaPagar){
        contasPagar = contasPagarDAO.quitarContasPagar(contaPagar);
        
        if(contasPagar.size() < 1){
            Util.mostrarMensagemErro("Informação", "Falhar ao quitar Divida");
        }else{
            Util.mostrarMensagemSucesso("Informação", "Dívida quitada com sucesso");
        }
    }

    public List<ContasPagar> getContasPagar() {
        return contasPagar;
    }

    public void setContasPagar(List<ContasPagar> contasPagar) {
        this.contasPagar = contasPagar;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
       
        ListarContasPagar();
    }
    
    
    
}
