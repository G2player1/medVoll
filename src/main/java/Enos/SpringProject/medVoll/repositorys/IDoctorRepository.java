package Enos.SpringProject.medVoll.repositorys;

import Enos.SpringProject.medVoll.enums.ExpertiseEnum;
import Enos.SpringProject.medVoll.models.Doctor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDoctorRepository extends JpaRepository<Doctor,Long> {

    List<Doctor> findByActive(Integer active);
    List<Doctor> findByExpertises_ExpertiseAndActive(ExpertiseEnum expertise, Integer doctorActive);
    Doctor getReferenceByIdAndActive(Long id,Integer active);
}
