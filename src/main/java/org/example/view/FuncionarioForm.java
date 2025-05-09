package org.example.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class FuncionarioForm extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtTipoIdentificacion, txtNumeroIdentificacion, txtNombres, txtApellidos;
    private JTextField txtEstadoCivil, txtSexo, txtDireccion, txtTelefono, txtFechaNacimiento;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnLimpiar;
    private FuncionarioDAO funcionarioDAO;

    public FuncionarioForm() {
        try {
            funcionarioDAO = new FuncionarioDAO();
            initComponents();
            cargarDatos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initComponents() {
        setTitle("Gestión de Funcionarios");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Panel de formulario
        JPanel panelForm = new JPanel(new GridLayout(9, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Funcionario"));

        panelForm.add(new JLabel("Tipo Identificación:"));
        txtTipoIdentificacion = new JTextField();
        panelForm.add(txtTipoIdentificacion);

        panelForm.add(new JLabel("Número Identificación:"));
        txtNumeroIdentificacion = new JTextField();
        panelForm.add(txtNumeroIdentificacion);

        panelForm.add(new JLabel("Nombres:"));
        txtNombres = new JTextField();
        panelForm.add(txtNombres);

        panelForm.add(new JLabel("Apellidos:"));
        txtApellidos = new JTextField();
        panelForm.add(txtApellidos);

        panelForm.add(new JLabel("Estado Civil:"));
        txtEstadoCivil = new JTextField();
        panelForm.add(txtEstadoCivil);

        panelForm.add(new JLabel("Sexo:"));
        txtSexo = new JTextField();
        panelForm.add(txtSexo);

        panelForm.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField();
        panelForm.add(txtDireccion);

        panelForm.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelForm.add(txtTelefono);

        panelForm.add(new JLabel("Fecha Nacimiento (YYYY-MM-DD):"));
        txtFechaNacimiento = new JTextField();
        panelForm.add(txtFechaNacimiento);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        // Tabla de datos
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Tipo ID");
        model.addColumn("Número ID");
        model.addColumn("Nombres");
        model.addColumn("Apellidos");
        model.addColumn("Estado Civil");
        model.addColumn("Sexo");
        model.addColumn("Dirección");
        model.addColumn("Teléfono");
        model.addColumn("Fecha Nacimiento");

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Configurar layout principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.add(panelForm, BorderLayout.NORTH);
        panelPrincipal.add(panelBotones, BorderLayout.CENTER);
        panelPrincipal.add(scrollPane, BorderLayout.SOUTH);

        add(panelPrincipal);

        // Eventos
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarFuncionario();
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarFuncionario();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarFuncionario();
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarFuncionario();
            }
        });
    }

    private void cargarDatos() {
        try {
            List<Funcionarios> funcionarios = funcionarioDAO.seleccionar();
            model.setRowCount(0); // Limpiar tabla

            for (Funcionarios funcionario : funcionarios) {
                Object[] row = {
                        funcionario.getIdFuncionario(),
                        funcionario.getTipoIdentificacion(),
                        funcionario.getNumeroIdentificacion(),
                        funcionario.getNombres(),
                        funcionario.getApellidos(),
                        funcionario.getEstadoCivil(),
                        funcionario.getSexo(),
                        funcionario.getDireccion(),
                        funcionario.getTelefono(),
                        funcionario.getFechaNacimiento()
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarFuncionario() {
        try {
            Funcionarios funcionario = new Funcionarios(
                    txtTipoIdentificacion.getText(),
                    txtNumeroIdentificacion.getText(),
                    txtNombres.getText(),
                    txtApellidos.getText(),
                    txtEstadoCivil.getText(),
                    txtSexo.getText(),
                    txtDireccion.getText(),
                    txtTelefono.getText(),
                    java.sql.Date.valueOf(txtFechaNacimiento.getText())
            );

            int resultado = funcionarioDAO.insertar(funcionario);
            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "Funcionario agregado con éxito",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatos();
                limpiarCampos();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar funcionario: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use YYYY-MM-DD",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarFuncionario() {
        int filaSeleccionada = table.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un funcionario para actualizar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int id = (int) model.getValueAt(filaSeleccionada, 0);
            Funcionarios funcionario = new Funcionarios(
                    txtTipoIdentificacion.getText(),
                    txtNumeroIdentificacion.getText(),
                    txtNombres.getText(),
                    txtApellidos.getText(),
                    txtEstadoCivil.getText(),
                    txtSexo.getText(),
                    txtDireccion.getText(),
                    txtTelefono.getText(),
                    java.sql.Date.valueOf(txtFechaNacimiento.getText())
            );
            funcionario.setIdFuncionario(id);

            int resultado = funcionarioDAO.actualizar(funcionario);
            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "Funcionario actualizado con éxito",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatos();
                limpiarCampos();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar funcionario: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use YYYY-MM-DD",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarFuncionario() {
        int filaSeleccionada = table.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un funcionario para eliminar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar este funcionario?", "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                int id = (int) model.getValueAt(filaSeleccionada, 0);
                Funcionarios funcionario = new Funcionarios();
                funcionario.setIdFuncionario(id);

                int resultado = funcionarioDAO.eliminar(funcionario);
                if (resultado > 0) {
                    JOptionPane.showMessageDialog(this, "Funcionario eliminado con éxito",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarDatos();
                    limpiarCampos();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar funcionario: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void seleccionarFuncionario() {
        int filaSeleccionada = table.getSelectedRow();
        if (filaSeleccionada != -1) {
            txtTipoIdentificacion.setText(model.getValueAt(filaSeleccionada, 1).toString());
            txtNumeroIdentificacion.setText(model.getValueAt(filaSeleccionada, 2).toString());
            txtNombres.setText(model.getValueAt(filaSeleccionada, 3).toString());
            txtApellidos.setText(model.getValueAt(filaSeleccionada, 4).toString());
            txtEstadoCivil.setText(model.getValueAt(filaSeleccionada, 5).toString());
            txtSexo.setText(model.getValueAt(filaSeleccionada, 6).toString());
            txtDireccion.setText(model.getValueAt(filaSeleccionada, 7).toString());
            txtTelefono.setText(model.getValueAt(filaSeleccionada, 8).toString());
            txtFechaNacimiento.setText(model.getValueAt(filaSeleccionada, 9).toString());
        }
    }

    private void limpiarCampos() {
        txtTipoIdentificacion.setText("");
        txtNumeroIdentificacion.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtEstadoCivil.setText("");
        txtSexo.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtFechaNacimiento.setText("");
        table.clearSelection();
    }
}