package com.qianfeng.anlystic.modle.dim.base;

import org.apache.hadoop.io.WritableComparable;

/**
 * 维度的顶级父类,他的子类有所有维度类:"平台/时间/浏览器等
 */

public abstract class BaseDimension implements WritableComparable<BaseDimension> {

}
