package Enos.SpringProject.medVoll.repositorys;

import Enos.SpringProject.medVoll.enums.ExpertiseEnum;
import Enos.SpringProject.medVoll.models.Doctor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDoctorRepository extends JpaRepository<Doctor,Long> {

    List<Doctor> findByExpertises_Expertise(Pageable pageable, ExpertiseEnum expertise);
}
