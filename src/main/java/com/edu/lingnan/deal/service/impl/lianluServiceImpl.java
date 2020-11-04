package com.edu.lingnan.deal.service.impl;

import com.edu.lingnan.deal.mapper.lianluMapper;
import com.edu.lingnan.deal.pojo.lianlu;
import com.edu.lingnan.deal.service.lianluService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author shaosen
 * @Description //TODO
 * @Date 16:35 2020/10/30
 */
@Service
public class lianluServiceImpl implements lianluService {
    @Autowired
    private lianluMapper lianluMapper;

    @Override
    public lianlu getByName(String name) {
        return lianluMapper.getByName(name);
    }

    @Override
    public Integer insert(lianlu lianlu) {
        return lianluMapper.insert(lianlu);
    }

    @Override
    public Integer update(lianlu lianlu) {
        return lianluMapper.update(lianlu);
    }
}
