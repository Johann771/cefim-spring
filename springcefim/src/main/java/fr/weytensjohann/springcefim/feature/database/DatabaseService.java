package fr.weytensjohann.springcefim.feature.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service
public class DatabaseService {
    private final Logger logger = LoggerFactory.getLogger(DatabaseService.class);
    @Autowired
    private EntityManager entityManager;
    public List<String> getProductNameList() {
        Query query = entityManager.createNativeQuery("show tables;");
        Tuple tuple = ((Tuple) query.getResultList());
        String resultList = String.join(" - ", tuple.toString());
        return Collections.singletonList(resultList);
    }

}
