package ru.folder.dao.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.folder.company.employer.Developer;

@Repository
public interface DeveloperRepository extends CrudRepository<Developer, Long> {
}
