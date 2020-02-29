package pro.it.serverauthjwtbega.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pro.it.serverauthjwtbega.constants.Role;
import pro.it.serverauthjwtbega.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByEmailAndRoleIn(String email, Collection<Role> roles);

}
