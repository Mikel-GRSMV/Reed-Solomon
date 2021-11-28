package ru.folder.dto;

import lombok.Data;
import org.springframework.util.CollectionUtils;
import ru.folder.company.ITCompany;
import ru.folder.company.employer.Employer;
import ru.folder.company.employer.ITRole;

import java.util.List;
import java.util.stream.Collectors;

@Data
//объект, который будет трансформироваться из какой то бизнесовой сущности(ITCompany).На практике за частую сами бизнес
//сущности не транслируются в клиентской часте, в контроллере.Возможно в ITCompany может быть много служебной информации
//которую впрниципе клиенту не нужно знать(id в БД и тд).По этому создается DataTransferObject, те это та сущность, которая
//будет отдаваться клиенту.
//Data Transfer Object
public class CompanyDTO {
    //данная сущность имеет 3 поля
    private String name;
    private ITEmployeeDTO director;
    private List<ITEmployeeDTO> employers;

    //создал статичный метод, который возвращает экземпляр класса CompanyDTO из бизнес-сущности ITCompany и я из него
    //собираю DTO.
    public static CompanyDTO from(ITCompany company){
        CompanyDTO companyDTO = new CompanyDTO();

        companyDTO.setDirector(ITEmployeeDTO.from(company.getDirector()));
        companyDTO.setName(company.getName());

        List<ITEmployeeDTO> itEmployeeDTOList = company.getEntities().stream()
                .map(ITEmployeeDTO::from)
                .collect(Collectors.toList());
        companyDTO.setEmployers(itEmployeeDTOList);

        return companyDTO;
    }

    //получаю сущность ITCompany
    public ITCompany toCompany(){
        ITCompany company = new ITCompany(this.name);//находится в состоянии transient(не сохранена эта сущность в контексте персистентности)

        //заполняю поля этой компании, в данном случае указываю директора(в составе DTO приходит дироектор(см выше))
        company.setDirector(this.director.toEmployee());//благодаря методу toEmployee() преобразую в сущность из DTO
        //устанавливаю для этого директора компанию
        company.getDirector().setCompany(company);

        //беру список сотрудников, которые пришли из DTO
        if (!CollectionUtils.isEmpty(this.employers)){
            List<Employer<ITRole>> employers = this.employers.stream()
                    .map(ITEmployeeDTO::toEmployee)//и тоже преобразую их в сотрудников
                    .peek(employer -> employer.setCompany(company))
                    .collect(Collectors.toList());
            company.getEntities().addAll(employers);
        }
        return company;//возвращаю сущность компанию, но она пока не персистентна.Для добавления нужно вызвать метод persist()
    }
}
