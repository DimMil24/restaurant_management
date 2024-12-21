package com.dimitris.restaurant_management.controller;

import com.dimitris.restaurant_management.entities.Category;
import com.dimitris.restaurant_management.entities.Product;
import com.dimitris.restaurant_management.entities.User;
import com.dimitris.restaurant_management.entities.requests.CategoryRequest;
import com.dimitris.restaurant_management.entities.requests.ProductRequest;
import com.dimitris.restaurant_management.services.CategoryService;
import com.dimitris.restaurant_management.services.ProductService;
import jakarta.validation.Valid;
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
    public String addProduct(@Valid ProductRequest productRequest,
                             @AuthenticationPrincipal User user) {
        if (productService.productExists(productRequest.name(),user.getRestaurant().getId())) {
            return "redirect:/product?duplicate=true";
        }
        Product product = productService.generateProduct(productRequest,user.getRestaurant());
        productService.addProduct(product);
        return "redirect:/product";
    }

    @PostMapping("/newCategory")
    public String newCategory(CategoryRequest categoryRequest,
                              @AuthenticationPrincipal User user) {
        categoryService.createCategory(categoryRequest.name(),user.getRestaurant());
        return "redirect:/product/categories";
    }

    @PostMapping("/deleteCategory/{category_id}")
    public String deleteCategory(@PathVariable Long category_id) {
        Category category = categoryService.findCategoryById(category_id);
        categoryService.deleteCategory(category);
        return "redirect:/product/categories";
    }

    @PostMapping("/deleteProduct/{restaurant_id}/{id}")
    public String deleteProduct(@PathVariable UUID restaurant_id, @PathVariable Long id) {
        Product product = productService.findProduct(restaurant_id,id);
        productService.deleteProduct(product);
        return "redirect:/product";
    }
}
