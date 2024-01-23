package edu.escuelaing.arem.ASE.app;
import java.io.*;
import java.net.*;

public class UrlExample {
    public static void main(String[] args) throws Exception {
         URL theBest = new URL("https://campusvirtual.escuelaing.edu.co:80/moodle/ClientService.pdf?cesar+amaya#entendi+esa+referencia"
                 );
         //getProtocol, getAuthority, getHost, getPort, getPath, getQuery,
        //getFile, getRef.
        System.out.println("-----------------------");
        System.out.println("Protocol: " + theBest.getProtocol());
        System.out.println("Authority: " + theBest.getAuthority());
        System.out.println("Host: " + theBest.getHost());
        System.out.println("Port: " + theBest.getPort());
        System.out.println("Phat: " + theBest.getPath());
        System.out.println("Query: " + theBest.getQuery());
        System.out.println("File: " + theBest.getFile());
        System.out.println("Ref: " + theBest.getRef());
        System.out.println("-----------------------");

    }
}
