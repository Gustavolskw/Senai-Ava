package senai.com.backend_atividades.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/ping")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PingController {

    @GetMapping
    public ResponseEntity<String> ping() {
        try{
            return ResponseEntity.ok("Pong");
        }catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
