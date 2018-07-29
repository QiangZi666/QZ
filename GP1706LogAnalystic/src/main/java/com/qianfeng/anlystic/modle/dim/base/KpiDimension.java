package com.qianfeng.anlystic.modle.dim.base;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 按小时的活跃用户,小时的session个数
 */
public class KpiDimension extends BaseDimension{
    private int id;
    private  String KpiName;

    public KpiDimension() {
    }
public KpiDimension(String kpiName){
        this.KpiName = kpiName;
}
    public KpiDimension(int id, String kpiName) {
        this.id = id;
        KpiName = kpiName;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKpiName() {
        return KpiName;
    }

    public void setKpiName(String kpiName) {
        KpiName = kpiName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KpiDimension)) return false;
        KpiDimension that = (KpiDimension) o;
        return KpiName!=null?KpiName.equals(that.KpiName):that.KpiName==null;
    }

    @Override
    public int hashCode() {

        int result = id;
        result = 31*result+(KpiName!=null?KpiName.hashCode():0);
        return result;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int compareTo(BaseDimension o) {
        if (this==o){
            return 0;
        }
        KpiDimension other = new KpiDimension();
        int tmp = this.id-other.id;
        if (tmp!=0){
            return tmp;
        }
        tmp= this.KpiName.compareTo(other.KpiName);
        return tmp;

    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(this.id);
        out.writeUTF(this.KpiName);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.id = in.readInt();
        this.KpiName = in.readUTF();
    }
}
