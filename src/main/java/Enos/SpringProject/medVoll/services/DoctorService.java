package Enos.SpringProject.medVoll.services;

import Enos.SpringProject.medVoll.enums.ExpertiseEnum;
import Enos.SpringProject.medVoll.models.Address;
import Enos.SpringProject.medVoll.models.Doctor;
import Enos.SpringProject.medVoll.models.Expertise;
import Enos.SpringProject.medVoll.models.associations.DoctorExpertiseAssociation;
import Enos.SpringProject.medVoll.models.dto.RegisterDoctorDTO;
import Enos.SpringProject.medVoll.models.dto.ListingDoctorDTO;
import Enos.SpringProject.medVoll.models.dto.ExpertiseDTO;
import Enos.SpringProject.medVoll.models.dto.UpdateDoctorDTO;
import Enos.SpringProject.medVoll.repositorys.IDoctorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    @Autowired
    private IDoctorRepository doctorRepository;

    @Transactional
    public void registerDoctorInDB(RegisterDoctorDTO registerDoctorDTO){
        Doctor doctor = new Doctor(registerDoctorDTO);
        Address address = new Address(registerDoctorDTO.endereco());
        address.setDoctor(doctor);
        doctor.setAddress(address);
        for (ExpertiseDTO expertiseDTO : registerDoctorDTO.especialidades()){
            Expertise expertise = new Expertise(expertiseDTO);
            DoctorExpertiseAssociation doctorExpertise = new DoctorExpertiseAssociation(doctor,expertise);
            expertise.add(doctorExpertise);
            doctor.addExpertises(doctorExpertise);
        }
        doctorRepository.save(doctor);
    }

    @Transactional
    public Page<ListingDoctorDTO> getDoctorsInDB(Pageable pageable){
        return doctorRepository.findAll(pageable).map(ListingDoctorDTO::new);
    }

    @Transactional
    public Page<ListingDoctorDTO> getDoctorsInDbByExpertise(Pageable pageable, String expertise) {
        return doctorRepository.findDoctorsByExpertise(pageable,ExpertiseEnum.fromString(expertise));
    }

    @Transactional
    public void updateDoctor(UpdateDoctorDTO updateDoctorDTO) {
        var doctor = doctorRepository.getReferenceById(updateDoctorDTO.id());
        doctor.updateData(updateDoctorDTO);
    }
}
