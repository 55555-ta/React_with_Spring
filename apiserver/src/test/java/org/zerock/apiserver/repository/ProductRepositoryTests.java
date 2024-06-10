package org.zerock.apiserver.repository;


import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.zerock.apiserver.domain.Product;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Log4j2

public class ProductRepositoryTests {


    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testInsert(){

        Product product = Product.builder().pname("Test ").pdesc("Test Desc").price(1000).build();

        product.addImageString(UUID.randomUUID()+"_"+"iMAGE1.jpg");
        product.addImageString(UUID.randomUUID()+"_"+"iMAGE2.jpg");

        productRepository.save(product);

    }



    @Test
    public void testRead(){
        Long pno = 1L;

        Optional<Product> result = productRepository.findById(pno);

        Product product = result.orElseThrow();

        log.info(product);

        log.info(product.getImageList());
    }


    @Test
    public void testRead2(){
        Long pno = 1L;

        Optional<Product> result = productRepository.selectOne(pno);

        Product product = result.orElseThrow();

        log.info(product);

        log.info(product.getImageList());
    }

    @Commit
    @Transactional
    @Test
    public void testDelete(){

        Long pno = 2L;

        productRepository.updateToDelete(2L, true);
    }

    @Test
    public void testUpdate() {

        Product product = productRepository.selectOne(1L).get();

        product.changePrice(5000);

        product.clearList();

        product.addImageString(UUID.randomUUID()+"_"+"iMAGE5.jpg");
        product.addImageString(UUID.randomUUID()+"_"+"iMAGE6.jpg");
        product.addImageString(UUID.randomUUID()+"_"+"iMAGE4.jpg");

        productRepository.save(product);
    }


}
