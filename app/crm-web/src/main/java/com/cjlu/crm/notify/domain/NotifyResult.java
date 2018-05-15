package com.cjlu.crm.notify.domain;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 通知结果
 *
 * @author minghui.y
 * @create 2018-05-15 16:00
 **/
public class NotifyResult {

    private Map<String, Boolean> detailMap;

    public NotifyResult() {
        detailMap = new HashMap<>();
    }

    public NotifyResult(int size) {
        detailMap = new HashMap<>(size);
    }

    public void add(String token, Boolean status) {
        detailMap.put(token, status);
    }

    public boolean isAllSuccess()  {
        return detailMap.values().stream()
                .filter(x -> x != null)
                .allMatch(x -> x == true);
    }

    public boolean checkToken(String token) {
        if (StringUtils.isEmpty(token) || !detailMap.containsKey(token)) {
            return false;
        }
        return detailMap.get(token);
    }


}
