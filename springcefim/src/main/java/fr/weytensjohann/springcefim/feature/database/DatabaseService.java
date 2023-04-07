package fr.weytensjohann.springcefim.feature.database;

import fr.weytensjohann.springcefim.model.ProductRepository;
import fr.weytensjohann.springcefim.model.Produit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseService {
    private final Logger logger = LoggerFactory.getLogger(DatabaseService.class);
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ProductRepository productRepository;
    public List<String> getProductNameList() {
        Query query = entityManager.createNativeQuery("SELECT nom FROM produits;",Tuple.class);
         List<Tuple> tuple = (List<Tuple>) query.getResultList();
         List<String> results = tuple.stream().map(t -> t.get("nom").toString()).toList();
         return results;
    }
    public List<ProduitDto> getListProduct(){
        String request = "select id, nom, description, prix_unitaire_HT from produits";
        Query query = entityManager.createNativeQuery(request, Tuple.class);
        List<Tuple> resultList = query.getResultList();
        List<ProduitDto> collect = resultList.stream().map(ProduitDto::new).toList();
        return collect;
    }

    public List<Produit> getListProductFromEntity() {
        return productRepository.findAll();
    }
    // with native query
    public ProduitWithPriceDto getOneProduct(Integer id) {
        String request = "select id, nom, description, prix_unitaire_HT from produits where id = :id";
        Query query = entityManager.createNativeQuery(request, Tuple.class)
                .setParameter("id", id);
        Tuple result = (Tuple) query.getSingleResult();
        return new ProduitWithPriceDto(result);
    }
    public Produit getOneProductEntity(Integer id){
        return productRepository.findById(id).orElse(null);
    }
    public List<ProduitWithPriceDto> getProductByAvis(int avisNote) {
        String request = "select produits.id, nom, description, prix_unitaire_HT from produits inner join avis on produits.id = avis.fk_produit where avis.note = :avisNote";
        Query query = entityManager.createNativeQuery(request, Tuple.class)
                .setParameter("avisNote", avisNote);
                // setParameter : Permet de remplacer un paramètre nommé (1er paramètre) dans la requête par la valeur du 2e paramètre (Ici productId)
                // Dans ma requête, un paramètre est identifié par le prefix ":"

        List<Tuple> resultList = (List<Tuple>) query.getResultList();
        // IDEM méthode getListProduct, sauf qu'on va créer des ProduitWithPriceDto pour stocker le unit_price de notre requête
        return resultList.stream().map(ProduitWithPriceDto::new).toList();
    }
    public List<ProduitWithPriceDto> getProductByName(String name) {
        String request = "select id, nom, description, prix_unitaire_HT from produits where nom LIKE :name";
        Query query = entityManager.createNativeQuery(request, Tuple.class)
                // Concaténation de % au début et à la fin de notre nom pour indiquer qu'il peut être n'importe où dans notre champ
                .setParameter("name", "%"+name+"%");
        List<Tuple> resultList = (List<Tuple>) query.getResultList();
        // IDEM méthode getListProduct, sauf qu'on va créer des ProduitWithPriceDto pour stocker le unit_price de notre requête
        return resultList.stream().map(ProduitWithPriceDto::new).toList();
    }
    public List<Produit> getProductEntityByAvisNote() {
        return productRepository.findByAvisNote();
    }
    public List<Produit> getProductEntityByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
}
