package com.qianfeng.anlystic.mr.nu;

import com.qianfeng.anlystic.modle.dim.StatsUserDimension;
import com.qianfeng.anlystic.modle.dim.value.map.TimeOutputValue;
import org.apache.hadoop.hbase.mapreduce.TableMapper;


/**
 * 统计新增用户和新增的总用户统计的mapper类
 */
public class NewUserMapper extends TableMapper<StatsUserDimension,TimeOutputValue> {

}
