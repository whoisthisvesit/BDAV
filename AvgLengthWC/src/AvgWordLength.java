import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class AvgWordLength {
	public static void main(String[] args) throws Exception {

        //Validate two arguments were passed from command line (input and output directories)
	if (args.length != 2) {
		System.out
			.printf("Usage: AvgWordLength <input dir> <output dir>\n");
		System.exit(-1);
	}

	//create new job object and identify the jar which contains mapper and reducer
	Job job = new Job();
	job.setJarByClass(AvgWordLength.class);

	//meaningful name which appears in reports and logs
	job.setJobName("Average Word Length");
 
	//Input (where read)  and output *where written) file paths
	FileInputFormat.setInputPaths(job, new Path(args[0]));
	FileOutputFormat.setOutputPath(job, new Path(args[1]));

	//Job object info about which classes to instantiate as Mapper and Reducer
	job.setMapperClass(LetterMapper.class);
	job.setReducerClass(AverageReducer.class);

	//intermediate output key/type produced by Mapper
	job.setMapOutputKeyClass(Text.class);
	job.setMapOutputValueClass(IntWritable.class);

	//Reducer output key/type
	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(DoubleWritable.class);

	// Start the MapReduce job and wait for it to finish, success returns 0
	boolean success = job.waitForCompletion(true);
	System.exit(success ? 0 : 1);
}
}