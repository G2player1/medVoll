package Enos.SpringProject.medVoll.controllers;

import Enos.SpringProject.medVoll.domain.models.dto.reads.ReadDetailedDoctorDTO;
import Enos.SpringProject.medVoll.domain.models.dto.reads.ReadDoctorDTO;
import Enos.SpringProject.medVoll.domain.models.dto.reads.ReadUpdatedDoctorDTO;
import Enos.SpringProject.medVoll.domain.models.dto.registers.RegisterDoctorDTO;
import Enos.SpringProject.medVoll.domain.models.dto.updates.UpdateDoctorDTO;
import Enos.SpringProject.medVoll.domain.services.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/register")
    public ResponseEntity<ReadDetailedDoctorDTO> registerDoctor(@RequestBody @Valid RegisterDoctorDTO registerDoctorDTO,
                                                                UriComponentsBuilder uriBuilder){
        var doctor = doctorService.registerDoctorInDB(registerDoctorDTO);
        var uri = uriBuilder.path("/doctors/{id}").buildAndExpand(doctor.id()).toUri();
        return ResponseEntity.created(uri).body(doctor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadDetailedDoctorDTO> getDoctorByID(@PathVariable("id") Long id){
        var doctor = doctorService.getDoctorById(id);
        return ResponseEntity.ok(doctor);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<ReadDoctorDTO>> getDoctorsInDB(){
        Page<ReadDoctorDTO> page = doctorService.getDoctorsInDB();
        return ResponseEntity.ok(page);
    }

    @GetMapping("/list/{expertise}")
    public ResponseEntity<Page<ReadDoctorDTO>> getDoctorsInDBByExpertise(@PageableDefault(size = 10,sort = {"name"})
                                                  @PathVariable("expertise") String expertise){
        Page<ReadDoctorDTO> page = doctorService.getDoctorsInDbByExpertise(expertise);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/edit")
    public ResponseEntity<ReadUpdatedDoctorDTO> updateDoctor(@RequestBody UpdateDoctorDTO updateDoctorDTO){
        var doctor = doctorService.updateDoctor(updateDoctorDTO);
        return ResponseEntity.ok(doctor);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteDoctor(@PathVariable("id") Long id){
        doctorService.deleteDoctorById(id);
        return ResponseEntity.noContent().build();
    }
}
