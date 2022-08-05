package cn.jike.mr;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

//继承mapper
//Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
public class FlowBeanMapper extends Mapper<LongWritable, Text, Text, FlowBean> {
    private Text outK;
    private FlowBean outV;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
    //初始化执行一次的方法，对象初始化操作都放在这
        outK = new Text();
        outV = new FlowBean();
    }


    //重写map方法， key，value是入参 context将数据发送到下面步骤
    //每读一行数据都会调用下map方法
    //map中不要进行new对象操作 容易oom
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //1.获取一行信息
        String line = value.toString();

        //2.切割
        //时间戳、            电话号码、         基站的物理地址、       访问网址的ip、         网站域名、  数据包、接包数、上行/传流量、下行/载流量、响应码
        //1363157985066 	13726230503	00-FD-07-A4-72-B8:CMCC	120.196.100.82	i02.c.aliimg.com 24	   27	2481	   24681	200
        String[] split = line.split("\t");

        //3.抓取数据
        String phoneno = split[1];
        String upflow = split[split.length - 3];
        String downflow = split[split.length - 2];

        //4.封装
        //手机号作为key
        outK.set(phoneno);
        outV.setUpFlow(Long.parseLong(upflow));
        outV.setDownFlow(Long.parseLong(downflow));
        outV.setSumFlow();

        //5.写出
        context.write(outK, outV);
    }
}
