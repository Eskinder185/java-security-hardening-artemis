package com.snhu.sslserver;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA-256 utility class for Artemis Financial application.
 * Provides secure hashing functionality for data integrity verification.
 */
public class Sha256 {

    /**
     * Generates SHA-256 hash of the input string.
     * 
     * @param input the string to hash
     * @return SHA-256 hash as hexadecimal string
     * @throws RuntimeException if SHA-256 algorithm is not available
     */
    public static String hash(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    /**
     * Generates SHA-256 hash of the input byte array.
     * 
     * @param input the byte array to hash
     * @return SHA-256 hash as hexadecimal string
     * @throws RuntimeException if SHA-256 algorithm is not available
     */
    public static String hash(byte[] input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input);
            return bytesToHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    /**
     * Verifies if the given input matches the expected hash.
     * 
     * @param input the input to verify
     * @param expectedHash the expected SHA-256 hash
     * @return true if the input matches the expected hash, false otherwise
     */
    public static boolean verify(String input, String expectedHash) {
        if (input == null || expectedHash == null) {
            return false;
        }
        
        String actualHash = hash(input);
        return actualHash.equals(expectedHash);
    }

    /**
     * Verifies if the given byte array matches the expected hash.
     * 
     * @param input the byte array to verify
     * @param expectedHash the expected SHA-256 hash
     * @return true if the input matches the expected hash, false otherwise
     */
    public static boolean verify(byte[] input, String expectedHash) {
        if (input == null || expectedHash == null) {
            return false;
        }
        
        String actualHash = hash(input);
        return actualHash.equals(expectedHash);
    }

    /**
     * Converts byte array to hexadecimal string.
     * 
     * @param bytes the byte array to convert
     * @return hexadecimal string representation
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * Generates a secure random salt for password hashing.
     * Note: This is a basic implementation. For production use,
     * consider using a more robust salt generation method.
     * 
     * @param length the length of the salt in bytes
     * @return random salt as hexadecimal string
     */
    public static String generateSalt(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Salt length must be positive");
        }
        
        java.security.SecureRandom random = new java.security.SecureRandom();
        byte[] salt = new byte[length];
        random.nextBytes(salt);
        return bytesToHex(salt);
    }

    /**
     * Example usage and testing method.
     * This method demonstrates how to use the SHA-256 utility.
     */
    public static void main(String[] args) {
        // Example usage
        String input = "Hello, Artemis Financial!";
        String hash = hash(input);
        
        System.out.println("Input: " + input);
        System.out.println("SHA-256 Hash: " + hash);
        System.out.println("Hash Length: " + hash.length() + " characters");
        
        // Verification example
        boolean isValid = verify(input, hash);
        System.out.println("Verification: " + (isValid ? "PASS" : "FAIL"));
        
        // Salt generation example
        String salt = generateSalt(16);
        System.out.println("Generated Salt: " + salt);
        
        // Hash with salt example
        String saltedInput = input + salt;
        String saltedHash = hash(saltedInput);
        System.out.println("Salted Hash: " + saltedHash);
    }
}
