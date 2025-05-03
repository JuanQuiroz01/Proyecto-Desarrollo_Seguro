package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Clase utilitaria para gestionar la conexión a la base de datos MySQL.
 */
public class ConnectionUtil {

    private static final String URL = "jdbc:mysql://localhost:3306/bd_01";

    private static final String USER = "root";

    private static final String PASSWORD = "";

    /**
     * Método que establece y devuelve una conexión activa a la base de datos.
     *
     * @return Objeto Connection listo para ejecutar consultas.
     * @throws SQLException Si ocurre un error al intentar la conexión.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
