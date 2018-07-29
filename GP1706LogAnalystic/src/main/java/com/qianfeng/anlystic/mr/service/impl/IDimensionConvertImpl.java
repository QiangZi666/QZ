package com.qianfeng.anlystic.mr.service.impl;

import com.qianfeng.anlystic.modle.dim.base.BaseDimension;
import com.qianfeng.anlystic.mr.service.IDimensionConvert;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 操作维度的接口实现
 */
public class IDimensionConvertImpl implements IDimensionConvert {
    private static final Logger logger=Logger.getLogger(IDimensionConvertImpl.class);
    //用于存储维度,维度累积的sql个个数
    public Map<String,Integer> batch = new HashMap<>();
    //维度:维度对应的id  缓存
    @Override
    public int getDimensionIdByDimension(BaseDimension dimension) throws Exception {
        return 0;
    }
}
