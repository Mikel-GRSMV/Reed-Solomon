package ru.folder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Эта аннотация собщает, что здесь точка входа
@SpringBootApplication
public class Program {
    public static void main(String[] args) {
//        ApplicationContext context = new AnnotationConfigApplicationContext(CompanyConfig.class);
//        //ITCompany company = context.getBean("CompanyComponent", ITCompany.class);
//        ITCompany company = context.getBean(ITCompany.class);
//        System.out.println(company.getName());
//        System.out.println(company.getDirector());

        //стартуем Spring-application, передав какой класс за это отвечает и какие аргументы сюда будут подаваться.
        SpringApplication.run(Program.class, args);

    }
}
