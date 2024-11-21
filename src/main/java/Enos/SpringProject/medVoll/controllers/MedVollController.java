package Enos.SpringProject.medVoll.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class MedVollController {

    @GetMapping
    public String helloWorld(){
        return """
                <h1>Hello World</h1>
                <h3>The MedVoll API says hello!
                """;
    }
}
