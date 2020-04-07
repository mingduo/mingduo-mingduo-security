package com.scp.cloudsecurityispriceapi.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.scp.cloudsecurityispriceapi.domain.PriceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

import static com.scp.cloudsecurityispriceapi.config.SentinelConfig.SETPRICE_PARAM;

/**
 * @author : weizc
 * @apiNode:
 * @since 2020/4/3
 */
@Slf4j
@RestController
public class PriceController {

    @GetMapping
    public PriceInfo getPrice() {
        try (Entry e = SphU.entry("getprice")) {
            return new PriceInfo(BigDecimal.TEN);

        } catch (BlockException e) {
            log.error("block", e);
        }
        return null;
    }


    @SentinelResource(value = SETPRICE_PARAM,fallback ="fallbackPrice",blockHandler = "doBlockHandler")
    @GetMapping(SETPRICE_PARAM)
    public PriceInfo getPrice(Integer price) throws InterruptedException {
        Thread.sleep(50);
        return new PriceInfo(new BigDecimal(price));
    }

    public PriceInfo fallbackPrice(){
        return new PriceInfo(BigDecimal.ZERO);
    }

    public PriceInfo doBlockHandler(Integer price,BlockException e) {
        log.error("",e);
        return fallbackPrice();
    }

    @SentinelResource(value = "getPriceInfo")
    @GetMapping("/{id}")
    public PriceInfo getPriceInfo(@PathVariable Integer id){
        return new PriceInfo(new BigDecimal(id));
    }
}
