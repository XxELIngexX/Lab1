package edu.escuelaing.arem.ASE.app;
import java.io.*;
import java.net.*;
import java.util.*;

public class URLReader {

    public static void main(String[] args) throws Exception {
         String site = "https://ldbn.is.escuelaing.edu.co/index.html#projects";
        // Crea el objeto que representa una URL
         URL siteURL = new URL(site);
         // Crea el objeto que URLConnection
        URLConnection urlConnection = siteURL.openConnection();
         // Obtiene los campos del encabezado y los almacena en un estructura Map
        Map<String, List<String>> headers = urlConnection.getHeaderFields();
         // Obtiene una vista del mapa como conjunto de pares <K,V>
// para poder navegarlo
         Set<Map.Entry<String, List<String>>> entrySet = headers.entrySet();
         // Recorre la lista de campos e imprime los valores
        for (Map.Entry<String, List<String>> entry : entrySet) {
             String headerName = entry.getKey();
//Si el nombre es nulo, significa que es la linea de estado
             if(headerName !=null){System.out.print(headerName + ":");}
            List<String> headerValues = entry.getValue();
             for (String value : headerValues) {
                System.out.print(value);
                 }
            System.out.println("");
             //System.out.println("");
        }
            try (BufferedReader reader
                         = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
             String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                 System.out.println(inputLine);
            }
            } catch (IOException x) {
                System.err.println(x);
            }
    }
 }
