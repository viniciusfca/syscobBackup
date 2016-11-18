/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cobranca.bean;

import br.com.cobranca.dao.ContasReceberDAO;
import br.com.cobranca.entity.ContasReceber;
import br.com.cobranca.util.Util;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Vinicius
 */
@ManagedBean(name = "contasReceberMB")
@ViewScoped
public class ContasReceberMB {

    private ContasReceberDAO contasReceberDAO;
    private ContasReceber contasReceber;

    private String nossoNumero;

    /**
     * Construtor
     */
    public ContasReceberMB() {
        contasReceber = new ContasReceber();
        contasReceberDAO = new ContasReceberDAO();
        nossoNumero = "";
    }

    /**
     * Metodo que retorno contas Receber pelo nossoNumero
     */
    public void buscarContasReceber() {

        while (nossoNumero.length() < 8) {
            nossoNumero = "0" + nossoNumero;
        }

        contasReceber = contasReceberDAO.buscarContasReceber(nossoNumero);
        nossoNumero = "";
    }

    /**
     * Metodo que baixa a divida
     */
    public void quitarDivida() {
        String retorno = contasReceberDAO.baixarReceber(contasReceber);

        if (retorno.equals("OK")) {
            Util.mostrarMensagemSucesso("Informação", "Dívida quitada com sucesso");
        } else {
            Util.mostrarMensagemErro("Informação", "Falhar ao quitar.");
        }
    }

    public String getNossoNumero() {
        return nossoNumero;
    }

    public void setNossoNumero(String nossoNumero) {
        this.nossoNumero = nossoNumero;
    }

    public ContasReceber getContasReceber() {
        return contasReceber;
    }

    public void setContasReceber(ContasReceber contasReceber) {
        this.contasReceber = contasReceber;
    }

}
