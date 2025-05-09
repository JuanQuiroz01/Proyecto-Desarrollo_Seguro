package org.example.view;

import org.example.controllers.FuncionarioController;
import org.example.domain.Funcionario;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FuncionarioController controller = new FuncionarioController();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        while (true) {
            System.out.println("\n--- Menú Funcionario ---");
            System.out.println("1. Listar funcionarios");
            System.out.println("2. Agregar funcionario");
            System.out.println("3. Actualizar funcionario");
            System.out.println("4. Eliminar funcionario");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    List<Funcionario> lista = controller.obtenerFuncionarios();
                    for (Funcionario f : lista) {
                        System.out.println(f.getIdFuncionario() + " - " + f.getNombres() + " " + f.getApellidos());
                    }
                    break;

                case 2:
                    Funcionario nuevo = new Funcionario();
                    System.out.print("Tipo identificación: ");
                    nuevo.setTipoIdentificacion(scanner.nextLine());
                    System.out.print("Número identificación: ");
                    nuevo.setNumeroIdentificacion(scanner.nextLine());
                    System.out.print("Nombres: ");
                    nuevo.setNombres(scanner.nextLine());
                    System.out.print("Apellidos: ");
                    nuevo.setApellidos(scanner.nextLine());
                    System.out.print("Estado civil: ");
                    nuevo.setEstadoCivil(scanner.nextLine());
                    System.out.print("Sexo (M/F): ");
                    nuevo.setSexo(scanner.nextLine());
                    System.out.print("Dirección: ");
                    nuevo.setDireccion(scanner.nextLine());
                    System.out.print("Teléfono: ");
                    nuevo.setTelefono(scanner.nextLine());
                    System.out.print("Fecha nacimiento (yyyy-MM-dd): ");
                    try {
                        Date fecha = sdf.parse(scanner.nextLine());
                        nuevo.setFechaNacimiento(fecha);
                        controller.agregarFuncionario(nuevo);
                        System.out.println("Funcionario agregado.");
                    } catch (Exception e) {
                        System.out.println("Error en fecha: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.print("ID del funcionario a actualizar: ");
                    int idUp = scanner.nextInt();
                    scanner.nextLine();
                    Funcionario actualizado = new Funcionario();
                    actualizado.setIdFuncionario(idUp);
                    System.out.print("Nuevo nombre: ");
                    actualizado.setNombres(scanner.nextLine());
                    System.out.print("Nuevo apellido: ");
                    actualizado.setApellidos(scanner.nextLine());
                    System.out.print("Nuevo estado civil: ");
                    actualizado.setEstadoCivil(scanner.nextLine());
                    System.out.print("Nuevo sexo (M/F): ");
                    actualizado.setSexo(scanner.nextLine());
                    System.out.print("Nueva dirección: ");
                    actualizado.setDireccion(scanner.nextLine());
                    System.out.print("Nuevo teléfono: ");
                    actualizado.setTelefono(scanner.nextLine());
                    // Aquí puedes pedir más campos si quieres
                    System.out.print("Nueva fecha nacimiento (yyyy-MM-dd): ");
                    try {
                        Date fecha = sdf.parse(scanner.nextLine());
                        actualizado.setFechaNacimiento(fecha);
                        controller.actualizarFuncionario(actualizado);
                        System.out.println("Funcionario actualizado.");
                    } catch (Exception e) {
                        System.out.println("Error en fecha: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.print("ID del funcionario a eliminar: ");
                    int idDel = scanner.nextInt();
                    controller.eliminarFuncionario(idDel);
                    System.out.println("Funcionario eliminado.");
                    break;

                case 5:
                    System.out.println("Saliendo...");
                    return;

                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
