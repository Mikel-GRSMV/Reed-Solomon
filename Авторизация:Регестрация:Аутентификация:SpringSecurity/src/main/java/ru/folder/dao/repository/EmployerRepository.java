package ru.folder.dao.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.folder.company.ITCompany;
import ru.folder.company.employer.Employer;
import ru.folder.company.employer.ITRole;

import java.util.List;

@Repository
public interface EmployerRepository extends CrudRepository<Employer<ITRole>, Long> {
    List<Employer> findByRoleAndCompany(ITRole role, ITCompany company);
    List<Employer> findByRoleAndCompanyId(ITRole role, long company_id);

    @Query("select e from Employer e where e.role = :role and e.company = :company")
    List<Employer> findByRoleAndCompanyQ(ITRole role, ITCompany company);
}
