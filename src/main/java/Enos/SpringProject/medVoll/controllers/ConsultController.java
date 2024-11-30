package Enos.SpringProject.medVoll.controllers;

import Enos.SpringProject.medVoll.models.dto.reads.ReadConsultDTO;
import Enos.SpringProject.medVoll.models.dto.registers.RegisterConsultDTO;
import Enos.SpringProject.medVoll.services.ConsultService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consults")
public class ConsultController {

    @Autowired
    private ConsultService consultService;

    @PostMapping("/register")
    public void registerConsult(@RequestBody @Valid RegisterConsultDTO registerConsultDTO){
        consultService.registerConsult(registerConsultDTO);
    }

    @GetMapping("/list")
    public Page<ReadConsultDTO> getConsultsInDB(){
        return consultService.getConsultsInDB();
    }

    @GetMapping("/list/doctor/{id}")
    public Page<ReadConsultDTO> getDoctorConsults(@PathVariable(value = "id") Long id){
        return consultService.getDoctorConsults(id);
    }

    @GetMapping("/list/patient/{id}")
    public Page<ReadConsultDTO> getPatientConsults(@PathVariable(value = "id") Long id){
        return consultService.getPatientConsults(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteConsult(@PathVariable(value = "id") Long id){
        consultService.deleteConsult(id);
    }
}
