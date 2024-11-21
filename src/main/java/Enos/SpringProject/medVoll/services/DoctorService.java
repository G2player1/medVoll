package Enos.SpringProject.medVoll.services;

import Enos.SpringProject.medVoll.models.Address;
import Enos.SpringProject.medVoll.models.Doctor;
import Enos.SpringProject.medVoll.models.Expertise;
import Enos.SpringProject.medVoll.models.associations.DoctorExpertiseAssociation;
import Enos.SpringProject.medVoll.models.dto.DoctorDTO;
import Enos.SpringProject.medVoll.models.dto.ExpertiseDTO;
import Enos.SpringProject.medVoll.repositorys.IDoctorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    @Autowired
    private IDoctorRepository doctorRepository;

    @Transactional
    public void registerDoctorInDB(DoctorDTO doctorDTO){
        Doctor doctor = new Doctor(doctorDTO);
        Address address = new Address(doctorDTO.endereco());
        address.setDoctor(doctor);
        doctor.setAddress(address);
        for (ExpertiseDTO expertiseDTO : doctorDTO.especialidades()){
            Expertise expertise = new Expertise(expertiseDTO);
            DoctorExpertiseAssociation doctorExpertise = new DoctorExpertiseAssociation(doctor,expertise);
            expertise.add(doctorExpertise);
            doctor.addExpertises(doctorExpertise);
        }
        doctorRepository.save(doctor);
    }
}
