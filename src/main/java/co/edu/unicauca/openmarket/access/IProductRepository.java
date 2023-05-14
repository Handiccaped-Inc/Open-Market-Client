package co.edu.unicauca.openmarket.access;

import co.unicauca.openmarket.commons.domain.Product;
import java.util.List;

/**
 *
 * @author Libardo, Julio
 */
public interface IProductRepository extends IRepository<Product> {
    List<Product> findByCategoryID(Long id);
}
