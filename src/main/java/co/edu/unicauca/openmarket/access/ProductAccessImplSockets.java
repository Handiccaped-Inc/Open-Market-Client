package co.edu.unicauca.openmarket.access;

import co.edu.unicauca.openmarket.infra.OpenMarketSocket;
import co.unicauca.openmarket.commons.domain.Product;
import co.unicauca.openmarket.commons.infra.JsonError;
import co.unicauca.openmarket.commons.infra.Protocol;
import com.google.gson.Gson;
import java.util.List;

/**
 * Es una implementación que tiene libertad de hacer una implementación del
 * contrato. Lo puede hacer con Sqlite, postgres, mysql, u otra tecnología
 *
 * @author Libardo, Julio
 */
public class ProductAccessImplSockets implements IProductRepository {

    /**
     * El servicio utiliza un socket para comunicarse con la aplicación server
     */
    private OpenMarketSocket mySocket;

    public ProductAccessImplSockets() {
        mySocket = new OpenMarketSocket();
    }

    @Override
    public boolean save(Product object) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean edit(Long id, Product object) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Product findById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Product> findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Product> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Extra los mensajes de la lista de errores
     *
     * @param jsonResponse lista de mensajes json
     * @return Mensajes de error
     */
    private String extractMessages(String jsonResponse) {
        JsonError[] errors = jsonToErrors(jsonResponse);
        String msjs = "";
        for (JsonError error : errors) {
            msjs += error.getMessage();
        }
        return msjs;
    }

    /**
     * Convierte el jsonError a un array de objetos jsonError
     *
     * @param jsonError
     * @return objeto MyError
     */
    private JsonError[] jsonToErrors(String jsonError) {
        Gson gson = new Gson();
        JsonError[] error = gson.fromJson(jsonError, JsonError[].class);
        return error;
    }

    /**
     * Crea una solicitud json para ser enviada por el socket
     *
     *
     * @param idProduct identificación del producto
     * @return solicitud de consulta del producto en formato Json, algo como:
     * {"resource":"product","action":"get","parameters":[{"productId":"1","name":"xxx"}]
     * etc...}
     */
    private String doFindProductRequestJson(String idProduct) {

        Protocol protocol = new Protocol();
        protocol.setResource("customer");
        protocol.setAction("get");
        protocol.addParameter("id", idProduct);

        Gson gson = new Gson();
        String requestJson = gson.toJson(protocol);

        return requestJson;
    }

    /**
     * Crea la solicitud json de creación del producto para ser enviado por el
     * socket
     *
     * @param Product objeto customer
     * @return devulve algo como:
     * {"resource":"product","action":"get","parameters":[{"productId":"1","name":"xxx"}],
     * [{"productId":"1","name":"xxx"}] etc...}
     */
    private String doCreateProductRequestJson(Product product) {

        Protocol protocol = new Protocol();
        protocol.setResource("customer");
        protocol.setAction("post");
        protocol.addParameter("id", product.getProductId().toString());
        protocol.addParameter("name", product.getName());
        protocol.addParameter("description", product.getDescription());
        protocol.addParameter("price", String.valueOf(product.getPrice()));

        Gson gson = new Gson();
        String requestJson = gson.toJson(protocol);
        return requestJson;
    }

    /**
     * Convierte jsonProduct, proveniente del server socket, de json a un objeto
     * Product
     *
     * @param jsonProduct objeto cliente en formato json
     */
    private Product jsonToProduct(String jsonProduct) {

        Gson gson = new Gson();
        Product product = gson.fromJson(jsonProduct, Product.class);
        return product;

    }
}
