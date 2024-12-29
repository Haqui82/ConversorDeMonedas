import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Principal {
    // Variables de instancia
    private String url = "https://v6.exchangerate-api.com/v6/7afb96b1099e105983c5b846/latest/";
    private String sufix1 = "USD";
    private String sufix2 = "ARS";
    private String sufix3 = "BRL";
    private String sufix4 = "COP";
    private Scanner scanner;

    // Constructor para inicializar el objeto Scanner
    public Principal() {
        scanner = new Scanner(System.in);
    }

    public static void main (String[] args) throws IOException, InterruptedException {
        Principal principal = new Principal();
        principal.menu();
    }

    //-------------------------------------------------------------------
    public void invocacion(String sufijo, String sufijo2) throws IOException, InterruptedException {
        System.out.print("Digita la cantidad a convertir: ");
        double cant = scanner.nextDouble();

        // Crear cliente
        HttpClient client = HttpClient.newHttpClient();
        // Crear solicitud
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + sufijo))
                .build();
        // Crear respuesta
        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        // Convertir la respuesta a un objeto de la clase JsonElement de la biblioteca Gson
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(response.body());

        // Convertir JsonElement a JsonObject
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        // Acceder al JsonObject dentro de "conversion_rates" (referencia dentro de la respuesta)
        JsonObject conversionRates = jsonObject.getAsJsonObject("conversion_rates");
        // Acceder al valor de...
        double value = conversionRates.get(sufijo2).getAsDouble();
        // Calcular la conversión
        double change = cant * value;
        // Imprimir el valor
        System.out.println(cant + " " + sufijo + "s son " + change + " " + sufijo2 + "s");
        System.out.println("El valor de un " + sufijo + " son " + value + " " + sufijo2 + "s");
        Thread.sleep(5000);
    }
    //-------------------------------------------------------------------

    // Función Menu
    public void menu() throws IOException, InterruptedException {
        // Desplegar saludo al usuario
        System.out.println("CONVERSOR DE MONEDAS");
        System.out.println("¡Bienvenid@! Por favor, indica qué tipo de tasa de cambio deseas realizar:");
        // Variable para almacenar la elección del usuario
        int option = 0;
        // Menú de opciones
        do {
            System.out.println("\nMenú de Opciones:");
            System.out.println("1. Dolar >>> Peso Argentino");
            System.out.println("2. Peso Argentino >>> Dolar");
            System.out.println("3. Dolar >>> Real Brasileño");
            System.out.println("4. Real Brasileño >>> Dolar");
            System.out.println("5. Dolar >>> Peso Colombiano");
            System.out.println("6. Peso Colombiano >>> Dolar");
            System.out.println("7. Salir");
            System.out.print("Por favor, elige una opción: ");
            // Leer la elección del usuario con control de errores
            try {
                option = scanner.nextInt();

                // Procesar la elección del usuario
                switch (option) {
                    case 1:
                        System.out.println("Has elegido la Opción 1. De Dólares (USD) a Pesos argentinos (ARS)");
                        invocacion(sufix1, sufix2);
                        break;
                    case 2:
                        System.out.println("Has elegido la Opción 2. De Pesos Argentinos (ARS) a Dolares (USD)");
                        invocacion(sufix2, sufix1);
                        break;
                    case 3:
                        System.out.println("Has elegido la Opción 3. De Dolares (USD) a Reales Brasileiros (BRL)");
                        invocacion(sufix1, sufix3);
                        break;
                    case 4:
                        System.out.println("Has elegido la Opción 4. De Reales Besileiros (BRL) a Dolares (USD)");
                        invocacion(sufix3, sufix1);
                        break;
                    case 5:
                        System.out.println("Has elegido la Opción 5. De Dolares (USD) a Pesos Colombianos (COP)");
                        invocacion(sufix1, sufix4);
                        break;
                    case 6:
                        System.out.println("Has elegido la Opción 6. De Pesos Colombianos (COP) a Dolares (USD)");
                        invocacion(sufix4, sufix1);
                        break;
                    case 7:
                        System.out.println("Has elegido la Opción 7. ¡Hasta luego!");
                        break;

                    default:
                        System.out.println("Opción no válida, por favor intenta de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("Ingreso no válido. Inténtelo de nuevo.");
                scanner.next(); // Limpiar el buffer del escáner
                Thread.sleep(3000);
            }
        } while (option != 7);
        // Cerrar el scanner
        scanner.close();
    }
}
