package org.example.data;

import org.example.domain.Funcionario;
import org.example.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public void insertar(Funcionario funcionario) {
        String sql = "INSERT INTO FUNCIONARIOS (tipo_identificacion, numero_identificacion, nombres, apellidos, estado_civil, sexo, direccion, telefono, fecha_nacimiento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getTipoIdentificacion());
            stmt.setString(2, funcionario.getNumeroIdentificacion());
            stmt.setString(3, funcionario.getNombres());
            stmt.setString(4, funcionario.getApellidos());
            stmt.setString(5, funcionario.getEstadoCivil());
            stmt.setString(6, funcionario.getSexo());
            stmt.setString(7, funcionario.getDireccion());
            stmt.setString(8, funcionario.getTelefono());
            stmt.setDate(9, new java.sql.Date(funcionario.getFechaNacimiento().getTime()));

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al insertar funcionario: " + e.getMessage());
        }
    }

    public List<Funcionario> listar() {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM FUNCIONARIOS";

        try (Connection conn = ConnectionUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setIdFuncionario(rs.getInt("id_funcionario"));
                f.setTipoIdentificacion(rs.getString("tipo_identificacion"));
                f.setNumeroIdentificacion(rs.getString("numero_identificacion"));
                f.setNombres(rs.getString("nombres"));
                f.setApellidos(rs.getString("apellidos"));
                f.setEstadoCivil(rs.getString("estado_civil"));
                f.setSexo(rs.getString("sexo"));
                f.setDireccion(rs.getString("direccion"));
                f.setTelefono(rs.getString("telefono"));
                f.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                lista.add(f);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar funcionarios: " + e.getMessage());
        }

        return lista;
    }

    public void actualizar(Funcionario funcionario) {
        String sql = "UPDATE FUNCIONARIOS SET nombres=?, apellidos=?, estado_civil=?, sexo=?, direccion=?, telefono=?, fecha_nacimiento=? WHERE id_funcionario=?";

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getNombres());
            stmt.setString(2, funcionario.getApellidos());
            stmt.setString(3, funcionario.getEstadoCivil());
            stmt.setString(4, funcionario.getSexo());
            stmt.setString(5, funcionario.getDireccion());
            stmt.setString(6, funcionario.getTelefono());
            stmt.setDate(7, new java.sql.Date(funcionario.getFechaNacimiento().getTime()));
            stmt.setInt(8, funcionario.getIdFuncionario());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al actualizar funcionario: " + e.getMessage());
        }
    }

    public void eliminar(int idFuncionario) {
        String sql = "DELETE FROM FUNCIONARIOS WHERE id_funcionario=?";

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFuncionario);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al eliminar funcionario: " + e.getMessage());
        }
    }
}

