package com.expedia.edw.hww.codingtest.question2;

import java.util.Iterator;

import com.expedia.edw.hww.codingtest.question2.mapreduce.Collector;
import com.expedia.edw.hww.codingtest.question2.mapreduce.Program;

public class WordCountProgram implements Program {

  public void map(String key, String value, Collector collector) {
    String[] words = value.split("\\W+");
    for (String word : words) {
      collector.write(word.toLowerCase(), null);
    }
  }

  public void reduce(String key, Iterator<String> values, Collector collector) {
    int count = 0;
    while (values.hasNext()) {
      values.next();
      count++;
    }
    collector.write(key, Integer.toString(count));
  }

}
