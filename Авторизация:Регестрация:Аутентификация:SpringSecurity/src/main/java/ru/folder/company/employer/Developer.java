package ru.folder.company.employer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "developers")
public class Developer extends Employer<ITRole> {
    @Column(name = "language")
    private String language;

    //конструктор для работы hibernate(ему очень важно чтобы у сущности был пустой конструктор)
    public Developer(){}

    public Developer(String name, int age, String language) {
        super(name, age, ITRole.DEVELOPER);
        this.language = language;
    }

    @Override
    public void work() {
        writeCode();
    }

    public String getLanguage() {
        return language;
    }

    public void writeCode(){
        System.out.println(this.getName() + " write " + this.language + " code...");
    }

    @Override
    public String toString() {
        return "Developer{" +
                "name= " + getName() + '\'' +
                "age= " + getAge() + '\'' +
                "language= " + language + '\'' +
                '}';
    }
}

