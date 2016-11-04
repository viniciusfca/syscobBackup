/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cobranca.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

    //localhost
        private final String URL = "jdbc:mysql://micro2048:3306/AppGestorCobranca";

    private final String USUARIO = "root";
    private final String SENHA = "1234";

    public Connection connection;

    public Connection conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USUARIO, SENHA);

            return connection;
        } catch (Exception e) {
            System.out.println("Erro ao conectar. Motivo: " + e.getMessage());
            return null;
        }
    }

    public void desconectar() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Erro ao desconectar. Motivo: " + e.getMessage());
        }
    }
}
