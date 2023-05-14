
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.openmarket.access;

import co.unicauca.openmarket.commons.infra.Protocol;
import co.unicauca.openmarket.commons.infra.JsonError;
import co.edu.unicauca.openmarket.infra.OpenMarketSocket;
import co.unicauca.openmarket.commons.domain.Category;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

/**
 *
 * @author INGESIS
 */
public class CategoryAccessImplSockets implements ICategoryRepository {

    /**
     * El servicio utiliza un socket para comunicarse con la aplicación server
     */
    private OpenMarketSocket mySocket;

    public CategoryAccessImplSockets() {
        mySocket = new OpenMarketSocket();
    }

    @Override
    public boolean save(Category category) {
        String jsonResponse = null;
        String requestJson = doSaveCategoryRequestJson(category);
        try {
            mySocket.connect();
            jsonResponse = mySocket.sendRequest(requestJson);
            mySocket.disconnect();

        } catch (IOException ex) {
            Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.SEVERE,
                    "No hubo conexión con el servidor", ex);
        }
        if (jsonResponse == null) {
            try {
                throw new Exception("No se pudo conectar con el servidor");
            } catch (Exception ex) {
                Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            if (jsonResponse.contains("error")) {
                // Devolvió algún error
                Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.INFO, jsonResponse);
                try {
                    throw new Exception(extractMessages(jsonResponse));
                } catch (Exception ex) {
                    Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                // Agregó correctamente, devuelve true
                Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.INFO,
                        "Lo que va en el JSon: (" + jsonResponse.toString() + ")");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean edit(Long id, Category category) {
        String jsonResponse = null;
        String requestJson = doEditCategoryRequestJson(id.toString(), category);
        System.out.println(requestJson);
        try {
            mySocket.connect();
            jsonResponse = mySocket.sendRequest(requestJson);
            mySocket.disconnect();

        } catch (IOException ex) {
            Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.SEVERE,
                    "No hubo conexión con el servidor", ex);
        }
        if (jsonResponse == null) {
            try {
                throw new Exception(
                        "No se pudo conectar con el servidor. Revise la red o que el servidor esté escuchando. ");
            } catch (Exception ex) {
                Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (jsonResponse.contains("error")) {
                // Devolvió algún error
                Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.INFO, jsonResponse);
                try {
                    throw new Exception(extractMessages(jsonResponse));
                } catch (Exception ex) {
                    Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                // edito correctamente, devuelve true
                Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.INFO,
                        "Lo que va en el JSon: (" + jsonResponse.toString() + ")");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        String jsonResponse = null;
        String requestJson = doDeleteCategoryRequestJson(id.toString());
        System.out.println(requestJson);
        try {
            mySocket.connect();
            jsonResponse = mySocket.sendRequest(requestJson);
            mySocket.disconnect();

        } catch (IOException ex) {
            Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.SEVERE,
                    "No hubo conexión con el servidor", ex);
        }
        if (jsonResponse == null) {
            try {
                throw new Exception(
                        "No se pudo conectar con el servidor. Revise la red o que el servidor esté escuchando. ");
            } catch (Exception ex) {
                Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (jsonResponse.contains("error")) {
                // Devolvió algún error
                Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.INFO, jsonResponse);
                try {
                    throw new Exception(extractMessages(jsonResponse));
                } catch (Exception ex) {
                    Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                // borro correctamente, devuelve true
                Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.INFO,
                        "Lo que va en el JSon: (" + jsonResponse.toString() + ")");
                return true;
            }
        }
        return false;
    }

    @Override
    public Category findById(Long id) {
        String jsonResponse = null;
        String requestJson = doFindByIdCategoryRequestJson(id.toString());
        System.out.println(requestJson);

        try {
            mySocket.connect();
            jsonResponse = mySocket.sendRequest(requestJson);
            mySocket.disconnect();

        } catch (IOException ex) {
            Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.SEVERE,
                    "No hubo conexión con el servidor", ex);
        }

        if (jsonResponse == null) {
            try {
                throw new Exception(
                        "No se pudo conectar con el servidor. Revise la red o que el servidor esté escuchando. ");
            } catch (Exception ex) {
                Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (jsonResponse.contains("error")) {
                // Devolvió algún error
                Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.INFO, jsonResponse);
                try {
                    throw new Exception(extractMessages(jsonResponse));
                } catch (Exception ex) {
                    Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                // Encontró el producto
                Category category = jsonToCategory(jsonResponse);
                Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.INFO,
                        "Lo que va en el JSon: (" + jsonResponse.toString() + ")");
                return category;
            }
        }
        return null;
    }

    @Override
    public List<Category> findByName(String name) {
        String jsonResponse = null;
        String requestJson = doFindByNameCategoryRequestJson(name);
        System.out.println(requestJson);

        try {
            mySocket.connect();
            jsonResponse = mySocket.sendRequest(requestJson);
            mySocket.disconnect();

        } catch (IOException ex) {
            Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.SEVERE,
                    "No hubo conexión con el servidor", ex);
        }

        if (jsonResponse == null) {
            try {
                throw new Exception(
                        "No se pudo conectar con el servidor. Revise la red o que el servidor esté escuchando. ");
            } catch (Exception ex) {
                Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (jsonResponse.contains("error")) {
                // Devolvió algún error
                Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.INFO, jsonResponse);
                try {
                    throw new Exception(extractMessages(jsonResponse));
                } catch (Exception ex) {
                    Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                // Encontró el producto
                List<Category> categorys = jsonToCategoryList(jsonResponse);
                Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.INFO,
                        "Lo que va en el JSon: (" + jsonResponse.toString() + ")");
                return categorys;
            }
        }
        return null;
    }

