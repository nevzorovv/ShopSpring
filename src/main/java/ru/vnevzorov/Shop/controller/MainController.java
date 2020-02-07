package ru.vnevzorov.Shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.vnevzorov.Shop.model.Category;
import ru.vnevzorov.Shop.repository.CategoryRepository;
import ru.vnevzorov.Shop.repository.OrderRepository;
import ru.vnevzorov.Shop.repository.ProductRepository;

@Controller
public class MainController {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;


    @GetMapping("hello")
    //@RequestMapping("hello")
    public ModelAndView helloWorld() { //ModelAndView - специальный класс для передачи данных для jsp
        ModelAndView modelAndView = new ModelAndView("helloworld.jsp");
        modelAndView.addObject("hello", "this is JSP");
        Category category = categoryRepository.findById(1).get();
        modelAndView.addObject("category", category);
        return modelAndView;
    }

    @PostMapping("saveCategory")
    public /*ModelAndView*/ RedirectView saveCategory(@ModelAttribute Category category) {
        System.out.println("category = " + category);

        return new RedirectView("hello");
        //return new ModelAndView("helloworld.jsp");
    }


}
