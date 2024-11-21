package Enos.SpringProject.medVoll.controllers;

import Enos.SpringProject.medVoll.dto.RegisterDocDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("doctors")
public class DoctorController {

    @PostMapping("/register")
    public void getRegisterDoctor(@RequestBody RegisterDocDTO registerDocDTO){
        System.out.println(registerDocDTO);
    }
}
