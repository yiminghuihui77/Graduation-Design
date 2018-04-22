package com.cjlu.crm.controller;

import com.cjlu.crm.constants.SysCodeEnum;
import com.cjlu.crm.domain.*;
import com.cjlu.crm.service.CustomerService;
import com.cjlu.crm.service.ProductService;
import com.cjlu.crm.service.SaleChanceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;

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
        //查询销售机会
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
        //查询所有客户
        List<CrmCustomer> customers = customerService.queryAllCustomers();
        //查询所有产品
        List<CrmProducts> products = productService.queryAllProducts();
        //返回数据
        Map<Object,Object> data = new HashMap<>();
        data.put("chanceList", chanceDTOs);
        data.put("total", chanceList.size());
        data.put("customerList", customers);
        data.put("productList", products);
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
    public Result fuzzyQuery(@RequestParam(value = "custName", required = false) String custName,
                            @RequestParam(value = "prodName", required = false) String prodName) {
        System.out.println("模糊查询销售机会-----------------");
        if (StringUtils.isEmpty(custName) && StringUtils.isEmpty(prodName)) {
            return new Result<>(SysCodeEnum.ERR_PARAM.getValue(), "模糊查询关键词有误！");
        }
        //查询销售机会
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
        if (StringUtils.isNotEmpty(custName) && StringUtils.isNotEmpty(prodName)) {
            //and关系查询
            List<SaleChanceDTO> finalList = chanceDTOs.stream()
                    .filter(x -> x.getCustName().contains(custName))
                    .filter(x -> x.getProdName().contains(prodName))
                    .collect(Collectors.toList());
            Map<Object,Object> data = new HashMap<>();
            data.put("chanceList", finalList);
            return new Result<Map>(SysCodeEnum.OK.getValue(), data);
        } else if (StringUtils.isNotEmpty(custName)) {
            List<SaleChanceDTO> finalList = chanceDTOs.stream()
                    .filter(x -> x.getCustName().contains(custName))
                    .collect(Collectors.toList());
            Map<Object,Object> data = new HashMap<>();
            data.put("chanceList", finalList);
            return new Result<Map>(SysCodeEnum.OK.getValue(), data);
        } else if (StringUtils.isNotEmpty(prodName)) {
            List<SaleChanceDTO> finalList = chanceDTOs.stream()
                    .filter(x -> x.getProdName().contains(prodName))
                    .collect(Collectors.toList());
            Map<Object,Object> data = new HashMap<>();
            data.put("chanceList", finalList);
            return new Result<Map>(SysCodeEnum.OK.getValue(), data);
        }

        return new Result<>(SysCodeEnum.ERR_SYS.getValue(), "模糊查询未知错误！");
    }

    @RequestMapping("/insertChance.json")
    public Result insertChance(CrmChance chance) {
        System.out.println("新增销售机会-----------------");
        chance.setGmtCreated(new Date());
        chance.setGmtModified(new Date());
        //插入chance
        saleChanceService.addChance(chance);
        return new Result<>(SysCodeEnum.OK.getValue(), "insert success！");
    }

    @RequestMapping("/updateChance.json")
    public Result refreshChance(CrmChance chance) {
        System.out.println("更新销售机会-----------------");
        chance.setGmtModified(new Date());
        //更新chance
        saleChanceService.updateChance(chance);
        return new Result<>(SysCodeEnum.OK.getValue(), "update success！");
    }
}
