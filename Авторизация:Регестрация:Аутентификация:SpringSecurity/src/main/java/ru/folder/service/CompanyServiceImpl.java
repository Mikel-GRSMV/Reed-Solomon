package ru.folder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.folder.company.ITCompany;
import ru.folder.company.employer.Developer;
import ru.folder.company.employer.Employer;
import ru.folder.company.employer.ITRole;
import ru.folder.dao.CompanyDao;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
//    @Autowired
//    //Указываю что сюда нужно внедрять bean именно с таким названием(живет рамках текущего запроса)
//    @Qualifier("RequestScopedCompany")
//    private ITCompany company;

    @Autowired
    CompanyDao companyDao;

    @Override
    @Transactional
    public Integer createCompany(ITCompany company) {
      return companyDao.createCompany(company);
    }

    @Override
    public ITCompany getCompany(int id) {
        return companyDao.find(id);
    }

    @Override
    @Transactional
    public void addDeveloper(Developer developer, int id) {
        developer.setCompany(getCompany(id));
        companyDao.addDeveloper(developer);
    }

    @Override
    public List<Employer> getEmployersByRole(ITRole role, int company_id) {
//        List<Employer<ITRole>> employers = company.getEntities().stream() //по всем сотрудникам компании прохожусь
//                .filter(itRoleEmployer -> itRoleEmployer.getRole().equals(role)) //фильтрую по роле указанной в @RequestParam
//                .collect(Collectors.toList()); //и создаю новый список сотрудников
        return companyDao.getEmployersByRole(role, company_id);
    }

//    @Override
//    @Transactional
//    public Employer<ITRole> getEmployerByIndex(int index) {
//       return companyDao.getEmployerByIndex(index);
//    }
}
