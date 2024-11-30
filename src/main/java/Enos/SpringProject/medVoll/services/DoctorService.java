package Enos.SpringProject.medVoll.services;

import Enos.SpringProject.medVoll.enums.ExpertiseEnum;
import Enos.SpringProject.medVoll.exceptions.CantGetEnumException;
import Enos.SpringProject.medVoll.models.Address;
import Enos.SpringProject.medVoll.models.Doctor;
import Enos.SpringProject.medVoll.models.Expertise;
import Enos.SpringProject.medVoll.models.dto.reads.ReadDoctorDTO;
import Enos.SpringProject.medVoll.models.dto.registers.RegisterDoctorDTO;
import Enos.SpringProject.medVoll.models.dto.registers.RegisterExpertiseDTO;
import Enos.SpringProject.medVoll.models.dto.updates.UpdateDoctorDTO;
import Enos.SpringProject.medVoll.repositorys.IDoctorRepository;
import Enos.SpringProject.medVoll.repositorys.IPatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private IDoctorRepository doctorRepository;
    @Autowired
    private IPatientRepository expertiseRepository;

    @Transactional
    public void registerDoctorInDB(RegisterDoctorDTO registerDoctorDTO){
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
    }

    @Transactional
    public Page<ReadDoctorDTO> getDoctorsInDB(){
        List<ReadDoctorDTO> doctorList;
        List<Doctor> aux;
        aux = doctorRepository.findByActive(1);
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
        List<Doctor> aux;
        try {
            aux = doctorRepository.findByExpertises_ExpertiseAndActive(ExpertiseEnum.fromString(expertise),1);
            aux.forEach(doctor -> doctor.getExpertises().removeIf(expertise1 -> !expertise1.isActive()));
            doctorList = aux
                    .stream()
                    .map(ReadDoctorDTO::new)
                    .toList();
            return new PageImpl<>(doctorList);
        } catch (CantGetEnumException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Transactional
    public void updateDoctor(UpdateDoctorDTO updateDoctorDTO) {
        var doctor = doctorRepository.getReferenceByIdAndActive(updateDoctorDTO.id(),1);
        doctor.updateData(updateDoctorDTO);
    }

    @Transactional
    public void deleteDoctorById(Long id) {
        var doctor = doctorRepository.getReferenceByIdAndActive(id,1);
        doctor.doctorDeleteLogical();
    }
}
