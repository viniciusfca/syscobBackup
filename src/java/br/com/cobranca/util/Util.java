/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cobranca.util;

import br.com.cobranca.entity.Pessoa;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class Util {

    /**
     * Metodo que valida email
     *
     * @param email
     * @return
     */
    public static boolean isEmailValido(String email) {
        if ((email == null) || (email.trim().length() == 0)) {
            return false;
        }
        String emailPattern = "\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";
        Pattern pattern = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String StringPrimeiraLetraMaiuscula(String str) {

        if (!str.isEmpty()) {
            return str.substring(0, 1).toUpperCase().concat(str.substring(1));
        }

        return "";
    }

    public static <T> boolean alterarRegistro(T objAlterado, Class<T> classe, Connection con, String strWhere) throws Exception, SQLException {

        if (strWhere == null || strWhere.trim().equals("")) {
            return false;
        }

        PreparedStatement ps = null;
        ResultSet rs = null;

        T objOriginal = classe.newInstance();

        try {

            // Recuperar objeto original no banco de dados
            String nomeTabela = objAlterado.getClass().getSimpleName();
            String strSql = "SELECT * FROM " + nomeTabela + " " + strWhere;

            ps = con.prepareStatement(strSql);
            rs = ps.executeQuery();

            if (rs.next()) {
                objOriginal = Util.atribuirValores(classe, rs);
            } else {
                return false;
            }

            rs.close();
            ps.close();

            // Comparar valores dos dois objetos
            strSql = "UPDATE " + nomeTabela + " SET ";

            boolean efetuarAlteracao;
            boolean usarVirgula = false;

            for (Field field : objAlterado.getClass().getDeclaredFields()) {

                efetuarAlteracao = false;

                String nomeColuna = field.getName();
                String tipoColuna = field.getType().getSimpleName();

                if (tipoColuna.toUpperCase().contains("INT")) {
                    tipoColuna = "Int";
                } else {
                    tipoColuna = StringPrimeiraLetraMaiuscula(tipoColuna);
                }

                // obj . get + nome do campo
                Method met = classe.getMethod("get" + StringPrimeiraLetraMaiuscula(field.getName()));

                if (tipoColuna.equals("Int")) {

                    Integer valorOriginal = (Integer) met.invoke(objOriginal);
                    Integer valorAlterado = (Integer) met.invoke(objAlterado);

                    if (!valorOriginal.equals(valorAlterado)) {
                        efetuarAlteracao = true;
                    }

                } else if (tipoColuna.equals("String")) {

                    String valorOriginal = (String) met.invoke(objOriginal);
                    String valorAlterado = (String) met.invoke(objAlterado);

                    if (!valorOriginal.equals(valorAlterado)) {
                        efetuarAlteracao = true;
                    }

                } else if (tipoColuna.equals("Double")) {

                    Double valorOriginal = (Double) met.invoke(objOriginal);
                    Double valorAlterado = (Double) met.invoke(objAlterado);

                    if (!valorOriginal.equals(valorAlterado)) {
                        efetuarAlteracao = true;
                    }

                } else if (tipoColuna.equals("Float")) {

                    Float valorOriginal = (Float) met.invoke(objOriginal);
                    Float valorAlterado = (Float) met.invoke(objAlterado);

                    if (!valorOriginal.equals(valorAlterado)) {
                        efetuarAlteracao = true;
                    }

                } else if (tipoColuna.equals("Long")) {

                    Long valorOriginal = (Long) met.invoke(objOriginal);
                    Long valorAlterado = (Long) met.invoke(objAlterado);

                    if (!valorOriginal.equals(valorAlterado)) {
                        efetuarAlteracao = true;
                    }

                } else if (tipoColuna.equals("Boolean")) {

                    Boolean valorOriginal = (Boolean) met.invoke(objOriginal);
                    Boolean valorAlterado = (Boolean) met.invoke(objAlterado);

                    if (!valorOriginal.equals(valorAlterado)) {
                        efetuarAlteracao = true;
                    }

                } else if (tipoColuna.equals("Date")) {

                    Date valorOriginal = (Date) met.invoke(objOriginal);
                    Date valorAlterado = (Date) met.invoke(objAlterado);

                    if (!valorOriginal.equals(valorAlterado)) {
                        efetuarAlteracao = true;
                    }

                } else {
                    return false;
                }

                if (efetuarAlteracao) {

                    if (usarVirgula) {
                        strSql = strSql + ", ";
                        //usarVirgula = false;
                    }
                    
                    strSql = strSql + nomeColuna + " = ? ";
                    usarVirgula = true;
                }

            }

            //Se não houve alteração, retorna falso
            if (!strSql.contains("?")) {
                return true;
            }

            strSql = strSql + strWhere;
            ps = con.prepareStatement(strSql);

            int i = 1;

            // ps.set?()
            for (Field field : objAlterado.getClass().getDeclaredFields()) {

                String nomeColuna = field.getName();
                String tipoColuna = field.getType().getSimpleName();

                if (tipoColuna.toUpperCase().contains("INT")) {
                    tipoColuna = "Int";
                } else {
                    tipoColuna = StringPrimeiraLetraMaiuscula(tipoColuna);
                }

                // obj . get + nome do campo
                Method met = classe.getMethod("get" + StringPrimeiraLetraMaiuscula(field.getName()));

                if (tipoColuna.equals("Int")) {

                    Integer valorOriginal = (Integer) met.invoke(objOriginal);
                    Integer valorAlterado = (Integer) met.invoke(objAlterado);

                    if (!valorOriginal.equals(valorAlterado)) {
                        if (valorAlterado == null) {
                            ps.setString(i, null);
                        } else {
                            ps.setInt(i, valorAlterado);
                        }
                        i++;
                    }

                } else if (tipoColuna.equals("String")) {
                    String valorOriginal = (String) met.invoke(objOriginal);
                    String valorAlterado = (String) met.invoke(objAlterado);

                    if (!valorOriginal.equals(valorAlterado)) {
                        ps.setString(i, valorAlterado);
                        i++;
                    }

                } else if (tipoColuna.equals("Double")) {

                    Double valorOriginal = (Double) met.invoke(objOriginal);
                    Double valorAlterado = (Double) met.invoke(objAlterado);

                    if (!valorOriginal.equals(valorAlterado)) {
                        if (valorAlterado == null) {
                            ps.setString(i, null);
                        } else {
                            ps.setDouble(i, valorAlterado);
                        }
                        i++;
                    }

                } else if (tipoColuna.equals("Float")) {

                    Float valorOriginal = (Float) met.invoke(objOriginal);
                    Float valorAlterado = (Float) met.invoke(objAlterado);

                    if (!valorOriginal.equals(valorAlterado)) {
                        if (valorAlterado == null) {
                            ps.setString(i, null);
                        } else {
                            ps.setFloat(i, valorAlterado);
                        }
                        i++;
                    }

                } else if (tipoColuna.equals("Long")) {

                    Long valorOriginal = (Long) met.invoke(objOriginal);
                    Long valorAlterado = (Long) met.invoke(objAlterado);

                    if (!valorOriginal.equals(valorAlterado)) {
                        if (valorAlterado == null) {
                            ps.setString(i, null);
                        } else {
                            ps.setLong(i, valorAlterado);
                        }
                        i++;
                    }

                } else if (tipoColuna.equals("Boolean")) {

                    Boolean valorOriginal = (Boolean) met.invoke(objOriginal);
                    Boolean valorAlterado = (Boolean) met.invoke(objAlterado);

                    if (!valorOriginal.equals(valorAlterado)) {
                        if (valorAlterado == null) {
                            ps.setString(i, null);
                        } else {
                            ps.setBoolean(i, valorAlterado);
                        }
                        i++;
                    }

                } else if (tipoColuna.equals("Date")) {

                    Date valorOriginal = (Date) met.invoke(objOriginal);
                    Date valorAlterado = (Date) met.invoke(objAlterado);

                    if (!valorOriginal.equals(valorAlterado)) {
                        if (valorAlterado == null) {
                            ps.setString(i, null);
                        } else {
                            ps.setDate(i, new java.sql.Date(valorAlterado.getTime()));
                        }
                        i++;
                    }

                } else {
                    return false;
                }

            }

            // fim
            int qtdLinhasAfetadas = ps.executeUpdate();

            if (qtdLinhasAfetadas <= 0) {
                return false;
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {

            if (rs != null) {
                rs.close();
            }

            if (ps != null) {
                ps.close();
            }
        }

        return true;

    }

    public static <T> int inserirRegistro(T obj, Connection con) throws Exception {

        int id = 0;

        String nomeTabela = obj.getClass().getSimpleName();

        String strSql = "INSERT INTO " + nomeTabela.toUpperCase() + " (";
        boolean usarVirgula = false;

        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            if (usarVirgula) {
                strSql = strSql + ", ";
            }

            strSql = strSql + field.getName();

            if (!usarVirgula) {
                usarVirgula = true;
            }
        }

        strSql = strSql + ") VALUES (";

        usarVirgula = false;

        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            if (usarVirgula) {
                strSql = strSql + ", ";
            }

            strSql = strSql + "?";

            if (!usarVirgula) {
                usarVirgula = true;
            }
        }

        strSql = strSql + ")";

        PreparedStatement ps = con.prepareStatement(strSql, Statement.RETURN_GENERATED_KEYS);

        try {

            int i = 1;
            for (Field field : obj.getClass().getDeclaredFields()) {

                String tipoColuna = field.getType().getSimpleName();

                if (tipoColuna.toUpperCase().contains("INT")) {
                    tipoColuna = "Int";
                } else {
                    tipoColuna = StringPrimeiraLetraMaiuscula(tipoColuna);
                }

                // obj . get + nome do campo
                Method met = obj.getClass().getMethod("get" + StringPrimeiraLetraMaiuscula(field.getName()));

                if (tipoColuna.equals("Int")) {

                    Integer valor = (Integer) met.invoke(obj);

                    if (valor == null) {
                        ps.setString(i, null);
                    } else {
                        ps.setInt(i, valor);
                    }

                } else if (tipoColuna.equals("String")) {
                    String valor = (String) met.invoke(obj);
                    ps.setString(i, valor);
                } else if (tipoColuna.equals("Double")) {

                    Double valor = (Double) met.invoke(obj);

                    if (valor == null) {
                        ps.setString(i, null);
                    } else {
                        ps.setDouble(i, valor);
                    }

                } else if (tipoColuna.equals("Float")) {

                    Float valor = (Float) met.invoke(obj);

                    if (valor == null) {
                        ps.setString(i, null);
                    } else {
                        ps.setFloat(i, valor);
                    }

                } else if (tipoColuna.equals("Long")) {

                    Long valor = (Long) met.invoke(obj);

                    if (valor == null) {
                        ps.setString(i, null);
                    } else {
                        ps.setLong(i, valor);
                    }

                } else if (tipoColuna.equals("Boolean")) {
                    Boolean valor = (Boolean) met.invoke(obj);

                    if (valor == null) {
                        ps.setString(i, null);
                    } else {
                        ps.setBoolean(i, valor);
                    }

                } else if (tipoColuna.equals("Date")) {
                    Date valor = (Date) met.invoke(obj);

                    if (valor == null) {
                        ps.setString(i, null);
                    } else {
                        ps.setDate(i, new java.sql.Date(valor.getTime()));
                    }

                } else {
                    return 0;
                }

                i++;
            }

            int qtdLinhasAfetadas = ps.executeUpdate();

            if (qtdLinhasAfetadas > 0) {

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getInt(1);
                    }
                }

            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            ps.close();
        }

        return id;
    }

    public static <T> T atribuirValores(Class<T> classe, ResultSet rs) throws Exception {

        T obj = classe.newInstance();

        //Percorre lista de colunas
        for (int j = 0; j < rs.getMetaData().getColumnCount(); j++) {

            String nomeColuna = rs.getMetaData().getColumnName(j + 1);

            Field field = obj.getClass().getDeclaredField(nomeColuna);
            field.setAccessible(true);

            String tipoColuna = field.getType().getSimpleName();

            if (tipoColuna.toUpperCase().contains("INT")) {
                tipoColuna = "Int";
            } else {
                tipoColuna = StringPrimeiraLetraMaiuscula(tipoColuna);
            }

            // rs . get + tipo da coluna, passando o nome da coluna como parâmetro
            Method met = rs.getClass().getMethod("get" + tipoColuna, String.class);

            if (tipoColuna.equals("Int")) {
                Integer valor = (Integer) met.invoke(rs, nomeColuna);

                met = obj.getClass().getMethod("set" + StringPrimeiraLetraMaiuscula(nomeColuna), Integer.class);
                met.invoke(obj, valor);
            } else if (tipoColuna.equals("String")) {
                String valor = (String) met.invoke(rs, nomeColuna);

                met = obj.getClass().getMethod("set" + StringPrimeiraLetraMaiuscula(nomeColuna), String.class);
                met.invoke(obj, valor);
            } else if (tipoColuna.equals("Double")) {
                Double valor = (Double) met.invoke(rs, nomeColuna);

                met = obj.getClass().getMethod("set" + StringPrimeiraLetraMaiuscula(nomeColuna), Double.class);
                met.invoke(obj, valor);
            } else if (tipoColuna.equals("Float")) {
                Float valor = (Float) met.invoke(rs, nomeColuna);

                met = obj.getClass().getMethod("set" + StringPrimeiraLetraMaiuscula(nomeColuna), Float.class);
                met.invoke(obj, valor);
            } else if (tipoColuna.equals("Long")) {
                Long valor = (Long) met.invoke(rs, nomeColuna);

                met = obj.getClass().getMethod("set" + StringPrimeiraLetraMaiuscula(nomeColuna), Long.class);
                met.invoke(obj, valor);
            } else if (tipoColuna.equals("Boolean")) {
                Boolean valor = (Boolean) met.invoke(rs, nomeColuna);

                met = obj.getClass().getMethod("set" + StringPrimeiraLetraMaiuscula(nomeColuna), Boolean.class);
                met.invoke(obj, valor);
            } else if (tipoColuna.equals("Date")) {
                Date valor = (Date) met.invoke(rs, nomeColuna);

                met = obj.getClass().getMethod("set" + StringPrimeiraLetraMaiuscula(nomeColuna), Date.class);
                met.invoke(obj, valor);
            } else {
                break;
            }
        }

        return obj;

    }

    public static String retirarMascara(String str) {

        if (str != null) {
            return str.replace(".", "")
                    .replace("/", "")
                    .replace("-", "")
                    .replace("(", "")
                    .replace(")", "")
                    .replace(" ", "")
                    .trim();
        }

        return null;

    }

    /**
     * Método que mostra mensagem de sucesso
     */
    public static void mostrarMensagemSucesso(String titulo, String mensagem) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, mensagem));
    }

    /**
     * Método que mostra mensagem de alerta
     */
    public static void mostrarMensagemAlerta(String titulo, String mensagem) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, mensagem));
    }

    /**
     * Método que mostra mensagem de erro
     */
    public static void mostrarMensagemErro(String titulo, String mensagem) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, mensagem));
    }

    /**
     * METODO VALIDA O CPF
     *
     * @param pCPF
     * @return
     */
    public static boolean isCPF(String pCPF) {
        String CPF = pCPF.replace(".", "").replace("-", "").trim();
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") || CPF.equals("11111111111")
                || CPF.equals("22222222222") || CPF.equals("33333333333")
                || CPF.equals("44444444444") || CPF.equals("55555555555")
                || CPF.equals("66666666666") || CPF.equals("77777777777")
                || CPF.equals("88888888888") || CPF.equals("99999999999")
                || (CPF.length() != 11)) {
            return (false);
        }

        char dig10, dig11;
        int sm, i, r, num, peso;

