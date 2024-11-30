package Enos.SpringProject.medVoll.controllers;

import Enos.SpringProject.medVoll.models.dto.reads.ReadPatientDTO;
import Enos.SpringProject.medVoll.models.dto.registers.RegisterPatientDTO;
import Enos.SpringProject.medVoll.models.dto.updates.UpdatePatientDTO;
import Enos.SpringProject.medVoll.services.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/register")
    public void registerPatient(@RequestBody @Valid RegisterPatientDTO registerPatientDTO){
        patientService.registerPatient(registerPatientDTO);
    }

    @GetMapping("/list")
    public Page<ReadPatientDTO> getPatientsInDB(){
        return patientService.getPatientsInDB();
    }

    @GetMapping("/list/{cpf}")
    public ReadPatientDTO getPatientByCPF(@PathVariable(value = "cpf") String cpf){
        return patientService.getPatientByCPF(cpf);
    }

    @PutMapping("/edit")
    public void updatePatient(@RequestBody @Valid UpdatePatientDTO updatePatientDTO){
        patientService.updatePatient(updatePatientDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePatient(@PathVariable(value = "id") Long id){
        patientService.deletePatient(id);
    }

}
