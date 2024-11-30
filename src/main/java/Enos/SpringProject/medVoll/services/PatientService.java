package Enos.SpringProject.medVoll.services;

import Enos.SpringProject.medVoll.models.Address;
import Enos.SpringProject.medVoll.models.Patient;
import Enos.SpringProject.medVoll.models.dto.reads.ReadPatientDTO;
import Enos.SpringProject.medVoll.models.dto.registers.RegisterPatientDTO;
import Enos.SpringProject.medVoll.models.dto.updates.UpdatePatientDTO;
import Enos.SpringProject.medVoll.repositorys.IPatientRepository;
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
    public void registerPatient(RegisterPatientDTO registerPatientDTO) {
        Patient patient = new Patient(registerPatientDTO);
        Address address = new Address(registerPatientDTO.endereco());
        patient.setAddress(address);
        address.setPatient(patient);
        patientRepository.save(patient);
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
    public ReadPatientDTO getPatientByCPF(String cpf) {
        var patient = patientRepository.findByCpfAndActive(cpf,1);
        return new ReadPatientDTO(patient);
    }

    @Transactional
    public void updatePatient(UpdatePatientDTO updatePatientDTO) {
        var patient = patientRepository.getReferenceByIdAndActive(updatePatientDTO.id(),1);
        patient.updateData(updatePatientDTO);
    }

    @Transactional
    public void deletePatient(Long id) {
        var patient = patientRepository.getReferenceByIdAndActive(id,1);
        patient.patientDeleteLogical();
    }
}
