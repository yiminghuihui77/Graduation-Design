package com.cjlu.crm.controller;

import com.cjlu.crm.constants.SysCodeEnum;
import com.cjlu.crm.domain.CrmCustomer;
import com.cjlu.crm.domain.CrmUser;
import com.cjlu.crm.domain.CustomerDTO;
import com.cjlu.crm.domain.Result;
import com.cjlu.crm.service.ActivityService;
import com.cjlu.crm.service.CustomerService;
import com.cjlu.crm.service.UserService;
import org.apache.commons.lang.StringUtils;
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
 * 客户信息管理控制器
 *
 * @author minghui.y
 * @create 2018-04-11 16:28
 **/
@RestController
@RequestMapping("/api")
public class CustomerInfoController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;

    @RequestMapping("/queryAllCustomer.json")
    public Result queryAllCustomer() {
        System.out.println("查询客户信息------------");
        List<CrmCustomer> rawCustomers = customerService.queryAllCustomers();
        if ((CollectionUtils.isEmpty(rawCustomers))) {
            return new Result<>(SysCodeEnum.ERR_SYS.getValue(), "查询客户信息失败！");
        }
        //转换为DTO
        List<CustomerDTO> targetCustomers = new ArrayList<>(rawCustomers.size());
        rawCustomers.forEach(x -> {
            CustomerDTO dto = new CustomerDTO();
            BeanUtils.copyProperties(x, dto);
            //获取客户经理姓名
            CrmUser manager = userService.queryUserById(x.getManagerId());
            String managerName = manager == null ? "N/A" : manager.getName();
            dto.setManagerName(managerName);
            //查询客户交往记录
            dto.setActivityList(activityService.queryByCustId(x.getId()));
            targetCustomers.add(dto);
        });
        //返回数据
        Map<Object, Object> data = new HashMap<>();
        data.put("customerList", targetCustomers);
        data.put("total", targetCustomers.size());
        return new Result<Map>(SysCodeEnum.OK.getValue(), data);
    }

    @RequestMapping("/changeStatus.json")
    public Result changeStatus(@RequestParam("id") Integer id, @RequestParam("status") String status) {
        System.out.println("改变客户状态------------");
        //切换状态
        if (customerService.changeStatus(id, status) <= 0) {
            return new Result<>(SysCodeEnum.ERR_SYS.getValue(), "更改客户状态失败！");
        }
        return new Result<>(SysCodeEnum.OK.getValue(), "更改客户状态成功！");
    }

    @RequestMapping("/fuzzyQueryCustomer.json")
    public Result fuzzyQueryCustomer(@RequestParam(value = "cusName", required = false) String cusName,
                                     @RequestParam(value = "managerName", required = false) String managerName,
                                     @RequestParam(value = "status", required = false) String status) {
        System.out.println("模糊查询客户信息------------");
        if (StringUtils.isEmpty(cusName) && StringUtils.isEmpty(managerName) && StringUtils.isEmpty(status)) {
            return new Result<>(SysCodeEnum.ERR_PARAM.getValue(), "模糊查询关键词错误！");
        }
        List<CrmCustomer> rawCustomers = customerService.queryAllCustomers();
        List<CustomerDTO> targetCustomers = new ArrayList<>(rawCustomers.size());
        rawCustomers.forEach(x -> {
            CustomerDTO dto = new CustomerDTO();
            BeanUtils.copyProperties(x, dto);
            //获取客户经理姓名
            CrmUser manager = userService.queryUserById(x.getManagerId());
            String managerNameStr = manager == null ? "N/A" : manager.getName();
            dto.setManagerName(managerNameStr);
            //查询客户交往记录
            dto.setActivityList(activityService.queryByCustId(x.getId()));
            targetCustomers.add(dto);
        });
        //过滤
        List<CustomerDTO> finalList = targetCustomers;
        if (StringUtils.isNotEmpty(cusName)) {
            finalList = finalList.stream()
                    .filter(x -> x.getCusName().contains(cusName))
                    .collect(Collectors.toList());
        }
        if (StringUtils.isNotEmpty(managerName)) {
            finalList = finalList.stream()
                    .filter(x -> x.getManagerName().contains(managerName))
                    .collect(Collectors.toList());
        }
        if (StringUtils.isNotEmpty(status)) {
            finalList = finalList.stream()
                    .filter(x -> StringUtils.equalsIgnoreCase(x.getStatus(), status))
                    .collect(Collectors.toList());
        }
        Map<Object,Object> data = new HashMap<>();
        data.put("customerList", finalList);
        data.put("total", finalList.size());
        return new Result<Map>(SysCodeEnum.OK.getValue(), data);
    }

    @RequestMapping("/addCustomer.json")
    public Result addCustomer(CrmCustomer customer) {

        return null;
    }

    @RequestMapping("refreshCustomer.json")
    public Result refreshCustomer(CrmCustomer customer) {

        return null;
    }

}
