

import java.io.IOException;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

//class definition. first are intermediate key/type. Last are  output key/type
public class AverageReducer extends
		Reducer<Text, IntWritable, Text, DoubleWritable> {

	@Override
	//reduce method runs once for each key received
	public void reduce(Text key, Iterable<IntWritable> values, Context context)
		throws IOException, InterruptedException {
	
		long wordLengthSum = 0;
		long wordCount=0;

		//add value to word length counter for this key. 
		for (IntWritable value : values) {
		    wordLengthSum += value.get();
		    wordCount++;
         }
         if (wordCount != 0 ) {
	     // calculates average word length
              double result = (double) wordLengthSum / (double)wordCount;
             
	      //calls write method on context object to emit key/value pair
              context.write(key, new DoubleWritable(result));
         }
      }
}