    @Override
    public List<Category> findAll() {
        String jsonResponse = null;
        String requestJson = doFindAllRequestJson();
        System.out.println(requestJson);
        try {
            mySocket.connect();
            jsonResponse = mySocket.sendRequest(requestJson);
            mySocket.disconnect();

        } catch (IOException ex) {
            Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.SEVERE,
                    "No hubo conexión con el servidor", ex);
        }
        if (jsonResponse == null) {
            try {
                throw new Exception(
                        "No se pudo conectar con el servidor. Revise la red o que el servidor esté escuchando. ");
            } catch (Exception ex) {
                Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (jsonResponse.contains("error")) {
                // Devolvió algún error
                Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.INFO, jsonResponse);
                try {
                    throw new Exception(extractMessages(jsonResponse));
                } catch (Exception ex) {
                    Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                // Encontró el customer
                List<Category> categorys = jsonToCategoryList(jsonResponse);
                Logger.getLogger(CategoryAccessImplSockets.class.getName()).log(Level.INFO,
                        "Lo que va en el JSon: (" + jsonResponse.toString() + ")");
                return categorys;
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

    private String doSaveCategoryRequestJson(Category category) {

        Protocol protocol = new Protocol();
        protocol.setResource("category");
        protocol.setAction("post");
        protocol.addParameter("name", category.getName());

        Gson gson = new Gson();
        String requestJson = gson.toJson(protocol);
        return requestJson;
    }

    private String doEditCategoryRequestJson(String idCategory, Category category) {

        Protocol protocol = new Protocol();
        protocol.setResource("category");
        protocol.setAction("update");
        protocol.addParameter("idCategory", idCategory);
        protocol.addParameter("name", category.getName());

        Gson gson = new Gson();
        String requestJson = gson.toJson(protocol);
        return requestJson;
    }

    private String doDeleteCategoryRequestJson(String idCategory) {

        Protocol protocol = new Protocol();
        protocol.setResource("category");
        protocol.setAction("delete");
        protocol.addParameter("id", idCategory);

        Gson gson = new Gson();
        String requestJson = gson.toJson(protocol);
        return requestJson;
    }

    private String doFindByIdCategoryRequestJson(String idCategory) {

        Protocol protocol = new Protocol();
        protocol.setResource("category");
        protocol.setAction("get");
        protocol.addParameter("id", idCategory);

        Gson gson = new Gson();
        String requestJson = gson.toJson(protocol);

        return requestJson;
    }

    private String doFindByNameCategoryRequestJson(String nameCategory) {

        Protocol protocol = new Protocol();
        protocol.setResource("category");
        protocol.setAction("findName");
        protocol.addParameter("name", nameCategory);

        Gson gson = new Gson();
        String requestJson = gson.toJson(protocol);
        return requestJson;
    }

    private String doFindAllRequestJson() {

        Protocol protocol = new Protocol();
        protocol.setResource("category");
        protocol.setAction("findAll");

        Gson gson = new Gson();
        String requestJson = gson.toJson(protocol);

        return requestJson;
    }

    /**
     * Convierte jsonCategory, proveniente del server socket, de json a un
     * objeto Category
     *
     * @param jsonCategory objeto cliente en formato json
     */
    private Category jsonToCategory(String jsonCategory) {

        Gson gson = new Gson();
        Category category = gson.fromJson(jsonCategory, Category.class);
        return category;

    }

    private List<Category> jsonToCategoryList(String jsonCategoryList) {
        Gson gson = new Gson();
        java.lang.reflect.Type productListType = new TypeToken<List<Category>>() {
        }.getType();
        List<Category> productList = gson.fromJson(jsonCategoryList, productListType);
        return productList;
    }

}
