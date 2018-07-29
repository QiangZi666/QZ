package com.qianfeng.anlystic.modle.dim.value.map;

import com.qianfeng.anlystic.modle.dim.value.BaseStatsValueWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 用户模块和浏览器模块的map阶段的value的输出类型
 */

public class TimeOutputValue extends BaseStatsValueWritable {

    private String id;
    private long time;
    @Override
    public void write(DataOutput out) throws IOException {

    }

    @Override
    public void readFields(DataInput in) throws IOException {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
