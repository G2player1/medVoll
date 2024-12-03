package Enos.SpringProject.medVoll.controllers;

import Enos.SpringProject.medVoll.models.dto.reads.ReadPatientDTO;
import Enos.SpringProject.medVoll.models.dto.registers.RegisterPatientDTO;
import Enos.SpringProject.medVoll.models.dto.updates.UpdatePatientDTO;
import Enos.SpringProject.medVoll.services.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/register")
    public ResponseEntity registerPatient(@RequestBody @Valid RegisterPatientDTO registerPatientDTO){
        var patient = patientService.registerPatient(registerPatientDTO);
        return ResponseEntity.ok(patient);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<ReadPatientDTO>> getPatientsInDB(){
        var page = patientService.getPatientsInDB();
        return ResponseEntity.ok(page);
    }

    @GetMapping("/list/{cpf}")
    public ResponseEntity<ReadPatientDTO> getPatientByCPF(@PathVariable(value = "cpf") String cpf){
        var patient = patientService.getPatientByCPF(cpf);
        return ResponseEntity.ok(patient);
    }

    @PutMapping("/edit")
    public ResponseEntity updatePatient(@RequestBody @Valid UpdatePatientDTO updatePatientDTO){
        var patient = patientService.updatePatient(updatePatientDTO);
        return ResponseEntity.ok(patient);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletePatient(@PathVariable(value = "id") Long id){
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

}
