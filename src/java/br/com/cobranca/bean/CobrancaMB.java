/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cobranca.bean;

import br.com.cobranca.dao.ContasReceberDAO;
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
import org.primefaces.context.RequestContext;

/**
 *
 * @author Vinicius
 */
@ManagedBean(name = "CobrancaMB")
@ViewScoped
public class CobrancaMB {

    private int parcela;

    private String filePDF;

    private double desconto;

    private boolean gerarBoleto;

    private List<Divida> dividas;
    private List<Divida> dividasDevedor;
    private DividaDAO dividaDAO;
    private HistoricoDAO historicoDAO;
    private ContasReceberDAO contasReceberDAO;

    private Divida divida;
    private Devedor devedor;
    private Historico historico;
    private List<Historico> historicos;

    public CobrancaMB() {
        dividas = new ArrayList<Divida>();
        dividasDevedor = new ArrayList<Divida>();
        historicos = new ArrayList<Historico>();

        dividaDAO = new DividaDAO();
        historicoDAO = new HistoricoDAO();
        contasReceberDAO = new ContasReceberDAO();

        divida = new Divida();
        historico = new Historico();

        divida.setCliente(new Pessoa());
        divida.setDevedor(new Devedor());

        dividas = dividaDAO.dividasDia();
        devedor = new Devedor();
        
        if(Util.getUsuarioLogado().getTipo().equals("C")){
            dividas = dividaDAO.dividasCliente(Util.getUsuarioLogado().getId());
        }

    }

    /**
     * Metodo que retorna historicos da dividas escolhida
     */
    public void listarHistorico() {
        historicos = historicoDAO.listarHistorico(divida.getId());
    }

    public void adicionarHistorico() {
        int retorno = 0;

        historico.setDivida(divida);
        historico.setPessoa(divida.getCliente());

        if (gerarBoleto) {
            if (desconto > 0) {
                desconto = desconto / 100;
                desconto = divida.getValorDivida() * desconto;
                divida.setValorDivida(divida.getValorDivida() - desconto);
                desconto = 0;
            }

            filePDF = Util.gerarBoleto(divida.getDevedor(), divida);
        }

        if (filePDF.equals(null) || filePDF.equals("") && gerarBoleto) {
            Util.mostrarMensagemErro("Informação", "Falha ao gerar Boleto");
        } else {

            retorno = historicoDAO.adicionarHistorico(historico);
            if (retorno > 0) {
                Util.mostrarMensagemSucesso("Informação", "Atendimento incluído com sucesso!");
                divida = dividaDAO.atualizarDivida(divida);
                dividas = dividaDAO.dividasDia();
                historicos = historicoDAO.listarHistorico(divida.getId());
                
                if(!filePDF.equals(null) || !filePDF.equals("")){
                    RequestContext.getCurrentInstance().update("formCadastro");
                    RequestContext.getCurrentInstance().execute("PF('dlgBoleto').show()");
                    filePDF = null;
                    
                    divida = new Divida();
                    divida.setDevedor(new Devedor());
                    historico = new Historico();
                    historicos.clear();
                    dividasDevedor.clear();
                    
                    
                }
                
            } else {
                Util.mostrarMensagemErro("Informação", "Falha ao incluir o atendimento!");
            }

        }
    }
    
    public void atualizarPagina(){
        RequestContext.getCurrentInstance().update("@form");
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

    public String getFilePDF() {
        return filePDF;
    }

    public void setFilePDF(String filePDF) {
        this.filePDF = filePDF;
    }

    public Devedor getDevedor() {
        return devedor;
    }

    public void setDevedor(Devedor devedor) {
        this.devedor = devedor;
        dividasDevedor = dividaDAO.dividasDevedor(devedor.getId());
        RequestContext.getCurrentInstance().execute("PF('dlgBoleto').hide()");
    }

    public List<Divida> getDividasDevedor() {
        return dividasDevedor;
    }

    public void setDividasDevedor(List<Divida> dividasDevedor) {
        this.dividasDevedor = dividasDevedor;
    }
    
    

    
}
