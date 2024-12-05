package Enos.SpringProject.medVoll.domain.services;

import Enos.SpringProject.medVoll.domain.enums.ExpertiseEnum;
import Enos.SpringProject.medVoll.domain.models.Address;
import Enos.SpringProject.medVoll.domain.models.Doctor;
import Enos.SpringProject.medVoll.domain.models.Expertise;
import Enos.SpringProject.medVoll.domain.models.dto.reads.ReadDetailedDoctorDTO;
import Enos.SpringProject.medVoll.domain.models.dto.reads.ReadDoctorDTO;
import Enos.SpringProject.medVoll.domain.models.dto.reads.ReadUpdatedDoctorDTO;
import Enos.SpringProject.medVoll.domain.models.dto.registers.RegisterDoctorDTO;
import Enos.SpringProject.medVoll.domain.models.dto.registers.RegisterExpertiseDTO;
import Enos.SpringProject.medVoll.domain.models.dto.updates.UpdateDoctorDTO;
import Enos.SpringProject.medVoll.domain.repositorys.IDoctorRepository;
import Enos.SpringProject.medVoll.domain.repositorys.IPatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private IDoctorRepository doctorRepository;
    @Autowired
    private IPatientRepository expertiseRepository;

    @Transactional
    public ReadDetailedDoctorDTO registerDoctorInDB(RegisterDoctorDTO registerDoctorDTO){
        Doctor doctor = new Doctor(registerDoctorDTO);
        Address address = new Address(registerDoctorDTO.endereco());
        address.setDoctor(doctor);
        doctor.setAddress(address);
        for (RegisterExpertiseDTO registerExpertiseDTO : registerDoctorDTO.especialidades()){
            Expertise expertise = new Expertise(registerExpertiseDTO);
            expertise.setDoctor(doctor);
            doctor.addExpertises(expertise);
        }
        doctorRepository.save(doctor);
        return new ReadDetailedDoctorDTO(doctor);
    }

    @Transactional
    public Page<ReadDoctorDTO> getDoctorsInDB(){
        List<ReadDoctorDTO> doctorList;
        List<Doctor> aux = doctorRepository.findByActive(1);
        aux.forEach(doctor -> doctor.getExpertises().removeIf(expertise -> !expertise.isActive()));
        doctorList = aux
                .stream()
                .map(ReadDoctorDTO::new)
                .toList();
        return new PageImpl<>(doctorList);
    }

    @Transactional
    public Page<ReadDoctorDTO> getDoctorsInDbByExpertise(String expertise) {
        List<ReadDoctorDTO> doctorList;
        List<Doctor> aux = doctorRepository.findByExpertises_ExpertiseAndActive(ExpertiseEnum.fromString(expertise),1);
        aux.forEach(doctor -> doctor.getExpertises().removeIf(expertise1 -> !expertise1.isActive()));
        doctorList = aux
                .stream()
                .map(ReadDoctorDTO::new)
                .toList();
        return new PageImpl<>(doctorList);
    }

    @Transactional
    public ReadUpdatedDoctorDTO updateDoctor(UpdateDoctorDTO updateDoctorDTO) {
        var doctor = doctorRepository.getReferenceByIdAndActive(updateDoctorDTO.id(),1);
        if (doctor == null){
            throw new EntityNotFoundException("o id do medico é invalido");
        }
        doctor.updateData(updateDoctorDTO);
        return new ReadUpdatedDoctorDTO(doctor);
    }

    @Transactional
    public void deleteDoctorById(Long id) {
        var doctor = doctorRepository.getReferenceByIdAndActive(id,1);
        if (doctor == null){
            throw new EntityNotFoundException("o id do medico é invalido");
        }
        doctor.doctorDeleteLogical();
    }

    @Transactional
    public ReadDetailedDoctorDTO getDoctorById(Long id) {
        var doctor = doctorRepository.getReferenceByIdAndActive(id,1);
        if (doctor == null){
            throw new EntityNotFoundException("o id do medico é invalido");
        }
        return new ReadDetailedDoctorDTO(doctor);
    }
}
