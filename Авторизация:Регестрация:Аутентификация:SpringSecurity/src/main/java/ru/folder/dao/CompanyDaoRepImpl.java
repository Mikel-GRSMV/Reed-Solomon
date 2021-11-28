package ru.folder.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.folder.company.ITCompany;
import ru.folder.company.employer.Developer;
import ru.folder.company.employer.Employer;
import ru.folder.company.employer.ITRole;
import ru.folder.dao.repository.CompanyRepository;
import ru.folder.dao.repository.DeveloperRepository;
import ru.folder.dao.repository.EmployerRepository;

import java.util.List;

//чтобы именно этот компонент инжектился в service
@Primary
@Component
public class CompanyDaoRepImpl implements CompanyDao{
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    DeveloperRepository developerRepository;
    @Autowired
    EmployerRepository employerRepository;

    @Override
    public Integer createCompany(ITCompany company) {
        ITCompany savedCompany = companyRepository.save(company);
        //возвращаю id вновь созданной компании.
        return savedCompany.getId();
    }

    @Override
    public ITCompany find(long id) {
        //findById() возвращает Optional.По этому добавил обработку.
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public void addDeveloper(Developer developer) {
        //добавление разработчика в таблицу.
        developerRepository.save(developer);
    }

    @Override
    public List<Employer> getEmployersByRole(ITRole role, int company_id) {
        ITCompany company = find(company_id);
        //return employerRepository.findByRoleAndCompany(role, company);
        //return employerRepository.findByRoleAndCompany(role, company_id);
        return employerRepository.findByRoleAndCompanyQ(role, company);
    }

//    @Override
//    public Employer<ITRole> getEmployerByIndex(int employee_id) {
//        return null;
//    }
}
