package com.cjlu.crm.controller;

import com.cjlu.crm.constants.SysCodeEnum;
import com.cjlu.crm.constants.SystemConstants;
import com.cjlu.crm.domain.CrmDevelop;
import com.cjlu.crm.domain.CrmUser;
import com.cjlu.crm.domain.DevelopDTO;
import com.cjlu.crm.domain.Result;
import com.cjlu.crm.notify.NotifyService;
import com.cjlu.crm.notify.domain.NotifyContent;
import com.cjlu.crm.notify.domain.NotifyResult;
import com.cjlu.crm.service.DevelopService;
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
 * 客户开发控制器
 *
 * @author minghui.y
 * @create 2018-04-11 15:36
 **/
@RestController
@RequestMapping("/api")
public class DevelopController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DevelopController.class);

    @Autowired
    private DevelopService developService;
    @Autowired
    private UserService userService;
    @Autowired
    private NotifyService notifyService;

    @RequestMapping("/queryAllDevelops.json")
    public Result queryAllDevelops() {
        LOGGER.info("查询所有开发客户--------");
        List<CrmDevelop> developList = developService.queryAllDevelops();
        if (CollectionUtils.isEmpty(developList)) {
            return new Result<>(SysCodeEnum.ERR_SYS.getValue(), "查询客户开发计划失败！");
        }
        //转换成DTO
        List<DevelopDTO> developDTOs = new ArrayList<>(developList.size());
        developList.forEach(x -> {
            DevelopDTO dto = new DevelopDTO();
            BeanUtils.copyProperties(x, dto);
            //设置dto的创建人、指派人姓名
            CrmUser createUser = userService.queryUserById(x.getCreateId());
            if (createUser != null) {
                dto.setCreateName(createUser.getName());
            }
            CrmUser dueUser = userService.queryUserById(x.getDueId());
            if (dueUser != null) {
                dto.setDueName(dueUser.getName());
            }
            developDTOs.add(dto);
        });
        //查询所有销售主管
        List<CrmUser> createList = userService.queryAllSaleCharges();
        //查询所有客户经理
        List<CrmUser> dueList = userService.queryAllManagers();

        //返回数据
        Map<Object, Object> data = new HashMap<>();
        data.put("developList", developDTOs);
        data.put("createList", createList);
        data.put("dueList", dueList);
        data.put("total", developDTOs.size());
        return new Result<Map>(SysCodeEnum.OK.getValue(), data);
    }

    @RequestMapping("/removeDevelop.json")
    public Result removeDevelop(Integer id) {
        LOGGER.info("删除客户开发计-----------！");
        //删除客户开发计划
        if (developService.removeDevelopById(id) <= 0) {
            return new Result<>(SysCodeEnum.ERR_SYS.getValue(), "删除客户开发计划失败！");
        }
        return new Result<>(SysCodeEnum.OK.getValue(), "删除客户开发计划成功！");
    }

    @RequestMapping("/developFuzzyQuery.json")
    public Result developFuzzyQuery(@RequestParam(value = "custName", required = false) String custName,
                                    @RequestParam(value = "status", required = false) String status) {
        LOGGER.info("客户开发计划模糊查询--------");
        List<CrmDevelop> developList = developService.queryAllDevelops();
        if (CollectionUtils.isEmpty(developList)) {
            return new Result<>(SysCodeEnum.ERR_SYS.getValue(), "客户开发计划模糊查询失败！");
        }
        //模糊查询
        if (StringUtils.isNotEmpty(custName)) {
            developList = developList.stream()
                    .filter(x -> x.getCusName().contains(custName))
                    .collect(Collectors.toList());
        }
        if (StringUtils.isNotEmpty(status)) {
            developList = developList.stream()
                    .filter(x -> x.getStatus().intValue() == Integer.parseInt(status))
                    .collect(Collectors.toList());
        }
        //转换成DTO
        List<DevelopDTO> developDTOs = new ArrayList<>(developList.size());
        developList.forEach(x -> {
            DevelopDTO dto = new DevelopDTO();
            BeanUtils.copyProperties(x, dto);
            //设置dto的创建人、指派人姓名
            CrmUser createUser = userService.queryUserById(x.getCreateId());
            if (createUser != null) {
                dto.setCreateName(createUser.getName());
            }
            CrmUser dueUser = userService.queryUserById(x.getDueId());
            if (dueUser != null) {
                dto.setDueName(dueUser.getName());
            }
            developDTOs.add(dto);
        });
        //返回数据
        Map<Object, Object> data = new HashMap<>();
        data.put("developList", developDTOs);
        data.put("total", developDTOs.size());
        return new Result<Map>(SysCodeEnum.OK.getValue(), data);
    }

    @RequestMapping("/updateDevelop.json")
    public Result updateDevelop(CrmDevelop develop) {
        LOGGER.info("修改客户开发计划---------");
        develop.setGmtModified(new Date());
        //修改
        if (developService.refreshDevelop(develop) <= 0) {
            new Result<>(SysCodeEnum.ERR_SYS.getValue(), "修改客户开发计划失败！");
        }
        return new Result<>(SysCodeEnum.OK.getValue(), "修改客户开发计划成功！");
    }

    @RequestMapping("/addDevelop.json")
    public Result addDevelop(CrmDevelop develop) {
        LOGGER.info("新增客户开发计划----------");
        develop.setStatus(0);
        develop.setGmtCreated(new Date());
        develop.setGmtModified(new Date());
        //插入
        if (developService.addDevelop(develop) <= 0) {
            return new Result<>(SysCodeEnum.ERR_SYS.getValue(), "新增客户开发计划失败！");
        }
        //数据解析
        CrmUser createUser = userService.queryUserById(develop.getCreateId());
        String createName = createUser == null ? "error" : createUser.getName();
        CrmUser dueUser = userService.queryUserById(develop.getDueId());
        String dueName = dueUser == null ? "error" : dueUser.getName();
        //每开发一个新客户时，通知到钉钉群
        String title = "创建客户开发计划通知";
        String content = "\\n [客户姓名]：" + develop.getCusName() + "\\n [客户电话]：" + develop.getPhone() + "\\n [成功几率]："
                + develop.getSuccessRate() + "% \\n [创建人]：" + createName + "\\n [负责人]：" + dueName;
        String sender = "CRM后端系统";
        NotifyContent notifyContent = new NotifyContent(title, content, sender);
        NotifyResult notifyResult = notifyService.notifyToDingDing(notifyContent, SystemConstants.DEFAULT_DINGDING_TOKEN);
        if (!notifyResult.isAllSuccess()) {
            LOGGER.warn("[创建客户开发计划]钉钉群通知任务失!");
        }

        return new Result<>(SysCodeEnum.OK.getValue(), "添加客户开发计划成功！");
    }

}
