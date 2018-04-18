package com.cjlu.crm.controller;

import com.cjlu.crm.constants.SysCodeEnum;
import com.cjlu.crm.domain.*;
import com.cjlu.crm.service.CustomerService;
import com.cjlu.crm.service.ProductService;
import com.cjlu.crm.service.SaleChanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 统计分析控制器
 *
 * @author minghui.y
 * @create 2018-04-18 21:18
 **/
@RestController
@RequestMapping("/api")
public class AnalyzeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnalyzeController.class);

    @Autowired
    private SaleChanceService saleChanceService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductService productService;

    @RequestMapping("/queryContribution.json")
    public Result queryContribution() {
        LOGGER.info("查询所有客户贡献分析----------");
        //统计分析
        List<ContributionDTO> contributionDTOList = new ArrayList<>();
        //1、查询所有销售机会
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
        //2、按照客户id分组
        Map<Integer, List<SaleChanceDTO>> group = chanceDTOs.stream()
                .collect(Collectors.groupingBy(x -> x.getCustId()));

        //3、生成统计列表
        group.forEach((key, value) -> {
            ContributionDTO dto = new ContributionDTO();
            dto.setCustId(key);
            dto.setCustName(value.get(0).getCustName());
            dto.setTradeHistory(value);
            dto.setPurchaseCount(value.size());
            //累加交易总金额
            BigDecimal totalAccount = new BigDecimal(0);
            for (SaleChanceDTO chance : value) {
                //查询产品获取其售价
                CrmProducts product = productService.queryById(chance.getProdId());
                BigDecimal account = new BigDecimal(chance.getAmount()).multiply(product.getSalePrice());
                totalAccount = totalAccount.add(account);
            }
            dto.setTotalAccount(totalAccount);
            contributionDTOList.add(dto);
        });
        Map<Object, Object> data = new HashMap<>();
        data.put("contributionList", contributionDTOList);
        data.put("total", contributionDTOList.size());
        return new Result<Map>(SysCodeEnum.OK.getValue(), data);
    }


}
