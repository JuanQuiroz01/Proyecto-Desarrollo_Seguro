package org.example;

import org.example.view.FuncionarioForm;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FuncionarioForm form = new FuncionarioForm();
            form.setVisible(true);
        });
    }
}