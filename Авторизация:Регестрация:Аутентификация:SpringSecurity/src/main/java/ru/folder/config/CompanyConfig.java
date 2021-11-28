package ru.folder.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;
import ru.folder.company.ITCompany;
import ru.folder.company.employer.Employer;
import ru.folder.company.employer.ITRole;

@Slf4j
@Configuration
@ComponentScan("ru.folder.company")
public class CompanyConfig {
    //тк здесь не указан какой bean - singleton
    @Bean
    @Primary
    //в параметре так же автоматически подставиться директор из @Bean("DirectorOurCompany")-DI
    public ITCompany getItCompany(Employer<ITRole> director) {
        ITCompany company = new ITCompany("SoftEngineer");
        company.setDirector(director);
        return company;
    }

    @Bean("RequestScopedCompany")
    @RequestScope
    public ITCompany getRequestScopeCompany(Employer<ITRole> director) {
        log.info("create RequestScope bean");
        ITCompany company = new ITCompany("SoftEngineer");
        company.setDirector(director);
        return company;
    }

    @Bean("SessionScopeCompany")
    @SessionScope
    public ITCompany getSessionScopeCompany(Employer<ITRole> director) {
        log.info("create SessionScope bean");
        ITCompany company = new ITCompany("SoftEngineer");
        company.setDirector(director);
        return company;
    }

    @Bean("PrototypeScopeCompany")
    @Scope("prototype")
    public ITCompany getPrototypeScopeCompany(Employer<ITRole> director) {
        log.info("create PrototypeScope bean");
        ITCompany company = new ITCompany("SoftEngineer");
        company.setDirector(director);
        return company;
    }

    //Этот Директор заинжектится в бин выше
    @Bean
    public Employer<ITRole> getDirector() {
        return new Employer<ITRole>("Oleg", 30, ITRole.DIRECTOR) {
            @Override
            public void work() {
                System.out.println(this.getName() + "is directing");
            }
        };
    }

//    @Bean("CompanyName")
//    public String getCompanyName() {
//        return "SoftLine";
//    }
//
//    @Bean("MaxEmployerCount")
//    public int getCount() {
//        return 100;
//    }
}
