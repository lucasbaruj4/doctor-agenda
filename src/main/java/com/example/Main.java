package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    // public static void main(String[] args) {
    //     Utils utils = new Utils();
    
    //     String textoOriginal = "Texto secreto para pruebas";
    //     System.out.println("Texto original: " + textoOriginal);
    
    //     // Encriptar
    //     String textoCifrado = utils.Encriptar(textoOriginal);
    //     System.out.println("Texto cifrado: " + textoCifrado);
    
    //     // Desencriptar
    //     String textoDescifrado = utils.Desencriptar(textoCifrado);
    //     System.out.println("Texto desencriptado: " + textoDescifrado);
    // }
}
}

