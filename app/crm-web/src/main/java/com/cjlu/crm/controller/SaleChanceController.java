package com.cjlu.crm.controller;

import com.cjlu.crm.constants.SysCodeEnum;
import com.cjlu.crm.domain.*;
import com.cjlu.crm.service.CustomerService;
import com.cjlu.crm.service.ProductService;
import com.cjlu.crm.service.SaleChanceService;
import com.sun.org.glassfish.external.statistics.annotations.Reset;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 销售机会控制器
 *
 * @author minghui.y
 * @create 2018-04-07 12:13
 **/
@Controller
@RequestMapping("/api")
public class SaleChanceController {

    @Autowired
    private SaleChanceService saleChanceService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductService productService;

    @RequestMapping("/queryAllChance.json")
    @ResponseBody
    public Result querySaleChance() {
        System.out.println("查询销售机会-----------------");
        //查询数据
        List<CrmChance> chanceList = saleChanceService.queryAllChance();
        List<SaleChanceDTO> chanceDTOs = new ArrayList<>(chanceList.size());
        chanceList.forEach(x -> {
            SaleChanceDTO dto = new SaleChanceDTO();
            BeanUtils.copyProperties(x, dto);
            //客户名称
            CrmCustomer customer = customerService.queryById(x.getCustId());
            if (customer != null) {
                dto.setCustName(customer.getCusName());
            }
            //产品名称
            CrmProducts product = productService.queryById(x.getProdId());
            if (product != null) {
                dto.setProdName(product.getProdName());
            }
            chanceDTOs.add(dto);
        });
        //返回数据
        Map<Object,Object> data = new HashMap<>();
        data.put("chanceList", chanceDTOs);
        data.put("total", chanceList.size());
        return new Result<Map>(SysCodeEnum.OK.getValue(), data);
    }

    @RequestMapping("/deleteChance.json")
    @ResponseBody
    public Result removeChance(@RequestParam(value = "id") Integer id) {
        System.out.println("删除销售机会-----------------" + id);
        //删除销售机会
        saleChanceService.removeChance(id);
        //返回信息
        return new Result<>(SysCodeEnum.OK.getValue(), "delete success！");
    }

    @RequestMapping("/fuzzyQuery.json")
    @ResponseBody
    public Reset fuzzyQuery(@RequestParam(value = "custName", required = false) String custName,
                            @RequestParam(value = "prodName", required = false) String prodName) {

        return null;
    }
}
