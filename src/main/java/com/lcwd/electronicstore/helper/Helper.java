package com.lcwd.electronicstore.helper;

import com.lcwd.electronicstore.dto.PageableResponce;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {


    //U= entity V= userDto
    public static <U,V> PageableResponce<V> getPageableResponce(Page<U> page,Class<V> type){

        List<U> entity = page.getContent();
        List<V> collect = entity.stream().map((object) -> new ModelMapper().map(object, type)).collect(Collectors.toList());


        PageableResponce<V> responce = new PageableResponce<V>();
        responce.setContent(collect);
        responce.setPageNumber(page.getNumber());
        responce.setPageSize(page.getSize());
        responce.setTotalElements(page.getTotalElements());
        responce.setTotalPages(page.getTotalPages());
        responce.setLastPage(page.isLast());

        return responce;

    }
}
