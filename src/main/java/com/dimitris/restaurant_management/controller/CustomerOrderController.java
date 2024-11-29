package com.dimitris.restaurant_management.controller;

import com.dimitris.restaurant_management.entities.*;
import com.dimitris.restaurant_management.services.CustomerOrderService;
import com.dimitris.restaurant_management.services.ProductService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String index(Model model) {
        model.addAttribute("orderList", customerOrderService.getAllOrders());
        return "order/index";
    }

    @GetMapping("/orderForm")
    public String addOrder(Model model) {
        model.addAttribute("products", productService.findAllFiltered());
        return "order/form";
    }

    @GetMapping("/details/{id}")
    public String index(@PathVariable Long id, Model model) {
        model.addAttribute("orderDetails", customerOrderService.findOrder(id));
        return "order/details";
    }

    @PostMapping("/newOrder")
    public String newOrder(@RequestParam List<Long> products,
                           @RequestParam List<Long> quantity,
                           @AuthenticationPrincipal User user) throws Exception {
        customerOrderService.AddOrder(products,quantity,user.getRestaurant());
        return "redirect:/order";
    }

    @PostMapping("/complete/{id}")
    public String completeOrder(@PathVariable Long id) throws Exception {
        customerOrderService.CompleteOrder(id);
        return "redirect:/order";
    }

    @PostMapping("/deleteOrder/{id}")
    public String deleteOrder(@PathVariable Long id) {
        customerOrderService.DeleteOrder(id);
        return "redirect:/order";
    }
}
