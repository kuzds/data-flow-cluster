package ru.kuzds.userflow.bare.db2web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kuzds.userflow.bare.db2web.repository.entity.NewUser;

@Repository
public interface NewUserRepository extends JpaRepository<NewUser, Integer> {
}
