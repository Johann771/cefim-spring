package fr.weytensjohann.springcefim.feature.database;

import jakarta.persistence.EntityNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping("")
    public Produit insertNewProduct(@RequestBody Produit produit){
        ResponseEntity.status(HttpStatus.CONFLICT).body("Product with name table already exists");
        return productService.insertNewProduct(produit);
    }
    @GetMapping("/get/{productId}")
    public ResponseEntity<Produit> getProductById(@PathVariable("productId") Integer productId){
        try{
            return ResponseEntity.ok(productService.getProductById(productId));
        } catch (EntityNotFoundException entityNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PutMapping("/update/{productId}")
    public ResponseEntity<Produit> updateProduct(@PathVariable("productId") Integer productId, @RequestBody Produit produit){
        try{
            return ResponseEntity.ok(productService.updateProduct(productId, produit));
        } catch (EntityNotFoundException notFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ConstraintViolationException constraintViolationException){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Produit> deleteNewProduct(@PathVariable("productId") Integer productId){
        try{
            return ResponseEntity.ok(productService.deleteProductById(productId));
        } catch (EntityNotFoundException entityNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
//    @DeleteMapping("/delete/{name}")
//    public void deleteNewProduct(@RequestParam("name") String name){
//        productService.deleteProductByName(name);
//        //
//    }
}
