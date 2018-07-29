package com.qianfeng.anlystic.mr.nu;

import com.qianfeng.anlystic.modle.dim.StatsUserDimension;
import com.qianfeng.anlystic.modle.dim.base.PlatformDimension;
import com.qianfeng.anlystic.modle.dim.value.map.TimeOutputValue;
import com.qianfeng.anlystic.modle.dim.value.reduce.MapWritableValue;
import org.apache.hadoop.mapreduce.Reducer;

/**
 *
 */
public class NewUserReudcer extends Reducer<StatsUserDimension,TimeOutputValue,
        StatsUserDimension,MapWritableValue> {
    PlatformDimension platformDimension = new PlatformDimension("website");

}
