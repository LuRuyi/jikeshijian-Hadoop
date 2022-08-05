package cn.jike.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

//mr程序的入口类
public class FlowBeanDriver extends Configured implements Tool {

    //代码组装都在run方法里进行
    @Override
    public int run(String[] strings) throws Exception {
        //1.获取job对象
        Job job = Job.getInstance(super.getConf(), FlowBeanDriver.class.getSimpleName());

        //2.设置jar 可以打包到集群上面运行
        job.setJarByClass(FlowBeanDriver.class);
        //3.关联自定义的mapper Reducer
        job.setMapperClass(FlowBeanMapper.class);
        job.setReducerClass(FlowBeanReducer.class);
        //4.设置 mapper 输出key 和 value 类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);
        //5.设置最终数据输出key 和value 类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        //6.设置数据的输入和输出路径
        FileInputFormat.setInputPaths(job, new Path("input/"));
        //注意：输出路径要不存在 存在会报错
        FileOutputFormat.setOutputPath(job, new Path("output/"));
        //7.提交job
        boolean result = job.waitForCompletion(true);
        return result ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        int run = ToolRunner.run(conf, new FlowBeanDriver(), args);
        //根据状态码 退出进程
        System.exit(run);
    }
}