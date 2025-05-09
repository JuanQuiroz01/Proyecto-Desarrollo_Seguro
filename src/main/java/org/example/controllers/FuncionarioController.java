package org.example.controllers;

import org.example.data.FuncionarioDAO;
import org.example.domain.Funcionario;

import java.util.List;

public class FuncionarioController {
    private final FuncionarioDAO funcionarioDAO;

    public FuncionarioController() {
        this.funcionarioDAO = new FuncionarioDAO();
    }

    // Crear
    public void agregarFuncionario(Funcionario funcionario) {
        funcionarioDAO.insertar(funcionario);
    }

    // Leer
    public List<Funcionario> obtenerFuncionarios() {
        return funcionarioDAO.listar();
    }

    // Actualizar
    public void actualizarFuncionario(Funcionario funcionario) {
        funcionarioDAO.actualizar(funcionario);
    }

    // Eliminar
    public void eliminarFuncionario(int idFuncionario) {
        funcionarioDAO.eliminar(idFuncionario);
    }
}
