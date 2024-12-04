package Enos.SpringProject.medVoll.domain.repositorys;

import Enos.SpringProject.medVoll.domain.enums.ExpertiseEnum;
import Enos.SpringProject.medVoll.domain.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDoctorRepository extends JpaRepository<Doctor,Long> {

    List<Doctor> findByActive(Integer active);
    List<Doctor> findByExpertises_ExpertiseAndActive(ExpertiseEnum expertise, Integer doctorActive);
    Doctor getReferenceByIdAndActive(Long id,Integer active);
}
