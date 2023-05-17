/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.openmarket.presentation.commands;

import co.edu.unicauca.openmarket.domain.service.CategoryService;
import co.unicauca.openmarket.commons.domain.Category;
import java.util.List;

/**
 *
 * @author restr
 */
public class OMAddCategoryCommand extends OMCommand {

    private Category pP;
    private CategoryService pS;
    boolean result = false;

    public OMAddCategoryCommand(Category pP, CategoryService pS) {
        this.pP = pP;
        this.pS = pS;
    }

    @Override
    public void make() {
        result = pS.saveCategory(pP.getName());
    }

    @Override
    public void unmake() {
        List<Category> categorys = pS.findAllCategory();
        for (Category each : categorys) {
            if (each.getName().equals(pP.getName())) {
                result = pS.deleteCategory(each.getCategoryId());
            }
        }
    }

    public boolean result() {
        return result;
    }
}
