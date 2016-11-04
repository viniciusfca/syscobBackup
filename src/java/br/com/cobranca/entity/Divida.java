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
public class Divida {
    
    private int id;
    
    private Devedor devedor;
    private Pessoa cliente;
    
    private double valorDivida;
    
    private String status;
    private String observacao;
    
    private Date dataCadastro;
    private Date dataCobranca;

    public Devedor getDevedor() {
        return devedor;
    }

    public void setDevedor(Devedor devedor) {
        this.devedor = devedor;
    }

    public Pessoa getCliente() {
        return cliente;
    }

    public void setCliente(Pessoa cliente) { 
           if(cliente ==  null){
                this.cliente = new Pessoa();
           }else{
                this.cliente = cliente;
           }
    }

    public double getValorDivida() {
        return valorDivida;
    }

    public void setValorDivida(double valorDivida) {
        this.valorDivida = valorDivida;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataCobranca() {
        return dataCobranca;
    }

    public void setDataCobranca(Date dataCobranca) {
        this.dataCobranca = dataCobranca;
    }
    
    
    
}
