package com.example.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class Utils {

    public String Encriptar(String texto) {
        String dato = "";
        String llave = "starbox@t5_#8256no";
        Logger logger = Logger.getLogger(this.getClass().getName());
    
        try {
            if (texto == null || texto.trim().isEmpty()) {
                logger.log(Level.SEVERE, "El texto a encriptar no puede ser nulo o vacío");
                return dato; // Salir temprano si el texto está vacío
            }
    
            logger.log(Level.INFO, "Texto original recibido para encriptar: {0}", texto);
    
            // Generar digest de la llave
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(llave.getBytes("utf-8"));
            logger.log(Level.INFO, "Digest de la llave generado: {0}", Arrays.toString(digestOfPassword));
    
            // Crear clave a partir del digest
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 16); // Cambiar a 16 bytes para AES-128
            SecretKey key = new SecretKeySpec(keyBytes, "AES");
            logger.log(Level.INFO, "Clave generada para encriptación: {0}", Arrays.toString(keyBytes));
    
            // Configurar Cipher para encriptación
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            logger.log(Level.INFO, "Cipher inicializado para encriptación");
    
            // Encriptar el texto
            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] encryptedBytes = cipher.doFinal(plainTextBytes);
            logger.log(Level.INFO, "Bytes encriptados: {0}", Arrays.toString(encryptedBytes));
    
            // Codificar en Base64
            byte[] baseDato = Base64.encodeBase64(encryptedBytes);
            dato = new String(baseDato, "utf-8");
            logger.log(Level.INFO, "Texto encriptado en Base64: {0}", dato);
    
        } catch (UnsupportedEncodingException ex) {
            logger.log(Level.SEVERE, "Codificación no soportada", ex);
        } catch (NoSuchAlgorithmException ex) {
            logger.log(Level.SEVERE, "Algoritmo no encontrado", ex);
        } catch (NoSuchPaddingException ex) {
            logger.log(Level.SEVERE, "Padding no soportado", ex);
        } catch (InvalidKeyException ex) {
            logger.log(Level.SEVERE, "Clave inválida", ex);
        } catch (IllegalBlockSizeException ex) {
            logger.log(Level.SEVERE, "El tamaño del bloque no es válido", ex);
        } catch (BadPaddingException ex) {
            logger.log(Level.SEVERE, "Error de padding durante la encriptación", ex);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error inesperado durante la encriptación", ex);
        }
    
        return dato;
    }
    



    public String Desencriptar(String texto) {
        String dato = "";
        String llave = "starbox@t5_#8256no";
        Logger logger = Logger.getLogger(this.getClass().getName());
    
        try {
            if (texto == null || texto.trim().isEmpty()) {
                logger.log(Level.SEVERE, "El texto cifrado no puede ser nulo o vacío");
                return dato; // Salir temprano si el texto está vacío
            }
    
            logger.log(Level.INFO, "Texto cifrado recibido: {0}", texto);
    
            // Decodificar el texto cifrado desde Base64
            byte[] message = Base64.decodeBase64(texto.getBytes("utf-8"));
            logger.log(Level.INFO, "Bytes decodificados de Base64: {0}", Arrays.toString(message));
    
            // Generar la clave secreta
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(llave.getBytes("utf-8"));
            logger.log(Level.INFO, "Digest de la clave: {0}", Arrays.toString(digestOfPassword));
    
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 16); // Cambiar a 16 bytes para AES-128
            SecretKey key = new SecretKeySpec(keyBytes, "AES");
            logger.log(Level.INFO, "Clave generada: {0}", Arrays.toString(keyBytes));
    
            // Configurar el Cipher para desencriptación
            Cipher decipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            decipher.init(Cipher.DECRYPT_MODE, key);
            logger.log(Level.INFO, "Cipher inicializado para desencriptación");
    
            // Desencriptar los bytes
            byte[] plainText = decipher.doFinal(message);
            dato = new String(plainText, "utf-8");
            logger.log(Level.INFO, "Texto desencriptado: {0}", dato);
    
        } catch (UnsupportedEncodingException ex) {
            logger.log(Level.SEVERE, "Codificación no soportada", ex);
        } catch (NoSuchAlgorithmException ex) {
            logger.log(Level.SEVERE, "Algoritmo no encontrado", ex);
        } catch (NoSuchPaddingException ex) {
            logger.log(Level.SEVERE, "Padding no soportado", ex);
        } catch (InvalidKeyException ex) {
            logger.log(Level.SEVERE, "Clave inválida", ex);
        } catch (IllegalBlockSizeException ex) {
            logger.log(Level.SEVERE, "El tamaño del bloque no es válido", ex);
        } catch (BadPaddingException ex) {
            logger.log(Level.SEVERE, "Error de padding: Texto cifrado alterado o clave incorrecta", ex);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error inesperado", ex);
        }
        return dato;
    }
    
}


