package com.qianfeng.anlystic.modle.dim;

import com.qianfeng.anlystic.modle.dim.base.BaseDimension;
import com.qianfeng.anlystic.modle.dim.base.BrowserDimension;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

/**
 * 可用于用户模块/浏览器模块map阶段的输出的key值
 */
public class StatsUserDimension extends StateDimension{
    private StatsCommonDimension statsCommonDimension = new StatsCommonDimension();
    private BrowserDimension browserDimension = new BrowserDimension();

    public StatsUserDimension() {
    }

    public StatsUserDimension(StatsCommonDimension statsCommonDimension, BrowserDimension browserDimension) {
        this.statsCommonDimension = statsCommonDimension;
        this.browserDimension = browserDimension;
    }

    public StatsCommonDimension getStatsCommonDimension() {
        return statsCommonDimension;
    }

    public void setStatsCommonDimension(StatsCommonDimension statsCommonDimension) {
        this.statsCommonDimension = statsCommonDimension;
    }

    public BrowserDimension getBrowserDimension() {
        return browserDimension;
    }

    public void setBrowserDimension(BrowserDimension browserDimension) {
        this.browserDimension = browserDimension;
    }

    /**
     * 克隆当前的一个对象
     * @param o
     * @return
     */
    public static StatsUserDimension clone(StatsUserDimension dimension){
        StatsCommonDimension statsCommonDimension = StatsCommonDimension.clone(dimension.statsCommonDimension);
        BrowserDimension browserDimension = new BrowserDimension(dimension.browserDimension.getBrowserName(),
                dimension.browserDimension.getBrowserVersion());
        return new StatsUserDimension(statsCommonDimension,browserDimension);
    }
    @Override
    public int compareTo(BaseDimension o) {
        if (this == o){
            return 0;
        }
        StatsUserDimension other = new StatsUserDimension();
        int tmp = this.statsCommonDimension.compareTo(other.statsCommonDimension);
        if (tmp!=0){
            return tmp;
        }
        tmp = this.browserDimension.compareTo(other.browserDimension);
        return tmp;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        this.browserDimension.write(out);
        this.statsCommonDimension.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
this.statsCommonDimension.readFields(in);
this.statsCommonDimension.readFields(in);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatsUserDimension)) return false;
        StatsUserDimension that = (StatsUserDimension) o;
        return Objects.equals(getStatsCommonDimension(), that.getStatsCommonDimension()) &&
                Objects.equals(getBrowserDimension(), that.getBrowserDimension());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getStatsCommonDimension(), getBrowserDimension());
    }
}
