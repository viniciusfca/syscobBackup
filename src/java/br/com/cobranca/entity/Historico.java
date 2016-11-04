/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cobranca.entity;

import java.util.Date;

/**
 *
 * @author Vinicius
 */
public class Historico {

        private int id;
        private int idAnexo;
        
        private String observacao;
        
        private Divida divida;
        private Pessoa pessoa;
        
        private Date dataCadastro;
        private Date dataUltimaCobranca;

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Divida getDivida() {
        return divida;
    }

    public void setDivida(Divida divida) {
        this.divida = divida;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getDataUltimaCobranca() {
        return dataUltimaCobranca;
    }

    public void setDataUltimaCobranca(Date dataUltimaCobranca) {
        this.dataUltimaCobranca = dataUltimaCobranca;
    }

    public int getIdAnexo() {
        return idAnexo;
    }

    public void setIdAnexo(int idAnexo) {
        this.idAnexo = idAnexo;
    }


        
}
