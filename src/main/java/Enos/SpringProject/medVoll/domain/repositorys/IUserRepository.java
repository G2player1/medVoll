package Enos.SpringProject.medVoll.domain.repositorys;

import Enos.SpringProject.medVoll.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {

    UserDetails findByLogin(String username);
}
