# BDAV


Class names:
wc: 
matrix:
union:
intersection:
avglength:
groupsum:
word cooccurence: [Pairs & Stripes] [dynamic]

For Word Count / General:
EXPORT THE JAR USING IDE [NETBEANS & ECIPPLSE]
Or 
USING COMMAND:
javac MyApp.java
jar -cf myJar.jar MyApp.clas

hadoop jar path_of_jar name_of_class /dir/output name_of_file_for_output
hadoop  jar /home/cloudera/Desktop/wc.jar WordCount /p2results results 

For word cooccurence:

PAIRS:
hadoop jar /home/cloudera/Desktop/Cooccurrencejar.jar Cooccurrence pairs /premmc/wordcount.txt

STRIPES:
hadoop jar /home/cloudera/Desktop/Cooccurrencejar.jar Cooccurrence stripes /premmc/wordcount.txt
