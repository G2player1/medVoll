package Enos.SpringProject.medVoll.repositorys;

import Enos.SpringProject.medVoll.models.Expertise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IExpertiseRepository extends JpaRepository<Expertise,Long> {

    List<Expertise> findByActive(Integer active);
}
