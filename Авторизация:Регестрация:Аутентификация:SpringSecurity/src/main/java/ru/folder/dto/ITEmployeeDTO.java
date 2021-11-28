package ru.folder.dto;

import lombok.Data;
import ru.folder.company.employer.Employer;
import ru.folder.company.employer.ITRole;

@Data
public class ITEmployeeDTO {
    private int id;
    private String name;
    private int age;
    private ITRole role;

    public static ITEmployeeDTO from(Employer<ITRole> employer){
        ITEmployeeDTO dto = new ITEmployeeDTO();

        dto.setId(employer.getId());
        dto.setName(employer.getName());
        dto.setAge(employer.getAge());
        dto.setRole(employer.getRole());

        return dto;
    }

    public Employer<ITRole> toEmployee(){
        Employer<ITRole> employer = new Employer<>();

        employer.setId(this.id);
        employer.setAge(this.age);
        employer.setName(this.name);
        employer.setRole(this.role);

        return employer;
    }
}
