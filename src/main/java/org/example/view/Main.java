package org.example.view;

import org.example.controllers.FuncionarioController;
import org.example.domain.Funcionario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
/**
 * Ventana principal de la aplicación.
 * Permite agregar, actualizar, eliminar y listar funcionarios usando Swing.
 */
public class Main extends JFrame {

    // Campos de texto para capturar datos del funcionario
    private JTextField txtId, txtTipoId, txtNumeroId, txtNombres, txtApellidos, txtEstadoCivil, txtSexo, txtDireccion, txtTelefono, txtFechaNacimiento;

    // Tabla y modelo para mostrar datos
    private JTable table;
    private DefaultTableModel model;
    // Controlador que conecta la vista con la lógica (DAO)
    private FuncionarioController controller;

    public Main() {
        controller = new FuncionarioController(); // se inicializa el controlador
        setTitle("Gestión de Funcionarios");
        setSize(900, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel superior con formulario
        JPanel formPanel = new JPanel(new GridLayout(5, 4, 10, 10));

        txtId = new JTextField();
        txtTipoId = new JTextField();
        txtNumeroId = new JTextField();
        txtNombres = new JTextField();
        txtApellidos = new JTextField();
        txtEstadoCivil = new JTextField();
        txtSexo = new JTextField();
        txtDireccion = new JTextField();
        txtTelefono = new JTextField();
        txtFechaNacimiento = new JTextField();
        // Agrega cada campo al panel con su etiqueta
        formPanel.add(new JLabel("Tipo Documendo Identidad:")); formPanel.add(txtTipoId);
        formPanel.add(new JLabel("Número Cédula:")); formPanel.add(txtNumeroId);
        formPanel.add(new JLabel("Nombres:")); formPanel.add(txtNombres);
        formPanel.add(new JLabel("Apellidos:")); formPanel.add(txtApellidos);
        formPanel.add(new JLabel("Estado Civil:")); formPanel.add(txtEstadoCivil);
        formPanel.add(new JLabel("Sexo (M/F):")); formPanel.add(txtSexo);
        formPanel.add(new JLabel("Dirección:")); formPanel.add(txtDireccion);
        formPanel.add(new JLabel("Teléfono:")); formPanel.add(txtTelefono);
        formPanel.add(new JLabel("Fecha Nacimiento (yyyy-MM-dd):")); formPanel.add(txtFechaNacimiento);

        add(formPanel, BorderLayout.NORTH);

        // Tabla central para visualizar funcionarios
        model = new DefaultTableModel(new String[]{"ID", "Tipo ID", "Número ID", "Nombres", "Apellidos", "Estado Civil", "Sexo", "Dirección", "Teléfono", "Nacimiento"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Botones
        JPanel buttonPanel = new JPanel();

        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        buttonPanel.add(btnAgregar);
        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnLimpiar);

        add(buttonPanel, BorderLayout.SOUTH);

        // Eventos
        btnAgregar.addActionListener(e -> agregarFuncionario());
        btnActualizar.addActionListener(e -> actualizarFuncionario());
        btnEliminar.addActionListener(e -> eliminarFuncionario());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        // Cuando se selecciona una fila en la tabla, se cargan los datos al formulario
        table.getSelectionModel().addListSelectionListener(e -> cargarFuncionario());

        cargarFuncionarios();
        setVisible(true);
    }

    private void agregarFuncionario() {
        try {
            Funcionario f = leerFormulario();
            controller.agregarFuncionario(f);
            cargarFuncionarios();
            limpiarCampos();
        } catch (Exception ex) {
            mostrarError(ex);
        }
    }

    private void actualizarFuncionario() {
        try {
            Funcionario f = leerFormulario();
            f.setIdFuncionario(obtenerIdSeleccionado());
            controller.actualizarFuncionario(f);
            cargarFuncionarios();
            limpiarCampos();
        } catch (Exception ex) {
            mostrarError(ex);
        }
    }

    private void eliminarFuncionario() {
        try {
            int id = obtenerIdSeleccionado();
            controller.eliminarFuncionario(id);
            cargarFuncionarios();
            limpiarCampos();
        } catch (Exception ex) {
            mostrarError(ex);
        }
    }

    private void cargarFuncionarios() {
        model.setRowCount(0);
        List<Funcionario> lista = controller.obtenerFuncionarios();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Funcionario f : lista) {
            model.addRow(new Object[]{
                    f.getIdFuncionario(),
                    f.getTipoIdentificacion(),
                    f.getNumeroIdentificacion(),
                    f.getNombres(),
                    f.getApellidos(),
                    f.getEstadoCivil(),
                    f.getSexo(),
                    f.getDireccion(),
                    f.getTelefono(),
                    sdf.format(f.getFechaNacimiento())
            });
        }
    }

    private Funcionario leerFormulario() throws Exception {
        // Validar campos obligatorios
        if (txtTipoId.getText().isEmpty() ||
                txtNumeroId.getText().isEmpty() ||
                txtNombres.getText().isEmpty() ||
                txtApellidos.getText().isEmpty() ||
                txtFechaNacimiento.getText().isEmpty()) {
            throw new Exception("Los campos Tipo ID, Número ID, Nombres, Apellidos y Fecha de Nacimiento son obligatorios.");
        }

        Funcionario f = new Funcionario();
        f.setTipoIdentificacion(txtTipoId.getText().trim());
        f.setNumeroIdentificacion(txtNumeroId.getText().trim());
        f.setNombres(txtNombres.getText().trim());
        f.setApellidos(txtApellidos.getText().trim());
        f.setEstadoCivil(txtEstadoCivil.getText().trim());
        f.setSexo(txtSexo.getText().trim());
        f.setDireccion(txtDireccion.getText().trim());
        f.setTelefono(txtTelefono.getText().trim());

        try {
            Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(txtFechaNacimiento.getText().trim());
            f.setFechaNacimiento(fecha);
        } catch (Exception e) {
            throw new Exception("Formato de fecha inválido. Usa yyyy-MM-dd");
        }

        return f;
    }

    private void cargarFuncionario() {
        int fila = table.getSelectedRow();
        if (fila != -1) {
            txtId.setText(model.getValueAt(fila, 0).toString());
            txtTipoId.setText(model.getValueAt(fila, 1).toString());
            txtNumeroId.setText(model.getValueAt(fila, 2).toString());
            txtNombres.setText(model.getValueAt(fila, 3).toString());
            txtApellidos.setText(model.getValueAt(fila, 4).toString());
            txtEstadoCivil.setText(model.getValueAt(fila, 5).toString());
            txtSexo.setText(model.getValueAt(fila, 6).toString());
            txtDireccion.setText(model.getValueAt(fila, 7).toString());
            txtTelefono.setText(model.getValueAt(fila, 8).toString());
            txtFechaNacimiento.setText(model.getValueAt(fila, 9).toString());
        }
    }

    private int obtenerIdSeleccionado() {
        int fila = table.getSelectedRow();
        return Integer.parseInt(model.getValueAt(fila, 0).toString());
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtTipoId.setText("");
        txtNumeroId.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtEstadoCivil.setText("");
        txtSexo.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtFechaNacimiento.setText("");
        table.clearSelection();
    }

    private void mostrarError(Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        new Main();
    }
}

