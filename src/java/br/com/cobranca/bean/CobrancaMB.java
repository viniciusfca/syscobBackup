/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cobranca.bean;

import br.com.cobranca.dao.DividaDAO;
import br.com.cobranca.dao.HistoricoDAO;
import br.com.cobranca.entity.Devedor;
import br.com.cobranca.entity.Divida;
import br.com.cobranca.entity.Historico;
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
@ManagedBean(name="CobrancaMB")
@ViewScoped
public class CobrancaMB {
    
    private int parcela;
    
    private double desconto;
    
    private boolean gerarBoleto; 
    
    private List<Divida> dividas;
    
    private DividaDAO dividaDAO;
    private HistoricoDAO historicoDAO;
    
    private Divida divida;
    private Historico historico;
    private List<Historico> historicos;
    
    public CobrancaMB() {
        dividas = new ArrayList<Divida>();
        historicos = new ArrayList<Historico>();
        
        dividaDAO = new DividaDAO();
        historicoDAO = new HistoricoDAO();
        
        divida = new Divida();
        historico = new Historico();
        
        divida.setCliente(new Pessoa());
        divida.setDevedor(new Devedor());
        
        dividas = dividaDAO.dividasDia();
        
    }

    /**
     * Metodo que retorna historicos da dividas escolhida
     */
    public void listarHistorico(){
        historicos = historicoDAO.listarHistorico(divida.getId());
    }
    
    public void adicionarHistorico(){
        int retorno = 0;
        
        historico.setDivida(divida);
        historico.setPessoa(divida.getCliente());
        retorno = historicoDAO.adicionarHistorico(historico);
        
        if(retorno > 0){
            Util.mostrarMensagemSucesso("Informação", "Atendimento incluído com sucesso!");
            divida = dividaDAO.atualizarDivida(divida);
            dividas = dividaDAO.dividasDia();
            historicos = historicoDAO.listarHistorico(divida.getId());
        }else{
            Util.mostrarMensagemErro("Informação", "Falha ao incluir o atendimento!");
        }
        
    }
    
    
    public List<Divida> getDividas() {
        return dividas;
    }

    public void setDividas(List<Divida> dividas) {
        this.dividas = dividas;
    }

    public Divida getDivida() {
        return divida;
    }

    public void setDivida(Divida divida) {
        this.divida = divida;
        listarHistorico();
    }

    public int getParcela() {
        return parcela;
    }

    public void setParcela(int parcela) {
        this.parcela = parcela;
    }

    public boolean isGerarBoleto() {
        return gerarBoleto;
    }

    public void setGerarBoleto(boolean gerarBoleto) {
        this.gerarBoleto = gerarBoleto;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public Historico getHistorico() {
        return historico;
    }

    public void setHistorico(Historico historico) {
        this.historico = historico;
    }

    public List<Historico> getHistoricos() {
        return historicos;
    }

    public void setHistoricos(List<Historico> historicos) {
        this.historicos = historicos;
    }
    
    
    
}
