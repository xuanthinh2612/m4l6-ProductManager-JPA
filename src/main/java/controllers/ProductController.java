package controllers;

import model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.product.IProductService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class ProductController {

    @Autowired
    IProductService productService;


    @GetMapping("show")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("list");
        List<Product> productList = productService.findAll();
        modelAndView.addObject("productList", productList);
        return modelAndView;
    }

    @GetMapping("/update/{id}")
    public ModelAndView showUpdate(@PathVariable Integer id) {
        Product product = productService.findById(id);
        return new ModelAndView("updateForm", "product", product);
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute Product product) {

        product.setId(id);
        productService.create(product);
        return "redirect:/show";
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showDelete(@PathVariable Integer id) {
        Product product = productService.findById(id);
        return new ModelAndView("deleteForm", "product", product);
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        productService.delete(id);
        return "redirect:/show";
    }

    @GetMapping("/create")
    public ModelAndView showCreate(){
        return new ModelAndView("createForm","product",new Product());
    }
    @PostMapping("/create")
    public String create(@ModelAttribute Product product){
        int defaultId = 0;
        product.setId(defaultId);
        productService.create(product);
        return "redirect:/show";
    }
    @GetMapping("/search")
    public ModelAndView search(@RequestParam String name){
        List<Product> productList = new ArrayList<>();
        for (Product product:productService.findAll()){
            if (product.getProductName().contains(name)){
                productList.add(product);
            }
        }
        return new ModelAndView("list","productList",productList);
    }
}
