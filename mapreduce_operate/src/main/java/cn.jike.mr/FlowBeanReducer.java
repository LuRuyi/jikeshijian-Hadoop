package cn.jike.mr;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

//相同的key的数据发送到一个reduce里，相同key合并，value形成一个集合
//Reducer<k2, v2, k3, v3>
public class FlowBeanReducer extends Reducer<Text, FlowBean, Text, Text> {
    private FlowBean outV = new FlowBean();
    private Text outText;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        outText = new Text();
    }

    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
        //1.遍历集合累加值
        long totalup = 0;
        long totaldown = 0;
        long totalsum = 0;
        for (FlowBean value : values) {
            totalup += value.getUpFlow();
            totaldown += value.getDownFlow();
        }
        //2.封装
        outV.setUpFlow(totalup);
        outV.setDownFlow(totaldown);
        outV.setSumFlow();

        //3.写出
        totalsum = totaldown + totalup;
        outText.set(totalup + "\t" + totaldown + "\t" + totalsum);
        context.write(key, outText);
    }

}