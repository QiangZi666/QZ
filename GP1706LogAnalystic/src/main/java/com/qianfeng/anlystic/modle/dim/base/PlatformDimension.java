package com.qianfeng.anlystic.modle.dim.base;

import com.qianfeng.common.GlobalConstants;
import org.apache.commons.lang.StringUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 平台维度
 */
public class PlatformDimension extends BaseDimension {


    private int id;
    private String platformName;

    public PlatformDimension() {
    }

    public PlatformDimension(int id, String platformName) {
        this.id = id;
        this.platformName = platformName;
    }

    public PlatformDimension(String platformName) {
        this.platformName = platformName;
    }

    /**
     * 构建平台维度的对象
     *
     * @param
     * @return
     */
    public static List<PlatformDimension> buildList(String platformName) {
        if (StringUtils.isEmpty(platformName)) {
            platformName = GlobalConstants.DEFAULT_VALUE;
        }
        List<PlatformDimension> li = new ArrayList<>();
        li.add(new PlatformDimension(platformName));
        li.add(new PlatformDimension(GlobalConstants.DEFAULT_VALUE));
        return li;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (platformName != null ? platformName.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PlatformDimension that = (PlatformDimension) obj;
        if (id != that.id) {
            return false;
        }
        return platformName != null ? platformName.equals(that.platformName) : that.platformName == null;
    }

    @Override
    public String toString() {
        return "PlatformDimension{" +
                "id=" + id +
                ", platformName='" + platformName + '\'' +
                '}';
    }

    @Override
    public int compareTo(BaseDimension o) {
        if (this == o) {
            return 0;
        }
        PlatformDimension other = (PlatformDimension) o;
        int tmp = this.id - other.id;
        if (tmp != 0) {
            return tmp;
        }
        tmp = this.platformName.compareTo(other.platformName);
        return tmp;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(this.id);
        out.writeUTF(this.platformName);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.platformName = in.readUTF();
        this.id = in.readInt();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }
}
