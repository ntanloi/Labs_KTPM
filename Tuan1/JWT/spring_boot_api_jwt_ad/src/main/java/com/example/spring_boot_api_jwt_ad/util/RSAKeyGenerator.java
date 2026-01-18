package com.example.spring_boot_api_jwt_ad.util;

import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public class RSAKeyGenerator {
    
    public static void main(String[] args) {
        try {
            // Tạo RSA key pair
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            
            // Lưu private key
            savePrivateKey(privateKey, "src/main/resources/certs/private.pem");
            
            // Lưu public key
            savePublicKey(publicKey, "src/main/resources/certs/public.pem");
            
            System.out.println("RSA key pair generated successfully!");
            System.out.println("Private key saved to: src/main/resources/certs/private.pem");
            System.out.println("Public key saved to: src/main/resources/certs/public.pem");
            
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void savePrivateKey(RSAPrivateKey privateKey, String filename) throws IOException {
        byte[] encoded = privateKey.getEncoded();
        String base64Encoded = Base64.getEncoder().encodeToString(encoded);
        
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("-----BEGIN PRIVATE KEY-----\n");
            
            // Chia base64 string thành các dòng 64 ký tự
            for (int i = 0; i < base64Encoded.length(); i += 64) {
                int end = Math.min(i + 64, base64Encoded.length());
                writer.write(base64Encoded.substring(i, end) + "\n");
            }
            
            writer.write("-----END PRIVATE KEY-----\n");
        }
    }
    
    private static void savePublicKey(RSAPublicKey publicKey, String filename) throws IOException {
        byte[] encoded = publicKey.getEncoded();
        String base64Encoded = Base64.getEncoder().encodeToString(encoded);
        
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("-----BEGIN PUBLIC KEY-----\n");
            
            // Chia base64 string thành các dòng 64 ký tự
            for (int i = 0; i < base64Encoded.length(); i += 64) {
                int end = Math.min(i + 64, base64Encoded.length());
                writer.write(base64Encoded.substring(i, end) + "\n");
            }
            
            writer.write("-----END PUBLIC KEY-----\n");
        }
    }
}