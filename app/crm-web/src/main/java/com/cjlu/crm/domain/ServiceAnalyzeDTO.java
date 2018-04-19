package com.cjlu.crm.domain;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author minghui.y
 * @create 2018-04-19 21:22
 **/
@Data
@ToString
public class ServiceAnalyzeDTO {

    private Integer custId;

    private String custName;

    private Integer serviceCount;

    private List<ServiceDTO> serviceHistory;
}
