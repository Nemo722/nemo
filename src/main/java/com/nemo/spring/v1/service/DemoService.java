package com.nemo.spring.v1.service;

import com.nemo.spring.annotation.Service;

/**
 * @author mingLong.zhao
 */
@Service
public class DemoService implements IDemoService {
    @Override
    public String test(String someThing) {
        return "TEST-" + someThing;
    }
}
