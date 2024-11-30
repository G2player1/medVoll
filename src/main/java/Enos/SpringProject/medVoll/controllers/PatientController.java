package Enos.SpringProject.medVoll.controllers;

import Enos.SpringProject.medVoll.models.dto.reads.ReadPatientDTO;
import Enos.SpringProject.medVoll.models.dto.registers.RegisterPatientDTO;
import Enos.SpringProject.medVoll.models.dto.updates.UpdatePatientDTO;
import Enos.SpringProject.medVoll.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/register")
    public void registerPatient(@RequestBody RegisterPatientDTO registerPatientDTO){
        patientService.registerPatient(registerPatientDTO);
    }

    @GetMapping("/list")
    public Page<ReadPatientDTO> getPatientsInDB(){
        return patientService.getPatientsInDB();
    }

    @PutMapping("/edit")
    public void updatePatient(@RequestBody UpdatePatientDTO updatePatientDTO){
        patientService.updatePatient(updatePatientDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePatient(@PathVariable(value = "id") Long id){
        patientService.deletePatient(id);
    }

}
