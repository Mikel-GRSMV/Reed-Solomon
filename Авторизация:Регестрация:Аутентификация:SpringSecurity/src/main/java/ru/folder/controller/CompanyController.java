package ru.folder.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.folder.company.employer.Developer;
import ru.folder.company.employer.Employer;
import ru.folder.company.employer.ITRole;
import ru.folder.dto.ITEmployeeDTO;
import ru.folder.service.CompanyService;
import ru.folder.dto.CompanyDTO;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
//в случает данного контроллера view будет JSON(по умолчанию) или xml
@RestController
//начальный путь в этот Контроллер,то есть все запросы на /company будут приходить в этот контроллер
@RequestMapping("/company")
public class CompanyController {
    //по правилам MVC ее недолжно быть тут
    //Сюда инжектится бизнес логика из bean @Primary в config
    //@Autowired
    //ITCompany company;
    //Экземпляр класса ITCompany я сюда с помощью DI, просто внедряю данную зависимость из context берется подходящий
    //bean на это место.

    private final CompanyService service;
    @Autowired
    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @PostMapping
    public Integer createCompany(@RequestBody CompanyDTO companyDTO){
        return service.createCompany(companyDTO.toCompany());
    }

    //будет выдаваться сразу при /company. DispatcherServlet будет вызывать именно этот метод при запросе.
    @GetMapping("/{id}")
    public CompanyDTO company(@PathVariable int id) {
        //возвращает DTO файл который создан из company, который я заинжектил при помощи DI из context-Spring
        return CompanyDTO.from(service.getCompany(id));
    }

    //пост запрос по URL
    @PostMapping("/{id}/employers/developers")
    public ResponseEntity addEmployer(@RequestBody Developer developer,
                                      @PathVariable(name = "id") int company_id) {
        log.info("add developer");// в консоли будет выводится уведомление у добавлении
        service.addDeveloper(developer, company_id);
        return ResponseEntity.ok().build();//возвращаю ответ клиенту, что все хорошо прошло
    }

    //{index}-параметр, который может менять пользователь/клиент на своей стороне.И мы должны как то обрабатывать его.
//    @GetMapping("/employers/{index}")
//    public ResponseEntity<Employer<ITRole>> getEmployerByIndex(@PathVariable int index) {
//        log.info("get employer by index =" + index);
//        try {
//            return ResponseEntity.ok(service.getEmployerByIndex(index));
//        } catch (IndexOutOfBoundsException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

    //здесь указывается параметр /find?role=DEVELOPER
    //если объедиим с верхним то сможем достать по индексу сотрудника из определнной области деятельности
    //но для этого нужно будет написать еще один параметр в метод.
//    @GetMapping("/{id}/employers/find")
//    public ResponseEntity<List<ITEmployeeDTO>> getEmployerByRole(@RequestParam(name = "role") ITRole role,
//                                                                 @PathVariable(name = "id") int company_id) {
//        log.info("get employers by role = " + role);
//
//        return ResponseEntity.ok(service.getEmployersByRole(role, company_id)
//                .stream()
//                .map(ITEmployeeDTO::from)
//                .collect(Collectors.toList()));

        //return ResponseEntity.ok(service.getEmployersByRole(result));
 //   }

}
