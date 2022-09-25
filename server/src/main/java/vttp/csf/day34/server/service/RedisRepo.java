package vttp.csf.day34.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import vttp.csf.day34.server.models.Registration;


@Repository
public interface RedisRepo {
    public void save(final Registration reg);
    public List<Registration> findAll();
    public void deleteRegistration(final String id);
}