// "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
// Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
// converte o i-esimo caractere do CPF em um numero:
// por exemplo, transforma o caractere '0' no inteiro 0         
// (48 eh a posicao de '0' na tabela ASCII)         
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig10 = '0';
            } else {
                dig10 = (char) (r + 48); // converte no respectivo caractere numerico
            }
// Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig11 = '0';
            } else {
                dig11 = (char) (r + 48);
            }

// Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {
                return (true);
            } else {
                return (false);
            }
        } catch (Exception erro) {
            return (false);
        }
    }

     /**
     * Metodo que coloca o usuario na sessao
     * @param usuario 
     */
    public static void colocarUsuarioSessao(Pessoa pessoa){
        HttpSession ses = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        ses.setAttribute("usuarioLogado", pessoa);            
    }
    
    

    /**
     * Metodo que remove usuario da sessao
     */
    public static void retirarUsuarioSessao(){
        HttpSession ses = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        ses.invalidate();
    }
    
     /**
     * Metodo que redireciona pagina
     * @param url 
     */
    public static void redirecionar(String url){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {        
            ec.redirect(url);
        } catch (IOException ex) {
            mostrarMensagemErro("Informação", ex.getMessage());
        }
    }
    
    
    
    /**
     * Metodo que retorna o usuario logado
     * @return 
     */
    public static Pessoa getUsuarioLogado(){
        return (Pessoa) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
    }
    
}
