package com.dimitris.restaurant_management.controller;

import com.dimitris.restaurant_management.entities.ProductCategory;
import com.dimitris.restaurant_management.entities.User;
import com.dimitris.restaurant_management.services.ProductService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@RequestMapping("/product")
@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String index(Model model) {
        List<String> categories = Stream.of(ProductCategory.values())
                .map(Enum::name)
                .toList();
        model.addAttribute("categories",categories);
        model.addAttribute("productList", productService.getAllProducts());
        return "product/index";
    }

    @GetMapping("/details/{restaurant_id}/{id}")
    public String details(@PathVariable UUID restaurant_id, @PathVariable Long id, Model model) {
        model.addAttribute("product",productService.findProduct(restaurant_id,id));
        return "product/details";
    }

    @PostMapping("/newProduct")
    public String addProduct(@RequestParam String name,
                             @RequestParam Double price,
                             @RequestParam ProductCategory category,
                             @AuthenticationPrincipal User user) {
        productService.addProduct(name,price,category,user.getRestaurant());
        return "redirect:/product";
    }

    @PostMapping("/deleteProduct/{restaurant_id}/{id}")
    public String deleteProduct(@PathVariable UUID restaurant_id, @PathVariable Long id) {
        productService.deleteProduct(restaurant_id, id);
        return "redirect:/product";
    }
}
