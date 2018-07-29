package com.qianfeng.etl.mr.tohbase;


import com.qianfeng.common.EventLogsConstant;
import com.qianfeng.util.TimeUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

import java.io.IOException;


/**
 * 解析数据到hbase的runner(驱动)类
 */
public class ParseLogRunner implements Tool {
    private static final Logger logger = Logger.getLogger(ParseLogRunner.class);
    private Configuration conf = null;

    public static void main(String[] args) throws Exception {
        ToolRunner.run(new Configuration(), new ParseLogRunner(), args);
    }


    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf, "tohbase");
        job.setJarByClass(ParseLogRunner.class);

        job.setMapperClass(ParseLogMapper.class);
        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(Put.class);

        //输入参数处理和设置job的输入路径处理
        this.handleArgs(args, conf, job);
        //判断hbase中的表是否存在
        this.isExistHbaseTable(conf);

        //初始化reduce  addDependencyJars:false 本地提交本地运行,反之等于true是集群运行

        return job.waitForCompletion(true)?0:1;

    }

    /**
     * 判断hbase表是否存在
     *
     * @param conf
     */
    private void isExistHbaseTable(Configuration conf) throws IOException {
        HBaseAdmin ha = null;
        ha = new HBaseAdmin(conf);
        TableName tn = TableName.valueOf(EventLogsConstant.EVENT_LOG_HBASE_NAME);
        //判断是否存在,存在则不管,否则创建
        if (!ha.tableExists(tn)) {
            HTableDescriptor hdc = new HTableDescriptor(tn);
            HColumnDescriptor hcd = new HColumnDescriptor(EventLogsConstant.EVENT_LOG_FAMILY_NAME);
            hdc.addFamily(hcd);
            //提交创建
            ha.createTable(hdc);
        } else if (ha != null) {
            ha.close();
        }

    }

    /**
     * 运行:yarn jar *.jar.class -d 2018-0705
     *
     * @param args
     * @param conf
     * @param job
     */
    private void handleArgs(String[] args, Configuration conf, Job job) throws IOException {
        FileSystem fs = null;
        String date = null;
        for (int i = 0; i < args.length; i++) {
            if ("-d".equals(args[i])) {
                if (i + 1 < args.length) {
                    date = args[i + 1];
                    break;
                }
            }
        }
        //如果date为空或者无效,则默认使用昨天的date将date设置到conf中
        if (date == null || !TimeUtil.isRunningValidate(date)) {
            date = TimeUtil.getYesterday();
        }
        //设置输入的路径  /logs/07/05/**.log
        fs = FileSystem.get(conf);
        String dates[] = date.split("-");
        Path inputPath = new Path("/logs/" + dates[1] + "/" + dates[2]);
        if (fs.exists(inputPath)) {
            FileInputFormat.addInputPath(job, inputPath);
        } else if (fs != null) {
            fs.close();
        } else {
            throw new RuntimeException("job的运行数据目录不存在");
        }
    }

    @Override
    public void setConf(Configuration conf) {
        this.conf = HBaseConfiguration.create();
    }

    @Override
    public Configuration getConf() {
        return this.conf;
    }
}
