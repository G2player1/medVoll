package Enos.SpringProject.medVoll.controllers;

import Enos.SpringProject.medVoll.models.dto.reads.ReadDoctorDTO;
import Enos.SpringProject.medVoll.models.dto.registers.RegisterDoctorDTO;
import Enos.SpringProject.medVoll.models.dto.updates.UpdateDoctorDTO;
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
    public void registerDoctor(@RequestBody @Valid RegisterDoctorDTO registerDoctorDTO){
        doctorService.registerDoctorInDB(registerDoctorDTO);
    }

    @GetMapping("/list")
    public Page<ReadDoctorDTO> getDoctorsInDB(@PageableDefault(size = 10,sort = {"name"}) Pageable pageable){
        return doctorService.getDoctorsInDB();
    }

    @GetMapping("/list/{expertise}")
    public Page<ReadDoctorDTO> getDoctorsInDB(@PageableDefault(size = 10,sort = {"name"})
                                                  @PathVariable("expertise") String expertise){
        return doctorService.getDoctorsInDbByExpertise(expertise);
    }

    @PutMapping("/edit")
    public void updateDoctor(@RequestBody UpdateDoctorDTO updateDoctorDTO){
        doctorService.updateDoctor(updateDoctorDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDoctor(@PathVariable("id") Long id){
        doctorService.deleteDoctorById(id);
    }
}
