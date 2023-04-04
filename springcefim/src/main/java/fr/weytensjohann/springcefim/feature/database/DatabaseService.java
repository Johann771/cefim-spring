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
        Query query = entityManager.createNativeQuery("SELECT nom FROM produits;",Tuple.class);
         List<Tuple> tuple = (List<Tuple>) query.getResultList();
         List<String> results = tuple.stream().map(t -> t.get("nom").toString()).toList();
         return results;
    }
    public List<ProduitDto> getListProduct(){
        String request = "select id, nom, description from produits";
        Query query = entityManager.createNativeQuery(request, Tuple.class);
        List<Tuple> resultList = query.getResultList();
        List<ProduitDto> collect = resultList.stream().map(ProduitDto::new).toList();
        return collect;
    }

}
