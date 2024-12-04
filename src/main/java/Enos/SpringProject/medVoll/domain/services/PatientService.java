package Enos.SpringProject.medVoll.domain.services;

import Enos.SpringProject.medVoll.domain.exceptions.RequestBdException;
import Enos.SpringProject.medVoll.domain.models.Address;
import Enos.SpringProject.medVoll.domain.models.Patient;
import Enos.SpringProject.medVoll.domain.models.dto.reads.ReadDetailedPatientDTO;
import Enos.SpringProject.medVoll.domain.models.dto.reads.ReadPatientDTO;
import Enos.SpringProject.medVoll.domain.models.dto.reads.ReadUpdatedPatientDTO;
import Enos.SpringProject.medVoll.domain.models.dto.registers.RegisterPatientDTO;
import Enos.SpringProject.medVoll.domain.models.dto.updates.UpdatePatientDTO;
import Enos.SpringProject.medVoll.domain.repositorys.IPatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private IPatientRepository patientRepository;

    @Transactional
    public ReadDetailedPatientDTO registerPatient(RegisterPatientDTO registerPatientDTO) {
        Patient patient = new Patient(registerPatientDTO);
        Address address = new Address(registerPatientDTO.endereco());
        patient.setAddress(address);
        address.setPatient(patient);
        patientRepository.save(patient);
        return new ReadDetailedPatientDTO(patient);
    }

    @Transactional
    public Page<ReadPatientDTO> getPatientsInDB(){
        List<ReadPatientDTO> patientDTOList;
        List<Patient> aux = patientRepository.findByActive(1);
        patientDTOList = aux
                .stream()
                .map(ReadPatientDTO::new)
                .toList();
        return new PageImpl<>(patientDTOList);
    }

    @Transactional
    public ReadDetailedPatientDTO getPatientByCPF(String cpf) {
        var patient = patientRepository.findByCpfAndActive(cpf,1);
        if (patient == null){
            throw new RequestBdException("o cpf do paciente é invalido");
        }
        return new ReadDetailedPatientDTO(patient);
    }

    @Transactional
    public ReadUpdatedPatientDTO updatePatient(UpdatePatientDTO updatePatientDTO) {
        var patient = patientRepository.getReferenceByIdAndActive(updatePatientDTO.id(),1);
        if (patient == null){
            throw new RequestBdException("o id do paciente é invalido");
        }
        patient.updateData(updatePatientDTO);
        return new ReadUpdatedPatientDTO(patient);
    }

    @Transactional
    public void deletePatient(Long id) {
        var patient = patientRepository.getReferenceByIdAndActive(id,1);
        if (patient == null){
            throw new RequestBdException("o id do paciente é invalido");
        }
        patient.patientDeleteLogical();
    }
}
