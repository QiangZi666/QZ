package com.qianfeng.anlystic.modle.dim.base;

import com.qianfeng.common.DateEnum;
import com.qianfeng.util.TimeUtil;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class DateDimension extends BaseDimension {
    private int id;
    private int year;
    private int season;//季度
    private int month;
    private int week;
    private int day;
    private String type;  //类型，按天、周、月
    private Date calendar = new Date();//日期

    public DateDimension() {
    }

    public DateDimension(int year, int season, int month, int week, int day) {
        this.year = year;
        this.season = season;
        this.month = month;
        this.week = week;
        this.day = day;
    }

    public DateDimension(int year, int season, int month, int week, int day, String type) {
        this(year, season, month, week, day);
        this.type = type;
    }

    public DateDimension(int year, int season, int month, int week, int day, String type, Date calendar) {
        this(year, season, month, week, day, type);
        this.calendar = calendar;
    }

    public DateDimension(int id, int year, int season, int month, int week, int day, String type, Date calendar) {
        this(year, season, month, week, day, type, calendar);
        this.id = id;
    }

    /**
     * 根据type来构建时间维度对象
     *
     * @param
     * @return
     */
    public static DateDimension buildDate(long time, DateEnum type) {
        int year = TimeUtil.getDateInfo(time, DateEnum.YEAR);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        if (DateEnum.YEAR.equals(type)) {
            calendar.set(year, 0, 1);
            return new DateDimension(year, 0, 0, 0, 1, type.typeName, calendar.getTime());

        }
        int season = TimeUtil.getDateInfo(time, DateEnum.SEASON);

        if (DateEnum.SEASON.equals(type)) {
            int month = season * 3 - 2;
            calendar.set(year, -1, 0);
            return new DateDimension(year, season, month - 1, 0, 1, type.typeName, calendar.getTime());

        }
        int month = TimeUtil.getDateInfo(year, DateEnum.MONTH);
        if (DateEnum.MONTH.equals(type)) {
            calendar.set(year, month - 1, 1);
            return new DateDimension(year, season, month, 0, 1, type.typeName, calendar.getTime());

        }
        int week = TimeUtil.getDateInfo(year, DateEnum.WEEK);
        if (DateEnum.WEEK.equals(type)) {
            //通过时间戳获取指定日期的周的第一天,返回当前周的第一天的时间戳
            long fiistDayOfWeek = TimeUtil.getFirstDayOfWeek(time);
            year = TimeUtil.getDateInfo(time, DateEnum.YEAR);
            season = TimeUtil.getDateInfo(time, DateEnum.SEASON);
            month = TimeUtil.getDateInfo(time, DateEnum.MONTH);
            week = TimeUtil.getDateInfo(time, DateEnum.WEEK);
            return new DateDimension(year, season, month - 1, week, 0, type.typeName, new Date(fiistDayOfWeek));
        }
        int day = TimeUtil.getDateInfo(year, DateEnum.DAY);
        if (DateEnum.DAY.equals(day)) {
            calendar.set(year, month - 1, 1);
            return new DateDimension(year, season, month, 0, 1, type.typeName, calendar.getTime());

        }
        throw new RuntimeException("时间类型暂不支持构建DateDimension对象,type:" + type.typeName);
    }


    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + year;
        result = 31 * result + season;
        result = 31 * result + month;
        result = 31 * result + week;
        result = 31 * result + day;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (calendar != null ? calendar.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateDimension that = (DateDimension) o;

        if (id != that.id) return false;
        if (year != that.year) return false;
        if (season != that.season) return false;
        if (month != that.month) return false;
        if (week != that.week) return false;
        if (day != that.day) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        return calendar != null ? calendar.equals(that.calendar) : that.calendar == null;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int compareTo(BaseDimension o) {
        if (this == o) {
            return 0;
        }
        DateDimension other = (DateDimension) o;
        int tmp = this.id - other.id;
        if (tmp != 0) {
            return tmp;
        }
        tmp = this.year - other.year;
        if (tmp != 0) {
            return tmp;
        }
        tmp = this.season - other.season;
        if (tmp != 0) {
            return tmp;
        }
        tmp = this.month - other.month;
        if (tmp != 0) {
            return tmp;
        }
        tmp = this.week - other.week;
        if (tmp != 0) {
            return tmp;
        }
        tmp = this.day - other.day;
        if (tmp != 0) {
            return tmp;
        }
        tmp = this.type.compareTo(other.type);
        return tmp;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(this.id);
        out.writeInt(this.year);
        out.writeInt(this.season);
        out.writeInt(this.month);
        out.writeInt(this.week);
        out.writeInt(this.day);
        out.writeUTF(this.type);
        out.writeLong(this.calendar.getTime());  //Date类型wirte
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.id = in.readInt();
        this.year = in.readInt();
        this.season = in.readInt();
        this.month = in.readInt();
        this.week = in.readInt();
        this.day = in.readInt();
        this.type = in.readUTF();
        this.calendar.setTime(in.readLong()); //date类型的读
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCalendar() {
        return calendar;
    }

    public void setCalendar(Date calendar) {
        this.calendar = calendar;
    }
}
