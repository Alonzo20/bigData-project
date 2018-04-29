package com.alonzo.ae.service;

import java.util.List;
import java.util.Map;

import com.alonzo.ae.model.QueryModel;

/**
 * 处理ae基本数据交换的接口
 * 
 * @author alonzo
 *
 */
public interface AEService {
    public List<Map<String, Object>> execute(QueryModel queryModel) throws Exception;

}
