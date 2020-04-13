package ru.vnevzorov.Shop.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.vnevzorov.Shop.model.Category;
import ru.vnevzorov.Shop.model.Product;
import ru.vnevzorov.Shop.repository.CategoryRepository;
import ru.vnevzorov.Shop.service.CategoryService;
import ru.vnevzorov.Shop.service.ProductService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

@Controller
public class MainController {
    private static final Logger log = LogManager.getLogger();

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("mainpage")
    public ModelAndView getMainpage() {
        log.info("GET: /mainpage");

        ModelAndView modelAndView = new ModelAndView("jsp/mainpage.jsp");
        Iterable<Category> categories = categoryService.getAll();
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("category", new Category());
        modelAndView.addObject("number_of_positions", 10);

        log.info("returning model:" + modelAndView.getModel().toString());

        return modelAndView;
    }

    /*@GetMapping("addproduct")
    public ModelAndView getAddProduct() {
        log.info("GET: /addproduct");

        ModelAndView modelAndView = new ModelAndView("jsp/addproduct.jsp");
        Product newProduct = new Product();
        modelAndView.addObject("product", newProduct);

        Iterable<Category> categories = categoryService.getAll();
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("discount_type", Arrays.asList("$", "%"));

        log.info("returning model:" + modelAndView.getModel().toString());

        return modelAndView;
    }*/

    @GetMapping("addproduct")
    public ModelAndView getAddProduct(/*Model model, */@ModelAttribute @Valid @NotNull Product product, BindingResult result) {
        log.info("GET: /addproduct");
        ModelAndView modelAndView = new ModelAndView("jsp/addproduct.jsp");

        modelAndView.addObject("product", product/*model.getAttribute("product")*/);

        Iterable<Category> categories = categoryService.getAll();
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("discount_type", Arrays.asList("$", "%"));

        log.info("returning model:" + modelAndView.getModel().toString());

        return modelAndView;
    }

    //TODO сделать валидацию
    /*@PostMapping("saveProduct")
    public RedirectView saveProduct(@ModelAttribute Product product) {
        log.info("POST: /saveProduct, product: " + product.toString());

        productService.saveProduct(product);

        String category = product.getCategory().getName();
        RedirectView redirectView = new RedirectView("products" + "?category=" + category);

        return redirectView;
    }*/
    @PostMapping("saveProduct")
    public RedirectView saveProduct(@ModelAttribute @Valid @NotNull Product product, BindingResult result, RedirectAttributes redirectAttributes) {
        log.info("POST: /saveProduct, product: " + product.toString());

        //TODO как проверить категорию продукта на валидацию?

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("product", product);
            return new RedirectView("addproduct");
        }

        productService.saveProduct(product);

        String category = product.getCategory().getName();
        RedirectView redirectView = new RedirectView("products" + "?category=" + category);

        return redirectView;
    }

    @GetMapping("products")
    public ModelAndView showAllProducts(@RequestParam("category") @Nullable String categoryName) {
        log.info("GET: /products" + "?category=" + categoryName);
        ModelAndView modelAndView = new ModelAndView("jsp/products.jsp");

        Iterable<Product> products;
        if (categoryName == null) {
            categoryName = "All products";
            products = productService.getAll();
        } else {
            products = productService.getProductsByCategory(categoryService.getByName(categoryName));
        }

        modelAndView.addObject("category_name", categoryName);
        modelAndView.addObject("products", products);
        modelAndView.addObject("categories", categoryService.getAll());
        modelAndView.addObject("category", new Category());
        modelAndView.addObject("chosenProduct", new Product());

        return modelAndView;
    }

    @PostMapping("products")
    public ModelAndView showProducts(@ModelAttribute Category selectedCategory) {
        log.info("POST: /products, category: " + selectedCategory.getName());

        ModelAndView modelAndView = new ModelAndView("jsp/products.jsp");
        modelAndView.addObject("categories", categoryService.getAll());
        modelAndView.addObject("category", new Category());

        Iterable<Product> products;
        if (selectedCategory != null && selectedCategory.getName().length() > 2) {
            Category category = categoryService.getByName(selectedCategory.getName());
            products = productService.getProductsByCategory(category);
            modelAndView.addObject("category_name", category.getName());
        } else {
            modelAndView.addObject("category_name", "All products");
            products = productService.getAll();
        }
        modelAndView.addObject("products", products);
        modelAndView.addObject("chosenProduct", new Product());

        log.info("returning model:" + modelAndView.getModel().toString());
        log.info("returning view:" + modelAndView.getViewName());

        return modelAndView;
    }

    /*********************************  TEST  *****************************/

    @GetMapping("hello")
    //@RequestMapping("hello")
    public ModelAndView helloWorld() { //ModelAndView - специальный класс для передачи данных для jsp
        ModelAndView modelAndView = new ModelAndView("jsp/helloworld.jsp");
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
