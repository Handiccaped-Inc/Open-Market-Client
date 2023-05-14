package co.edu.unicauca.openmarket.access;

import co.edu.unicauca.openmarket.infra.OpenMarketSocket;
import co.unicauca.openmarket.commons.domain.Product;
import co.unicauca.openmarket.commons.infra.JsonError;
import co.unicauca.openmarket.commons.infra.Protocol;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public boolean save(Product product) {
        String jsonResponse = null;
        String requestJson = doSaveProductRequestJson(product);
        try {
            mySocket.connect();
            jsonResponse = mySocket.sendRequest(requestJson);
            mySocket.disconnect();

        } catch (IOException ex) {
            Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.SEVERE, "No hubo conexión con el servidor", ex);
        }
        if (jsonResponse == null) {
            try {
                throw new Exception("No se pudo conectar con el servidor");
            } catch (Exception ex) {
                Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            if (jsonResponse.contains("error")) {
                //Devolvió algún error                
                Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.INFO, jsonResponse);
                try {
                    throw new Exception(extractMessages(jsonResponse));
                } catch (Exception ex) {
                    Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                //Agregó correctamente, devuelve true
                Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.INFO, "Lo que va en el JSon: ("+jsonResponse.toString()+ ")");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean edit(Long id, Product product) {
        String jsonResponse = null;
        String requestJson = doEditProductRequestJson(id.toString(), product);
        System.out.println(requestJson);
        try {
            mySocket.connect();
            jsonResponse = mySocket.sendRequest(requestJson);
            mySocket.disconnect();

        } catch (IOException ex) {
            Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.SEVERE, "No hubo conexión con el servidor", ex);
        }
        if (jsonResponse == null) {
            try {
                throw new Exception("No se pudo conectar con el servidor. Revise la red o que el servidor esté escuchando. ");
            } catch (Exception ex) {
                Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (jsonResponse.contains("error")) {
                //Devolvió algún error
                Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.INFO, jsonResponse);
                try {
                    throw new Exception(extractMessages(jsonResponse));
                } catch (Exception ex) {
                    Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                //edito correctamente, devuelve true 
                Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.INFO, "Lo que va en el JSon: ("+jsonResponse.toString()+ ")");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        String jsonResponse = null;
        String requestJson = doDeleteProductRequestJson(id.toString());
        System.out.println(requestJson);
        try {
            mySocket.connect();
            jsonResponse = mySocket.sendRequest(requestJson);
            mySocket.disconnect();

        } catch (IOException ex) {
            Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.SEVERE, "No hubo conexión con el servidor", ex);
        }
        if (jsonResponse == null) {
            try {
                throw new Exception("No se pudo conectar con el servidor. Revise la red o que el servidor esté escuchando. ");
            } catch (Exception ex) {
                Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (jsonResponse.contains("error")) {
                //Devolvió algún error
                Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.INFO, jsonResponse);
                try {
                    throw new Exception(extractMessages(jsonResponse));
                } catch (Exception ex) {
                    Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                //borro correctamente, devuelve true 
                Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.INFO, "Lo que va en el JSon: ("+jsonResponse.toString()+ ")");
                return true;
            }
        }
        return false;
    }

    @Override
    public Product findById(Long id) {
        String jsonResponse = null;
        String requestJson = doFindByIdProductRequestJson(id.toString());
        System.out.println(requestJson);

        try {
            mySocket.connect();
            jsonResponse = mySocket.sendRequest(requestJson);
            mySocket.disconnect();

        } catch (IOException ex) {
            Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.SEVERE, "No hubo conexión con el servidor", ex);
        }

        if (jsonResponse == null) {
            try {
                throw new Exception("No se pudo conectar con el servidor. Revise la red o que el servidor esté escuchando. ");
            } catch (Exception ex) {
                Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (jsonResponse.contains("error")) {
                //Devolvió algún error
                Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.INFO, jsonResponse);
                try {
                    throw new Exception(extractMessages(jsonResponse));
                } catch (Exception ex) {
                    Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                //Encontró el producto
                Product product = jsonToProduct(jsonResponse);
                Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.INFO, "Lo que va en el JSon: (" + jsonResponse.toString() + ")");
                return product;
            }
        }
        return null;
    }

    @Override
    public List<Product> findByName(String name) {
        String jsonResponse = null;
        String requestJson = doFindByNameProductRequestJson(name);
        System.out.println(requestJson);

        try {
            mySocket.connect();
            jsonResponse = mySocket.sendRequest(requestJson);
            mySocket.disconnect();

        } catch (IOException ex) {
            Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.SEVERE, "No hubo conexión con el servidor", ex);
        }

        if (jsonResponse == null) {
            try {
                throw new Exception("No se pudo conectar con el servidor. Revise la red o que el servidor esté escuchando. ");
            } catch (Exception ex) {
                Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (jsonResponse.contains("error")) {
                //Devolvió algún error
                Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.INFO, jsonResponse);
                try {
                    throw new Exception(extractMessages(jsonResponse));
                } catch (Exception ex) {
                    Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                //Encontró el producto
                List<Product> products = jsonToProductList(jsonResponse);
                Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.INFO, "Lo que va en el JSon: (" + jsonResponse.toString() + ")");
                return products;
            }
        }
        return null;
    }

    @Override
    public List<Product> findAll() {
        String jsonResponse = null;
        String requestJson = doFindAllRequestJson();
        System.out.println(requestJson);
        try {
            mySocket.connect();
            jsonResponse = mySocket.sendRequest(requestJson);
            mySocket.disconnect();

        } catch (IOException ex) {
            Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.SEVERE, "No hubo conexión con el servidor", ex);
        }
        if (jsonResponse == null) {
            try {
                throw new Exception("No se pudo conectar con el servidor. Revise la red o que el servidor esté escuchando. ");
            } catch (Exception ex) {
                Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (jsonResponse.contains("error")) {
                //Devolvió algún error
                Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.INFO, jsonResponse);
                try {
                    throw new Exception(extractMessages(jsonResponse));
                } catch (Exception ex) {
                    Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                //Encontró el customer
                List<Product> products = jsonToProductList(jsonResponse);
                Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.INFO, "Lo que va en el JSon: (" + jsonResponse.toString() + ")");
                return products;
            }
        }
        return null;
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
     * Crea la solicitud json de creación del producto para ser enviado por el
     * socket
     *
     * @param Product objeto customer
     * @return devulve algo como:
     * {"resource":"product","action":"get","parameters":[{"productId":"1","name":"xxx"}],
     * [{"productId":"1","name":"xxx"}] etc...}
     */
    private String doSaveProductRequestJson(Product product) {

        Protocol protocol = new Protocol();
        protocol.setResource("product");
        protocol.setAction("post");
        protocol.addParameter("name", product.getName());
        protocol.addParameter("description", product.getDescription());
        protocol.addParameter("price", String.valueOf(product.getPrice()));

        Gson gson = new Gson();
        String requestJson = gson.toJson(protocol);
        return requestJson;
    }

    private String doEditProductRequestJson(String idProduct, Product product) {

        Protocol protocol = new Protocol();
        protocol.setResource("product");
        protocol.setAction("update");
        protocol.addParameter("idProduct", idProduct);
        protocol.addParameter("name", product.getName());
        protocol.addParameter("description", product.getDescription());
        protocol.addParameter("price", String.valueOf(product.getPrice()));

        Gson gson = new Gson();
        String requestJson = gson.toJson(protocol);
        return requestJson;
    }

    private String doDeleteProductRequestJson(String idProduct) {

        Protocol protocol = new Protocol();
        protocol.setResource("product");
        protocol.setAction("delete");
        protocol.addParameter("id", idProduct);

        Gson gson = new Gson();
        String requestJson = gson.toJson(protocol);
        return requestJson;
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
    private String doFindByIdProductRequestJson(String idProduct) {

        Protocol protocol = new Protocol();
        protocol.setResource("product");
        protocol.setAction("get");
        protocol.addParameter("id", idProduct);

        Gson gson = new Gson();
        String requestJson = gson.toJson(protocol);

        return requestJson;
    }

    private String doFindByNameProductRequestJson(String nameProduct) {

        Protocol protocol = new Protocol();
        protocol.setResource("product");
        protocol.setAction("findName");
        protocol.addParameter("name", nameProduct);

        Gson gson = new Gson();
        String requestJson = gson.toJson(protocol);
        return requestJson;
    }

    private String doFindAllRequestJson() {

        Protocol protocol = new Protocol();
        protocol.setResource("product");
        protocol.setAction("findAll");

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

    private List<Product> jsonToProductList(String jsonProductList) {
        Gson gson = new Gson();
        java.lang.reflect.Type productListType = new TypeToken<List<Product>>() {}.getType();
        List<Product> productList = gson.fromJson(jsonProductList, productListType);
        return productList;
    }

}
