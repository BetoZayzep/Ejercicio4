import java.io.*;
import java.util.*;

class AddressBook {
    private final Map<String, String> contacts;
    private final String filePath;

    public AddressBook(String filePath) {
        this.filePath = filePath;
        this.contacts = new HashMap<>();
    }

    // Método para cargar los contactos desde el archivo
    public void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String phoneNumber = parts[0];
                    String name = parts[1];
                    contacts.put(phoneNumber, name);
                }
            }
            System.out.println("Contactos cargados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al cargar los contactos: " + e.getMessage());
        }
    }

    // Método para guardar los cambios en el archivo
    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                String phoneNumber = entry.getKey();
                String name = entry.getValue();
                bw.write(phoneNumber + "," + name);
                bw.newLine();
            }
            System.out.println("Cambios guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los cambios: " + e.getMessage());
        }
    }

    // Método para mostrar la lista de contactos
    public void list() {
        System.out.println("Contactos:");
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            String phoneNumber = entry.getKey();
            String name = entry.getValue();
            System.out.println(phoneNumber + " : " + name);
        }
    }

    // Método para crear un nuevo contacto
    public void create(String phoneNumber, String name) {
        contacts.put(phoneNumber, name);
        System.out.println("Contacto creado correctamente.");
    }

    // Método para eliminar un contacto existente
    public void delete(String phoneNumber) {
        if (contacts.containsKey(phoneNumber)) {
            contacts.remove(phoneNumber);
            System.out.println("Contacto eliminado correctamente.");
        } else {
            System.out.println("El contacto no existe en la agenda.");
        }
    }

    public static void main(String[] args) {
        // Crear una instancia de AddressBook y cargar los contactos del archivo
        AddressBook addressBook = new AddressBook("contacts.txt");
        addressBook.load();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        // Menú interactivo para realizar acciones en la agenda telefónica
        while (!exit) {
            System.out.println("\n--- Agenda Telefónica ---");
            System.out.println("1. Mostrar contactos");
            System.out.println("2. Crear contacto");
            System.out.println("3. Eliminar contacto");
            System.out.println("4. Guardar cambios");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea después de leer la opción

            switch (option) {
                case 1 -> addressBook.list(); // Mostrar la lista de contactos
                case 2 -> {
                    // Solicitar el número y nombre del nuevo contacto a crear
                    System.out.print("Ingresa el número de teléfono: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Ingresa el nombre: ");
                    String name = scanner.nextLine();
                    addressBook.create(phoneNumber, name); // Crear el nuevo contacto
                }
                case 3 -> {
                    // Solicitar el número del contacto a eliminar
                    System.out.print("Ingresa el número de teléfono a eliminar: ");
                    String phoneNumberToDelete = scanner.nextLine();
                    addressBook.delete(phoneNumberToDelete); // Eliminar el contacto
                }
                case 4 -> addressBook.save(); // Guardar los cambios en el archivo
                case 5 -> {
                    exit = true;
                    System.out.println("¡Hasta luego!");
                }
                default -> System.out.println("Opción inválida.");
            }
        }

        scanner.close();
    }
}
