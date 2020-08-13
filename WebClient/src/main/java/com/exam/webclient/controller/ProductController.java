package com.exam.webclient.controller;

import com.exam.webclient.Service.ProductService;
import com.exam.webclient.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @RequestMapping(value="/products", method = RequestMethod.GET)
    public String findAll(Model model) {
        model.addAttribute("products", productService.findAll());
        model.addAttribute("newProduct", new Product());
        return "products";
    }

    @RequestMapping(value="/products/{id}", method = RequestMethod.PUT)
    public String update(@RequestParam Long id, Product product) {
        productService.update(id, product);
        return "redirect:/";
    }

    @RequestMapping(value="/products/{id}", method = RequestMethod.DELETE)
    public String delete(@RequestParam Long id) {
        productService.delete(id);
        return "redirect:/";
    }

    @RequestMapping(value="/products",method = RequestMethod.POST)
    public String create(Product product) {
        productService.create(product);
        return "redirect:/";
    }

}
