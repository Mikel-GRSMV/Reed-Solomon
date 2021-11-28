package ru.folder.company;

import org.springframework.beans.factory.annotation.Autowired;
import ru.folder.company.employer.Employer;
import ru.folder.company.employer.ITRole;

import javax.persistence.*;

//@Component("CompanyComponent")
//здесь я утверждаю, что менеджер сущностей конкретно будет, менеджер сущностьей объекта - Worker.
//явно говорю, что ITCompany это по сути менеджер реализует логику EmployerManager над объектами интерфейса Worker.
@Entity
@Table(name = "company")
public class ITCompany extends EmployerManager<Employer<ITRole>> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

   // @Value("NameOurCompany")
    @Column(name = "name")
    private String name;

    //@Autowired
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "director_id")
    private Employer<ITRole> director;

    public ITCompany(String companyName) {
        super();
        this.name = companyName;
    }

    public ITCompany() {
    }

    //ITCompany начинает работать
    public void startWork() {
        getEntities().forEach(itRoleEmployer -> {
            itRoleEmployer.work();
            System.out.println(itRoleEmployer.getName() + " is " + itRoleEmployer.getRole());
        });
        }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
            return name;
        }

        public Employer<ITRole> getDirector() {
            return director;
        }

        public void setDirector(Employer<ITRole> director) {
            this.director = director;
        }




    }

