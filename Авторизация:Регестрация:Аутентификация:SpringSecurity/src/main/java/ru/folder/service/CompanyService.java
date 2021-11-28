package ru.folder.service;

import ru.folder.company.ITCompany;
import ru.folder.company.employer.Developer;
import ru.folder.company.employer.Employer;
import ru.folder.company.employer.ITRole;
import ru.folder.company.employer.PM;

import java.util.List;

public interface CompanyService {
    //получать компанию
    ITCompany getCompany(int id);

    //добавляем разработчика
    void addDeveloper(Developer developer, int company_id);

    //получаем разработчика по роле
    List<Employer> getEmployersByRole(ITRole role, int company_id);

    //получаем разработчика по индексу
//    Employer<ITRole> getEmployerByIndex(int index);

    Integer createCompany(ITCompany company);
}
