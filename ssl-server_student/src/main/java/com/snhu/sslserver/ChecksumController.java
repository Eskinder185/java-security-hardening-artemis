package com.snhu.sslserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

@RestController
public class ChecksumController {

    // Route to return the checksum of static data
    @GetMapping("/checksum")
    public String getChecksum() {
        String data = "Hello World Check Sum!";
        try {
            // Generate checksum for the data
            String checksum = generateChecksum(data);
            return "Data: " + data + "\nChecksum: " + checksum;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "Error generating checksum";
        }
    }

    // Method to generate SHA-256 checksum
    private String generateChecksum(String data) throws NoSuchAlgorithmException {
        // Get SHA-256 MessageDigest instance
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        // Hash the input data
        byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));

        // Convert the byte array to a hexadecimal string
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}