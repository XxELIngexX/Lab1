package edu.escuelaing.arem.ASE.app;
import java.net.*;
 import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

public class HttpServerLab1 {
     private static ConcurrentHashMap<String, StringBuffer> cache;
    /**
     * Método principal que inicia el servidor HTTP para la búsqueda de películas.
     * Este servidor acepta conexiones entrantes, procesa las solicitudes de búsqueda de películas
     * y envía respuestas HTTP correspondientes.
     *
     * @param args Los argumentos de línea de comandos (no se utilizan en este caso).
     * @throws IOException Si ocurre un error durante la ejecución del servidor o al interactuar con los clientes.
     */
    public static void main(String[] args) throws IOException {
        // Inicializa el socket del servidor y el caché de respuestas
        ServerSocket serverSocket = null;
        cache = new ConcurrentHashMap<String, StringBuffer>();

        try {
            // Intenta escuchar en el puerto 35000
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            // Imprime un mensaje de error si no puede escuchar en el puerto
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        // Indica que el servidor está listo para recibir conexiones
        System.out.println("Server ready to receive requests...");

        // Loop principal que espera y procesa las solicitudes de los clientes
        boolean running = true;
        while (running) {
            // Acepta una conexión entrante del cliente
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                // Imprime un mensaje de error si no se puede aceptar la conexión
                System.err.println("Accept failed.");
                System.exit(1);
            }

            // Inicializa los flujos de entrada y salida para la comunicación con el cliente
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Variables para el procesamiento de la solicitud y la respuesta
            String inputLine, outputLine, outputLine1;

            boolean request = true;
            String MovietoSearch = "";

            // Objeto para realizar la conexión externa a la API de OMDB
            HttpConnectionLab1 connectionToApi = new HttpConnectionLab1();
            StringBuffer apiResponse = new StringBuffer();

            // Lee y procesa las líneas de la solicitud HTTP del cliente
            while ((inputLine = in.readLine()) != null) {
                if (request) {
                    MovietoSearch = inputLine.split(" ")[1];
                    request = false;
                    System.out.println("@@@@@ " + MovietoSearch);
                }

                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }
            // Construye la respuesta HTML base para la página de búsqueda de películas
            outputLine = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type:text/html; charset=utf-8\r\n"
                    + "\r\n"
                    + "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "    <head>\n" +
                    "        <title>Search Movie</title>\n" +
                    "        <meta charset=\"UTF-8\">\n" +
                    "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "<style>\n" +
                    "        body {\n" +
                    "            font-family: Arial, sans-serif;\n" +
                    "            background-color: #f5f5f5;\n" +
                    "            margin: 20px;\n" +
                    "        }\n" +
                    "\n" +
                    "        h1 {\n" +
                    "            color: #333;\n" +
                    "        }\n" +
                    "\n" +
                    "        #getrespmsg {\n" +
                    "            background-color: #fff;\n" +
                    "            padding: 20px;\n" +
                    "            border-radius: 5px;\n" +
                    "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                    "            margin-top: 20px;\n" +
                    "        }\n" +
                    "\n" +
                    "        table {\n" +
                    "            width: 100%;\n" +
                    "            border-collapse: collapse;\n" +
                    "            margin-top: 20px;\n" +
                    "        }\n" +
                    "\n" +
                    "        th, td {\n" +
                    "            padding: 10px;\n" +
                    "            border: 1px solid #ddd;\n" +
                    "            text-align: left;\n" +
                    "        }\n" +
                    "\n" +
                    "        th {\n" +
                    "            background-color: #f2f2f2;\n" +
                    "        }\n" +
                    "    </style>\n"+
                    "    </head>\n" +

                    "    <body>\n" +
                    "        <h1>Search Movie</h1>\n" +
                    "            <form action=\"/Film\" method=\"GET\" onsubmit=\"loadGetMsg(); return false;\">\n"+
                    "            <label for=\"name\">Title:</label><br>\n" +
                    "            <input type=\"text\" id=\"name\" name=\"name\" value=\"Film Title\"><br><br>\n" +
                    "            <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n" +
                    "        </form> \n" +
                    "        <div id=\"getrespmsg\"></div>\n" +
                    "\n" +
                    "        <script>\n" +
                    "            function loadGetMsg() {\n" +
                    "                let nameVar = document.getElementById(\"name\").value;\n" +
                    "                const xhttp = new XMLHttpRequest();\n" +
                    "                xhttp.onload = function() {\n" +
                    "                    try {\n" +
                    "                        const response = JSON.parse(this.responseText);\n" +
                    "                        const table = createTable(response);\n" +
                    "                        document.getElementById(\"getrespmsg\").innerHTML = table;\n" +
                    "                    } catch (error) {\n" +
                    "                        document.getElementById(\"getrespmsg\").innerHTML = this.responseText;\n" +
                    "                    }\n" +
                    "                };\n" +
                    "                xhttp.open(\"GET\", \"/Film?title=\" + nameVar);\n" +
                    "                xhttp.send();\n" +
                    "            }\n" +
                    "\n" +
                    "function createTable(data) {\n" +
                    "    let tableHTML = '<table>';\n" +
                    "    for (const key in data) {\n" +
                    "        if (data.hasOwnProperty(key)) {\n" +
                    "            if (key === 'Poster' && typeof data[key] === 'string') {\n" +
                    "                // Si la llave es 'Poster' y el valor es una cadena, crea una etiqueta de imagen\n" +
                    "                tableHTML += `<tr><th>${key}</th><td><img src='${data[key]}' alt='Poster' style='max-width:100%;'></td></tr>`;\n" +
                    "            } else if (Array.isArray(data[key])) {\n" +
                    "                // Handle arrays (e.g., Ratings)\n" +
                    "                tableHTML += `<tr><th>${key}</th><td>${createTable(data[key])}</td></tr>`;\n" +
                    "            } else if (typeof data[key] === 'object') {\n" +
                    "                // Handle nested objects\n" +
                    "                tableHTML += `<tr><th>${key}</th><td>${createTable(data[key])}</td></tr>`;\n" +
                    "            } else {\n" +
                    "                // Handle regular key-value pairs\n" +
                    "                tableHTML += `<tr><th>${key}</th><td>${data[key]}</td></tr>`;\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }\n" +
                    "    tableHTML += '</table>';\n" +
                    "    return tableHTML;\n" +
                    "}\n"+
                    "        </script>\n" +
                    "    </body>\n" +
                    "</html>";

            // Verifica si la solicitud HTTP comienza con "/Film"
            if (MovietoSearch.startsWith("/Film")){
                String titleValue = MovietoSearch.substring(12);
                // Verifica si el título de la película está en la caché y si no esta lo agrega
                if (!cache.containsKey(titleValue)) {
                    apiResponse = connectionToApi.getMovie(titleValue);
                    cache.put(titleValue, apiResponse);
                } else {
                    apiResponse = cache.get(titleValue);
                }
                // Construye la respuesta HTTP para enviar al cliente, pero esta vez con la respuesta a la consulta
                outputLine1 = "HTTP/1.1 200 OK"
                        + "Content-Type: application/json\r\n"
                        + "\r\n"
                        + apiResponse.toString();
                out.println(outputLine1);
            }
            // Si la solicitud HTTP comienza con "/Client" Envía la respuesta HTML base al cliente
            else if(MovietoSearch.startsWith("/Client")){
                out.println(outputLine);
            }

            // Cierra los flujos y el socket del cliente
            out.close();
            in.close();
            clientSocket.close();
        }
        // Cierra el socket del servidor al finalizar
            serverSocket.close();

    }
}
