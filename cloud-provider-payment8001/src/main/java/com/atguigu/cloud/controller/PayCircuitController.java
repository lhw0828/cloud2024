package com.atguigu.cloud.controller;

import cn.hutool.core.util.IdUtil;
import com.atguigu.cloud.entities.Pay;
import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.resp.ResultData;
import com.atguigu.cloud.resp.ReturnCodeEnum;
import com.atguigu.cloud.service.PayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@Tag(name = "支付微服务熔断模块", description = "支付微服务熔断CRUD")
public class PayCircuitController {

    //=========Resilience4j CircuitBreaker 的例子
    @GetMapping(value = "/pay/circuit/{id}")
    public String myCircuit(@PathVariable("id") Integer id)
    {
        if(id == -4) throw new RuntimeException("----circuit id 不能负数");
        if(id == 9999){
            try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        return "Hello, circuit! inputId:  "+id+" \t " + IdUtil.simpleUUID();
    }
    //=========Resilience4j bulkhead 的例子
    @GetMapping(value = "/pay/bulkhead/{id}")
    public String myBulkhead(@PathVariable("id") Integer id)
    {
        if(id == -4) throw new RuntimeException("----bulkhead id 不能-4");

        if(id == 9999)
        {
            try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
        }

        return "Hello, bulkhead! inputId:  "+id+" \t " + IdUtil.simpleUUID();
    }

    //=========Resilience4j ratelimit 的例子  限流的方法
    @GetMapping(value = "/pay/ratelimit/{id}")
    public String myRatelimit(@PathVariable("id") Integer id)
    {
        return "Hello, myRatelimit欢迎到来 inputId:  "+id+" \t " + IdUtil.simpleUUID();
    }
}
