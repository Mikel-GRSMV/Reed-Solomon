package ru.folder.dao;

import ru.folder.company.ITCompany;
import ru.folder.company.employer.Developer;
import ru.folder.company.employer.Employer;
import ru.folder.company.employer.ITRole;

import java.util.List;

public interface CompanyDao {
    Integer createCompany(ITCompany company);

    ITCompany find(long id);

    void addDeveloper(Developer developer);

    List<Employer> getEmployersByRole(ITRole role, int company_id);

//    Employer<ITRole> getEmployerByIndex(int employee_id);
}
