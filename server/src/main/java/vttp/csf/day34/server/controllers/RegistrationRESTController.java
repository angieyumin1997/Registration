package vttp.csf.day34.server.controllers;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import vttp.csf.day34.server.models.Registration;
import vttp.csf.day34.server.models.Response;
import vttp.csf.day34.server.service.ContactsRepo;
import vttp.csf.day34.server.service.RedisRepo;

@RestController
@RequestMapping(path="/api/registration", produces=MediaType.APPLICATION_JSON_VALUE)
public class RegistrationRESTController {

    @Autowired
    private RedisRepo redisRepo;

    private Logger logger = Logger.getLogger(RegistrationRESTController.class.getName());
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postRegistration(@RequestBody String payload) {

        logger.info("Payload:%s".formatted(payload));

        //Read the payload and save the data to database
        String id = UUID.randomUUID().toString().substring(0,8);
        Registration reg;
        Response resp;
        
        try{
            reg = Registration.create(payload);
            reg.setId(id);
        }catch(Exception ex){
            resp = new Response();
            resp.setCode(400);
            resp.setMessage(ex.getMessage());
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(resp.toJson().toString());
        }

        //Save to database
        redisRepo.save(reg);

        resp = new Response();
        resp.setCode(201);
        resp.setMessage(id);
        resp.setData(reg.toJson().toString());
        return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(resp.toJson().toString());
    }

    @GetMapping
    public ResponseEntity<String> getContacts(){
        List<Registration> registrations = redisRepo.findAll();

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for ( Registration registration:registrations)
            arrBuilder.add(registration.toJson());
        return ResponseEntity.ok(arrBuilder.build().toString());
    }

    @PostMapping(path="/delete",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteContact(@RequestBody String payload){
        Response resp;
        String id = Registration.getId(payload);
        try{
            redisRepo.deleteRegistration(id);
        }catch(Exception ex){
            resp = new Response();
            resp.setCode(400);
            resp.setMessage(ex.getMessage());
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(resp.toJson().toString());
        }

        resp = new Response();
        resp.setCode(201);
        resp.setMessage("Contact is deleted");
        resp.setData(id);

        return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(resp.toJson().toString());
    }

}
