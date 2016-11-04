/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cobranca.entity;

import br.com.cobranca.util.Util;
import java.util.Date;

public class Pessoa {
    
    private Integer id;
    private String tipo;
    
    private String nome;
    private String sexo;
    private String cpf;
    private String rg;
    
    private String telefone;
    private String celular;
    private String email;
    
    private String endereco;
    private Long numero;
    private String bairro;
    private String complemento;
    private String uf;
    private String cidade;
    private String username;
    private String senha;
    
    private Date dataNascimento;
    private Date dataCadastro;

    public Integer getId() {
        
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if(nome == null || nome == ""){
            this.nome = "";
        }else{
            this.nome = nome;
        }
        
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        
        if(cpf != null){
            this.cpf = Util.retirarMascara(cpf);
        }
        else{
            this.cpf = cpf;
        }
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        
        if(rg != null){
            this.rg = Util.retirarMascara(rg);
        }
        else{
            this.rg = rg;
        }
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        
        if(telefone != null){
            this.telefone = Util.retirarMascara(telefone);
        }
        else{
            this.telefone = telefone;
        }
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        if(celular != null){
            this.celular = Util.retirarMascara(celular);
        }
        
        else{
            this.celular = celular;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
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

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

}
