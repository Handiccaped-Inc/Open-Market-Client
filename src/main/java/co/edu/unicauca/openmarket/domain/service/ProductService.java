package co.edu.unicauca.openmarket.domain.service;

import co.edu.unicauca.openmarket.access.IProductRepository;
import co.unicauca.openmarket.commons.domain.Category;
import co.unicauca.openmarket.commons.domain.Product;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Libardo, Julio
 */
public class ProductService {

    /**
     * Constructor privado que evita que otros objetos instancien
     *
     * @param service implementacion de tipo IProductRepository
     */
    private final IProductRepository access;

    /**
     * Inyección de dependencias en el constructor. Ya no conviene que el mismo
     * servicio cree un repositorio concreto
     *
     * @param access una clase hija de IProductRepository
     */
    public ProductService(IProductRepository access) {
        this.access = access;
    }

    public boolean saveProduct(String name, String description, Category category) {

        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setDescription(description);
        newProduct.setCategory(category);
        System.out.println("Categoria: "+category.getName()+category.getCategoryId());

        //Validate product
        if (newProduct.getName().isEmpty()) {
            return false;
        }

        return access.save(newProduct);

    }

    public List<Product> findAllProducts() {
        List<Product> products = new ArrayList<>();
        products = access.findAll();

        return products;
    }

    public List<Product> findByName(String name) {
        List<Product> products = new ArrayList<>();
        products = access.findByName(name);
        return products;
    }

    public Product findProductById(Long id) {
        return access.findById(id);
    }

    public boolean deleteProduct(Long id) {
        return access.delete(id);
    }

    public boolean editProduct(Long productId, Product prod) {

        //Validate product
        if (prod == null || prod.getName().isEmpty()) {
            return false;
        }
        return access.edit(productId, prod);
    }
    
    public List<Product> findProductByCategory(Long id) {
        return access.findByCategoryID(id);
    }
    
}
