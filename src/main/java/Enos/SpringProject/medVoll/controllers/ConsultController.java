package Enos.SpringProject.medVoll.controllers;

import Enos.SpringProject.medVoll.domain.models.consult.dto.ReadConsultDTO;
import Enos.SpringProject.medVoll.domain.models.consult.dto.RegisterConsultDTO;
import Enos.SpringProject.medVoll.domain.models.consult.ConsultService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("consults")
@SecurityRequirement(name = "bearer-key")
public class ConsultController {

    @Autowired
    private ConsultService consultService;

    @PostMapping("/register")
    public ResponseEntity<ReadConsultDTO> registerConsult(@RequestBody @Valid RegisterConsultDTO registerConsultDTO,
                                          UriComponentsBuilder uriBuilder){
            var consult = consultService.registerConsult(registerConsultDTO);
            var uri = uriBuilder.path("/consults/{id}").buildAndExpand(consult.getId()).toUri();
            return ResponseEntity.created(uri).body(consult);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadConsultDTO> getCosultById(@PathVariable("id") Long id){
            var consult = consultService.getConsultById(id);
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
