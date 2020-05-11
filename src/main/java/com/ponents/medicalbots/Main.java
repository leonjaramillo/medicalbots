package com.ponents.medicalbots;

import com.ponents.medicalbots.model.MedicalRecord;
import com.ponents.medicalbots.model.MedicalRecordRepository;
import com.ponents.medicalbots.model.MedicalRecordRepositoryImpl;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;

public class Main {

    private static final String PERSISTENCE_UNIT_NAME = "medicalRecords";
    private static EntityManagerFactory factory;

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        MedicalRecordRepository archivo = new MedicalRecordRepositoryImpl(em);
        int opcion = 0;

        while (opcion != 5) {
            mostrarMenu();
            try {
            opcion = Integer.parseInt(entrada.nextLine());
            switch (opcion) {
                case 1:
                    opcionListar(archivo);
                    break;
                case 2:
                    opcionAgregar(archivo, entrada);
                    break;
                case 3:
                    opcionConsultar(archivo, entrada);
                    break;
                case 4:
                    opcionBorrar(archivo, entrada);
                    break;
            }
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido");
            }
        }
        em.close();
    }

    private static void mostrarMenu() {
        System.out.println("EXPEDIENTES MÉDICOS\n"
                + "Ingrese el número correspondiente a la opción deseada:\n"
                + "1. Mostrar todos los expedientes\n"
                + "2. Agregar expediente\n"
                + "3. Obtener expediente por ID\n"
                + "4. Borrar expediente\n"
                + "5. Salir\n");
    }

    private static void opcionListar(MedicalRecordRepository archivo) {
        List<MedicalRecord> registros = archivo.getAll();
        System.out.println("El archivo cuenta con " + registros.size() + " registros.");
        registros.forEach((historia) -> {
            System.out.println(historia.toString());
        });
        System.out.println();
    }

    private static void opcionAgregar(MedicalRecordRepository archivo, Scanner entrada) {
        MedicalRecord historia = new MedicalRecord();
        System.out.println("\nIngrese el nombre del paciente: ");
        historia.setFirstName(entrada.nextLine());
        System.out.println("\nIngrese el apellido del paciente: ");
        historia.setLastName(entrada.nextLine());
        System.out.println("\nIngrese la edad del paciente: ");
        historia.setAge(Integer.parseInt(entrada.nextLine()));
        System.out.println("\nIngrese la descripción del paciente: ");
        historia.setDescription(entrada.nextLine());
        archivo.add(historia);
    }

    private static void opcionConsultar(MedicalRecordRepository archivo, Scanner entrada) {
        MedicalRecord historiaConsultar;
        System.out.println("\nIngrese el ID del paciente que desea consultar: ");
        try {
            historiaConsultar = archivo.getMedicalRecordById(Long.parseLong(entrada.nextLine()));
        System.out.println("Nombres: " + historiaConsultar.getFirstName()
                + "\nApellidos: " + historiaConsultar.getLastName()
                + "\nEdad: " + historiaConsultar.getAge()
                + "\nDescripción: " + historiaConsultar.getDescription() + "\n");
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
    }

    private static void opcionBorrar(MedicalRecordRepository archivo, Scanner entrada) {
        MedicalRecord historiaBorrar;
        System.out.println("\nIngrese el ID del paciente que desea borrar: ");
        try {
        archivo.delete(Long.parseLong(entrada.nextLine()));
            System.out.println("Historia médica borrada");
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
