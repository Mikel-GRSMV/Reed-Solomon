package ru.folder.company;

import ru.folder.company.employer.Employer;

import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
//менеджер сущностей<изначально работает с любым типом который ему подастся>
public class EmployerManager<T extends Employer> {
    @OneToMany
    @JoinColumn(name = "company_id")
    private final List<T> employer;

    public EmployerManager() {
        this.employer = new ArrayList<>();
    }

    public int getSize() {
        return employer.size();
    }

    public List<T> getEntities() { //вернуть массив всех сущностей, которые сохранил в entities
        return employer;
    }
}
