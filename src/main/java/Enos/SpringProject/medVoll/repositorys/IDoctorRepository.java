package Enos.SpringProject.medVoll.repositorys;

import Enos.SpringProject.medVoll.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDoctorRepository extends JpaRepository<Doctor,Long> {
}
