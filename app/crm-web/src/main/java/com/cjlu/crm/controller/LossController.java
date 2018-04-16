package com.cjlu.crm.controller;

import com.cjlu.crm.constants.SysCodeEnum;
import com.cjlu.crm.domain.CrmCustomer;
import com.cjlu.crm.domain.CrmLoss;
import com.cjlu.crm.domain.LossDTO;
import com.cjlu.crm.domain.Result;
import com.cjlu.crm.service.CustomerService;
import com.cjlu.crm.service.LossService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 客户流失控制器
 *
 * @author minghui.y
 * @create 2018-04-15 13:13
 **/
@RestController
@RequestMapping("/api")
public class LossController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LossController.class);

    @Autowired
    private LossService lossService;
    @Autowired
    private CustomerService customerService;

    @RequestMapping("/queryAllLoss.json")
    public Result queryAllLoss() {
        LOGGER.info("查询所有客户流失---------");
        List<CrmLoss> lossList = lossService.queryAllLoss();
        if (CollectionUtils.isEmpty(lossList)) {
            return new Result<>(SysCodeEnum.ERR_SYS.getValue(), "查询流失率列表失败！");
        }
        //抓换成DTO
        List<LossDTO> lossDTOList = new ArrayList<>(lossList.size());
        lossList.forEach(x -> {
            LossDTO dto = new LossDTO();
            BeanUtils.copyProperties(x, dto);
            CrmCustomer customer = customerService.queryById(x.getCustId());
            if (customer != null) {
                dto.setCustName(customer.getCusName());
            }
            lossDTOList.add(dto);
        });
        //查询所有客户
        List<CrmCustomer> customers = customerService.queryAllCustomers();
        Map<Object,Object> data = new HashMap<>();
        data.put("lossList",lossDTOList);
        data.put("total", lossDTOList.size());
        data.put("customerList", customers);
        return new Result<Map>(SysCodeEnum.OK.getValue(), data);
    }

    @RequestMapping("/deleteLoss.json")
    public Result deleteLoss(@RequestParam("id") Integer id) {
        LOGGER.info("删除客户流失记录----");
        //删除
        if (lossService.removeLoss(id) <= 0) {
            return new Result<>(SysCodeEnum.ERR_SYS.getValue(), "删除客户流失记录失败！");
        }
        return new Result<String>(SysCodeEnum.OK.getValue(), "删除客户流失记录成功！");
    }

    @RequestMapping("/lossFuzzyQuery.json")
    public Result lossFuzzyQuery(@RequestParam(value = "custName", required = false) String custName,
                                 @RequestParam(value = "status", required = false) String status) {
        LOGGER.info("模糊查询客户流失记录----------");
        List<CrmLoss> lossList = lossService.queryAllLoss();
        if (CollectionUtils.isEmpty(lossList)) {
            return new Result<>(SysCodeEnum.ERR_SYS.getValue(), "查询流失率列表失败！");
        }
        //抓换成DTO
        List<LossDTO> lossDTOList = new ArrayList<>(lossList.size());
        lossList.forEach(x -> {
            LossDTO dto = new LossDTO();
            BeanUtils.copyProperties(x, dto);
            CrmCustomer customer = customerService.queryById(x.getCustId());
            if (customer != null) {
                dto.setCustName(customer.getCusName());
            }
            lossDTOList.add(dto);
        });
        //模糊查询
        if (StringUtils.isNotEmpty(custName) && StringUtils.isNotEmpty(status)) {
            List<LossDTO> finalList = lossDTOList.stream()
                    .filter(x -> x.getCustName().contains(custName))
                    .filter(x -> x.getStatus().intValue() == Integer.parseInt(status))
                    .collect(Collectors.toList());
            Map<Object,Object> data = new HashMap<>();
            data.put("lossList",finalList);
            data.put("total", finalList.size());
            return new Result<Map>(SysCodeEnum.OK.getValue(), data);
        } else if (StringUtils.isNotEmpty(custName)) {
            List<LossDTO> finalList = lossDTOList.stream()
                    .filter(x -> x.getCustName().contains(custName))
                    .collect(Collectors.toList());
            Map<Object,Object> data = new HashMap<>();
            data.put("lossList",finalList);
            data.put("total", finalList.size());
            return new Result<Map>(SysCodeEnum.OK.getValue(), data);
        } else if (StringUtils.isNotEmpty(status)) {
            List<LossDTO> finalList = lossDTOList.stream()
                    .filter(x -> x.getStatus().intValue() == Integer.parseInt(status))
                    .collect(Collectors.toList());
            Map<Object,Object> data = new HashMap<>();
            data.put("lossList",finalList);
            data.put("total", finalList.size());
            return new Result<Map>(SysCodeEnum.OK.getValue(), data);
        }
        return new Result<>(SysCodeEnum.ERR_SYS.getValue(), "模糊查询客户流失记录失败！");
    }
}
