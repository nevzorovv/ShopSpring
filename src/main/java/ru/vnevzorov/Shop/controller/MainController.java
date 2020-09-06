package ru.vnevzorov.Shop.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.jni.Local;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.vnevzorov.Shop.model.Category;
import ru.vnevzorov.Shop.model.Order;
import ru.vnevzorov.Shop.model.Product;
import ru.vnevzorov.Shop.model.user.AbstractUser;
import ru.vnevzorov.Shop.model.user.Admin;
import ru.vnevzorov.Shop.model.user.Role;
import ru.vnevzorov.Shop.model.user.User;
import ru.vnevzorov.Shop.repository.CategoryRepository;
import ru.vnevzorov.Shop.security.UserDetailsServiceImpl;
import ru.vnevzorov.Shop.service.CategoryService;
import ru.vnevzorov.Shop.service.ProductService;
import ru.vnevzorov.Shop.service.ShoppingCartService;
import ru.vnevzorov.Shop.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Stream;

@Controller
@ControllerAdvice(basePackages = "ru.vnevzorov.Shop.controller")  // в этом контроллере есть метод который должен распростарняться на другие контроллеры в указанном пакете (ModelAttribute)
public class MainController {
    private static final Logger log = LogManager.getLogger();

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    UserService userService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Qualifier("messageSource")
    @Autowired
    private MessageSource messages;

    @GetMapping("mainpage")
    public ModelAndView getMainpage(Model model, HttpServletRequest request) {
        log.info("GET: /mainpage");

        ModelAndView modelAndView = new ModelAndView("jsp/mainpage.jsp");
        Iterable<Category> categories = categoryService.getAll();
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("category", new Category());
        modelAndView.addObject("number_of_positions", 10);

        if (model.getAttribute("user") != null && ((AbstractUser) model.getAttribute("user")).getRole() == Role.ADMIN) {
            Admin admin = (Admin) model.getAttribute("user");
            //admin.setLastPasswordUpdate(LocalDate.now().minusDays(87)); FOR TESTING
            Locale locale = request.getLocale();
            if (admin.getLastPasswordUpdate().plusDays(85).isBefore(LocalDate.now())) {
                modelAndView.addObject("passExpiresSoon", messages.getMessage("message.passExpiresSoon", null, request.getLocale()));
            }
        }

        return modelAndView;
    }

    @GetMapping("myorders")
    public ModelAndView showMyOrders(Model model) {
        log.info("GET: /myorders");
        String viewName = "jsp/myorders.jsp";

        Object userObj = model.getAttribute("user");
        if (userObj == null || userObj instanceof String || userObj instanceof AbstractUser && ((AbstractUser) userObj).getRole() != Role.USER) {
            return new ModelAndView("jsp/login.jsp");
        }

        User currentUser;
        if (userObj instanceof AbstractUser && ((AbstractUser) userObj).getRole() == Role.USER) {
            currentUser = userService.getUserByLogin(((AbstractUser) userObj).getLogin());
        } else {
            log.error("unexpected error: currentUser=null");
            return new ModelAndView("jsp/login.jsp");
        }

        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.addObject("usersOrders", currentUser.getOrders());
        modelAndView.addObject("confirmedByUser", new Order());

        return modelAndView;
    }

    @GetMapping("addproduct")
    public String getAddProductPage(Model model) {
        log.info("GET: /addproduct");
        model.addAttribute(new Product());

        Iterable<Category> categories = categoryService.getAll();
        model.addAttribute("categories", categories);
        model.addAttribute("discount_type", Arrays.asList("$", "%"));

        log.info("returning model:" + model.toString());

        return "jsp/addproduct.jsp";
    }

    @PostMapping("addproduct")
    public String saveProduct(@ModelAttribute @Valid @NotNull Product product, BindingResult result, Model model) {
        log.info("POST: /saveProduct, product: " + product.toString());

        //TODO как проверить категорию продукта на валидацию?

        if (result.hasErrors()) {
            return "jsp/addproduct.jsp";
        }

        productService.saveProduct(product);

        String category = product.getCategory().getName();

        return "redirect:/products" + "?category=" + category;
    }

    @GetMapping("products")
    public ModelAndView showAllProducts(@RequestParam("category") @Nullable String categoryName/*,
                                        @RequestParam(value = "allow", required = false) String allow*/) {
        log.info("GET: /products" + "?category=" + categoryName + " allow=");

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

    /************************Controller Advice******************************/
    @ModelAttribute // если эту аннотацию поставить над методом, метод будет выполняться для каждого запроса
    public void recognizeUser(Model model) {
        AbstractUser currentUser = userDetailsService.getAbstractUser();
        if (currentUser == null) {
            model.addAttribute("user", "Guest");
            model.addAttribute("countItems", 0);
        } else {
            if (currentUser.getRole() != Role.USER) {
                model.addAttribute("countItems", 0);
            } else {
                Integer countItems = shoppingCartService.calculateItems(currentUser);
                model.addAttribute("countItems", countItems);
            }
            model.addAttribute("user", currentUser);
        }
    }
}
