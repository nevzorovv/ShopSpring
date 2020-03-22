package ru.vnevzorov.Shop.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.vnevzorov.Shop.model.Product;
import ru.vnevzorov.Shop.model.ShoppingCart;
/*import ru.vnevzorov.Shop.model.User;*/
import ru.vnevzorov.Shop.model.user.User;
import ru.vnevzorov.Shop.service.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@ControllerAdvice  // в этом контроллере есть метод который должен распростарняться на все другие контроллеры (ModelAttribut)
public class ShoppingCartController {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private ProductService productService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrderedProductService orderedProductService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("addToCart")
    public RedirectView addToShoppingCart(@RequestParam("id") String productId, @RequestParam("category") String categoryName) {
        log.info("GET: /addToCart?id=" + productId + "&category=" + categoryName);

        String url = "products" + "?category=" + categoryName;
        RedirectView redirectView = new RedirectView(url);

        shoppingCartService.addProduct(productId);

        return redirectView;
    }

    @GetMapping("shoppingcart")
    public ModelAndView showShoppingCart() {
        log.info("GET: /shoppingcart");

        String viewName = "jsp/shoppingcart.jsp";
        ModelAndView modelAndView = new ModelAndView(viewName);

        User user = userService.getUserByLogin("firstUser");
        ShoppingCart shoppingCart = shoppingCartService.getByUser(user);
        modelAndView.addObject("totalPrice", shoppingCart.getTotalPrice());
        modelAndView.addObject("totalDiscount", shoppingCart.getTotalDiscount());
        modelAndView.addObject("orderedProducts", shoppingCart.getOrderedProducts());
        modelAndView.addObject("shoppingCartId", shoppingCart.getId());

        log.info("returned: " + modelAndView.getViewName() + ", model: " + modelAndView.getModel());

        return modelAndView;
    }

    @PostMapping("changeQuantity")
    public RedirectView changeQuantity(HttpServletRequest request) {
        String url = "shoppingcart";
        String orderedProductId = request.getParameter("orderedProductId");
        String newQuantityStr = request.getParameter("quantity");
        int newQuantity;
        try {
            newQuantity = Integer.parseInt(newQuantityStr);
            if (newQuantity < 0) {
                throw  new IllegalArgumentException();
            }
        } catch (Exception e) {
            log.error("Exception: wrong format newQuantity = " + newQuantityStr);
            return new RedirectView("shoppingcart"); //TODO показать сообщение, что введены неверные данные
        }
        shoppingCartService.changeProductQuantity(orderedProductId, newQuantity);

        User user = userService.getUserByLogin("firstUser"); //FIXME

        return new RedirectView(url);
    }

    @GetMapping("deleteOrderedProduct")
    public RedirectView deleteOrderedProduct(@RequestParam("id") String orderedProductId) {
        String url = "shoppingcart";

        shoppingCartService.deleteOrderedProduct(orderedProductId);

        return new RedirectView(url);
    }

    @ModelAttribute // если эту аннотацию поставить над методом, метод будет выполняться для каждого запроса
    public void calculateItems(Model model) {
        Integer countItems = shoppingCartService.calculateItems();
        model.addAttribute("countItems", countItems);
    }

    //@ModelAttribute
    public User getUser() { // так тоже можно. В модель будет добавлять результат этого метода по имени класса(ключ)
        return userService.getUserByLogin("firstUser");
    }

}
