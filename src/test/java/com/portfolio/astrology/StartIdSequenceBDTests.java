package com.portfolio.astrology;

import org.h2.tools.RunScript;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;


public class StartIdSequenceBDTests {
    public static void startIdSequence() {
        try {
            // Configuração do banco de dados
            String url = "jdbc:h2:mem:testdb";
            String user = "sa";
            String password = "password";

            // Abre a conexão com o banco de dados
            Connection conn = DriverManager.getConnection(url, user, password);

            // Executa o script SQL
            RunScript.execute(conn, new FileReader("sequenceStarter.sql"));

            // Fecha a conexão com o banco de dados
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
