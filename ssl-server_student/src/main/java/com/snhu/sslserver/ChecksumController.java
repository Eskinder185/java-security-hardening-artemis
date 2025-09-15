package com.snhu.sslserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChecksumController {

    @GetMapping("/checksum")
    public String checksum(@RequestParam(value = "text", required = false) String text) {
        String data = (text != null && !text.isEmpty()) ? text : "Hello World Check Sum!";
        java.util.zip.CRC32 crc = new java.util.zip.CRC32();
        crc.update(data.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        return "Data: " + data + " | Checksum: " + Long.toHexString(crc.getValue());
    }

}