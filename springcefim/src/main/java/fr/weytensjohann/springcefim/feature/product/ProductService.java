package fr.weytensjohann.springcefim.feature.product;

import fr.weytensjohann.springcefim.model.ProductRepository;
import fr.weytensjohann.springcefim.model.Produit;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Produit insertNewProduct(Produit produit){
        return productRepository.save(produit);
    }
    public Produit deleteProductById(Integer produitId){
        Optional<Produit> byId = productRepository.findById(produitId);
        if (byId.isEmpty()){
            throw new EntityNotFoundException("Produit with ID %d not exist".formatted(produitId));
        }
        productRepository.deleteById(produitId);
        return null;
    }


    public Produit getProductById(Integer productId) {
        Optional<Produit> byId = productRepository.findById(productId);
        if (byId.isEmpty()){
            throw new EntityNotFoundException("Produit with ID %d not exist".formatted(productId));
        }
        return productRepository.findById(productId).get();
    }

    public Produit updateProduct(Integer productId, Produit produit) throws EntityNotFoundException, ConstraintViolationException {
        Optional<Produit> byId = productRepository.findById(productId);

        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Produit with ID %d not exist".formatted(productId));
        }

        List<Produit> byName = productRepository.findByNameContainingIgnoreCase(produit.getName());
        if (byName.size() > 0) {
            throw new ConstraintViolationException("Name for product " + produit.getName() + " already exist", new SQLException(), "produit::name INDEX UNIQUE");
        }

        Produit findProduct = byId.get();
        findProduct.setName(produit.getName());
        findProduct.setDescription(produit.getDescription());
        return productRepository.save(findProduct);
    }

    public Produit deleteProductByName(String name) {
        List<Produit> byId = productRepository.findByNameContainingIgnoreCase(name);
        if (byId.isEmpty()){
            throw new EntityNotFoundException("Produit with name : "+ name+" does not exist.");
        }
        productRepository.deleteByName(name);
        return null;
    }
}
