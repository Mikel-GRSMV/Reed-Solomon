package ru.folder.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.folder.company.ITCompany;

@Repository
public interface CompanyRepository extends CrudRepository<ITCompany, Long> {

}
