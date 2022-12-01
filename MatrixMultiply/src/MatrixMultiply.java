/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.util.ReflectionUtils;

/**
 *
 * @author NARENDER KESWANI
 */
class Element implements Writable {

    int tag;
    int index;
    double value;

    Element() {
        tag = 0;
        index = 0;
        value = 0.0;
    }

    Element(int tag, int index, double value) {
        this.tag = tag;
        this.index = index;
        this.value = value;
    }

    @Override

    public void readFields(DataInput input) throws IOException {
        tag = input.readInt();
        index = input.readInt();
        value = input.readDouble();
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeInt(tag);
        output.writeInt(index);
        output.writeDouble(value);
    }
}

class Pair implements WritableComparable<Pair> {

    int i;
    int j;

    Pair() {
        i = 0;
        j = 0;
    }

    Pair(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public void readFields(DataInput input) throws IOException {
        i = input.readInt();
        j = input.readInt();
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeInt(i);
        output.writeInt(j);

    }

    @Override
    public int compareTo(Pair compare) {
        if (i > compare.i) {
            return 1;
        } else if (i < compare.i) {
            return -1;
        } else {
            if (j > compare.j) {
                return 1;
            } else if (j < compare.j) {
                return -1;
            }
        }
        return 0;
    }

    public String toString() {
        return i + " " + j + " ";
    }
}

public class MatrixMultiply {

    public static class MatrixMapperM extends Mapper<Object, Text, IntWritable, Element> {

        @Override
        public void map(Object key, Text value, Context context) throws
                IOException, InterruptedException {

            String readLine = value.toString();
            String[] tokens = readLine.split(",");
            int index = Integer.parseInt(tokens[0]);
            double elementVal = Double.parseDouble(tokens[2]);
            Element e = new Element(0, index, elementVal);

            IntWritable keyval = new IntWritable(Integer.parseInt(tokens[1]));
            context.write(keyval, e);
        }
    }

    public static class MatrixMapperN extends Mapper<Object, Text, IntWritable, Element> {

        @Override
        public void map(Object key, Text value, Context context) throws
                IOException, InterruptedException {

            String readLine = value.toString();
            String[] tokens = readLine.split(",");
            int index = Integer.parseInt(tokens[1]);
            double elementVal = Double.parseDouble(tokens[2]);
            Element e = new Element(1, index, elementVal);
            IntWritable keyval = new IntWritable(Integer.parseInt(tokens[0]));
            context.write(keyval, e);
        }
    }

    public static class ReducerMN extends Reducer<IntWritable, Element, Pair, DoubleWritable> {

        @Override
        public void reduce(IntWritable key, Iterable<Element> values, Context context)
                throws IOException, InterruptedException {

            ArrayList<Element> M = new ArrayList<Element>();
            ArrayList<Element> N = new ArrayList<Element>();
            Configuration conf = context.getConfiguration();
            for (Element element : values) {
                Element temp = ReflectionUtils.newInstance(Element.class, conf);
                ReflectionUtils.copy(conf, element, temp);
                if (temp.tag == 0) {
                    M.add(temp);
                } else if (temp.tag == 1) {
                    N.add(temp);
                }
            }
            for (int i = 0; i < M.size(); i++) {
                for (int j = 0; j < N.size(); j++) {
                    Pair p = new Pair(M.get(i).index, N.get(j).index);
                    double mul = M.get(i).value * N.get(j).value;
                    context.write(p, new DoubleWritable(mul));
                }
            }
        }
    }

    public static class MapMN extends Mapper<Object, Text, Pair, DoubleWritable> {

        @Override
        public void map(Object key, Text value, Context context) throws IOException,
                InterruptedException {

            String readLine = value.toString();
            String[] pairValue = readLine.split(" ");
            Pair p = new Pair(Integer.parseInt(pairValue[0]), Integer.parseInt(pairValue[1]));

            DoubleWritable val = new DoubleWritable(Double.parseDouble(pairValue[2]));

            context.write(p, val);
        }
    }

    public static class ReduceMN extends Reducer<Pair, DoubleWritable, Pair, DoubleWritable> {

        @Override
        public void reduce(Pair key, Iterable<DoubleWritable> values, Context context)
                throws IOException, InterruptedException {
            double sum = 0.0;
            for (DoubleWritable value : values) {
                sum += value.get();
            }
            context.write(key, new DoubleWritable(sum));
        }
    }

    public static void main(String[] args) throws Exception {
        Path MPath = new Path("/prac4/input/M");
        Path NPath = new Path("/prac4/input/N");
        Path intermediatePath = new Path("/prac4/interim");
        Path outputPath = new Path("/prac4/output");

        Job job1 = new Job(new Configuration());
        job1.setJobName("Map Intermediate");
        job1.setJarByClass(MatrixMultiply.class);

        MultipleInputs.addInputPath(job1, MPath, TextInputFormat.class, MatrixMapperM.class);
        MultipleInputs.addInputPath(job1, NPath, TextInputFormat.class, MatrixMapperN.class);
        job1.setReducerClass(ReducerMN.class);
        job1.setMapOutputKeyClass(IntWritable.class);
        job1.setMapOutputValueClass(Element.class);
        job1.setOutputKeyClass(Pair.class);
        job1.setOutputValueClass(DoubleWritable.class);

        job1.setOutputFormatClass(TextOutputFormat.class);
        FileOutputFormat.setOutputPath(job1, intermediatePath);
        job1.waitForCompletion(true);
        
        Job job2 = new Job(new Configuration());
        job2.setJobName("Map Final Output");
        job2.setJarByClass(MatrixMultiply.class);
        job2.setMapperClass(MapMN.class);
        job2.setReducerClass(ReduceMN.class);
        job2.setOutputKeyClass(Pair.class);
        job2.setOutputValueClass(DoubleWritable.class);
        job2.setInputFormatClass(TextInputFormat.class);
        job2.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.addInputPath(job2, intermediatePath);
        FileOutputFormat.setOutputPath(job2, outputPath);
        job2.waitForCompletion(true);
    }
}
