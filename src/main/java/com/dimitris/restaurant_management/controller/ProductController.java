package com.dimitris.restaurant_management.controller;

import com.dimitris.restaurant_management.entities.Category;
import com.dimitris.restaurant_management.entities.User;
import com.dimitris.restaurant_management.entities.requests.ProductRequest;
import com.dimitris.restaurant_management.services.CategoryService;
import com.dimitris.restaurant_management.services.ProductService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/product")
@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String index(@AuthenticationPrincipal User user, Model model) {
        List<Category> categories = categoryService.findAllCategoriesByRestaurant(user.getRestaurant().getId());
        model.addAttribute("categories",categories);
        model.addAttribute("productList", productService.getAllProducts(user.getRestaurant().getId()));
        return "product/index";
    }

    @GetMapping("/categories")
    public String categories(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("categories",categoryService.findAllCategoriesByCountAndByRestaurant(user.getRestaurant().getId()));
        return "product/categories";
    }

    @GetMapping("/details/{restaurant_id}/{id}")
    public String details(@PathVariable UUID restaurant_id, @PathVariable Long id, Model model) {
        model.addAttribute("product",productService.findProduct(restaurant_id,id));
        return "product/details";
    }

    @PostMapping("/newProduct")
    public String addProduct(ProductRequest productRequest,
                             @AuthenticationPrincipal User user) {
        productService.addProduct(productService.generateProduct(productRequest,user.getRestaurant()));
        return "redirect:/product";
    }

    @PostMapping("/deleteCategory/{category_id}")
    public String deleteCategory(@PathVariable Long category_id) {
        categoryService.deleteCategory(categoryService.findCategoryById(category_id));
        return "redirect:/product";
    }

    @PostMapping("/deleteProduct/{restaurant_id}/{id}")
    public String deleteProduct(@PathVariable UUID restaurant_id, @PathVariable Long id) {
        productService.deleteProduct(productService.findProduct(restaurant_id,id));
        return "redirect:/product";
    }
}
