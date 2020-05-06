package ru.vnevzorov.Shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vnevzorov.Shop.model.Category;
import ru.vnevzorov.Shop.model.Product;
import ru.vnevzorov.Shop.repository.DiscountRepository;
import ru.vnevzorov.Shop.repository.ProductRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class ProductService {

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private ProductRepository productRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void saveProduct(Product product) {
        if (product == null || product.getModel() == null || product.getModel().isEmpty()) {
            throw new RuntimeException("неверные данные");
        }
        discountRepository.save(product.getDiscount());
        productRepository.save(product); // после save сущность помещается в persistance context
    }

    public Iterable<Product> getProductsByCategory(Category category) {
        return productRepository.getProductsByCategory(category);
    }

    @CachePut("products")
    public Product getProductById(Long id) {
        return productRepository.findById(id).get();
    }

    @CachePut("products")
    public Product getProductById(String id) {
        Product product = productRepository.findById(Long.parseLong(id)).get();
        return product;
    }

    public Iterable<Product> getAll() {
        return productRepository.findAll();
    }

    @Transactional
    public void test() {
        Product product = productRepository.findById(1L).get();
        System.out.println("product = " + product);
        product.setManufacturer("123");
        entityManager.detach(product); // удалает объект из persistance context, то есть изменения не будут применены
        //productRepository.save(product); save писать не нужно, так как открыта транзакция: beginTransaction/commit
    }
}
