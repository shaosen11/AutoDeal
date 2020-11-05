package com.edu.lingnan.deal.mapper;

import com.edu.lingnan.deal.pojo.lianlu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author shaosen
 * @Description //TODO
 * @Date 16:34 2020/10/30
 */
@Mapper
@Repository
public interface lianluMapper {
    lianlu getByName(String name);

    Integer insert(lianlu lianlu);

    Integer update(lianlu lianlu);

    Integer delete(lianlu lianlu);
}
