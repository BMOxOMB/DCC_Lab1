/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cenfotec.componentes.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author moren
 */


public class Conexion {

// Se agrega serverTimezone para evitar errores de compatibilidad y useSSL=false si no tienes certificado
    private static final String URL = "jdbc:mysql://localhost:3306/SistemaGestionPersonas?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "!Q02w12e22r";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver"; // Driver moderno

    public static Connection getConexion() throws SQLException {
        try {
            // Registramos el driver manualmente (necesario en entornos de escritorio)
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver de MySQL (JAR faltante): " + e.getMessage());
        }
    }
}
