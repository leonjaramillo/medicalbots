package com.ponents.medicalbots;

import com.ponents.medicalbots.model.MedicalRecord;
import com.ponents.medicalbots.model.MedicalRecordRepository;
import com.ponents.medicalbots.model.MedicalRecordRepositoryImpl;
import com.ponents.medicalbots.model.Triage;
import com.ponents.medicalbots.model.TriageRepository;
import com.ponents.medicalbots.model.TriageRepositoryImpl;
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
        MedicalRecordRepository archivoHistorias = new MedicalRecordRepositoryImpl(em);
        TriageRepository archivoTriage = new TriageRepositoryImpl(em);
        int opcion = 0;

        while (opcion != 8) {
            mostrarMenu();
            try {
            opcion = Integer.parseInt(entrada.nextLine());
            switch (opcion) {
                case 1:
                    opcionListarHistorias(archivoHistorias);
                    break;
                case 2:
                    opcionAgregarHistoria(archivoHistorias, entrada);
                    break;
                case 3:
                    opcionConsultarHistoria(archivoHistorias, entrada);
                    break;
                case 4:
                    opcionBorrarHistoria(archivoHistorias, entrada);
                    break;
                case 5:
                    opcionListarTriage(archivoTriage);
                    break;
                case 6:
                    opcionAgregarTriage(archivoTriage, entrada);
                    break;
                case 7:
                    opcionBorrarTriage(archivoTriage, entrada);
                    break;
            }
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido");
            }
        }
        em.close();
    }

    private static void mostrarMenu() {
        System.out.println("REGISTRO DE INFORMACIÓN\n"
                + "------\n"
                + "Ingrese el número correspondiente a la opción deseada:\n"
                + "1. Listar todas las historias médicas\n"
                + "2. Agregar historia médica\n"
                + "3. Obtener historia médica por ID\n"
                + "4. Borrar historia médica\n"
                + "------\n"
                + "5. Listar todos los registros de triage\n"
                + "6. Ingresar registro de triage\n"
                + "7. Borrar registro de triage\n"
                + "------\n"
                + "8. Salir\n");
    }

    private static void opcionListarHistorias(MedicalRecordRepository archivo) {
        List<MedicalRecord> registros = archivo.getAll();
        System.out.println("El archivo cuenta con " + registros.size() + " historias médicas.");
        System.out.println("ID -- NOMBRES -- APELLIDOS");
        registros.forEach((historia) -> {
            System.out.println(historia.toString());
        });
        System.out.println();
    }

    private static void opcionAgregarHistoria(MedicalRecordRepository archivo, Scanner entrada) {
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

    private static void opcionConsultarHistoria(MedicalRecordRepository archivo, Scanner entrada) {
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

    private static void opcionBorrarHistoria(MedicalRecordRepository archivo, Scanner entrada) {
        System.out.println("\nIngrese el ID del paciente que desea borrar: ");
        try {
        archivo.delete(Long.parseLong(entrada.nextLine()));
            System.out.println("Historia médica borrada");
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private static void opcionListarTriage(TriageRepository archivo) {
        List<Triage> registros = archivo.getAll();
        System.out.println("El archivo cuenta con " + registros.size() + " registros de triage.");
        System.out.println("ID -- FECHA -- HORA -- SÍNTOMAS");
        registros.forEach((triage) -> {
            System.out.println(triage.toString());
        });
        System.out.println();
    }

    private static void opcionAgregarTriage(TriageRepository archivo, Scanner entrada) {
        Triage triage = new Triage();
        System.out.println("\nIngrese la fecha del triage: ");
        triage.setDate(entrada.nextLine());
        System.out.println("\nIngrese la hora del triage: ");
        triage.setTime(entrada.nextLine());
        System.out.println("\nIngrese los síntomas: ");
        triage.setSymptoms(entrada.nextLine());
        archivo.add(triage);
    }

    private static void opcionBorrarTriage(TriageRepository archivo, Scanner entrada) {
        System.out.println("\nIngrese el ID del registro de triage que desea borrar: ");
        try {
        archivo.delete(Long.parseLong(entrada.nextLine()));
            System.out.println("Registro de triage borrado");
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
