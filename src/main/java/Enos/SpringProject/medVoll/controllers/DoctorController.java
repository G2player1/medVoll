package Enos.SpringProject.medVoll.controllers;

import Enos.SpringProject.medVoll.models.dto.DoctorDTO;
import Enos.SpringProject.medVoll.models.dto.DoctorListingDataDTO;
import Enos.SpringProject.medVoll.services.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

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
    public Page<DoctorListingDataDTO> getDoctorsInDB(@PageableDefault(size = 10,sort = {"name"}) Pageable pageable){
        return doctorService.getDoctorsInDB(pageable);
    }

    @GetMapping("/list/{expertise}")
    public Page<DoctorListingDataDTO> getDoctorsInDB(@PageableDefault(size = 10,sort = {"name"}) Pageable pageable,
                                                     @PathVariable("expertise") String expertise){
        return doctorService.getDoctorsInDbByExpertise(pageable,expertise);
    }
}
