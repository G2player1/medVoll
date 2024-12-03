package Enos.SpringProject.medVoll.controllers;

import Enos.SpringProject.medVoll.models.dto.reads.ReadDoctorDTO;
import Enos.SpringProject.medVoll.models.dto.registers.RegisterDoctorDTO;
import Enos.SpringProject.medVoll.models.dto.updates.UpdateDoctorDTO;
import Enos.SpringProject.medVoll.services.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/register")
    public ResponseEntity registerDoctor(@RequestBody @Valid RegisterDoctorDTO registerDoctorDTO){
        var doctor = doctorService.registerDoctorInDB(registerDoctorDTO);
        return ResponseEntity.ok(doctor);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<ReadDoctorDTO>> getDoctorsInDB(){
        Page<ReadDoctorDTO> page = doctorService.getDoctorsInDB();
        return ResponseEntity.ok(page);
    }

    @GetMapping("/list/{expertise}")
    public ResponseEntity<Page<ReadDoctorDTO>> getDoctorsInDB(@PageableDefault(size = 10,sort = {"name"})
                                                  @PathVariable("expertise") String expertise){
        Page<ReadDoctorDTO> page = doctorService.getDoctorsInDbByExpertise(expertise);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/edit")
    public ResponseEntity updateDoctor(@RequestBody UpdateDoctorDTO updateDoctorDTO){
        var doctor = doctorService.updateDoctor(updateDoctorDTO);
        return ResponseEntity.ok(doctor);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteDoctor(@PathVariable("id") Long id){
        doctorService.deleteDoctorById(id);
        return ResponseEntity.noContent().build();
    }
}
