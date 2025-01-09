package com.example.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class Utils {

    private static final String SECRET_KEY = "starbox@t5_#8256no";

    public String Encriptar(String texto) {
        try {
            if (texto == null || texto.trim().isEmpty()) {
                return "";
            }

            // Generar digest de la llave
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(SECRET_KEY.getBytes("utf-8"));

            // Crear clave a partir del digest
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 16);
            SecretKey key = new SecretKeySpec(keyBytes, "AES");

            // Configurar Cipher para encriptación
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            // Encriptar el texto
            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] encryptedBytes = cipher.doFinal(plainTextBytes);

            // Codificar en Base64
            return new String(Base64.encodeBase64(encryptedBytes), "utf-8");

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException |
                 InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("Error al encriptar el texto", e);
        }
    }

    public String Desencriptar(String texto) {
        try {
            if (texto == null || texto.trim().isEmpty()) {
                return "";
            }

            // Decodificar el texto cifrado desde Base64
            byte[] message = Base64.decodeBase64(texto.getBytes("utf-8"));

            // Generar la clave secreta
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(SECRET_KEY.getBytes("utf-8"));

            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 16);
            SecretKey key = new SecretKeySpec(keyBytes, "AES");

            // Configurar el Cipher para desencriptación
            Cipher decipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            decipher.init(Cipher.DECRYPT_MODE, key);

            // Desencriptar los bytes
            byte[] plainText = decipher.doFinal(message);
            return new String(plainText, "utf-8");

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException |
                 InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("Error al desencriptar el texto", e);
        }
    }

    public  String crearJWT(String correo, String subject, int tiempo){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long timeInSecs = System.currentTimeMillis();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime afterAddingMins;
        if(tiempo==-1){
            afterAddingMins =  now.plusYears(10);
        }else{

            afterAddingMins =  now.plusSeconds(tiempo);
        }
        Date dateNow = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder().setId(correo)
                .setIssuedAt(dateNow)
                .setSubject(subject)
                .signWith(signatureAlgorithm, signingKey);

        if (timeInSecs > 0) {
            Date exp =  Date.from(afterAddingMins.atZone(ZoneId.systemDefault()).toInstant());
            builder.setExpiration(exp);
        }

        return builder.compact();
    }
}
