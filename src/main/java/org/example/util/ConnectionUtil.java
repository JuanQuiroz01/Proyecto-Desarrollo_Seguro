package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase utilitaria para gestionar la conexión a la base de datos MySQL.
 */
public class ConnectionUtil {

    // URL de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/bd_01?useSSL=false&serverTimezone=UTC";

    // Usuario de la base de datos
    private static final String USER = "root";

    // Contraseña del usuario
    private static final String PASSWORD = ""; // Cámbiala si tu usuario tiene clave

    /**
     * Método que establece y devuelve una conexión activa a la base de datos.
     *
     * @return Objeto Connection listo para ejecutar consultas.
     * @throws SQLException Si ocurre un error al intentar la conexión.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Registrar explícitamente el driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Error al cargar el driver MySQL", e);
        }

        // Retornar la conexión activa
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

