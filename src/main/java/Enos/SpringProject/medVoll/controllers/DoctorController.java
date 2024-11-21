package Enos.SpringProject.medVoll.controllers;

import Enos.SpringProject.medVoll.models.Doctor;
import Enos.SpringProject.medVoll.models.dto.DoctorDTO;
import Enos.SpringProject.medVoll.models.dto.DoctorListingDataDTO;
import Enos.SpringProject.medVoll.services.DoctorService;
import jakarta.validation.Valid;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/register")
    public void registerDoctor(@RequestBody @Valid DoctorDTO doctorDTO){
        doctorService.registerDoctorInDB(doctorDTO);
    }

    @GetMapping("/list")
    public List<DoctorListingDataDTO> getDoctorsInDB(){
        return doctorService.getDoctorsInDB();
    }

    @GetMapping("/list/{expertise}")
    public List<DoctorListingDataDTO> getDoctorsInDB(@PathVariable("expertise") String expertise){
        return doctorService.getDoctorsInDbByExpertise(expertise);
    }
}
