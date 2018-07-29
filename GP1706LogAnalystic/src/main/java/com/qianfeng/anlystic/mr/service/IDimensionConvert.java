package com.qianfeng.anlystic.mr.service;

import com.qianfeng.anlystic.modle.dim.base.BaseDimension;

/**
 * 根据维度对象获取对应维度的id的接口
 */
public interface IDimensionConvert {
    int getDimensionIdByDimension(BaseDimension dimension) throws Exception;

}
