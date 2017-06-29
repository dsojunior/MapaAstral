/*
 * Classe que implementa a min com MapReduce
 */
package mapReduce;

import java.io.IOException;

import mapReduce.mappers.GroupMapper;
import mapReduce.util.StatisticsJobConf;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Reducer;

public class Min {
  
    public static class MinReducer extends Reducer<Text,DoubleWritable,Text,DoubleWritable> 
    {
        private DoubleWritable result = new DoubleWritable();

        public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException 
        {
            Double value = Double.MAX_VALUE;
            for (DoubleWritable val : values) 
            {
                value = value.compareTo(val.get()) < 0 ? value : val.get();
            }
            result.set(value);
            context.write(key, result);
        }
    }
//TODO revisar isso
    public static void main(String[] args) throws Exception 
    {
        Job job = StatisticsJobConf.getJob(DesvioPadrao.class, "dp", GroupMapper.class, MinReducer.class, args);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}