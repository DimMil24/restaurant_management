package com.dimitris.restaurant_management.controller;

import com.dimitris.restaurant_management.entities.*;
import com.dimitris.restaurant_management.entities.requests.NewOrderRequest;
import com.dimitris.restaurant_management.services.CustomerOrderService;
import com.dimitris.restaurant_management.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/order")
@Controller
public class CustomerOrderController {

    private final CustomerOrderService customerOrderService;
    private final ProductService productService;

    public CustomerOrderController(CustomerOrderService customerOrderService, ProductService productService) {
        this.customerOrderService = customerOrderService;
        this.productService = productService;
    }

    @GetMapping
    public String index(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("orderList", customerOrderService.getAllOrders(user.getRestaurant().getId()));
        return "order/index";
    }

    @GetMapping("/orderForm")
    public String addOrder(@AuthenticationPrincipal User user ,Model model) {
        model.addAttribute("products", productService.getAllProducts(user.getRestaurant().getId()));
        return "order/form";
    }

    @GetMapping("/details/{id}")
    public String index(@PathVariable Long id, Model model) {
        model.addAttribute("orderDetails", customerOrderService.findOrder(id));
        return "order/details";
    }

    @PostMapping("/complete/{id}")
    public String completeOrder(@PathVariable Long id) {
        customerOrderService.CompleteOrder(id);
        return "redirect:/order";
    }

    @PostMapping("/deleteOrder/{id}")
    public String deleteOrder(@PathVariable Long id) {
        customerOrderService.DeleteOrder(id);
        return "redirect:/order";
    }

    @PostMapping("/newOrderV1")
    public String newOrder(@RequestBody @Valid NewOrderRequest newOrderRequest, @AuthenticationPrincipal User user) {
        customerOrderService.AddUserOrder(newOrderRequest,user);
        return "redirect:/";
    }
}
