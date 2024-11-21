package Enos.SpringProject.medVoll.controllers;

import Enos.SpringProject.medVoll.models.dto.DoctorDTO;
import Enos.SpringProject.medVoll.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/register")
    public void getRegisterDoctor(@RequestBody DoctorDTO doctorDTO){
        doctorService.registerDoctorInDB(doctorDTO);
    }
}
