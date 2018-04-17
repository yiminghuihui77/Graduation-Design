package com.cjlu.crm.controller;

import com.cjlu.crm.constants.SysCodeEnum;
import com.cjlu.crm.domain.*;
import com.cjlu.crm.service.CustomerService;
import com.cjlu.crm.service.ServeService;
import com.cjlu.crm.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 服务控制器
 *
 * @author minghui.y
 * @create 2018-04-17 16:55
 **/
@RestController
@RequestMapping("/api")
public class ServiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceController.class);

    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserService userService;
    @Autowired
    private ServeService serveService;

    @RequestMapping("/loadDataOfCreated.json")
    public Result loadDataOfCreated() {
        LOGGER.info("加载创建服务数据------");
        //查询所有客户
        List<CrmCustomer> customers = customerService.queryAllCustomers();
        //查询所有客户经理
        List<CrmUser> managerList = userService.queryAllManagers();
        Map<Object, Object> data = new HashMap<>();
        data.put("customerList", customers);
        data.put("dueList", managerList);
        return new Result<Map>(SysCodeEnum.OK.getValue(), data);
    }

    @RequestMapping("/addService.json")
    public Result addService(CrmService service) {
        LOGGER.info("创建服务------------");
        //补全数据
        service.setGmtCreated(new Date());
        service.setGmtModified(new Date());
        //插入数据
        if (serveService.createService(service) <= 0) {
            return new Result<>(SysCodeEnum.ERR_SYS.getValue(), "创建服务失败！");
        }
        return new Result<>(SysCodeEnum.OK.getValue(), "创建服务成功！");
    }

    @RequestMapping("/queryAllService.json")
    public Result queryAllService() {
        LOGGER.info("查询所有服务----------");
        List<CrmService> services = serveService.queryAllService();
        if (CollectionUtils.isEmpty(services)) {
            return new Result<>(SysCodeEnum.ERR_SYS.getValue(), "查询服务失败！");
        }
        //转换成DTO
        List<ServiceDTO> serviceDTOList = new ArrayList<>(services.size());
        services.forEach(x -> {
            ServiceDTO dto = new ServiceDTO();
            BeanUtils.copyProperties(x, dto);
            //设置客户姓名
            CrmCustomer customer = customerService.queryById(x.getCustId());
            if (customer != null) {
                dto.setCustName(customer.getCusName());
            }
            //设置客户经理性命跟
            CrmUser user = userService.queryUserById(x.getDueId());
            if (user != null) {
                dto.setDueName(user.getName());
            }
            serviceDTOList.add(dto);
        });
        //存储数据
        Map<Object, Object> data = new HashMap<>();
        data.put("serviceList", serviceDTOList);
        data.put("total", serviceDTOList.size());
        return new Result<Map>(SysCodeEnum.OK.getValue(), data);
    }

    @RequestMapping("/recordProcessResult.json")
    public Result recordProcessResult(@RequestParam("id")Integer id, @RequestParam("remark")String remark) {
        LOGGER.info("记录服务处理结果------");
        //处理
        if (serveService.refreshRemarkById(id, remark) <= 0) {
            return new Result<>(SysCodeEnum.ERR_SYS.getValue(), "记录服务处理结果失败！");
        }
        return new Result<>(SysCodeEnum.OK.getValue(), "记录服务处理结果成功！");
    }

    @RequestMapping("/serviceFuzzyQuery.json")
    public Result serviceFuzzyQuery(@RequestParam(value = "custName") String custName,
                                    @RequestParam(value = "type") Integer type) {
        LOGGER.info("模糊查询服务---------");
        List<CrmService> services = serveService.queryAllService();
        if (CollectionUtils.isEmpty(services)) {
            return new Result<>(SysCodeEnum.ERR_SYS.getValue(), "查询服务失败！");
        }
        //转换成DTO
        List<ServiceDTO> serviceDTOList = new ArrayList<>(services.size());
        services.forEach(x -> {
            ServiceDTO dto = new ServiceDTO();
            BeanUtils.copyProperties(x, dto);
            //设置客户姓名
            CrmCustomer customer = customerService.queryById(x.getCustId());
            if (customer != null) {
                dto.setCustName(customer.getCusName());
            }
            //设置客户经理性命跟
            CrmUser user = userService.queryUserById(x.getDueId());
            if (user != null) {
                dto.setDueName(user.getName());
            }
            serviceDTOList.add(dto);
        });
        //过滤
        if (StringUtils.isNotEmpty(custName) && type != null) {
            List<ServiceDTO> finalList = serviceDTOList.stream()
                    .filter(x -> x.getCustName().contains(custName))
                    .filter(x -> x.getType().intValue() == type.intValue())
                    .collect(Collectors.toList());
            //存储数据
            Map<Object, Object> data = new HashMap<>();
            data.put("serviceList", finalList);
            data.put("total", finalList.size());
            return new Result<Map>(SysCodeEnum.OK.getValue(), data);
        } else if (StringUtils.isNotEmpty(custName)) {
            List<ServiceDTO> finalList = serviceDTOList.stream()
                    .filter(x -> x.getCustName().contains(custName))
                    .collect(Collectors.toList());
            //存储数据
            Map<Object, Object> data = new HashMap<>();
            data.put("serviceList", finalList);
            data.put("total", finalList.size());
            return new Result<Map>(SysCodeEnum.OK.getValue(), data);
        } else if (type != null) {
            List<ServiceDTO> finalList = serviceDTOList.stream()
                    .filter(x -> x.getType().intValue() == type.intValue())
                    .collect(Collectors.toList());
            //存储数据
            Map<Object, Object> data = new HashMap<>();
            data.put("serviceList", finalList);
            data.put("total", finalList.size());
            return new Result<Map>(SysCodeEnum.OK.getValue(), data);
        }
        //没有查询参数时，全量返回
        //存储数据
        Map<Object, Object> data = new HashMap<>();
        data.put("serviceList", serviceDTOList);
        data.put("total", serviceDTOList.size());
        return new Result<Map>(SysCodeEnum.OK.getValue(), data);
    }

    @RequestMapping("/loadProcessedService")
    public Result loadProcessedService() {
        LOGGER.info("服务归档查询服务----------");
        List<CrmService> services = serveService.queryArchiveService();
        if (CollectionUtils.isEmpty(services)) {
            return new Result<>(SysCodeEnum.ERR_SYS.getValue(), "查询服务失败！");
        }
        //转换成DTO
        List<ServiceDTO> serviceDTOList = new ArrayList<>(services.size());
        services.forEach(x -> {
            ServiceDTO dto = new ServiceDTO();
            BeanUtils.copyProperties(x, dto);
            //设置客户姓名
            CrmCustomer customer = customerService.queryById(x.getCustId());
            if (customer != null) {
                dto.setCustName(customer.getCusName());
            }
            //设置客户经理性命跟
            CrmUser user = userService.queryUserById(x.getDueId());
            if (user != null) {
                dto.setDueName(user.getName());
            }
            serviceDTOList.add(dto);
        });
        //存储数据
        Map<Object, Object> data = new HashMap<>();
        data.put("serviceList", serviceDTOList);
        data.put("total", serviceDTOList.size());
        return new Result<Map>(SysCodeEnum.OK.getValue(), data);
    }

    @RequestMapping("/archiveService.json")
    public Result archiveService(@RequestParam("id")Integer id, @RequestParam("starLevel")Integer starLevel) {
        LOGGER.info("归档服务---------------");
        //归档
        if (serveService.archiveService(id, starLevel) <= 0) {
            return new Result<>(SysCodeEnum.ERR_SYS.getValue(), "服务归档失败！");
        }
        return new Result<>(SysCodeEnum.OK.getValue(), "服务归档成功！");
    }

}
