package com.edu.lingnan.deal.service;

import com.edu.lingnan.deal.pojo.lianlu;

/**
 * @Author shaosen
 * @Description //TODO
 * @Date 16:34 2020/10/30
 */
public interface lianluService {
    lianlu getByName(String name);

    Integer insert(lianlu lianlu);

    Integer update(lianlu lianlu);
}
