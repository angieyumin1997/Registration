package vttp.csf.day34.server.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import vttp.csf.day34.server.models.Registration;

@Service
public class ContactsRepo implements RedisRepo{
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String CONTACT_ENTITY = "contactlist";


    @Override
    public void save(final Registration reg){
        redisTemplate.opsForList().leftPush(CONTACT_ENTITY, reg.getId());
        redisTemplate.opsForHash().put(CONTACT_ENTITY + "_Map", reg.getId(), reg);
    }


    @Override
    public List<Registration> findAll() {
        List<Object> fromContactList = redisTemplate.opsForList()
            .range(CONTACT_ENTITY, 0,  10);
        List<Registration> ctcs =                                 
            (List<Registration>)redisTemplate.opsForHash()
                .multiGet(CONTACT_ENTITY + "_Map", fromContactList)
                .stream()
                .filter(Registration.class::isInstance)
                .map(Registration.class::cast)
                .toList();

        return ctcs;
    };

    @Override
    public void deleteRegistration(String id){
        redisTemplate.opsForList().remove(CONTACT_ENTITY,1,id);
        redisTemplate.opsForHash().delete(CONTACT_ENTITY + "_Map", id);
    }
}
