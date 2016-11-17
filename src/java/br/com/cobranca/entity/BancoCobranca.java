/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cobranca.entity;

/**
 *
 * @author Vinicius
 */
public class BancoCobranca {
    
    private int agencia;
    private int cc;
    private int ccDv;
    private String bancoNome;
    private String cedenteNome;
    private String cedenteCnpj;
    private int carteira;
    private String localPagamento;
    private String instrucoes;
    private long nossoNumero;
    private int diasVencimento;
    private long nossoNumeroMaximo;

    public int getAgencia() {
        return agencia;
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    public int getCc() {
        return cc;
    }

    public void setCc(int cc) {
        this.cc = cc;
    }

    public int getCcDv() {
        return ccDv;
    }

    public void setCcDv(int ccDv) {
        this.ccDv = ccDv;
    }

    public String getBancoNome() {
        return bancoNome;
    }

    public void setBancoNome(String bancoNome) {
        this.bancoNome = bancoNome;
    }

    public String getCedenteNome() {
        return cedenteNome;
    }

    public void setCedenteNome(String cedenteNome) {
        this.cedenteNome = cedenteNome;
    }

    public String getCedenteCnpj() {
        return cedenteCnpj;
    }

    public void setCedenteCnpj(String cedenteCnpj) {
        this.cedenteCnpj = cedenteCnpj;
    }

    public int getCarteira() {
        return carteira;
    }

    public void setCarteira(int carteira) {
        this.carteira = carteira;
    }

    public String getLocalPagamento() {
        return localPagamento;
    }

    public void setLocalPagamento(String localPagamento) {
        this.localPagamento = localPagamento;
    }

    public String getInstrucoes() {
        return instrucoes;
    }

    public void setInstrucoes(String instrucoes) {
        this.instrucoes = instrucoes;
    }

    public long getNossoNumero() {
        return nossoNumero;
    }

    public void setNossoNumero(long nossoNumero) {
        this.nossoNumero = nossoNumero;
    }

    public int getDiasVencimento() {
        return diasVencimento;
    }

    public void setDiasVencimento(int diasVencimento) {
        this.diasVencimento = diasVencimento;
    }

    public long getNossoNumeroMaximo() {
        return nossoNumeroMaximo;
    }

    public void setNossoNumeroMaximo(long nossoNumeroMaximo) {
        this.nossoNumeroMaximo = nossoNumeroMaximo;
    }
    
    
}
