package com.qianfeng.anlystic.modle.dim.value.reduce;

import com.qianfeng.anlystic.modle.dim.value.BaseStatsValueWritable;
import com.qianfeng.common.KpiType;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.WritableUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class MapWritableValue extends BaseStatsValueWritable {
    private MapWritable value = new MapWritable();
    private KpiType kpi;

    @Override
    public void write(DataOutput out) throws IOException {
        this.value.write(out);
        WritableUtils.writeEnum(out, kpi);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
this.value.readFields(in);
WritableUtils.readEnum(in,KpiType.class);

    }

    public MapWritable getValue() {
        return value;
    }

    public void setValue(MapWritable value) {
        this.value = value;
    }

    public KpiType getKpi() {
        return kpi;
    }

    public void setKpi(KpiType kpi) {
        this.kpi = kpi;
    }
}
