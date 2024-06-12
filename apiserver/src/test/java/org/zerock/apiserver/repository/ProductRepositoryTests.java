package org.zerock.apiserver.repository;


import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.zerock.apiserver.domain.Product;
import org.zerock.apiserver.dto.PageRequestDTO;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Log4j2

public class ProductRepositoryTests {


    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testInsert(){

        for (int i=0; i < 10; i++){
            Product product = Product.builder().pname("Test " + i).pdesc("Test Desc" + i).price(1000 * i).build();

            product.addImageString(UUID.randomUUID() + "_IMAGE" + (i+2) + "_1.jpg");
            product.addImageString(UUID.randomUUID() + "_IMAGE" +(i+2) + "_2.jpg");

            productRepository.save(product);

        }



    }



//    @Test
//    public void testRead(){
//        Long pno = 1L;
//
//        Optional<Product> result = productRepository.findById(pno);
//
//        Product product = result.orElseThrow();
//
//        log.info(product);
//
//        log.info(product.getImageList());
//    }


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

    @Test
    public void testList(){

        Pageable pageable = PageRequest.of(0, 10, Sort.by("pno").descending());

        Page<Object[]> result = productRepository.selectList(pageable);

        result.getContent().forEach(arr -> log.info(Arrays.toString(arr)));
    }

    @Test
    public void testSearch(){


        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();

        productRepository.searchList(pageRequestDTO);

    }


}
