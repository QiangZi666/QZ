package com.qianfeng.anlystic.modle.dim.base;

import com.qianfeng.common.GlobalConstants;
import org.apache.commons.lang.StringUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 浏览器维度
 */
public class BrowserDimension extends BaseDimension {
    private int id;
    private String browserName;
    private String browserVersion;


    public BrowserDimension(){

    }
    public BrowserDimension(String browserName,String browserVersion){
        this.browserName=  browserName;
        this.browserVersion = browserVersion;
    }
    public BrowserDimension(int id,String browserName,String browserVersion){
        this(browserName,browserVersion);
        this.id = id;
    }

    /**
     * 获取当前对象的一个静态方法
     */
    public static BrowserDimension newInstance(String browserName,String browserVersion){
        BrowserDimension bd = new BrowserDimension();
        bd.browserName = browserName;
        bd.browserVersion = browserVersion;
        return bd;
    }
    /**
     * 构建维度集合对象
     */
    public static List<BrowserDimension> buildList(String broweserName,String browserVersion){
        List<BrowserDimension> li = new ArrayList<>();
        if (StringUtils.isEmpty(broweserName)){
            broweserName = GlobalConstants.DEFAULT_VALUE;
            browserVersion = GlobalConstants.DEFAULT_VALUE;
        }

        if (StringUtils.isEmpty(browserVersion)){
            browserVersion =GlobalConstants.DEFAULT_VALUE;
        }
        //将对象添加到list中
        li.add(BrowserDimension.newInstance(broweserName,browserVersion));
        li.add(BrowserDimension.newInstance(broweserName,GlobalConstants.ALL_OF_VALUE));
        return li;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(this.id);
        out.writeUTF(this.browserName);
        out.writeUTF(this.browserVersion);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.id = in.readInt();
        this.browserName = in.readUTF();
        this.browserVersion = in.readUTF();
    }

    @Override
    public int compareTo(BaseDimension o) {
        if(this == o){
            return  0;
        }
        BrowserDimension other = (BrowserDimension) o;
        int tmp = this.id - other.id;
        if(tmp != 0){
            return tmp;
        }
        tmp = this.browserName.compareTo(other.browserName);
        if(tmp != 0){
            return tmp;
        }
        return this.browserVersion.compareTo(other.browserVersion);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BrowserDimension)) return false;
        BrowserDimension that = (BrowserDimension) o;
        return getId() == that.getId() &&
                Objects.equals(getBrowserName(), that.getBrowserName()) &&
                Objects.equals(getBrowserVersion(), that.getBrowserVersion());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getBrowserName(), getBrowserVersion());
    }

    @Override
    public String toString() {
        return "BrowserDimension{" +
                "id=" + id +
                ", browserName='" + browserName + '\'' +
                ", browserVersion='" + browserVersion + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }
}
