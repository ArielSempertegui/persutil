package net.ausiasmarch.persutil.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins="*", allowedHeaders="*")
@RestController
@RequestMapping("/sempertegui")
public class SemperteguiApi {
    

    @GetMapping("/pelicula")
    public ResponseEntity<String> pelicula(){
        return ResponseEntity.ok("HOLA ESTA TODO OK");
    }
}
