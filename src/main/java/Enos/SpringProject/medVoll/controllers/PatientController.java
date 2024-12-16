package Enos.SpringProject.medVoll.controllers;

import Enos.SpringProject.medVoll.domain.models.patient.dto.ReadDetailedPatientDTO;
import Enos.SpringProject.medVoll.domain.models.patient.dto.ReadPatientDTO;
import Enos.SpringProject.medVoll.domain.models.patient.dto.ReadUpdatedPatientDTO;
import Enos.SpringProject.medVoll.domain.models.patient.dto.RegisterPatientDTO;
import Enos.SpringProject.medVoll.domain.models.patient.dto.UpdatePatientDTO;
import Enos.SpringProject.medVoll.domain.models.patient.PatientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("patients")
@SecurityRequirement(name = "bearer-key")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/register")
    public ResponseEntity<ReadDetailedPatientDTO> registerPatient(@RequestBody @Valid RegisterPatientDTO registerPatientDTO,
                                                                  UriComponentsBuilder uriBuilder){
        var patient = patientService.registerPatient(registerPatientDTO);
        var uri = uriBuilder.path("/patients/{cpf}").buildAndExpand(patient.cpf()).toUri();
        return ResponseEntity.created(uri).body(patient);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<ReadDetailedPatientDTO> getPatientByCPF(@PathVariable(value = "cpf") String cpf){
            var patient = patientService.getPatientByCPF(cpf);
            return ResponseEntity.ok(patient);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<ReadPatientDTO>> getPatientsInDB(){
            var page = patientService.getPatientsInDB();
            return ResponseEntity.ok(page);
    }

    @PutMapping("/edit")
    public ResponseEntity<ReadUpdatedPatientDTO> updatePatient(@RequestBody @Valid UpdatePatientDTO updatePatientDTO){
            var patient = patientService.updatePatient(updatePatientDTO);
            return ResponseEntity.ok(patient);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletePatient(@PathVariable(value = "id") Long id){
            patientService.deletePatient(id);
            return ResponseEntity.noContent().build();
    }

}
