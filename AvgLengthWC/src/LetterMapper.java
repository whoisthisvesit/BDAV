import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class LetterMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	@Override
	//method map is passed a key, value, and context object. Runs once for each line of text in input file
	public void map(LongWritable key, Text value, Context context)
	    throws IOException, InterruptedException {

	    //convert the value which is receied as a text object, so retrieve the string it contains
	    String line = value.toString();

	    //split the value (string line) into words using regular expression, loop through these words     
	    for (String word : line.split("\\W+")) {
	      if (word.length() > 0) {
	        
		  String firstLetter = word.substring(0,1).toLowerCase();
	    	 	    	
		   //call write method on context object to emit key/value pair. Must match type in driver
	    	   context.write(new Text(firstLetter), new IntWritable(word.length()));
	      }
	    }
	}   
}