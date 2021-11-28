package ru.folder.company.employer;

import ru.folder.company.ITCompany;

import javax.persistence.*;

//указывается чтобы все прописанные в этом классе поля перетекли в дочерние классы.
//@MappedSuperclass

@Entity
@Table(name = "employees")
@Inheritance(strategy = InheritanceType.JOINED)
//этот класс абстрактный тк я не указываю в нем реализацию метода интерфейса Worker, если бы он был здесь реализован,
//то необходимости в абстракции не было.Если я захочу создать экземпляр данного класса, то нужно будет на месте через
//анонимный класс реализовать метод work().
//Этот класс можно было сделать интерфейсом,но в нем есть состояние(поля) присущее именно сотруднику.И приходилось
//бы реализовывать эти поля в каждом классе реализующий интерфейс Employer.
public class Employer<T> implements Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private T role;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private ITCompany company;

    public Employer() {
    }

    //конструктор отвечает за инициализацию объекта
    public Employer(String name, int age, T role) { //принимает параметры
        //при создании объекта сохраняю поля, которые пришли в качестве параметра
        this.name = name;
        this.age = age;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ITCompany getCompany() {
        return company;
    }

    public void setCompany(ITCompany company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println(name + "is set");
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        System.out.println(age + "is set");
        this.age = age;
    }

    public T getRole() {
        return role;
    }

    public void setRole(T role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Employer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", role=" + role +
                '}';
    }
}
