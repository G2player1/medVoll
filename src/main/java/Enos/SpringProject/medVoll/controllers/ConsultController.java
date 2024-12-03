package Enos.SpringProject.medVoll.controllers;

import Enos.SpringProject.medVoll.models.dto.reads.ReadConsultDTO;
import Enos.SpringProject.medVoll.models.dto.registers.RegisterConsultDTO;
import Enos.SpringProject.medVoll.services.ConsultService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consults")
public class ConsultController {

    @Autowired
    private ConsultService consultService;

    @PostMapping("/register")
    public ResponseEntity registerConsult(@RequestBody @Valid RegisterConsultDTO registerConsultDTO){
        var consult = consultService.registerConsult(registerConsultDTO);
        return ResponseEntity.ok(consult);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<ReadConsultDTO>> getConsultsInDB(){
        var page = consultService.getConsultsInDB();
        return ResponseEntity.ok(page);
    }

    @GetMapping("/list/doctor/{id}")
    public ResponseEntity<Page<ReadConsultDTO>> getDoctorConsults(@PathVariable(value = "id") Long id){
        var page = consultService.getDoctorConsults(id);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/list/patient/{id}")
    public ResponseEntity<Page<ReadConsultDTO>> getPatientConsults(@PathVariable(value = "id") Long id){
        var page = consultService.getPatientConsults(id);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteConsult(@PathVariable(value = "id") Long id){
        consultService.deleteConsult(id);
        return ResponseEntity.noContent().build();
    }
}
