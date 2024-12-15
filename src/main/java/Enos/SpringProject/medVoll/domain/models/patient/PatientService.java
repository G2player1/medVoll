package Enos.SpringProject.medVoll.domain.models.patient;

import Enos.SpringProject.medVoll.domain.models.address.Address;
import Enos.SpringProject.medVoll.domain.models.patient.dto.ReadDetailedPatientDTO;
import Enos.SpringProject.medVoll.domain.models.patient.dto.ReadPatientDTO;
import Enos.SpringProject.medVoll.domain.models.patient.dto.ReadUpdatedPatientDTO;
import Enos.SpringProject.medVoll.domain.models.patient.dto.RegisterPatientDTO;
import Enos.SpringProject.medVoll.domain.models.patient.dto.UpdatePatientDTO;
import jakarta.persistence.EntityNotFoundException;
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
            throw new EntityNotFoundException("O cpf do paciente não existe no Banco de Dados");
        }
        return new ReadDetailedPatientDTO(patient);
    }

    @Transactional
    public ReadUpdatedPatientDTO updatePatient(UpdatePatientDTO updatePatientDTO) {
        var patient = patientRepository.getReferenceByIdAndActive(updatePatientDTO.id(),1);
        if (patient == null){
            throw new EntityNotFoundException("o id do paciente é invalido");
        }
        patient.updateData(updatePatientDTO);
        return new ReadUpdatedPatientDTO(patient);
    }

    @Transactional
    public void deletePatient(Long id) {
        var patient = patientRepository.getReferenceByIdAndActive(id,1);
        if (patient == null){
            throw new EntityNotFoundException("o id do paciente é invalido");
        }
        patient.patientDeleteLogical();
    }
}
