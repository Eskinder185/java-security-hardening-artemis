package com.snhu.sslserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HashController {

    @GetMapping("/hash")
    public String getHash() {
        String data = "Brian - Unique Data String for Verification";
        return "Data: " + data + " | Hash: " + generateHash(data);
    }

    private String generateHash(String data) {
        
        return "samplehash";  
    }
}