package com.lcwd.electronicstore.service;

import com.lcwd.electronicstore.dto.PageableResponce;
import com.lcwd.electronicstore.dto.ProductDto;
import com.lcwd.electronicstore.entity.Category;
import com.lcwd.electronicstore.entity.Product;
import com.lcwd.electronicstore.helper.Helper;
import com.lcwd.electronicstore.repository.CategoryRepository;
import com.lcwd.electronicstore.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductServiceTest {
    @MockBean
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ModelMapper mapper;
    @MockBean
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;

    Product product;

  Category category;


    @BeforeEach
    public void init() {
        product = Product.builder().live(true).price(252.00).discountedPrice(250.00).stock(true).title("mobile1")
                .description("all types of mobiles").productImageName("abc.png").quantity(100).build();



    }

    @Test
    public void createProductTest() {
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        ProductDto product1 = productService.createProduct(mapper.map(product, ProductDto.class));
        Assertions.assertNotNull(product1);
        Assertions.assertEquals(product.getTitle(), product1.getTitle());

    }

    @Test
    public void updateProductTest() {
        String productId = "pro123";
        ProductDto productDto = ProductDto.builder()
                .productImageName("abcd.png").price(300).discountedPrice(250).description("new colletion here")
                .quantity(50).stock(false).addedDate(new Date()).title("mobiles").build();

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);

        ProductDto productDto1 = productService.updateProduct(productDto, productId);
        Assertions.assertEquals("abcd.png", productDto1.getProductImageName());
        Assertions.assertNotNull(productDto1);
        Assertions.assertNotNull(productDto1.getDescription());
    }

    @Test
    public void deleteProductTest() {
        String productId = "pro123";
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        productService.deleteProduct(productId);
        Mockito.verify(productRepository, Mockito.times(1)).delete(product);
    }

    @Test
    public void getProductByIdTest() {
        String productId = "pro123";
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        ProductDto product1 = productService.getProduct(productId);
        Assertions.assertNotNull(product1);
    }

    @Test
    public void getAllProductTest() {
        Product product1 = Product.builder().live(true).price(252.00).discountedPrice(250.00).stock(true).title("mobile")
                .description("all types of mobiles").productImageName("abc.png").quantity(100).build();

        Product product2 = Product.builder().live(true).price(252.00).discountedPrice(250.00).stock(true).title("mobile")
                .description("all types of mobiles").productImageName("abc.png").quantity(100).build();

        List<Product> productsList = Arrays.asList(product, product1, product2);
        Page<Product> page= new PageImpl<>(productsList);
        Mockito.when(productRepository.findAll((Pageable) Mockito.any())).thenReturn(page);

        PageableResponce<ProductDto> allProduct = productService.getAllProduct(1, 2, "title", "asc");
        Assertions.assertEquals(3,allProduct.getContent().size());
        Assertions.assertNotNull(allProduct);
    }
    @Test
    public  void getAllLiveTest(){
        Product product1 = Product.builder().live(true).price(252.00).discountedPrice(250.00).stock(true).title("mobile")
                .description("all types of mobiles").productImageName("abc.png").quantity(100).build();

        Product product2 = Product.builder().live(true).price(252.00).discountedPrice(250.00).stock(true).title("mobile")
                .description("all types of mobiles").productImageName("abc.png").quantity(100).build();

        List<Product> productsList = Arrays.asList(product, product1, product2);
        Page<Product> page= new PageImpl<>(productsList);
        Mockito.when(productRepository.findByLiveTrue((Pageable)Mockito.any())).thenReturn(page);
        PageableResponce<ProductDto> allLive = productService.getAllLive(0, 3, "title", "asc");

        Assertions.assertEquals(3,allLive.getContent().size());
        Assertions.assertNotNull(allLive);
    }
//    @Test
//    public  void searchByTitleTest() {
//        String subtitle="hello";
//        ProductDto product1 = ProductDto.builder().live(true).price(252.00).discountedPrice(250.00).stock(true).title("mobile")
//                .description("all types of mobiles").productImageName("abc.png").quantity(100).build();
//
//        ProductDto product2 = ProductDto.builder().live(true).price(252.00).discountedPrice(250.00).stock(true).title("mobile2")
//                .description("all types of mobiles").productImageName("abc.png").quantity(100).build();
//
//        String keywoard ="m";
//        List<ProductDto> productDtos = Arrays.asList(product1, product2);
//        Page<ProductDto> page= new PageImpl<>(productDtos);
//        Mockito.when(productRepository.findByTitleContaining(subtitle,Mockito.any())).thenReturn(page);
//        PageableResponce<ProductDto> responce = productService.searchByTitle(keywoard, 0, 3, "title", "asc");
//
//       // Assertions.assertEquals(3,responce.getContent().size());
//        Assertions.assertNotNull(responce);
//
//    }

//    @Test
//    public  void getAllCategoryTest(){
//        Product product11 = Product.builder().live(true).price(252.00).discountedPrice(250.00).stock(true).title("mobile")
//                .description("all types of mobiles").productImageName("abc.png").quantity(100).build();
//
//        Product product22 = Product.builder().live(true).price(252.00).discountedPrice(250.00).stock(true).title("mobile")
//                .description("all types of mobiles").productImageName("abc.png").quantity(100).build();
//
//        List<Product> listProducts = Arrays.asList(product, product11, product22);
//
//        category = Category.builder().title("music").description("best quality sound bar system")
//                .coverImage("abc.png").products(listProducts).build();
//
//
//
//        String catId="cate123";
//        Page<Product> page=new PageImpl<>(listProducts);
//
//        Mockito.when(productRepository.findById(catId)).Mockito.any()).thenReturn();
//
//        PageableResponce<ProductDto> allOfCategory = productService.getAllOfCategory(catId, 0, 2, "title", "asc");
//
//
//    }


}
