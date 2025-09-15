package com.snhu.sslserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HashController {

    @GetMapping("/hash")
    public String getHash(@RequestParam(value = "text", required = false) String text) {
        String data = (text != null && !text.isEmpty()) ? text : "Hello World Check Sum!";
        return "Data: " + data + " | Hash: " + Sha256.hash(data);
    }

}