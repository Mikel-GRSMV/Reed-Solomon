package ru.folder.dao;

import org.springframework.stereotype.Repository;
import ru.folder.company.ITCompany;
import ru.folder.company.employer.Developer;
import ru.folder.company.employer.Employer;
import ru.folder.company.employer.ITRole;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CompanyDaoImpl implements CompanyDao {
    //Если бы это был обычный Spring, то нужно было бы настроить Entity Manager/Transaction Manager/Data Source...
    //в Spring Boot я делаю просто при помощи аннотации вытаскиваю из context-spring вытянуть EmployerManager
    @PersistenceContext
    //инструмент через который мы можем производить операции в БД оперируя созданными сущностями
    private EntityManager entityManager;

    @Override
    public Integer createCompany(ITCompany company) {
        //после сохранения компании в БД
        entityManager.persist(company);
        entityManager.flush();
        //получаю id этой компании
        return company.getId();
    }

    @Override
    public ITCompany find(long id) {
        //Ищу класс ITCompany по заданному id
        return entityManager.find(ITCompany.class, id);
    }

    @Override
    public void addDeveloper(Developer developer) {
         //company.getEntities().add(developer);//добавляю developer как Employer
        //область персистентности куда складываются объекты для того чтобы в последующем записать в базу(кэш 1-уровня)
        entityManager.persist(developer);//будет сохраняться новая таблица в БД.Все что нужно это вызвать метод persist
        //у entityManager и положить в него экземпляр класса(сложит эту сущность в кэш 1уровня с послед-м сохранением в БД)
        //entityManager.flush();//для того чтобы вручную сбросить кэш в БД
    }

    @Override
    public List<Employer> getEmployersByRole(ITRole role, int company_id) {
        List<Employer> employers = entityManager.createQuery(
                "select e from Employer e where e.role = :role and e.company = :company", Employer.class)
                .setParameter("role", role)
                .setParameter("company", find(company_id))
                .getResultList();
        return employers; //возвращаю ответом список сотрудников
    }

//    @Override
//    public Employer<ITRole> getEmployerByIndex(int employee_id) {
//        //Employer<ITRole> employer = company.getEntities().get(index);//берем у company всех сотрудников,ищем по индексу
//        Developer developer = entityManager.find(Developer.class, employee_id);
//        return developer;//если находим response(ответ) сотрудника приходит статус 200 ОК
//    }
}
