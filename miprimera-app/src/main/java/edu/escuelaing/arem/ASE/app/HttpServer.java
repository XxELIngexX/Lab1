package edu.escuelaing.arem.ASE.app;
import java.net.*;
 import java.io.*;

 public class HttpServer {
     static String url = "http://www.omdbapi.com/";
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine;
            boolean request=true;
            String uri ="";
            while ((inputLine = in.readLine()) != null) {
                if(request){
                    uri =inputLine.split(" ")[1];
                    request=false;
                    System.out.println("@@@@@ " + uri);
                }
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }
            outputLine = "HTTP/1.1 200 OK"
                    + "Content-Type:text/html; charset=utf-8\r\n"
                    + "\r\n"
                    + "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "    <head>\n" +
                    "        <title>Form Example</title>\n" +
                    "        <meta charset=\"UTF-8\">\n" +
                    "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    </head>\n" +
                    "    <body>\n" +
                    "        <h1>Form with GET</h1>\n" +
                    "        <form action=\"/Film\">\n" +
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
                    "                    document.getElementById(\"getrespmsg\").innerHTML =\n" +
                    "                    this.responseText;\n" +
                    "                }\n" +
                    "                xhttp.open(\"GET\", \"/Film?title=\"+nameVar);\n" +
                    "                xhttp.send();\n" +
                    "            }\n" +
                    "        </script>\n" +
                    "\n" +

                    "    </body>\n" +
                    "</html>";

            if (uri.startsWith("/Film")){
                String titleValue = uri.substring(11);
                outputLine = "HTTP/1.1 200 OK"
                        + "Content-Type: application/json\r\n"
                        + "\r\n"
                        + "\"title\": \"" + titleValue + "\"}";
                out.println(outputLine);
            }
            else if(uri.startsWith("/Client")){
                out.println(outputLine);
            }


            out.close();
            in.close();
            clientSocket.close();
        }
            serverSocket.close();

    }
}
