/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.openmarket.domain.service;

import co.edu.unicauca.openmarket.access.ICategoryRepository;
import co.unicauca.openmarket.commons.domain.Category;
import framework.obsobs.Observado;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author restr
 */
public class CategoryService extends Observado {

    private final ICategoryRepository access;

    public CategoryService(ICategoryRepository access) {
        this.access = access;
    }

    public boolean saveCategory(String name) {

        Category newCategory = new Category();
        newCategory.setName(name);

        //Validate category
        if (newCategory.getName().isEmpty()) {
            return false;
        }

        boolean result;
        result = access.save(newCategory);
        this.notificar();
        return result;
    }

    public List<Category> findAllCategory() {
        List<Category> category = new ArrayList<>();
        category = access.findAll();

        return category;
    }

    public Category findCategoryById(Long id) {
        return access.findById(id);
    }

    public boolean deleteCategory(Long id) {
        boolean result;
        result = access.delete(id);
        this.notificar();
        return result;
    }

    public boolean editCategory(Long categoryId, Category category) {

        //Validate category
        if (category == null || category.getName().isEmpty()) {
            return false;
        }
        return access.edit(categoryId, category);
    }

    public List<Category> findCategoryByName(String name) {
        List<Category> category = new ArrayList<>();
        category = access.findByName(name);

        return category;
    }
}
