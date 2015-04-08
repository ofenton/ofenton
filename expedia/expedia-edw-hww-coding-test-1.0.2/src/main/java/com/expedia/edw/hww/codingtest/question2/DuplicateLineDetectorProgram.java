package com.expedia.edw.hww.codingtest.question2;

import java.util.Iterator;

import com.expedia.edw.hww.codingtest.question2.mapreduce.Collector;
import com.expedia.edw.hww.codingtest.question2.mapreduce.Program;

/**
 *
 */
public class DuplicateLineDetectorProgram implements Program {

  @Override
  public void map(String key, String value, Collector collector) {
      collector.write(value, key);
  }

  @Override
  public void reduce(String key, Iterator<String> values, Collector collector) {
      StringBuilder sb = new StringBuilder();
      int count = 0;

      while (values.hasNext()) {
          String thisValue = values.next();
          if (count != 0) {
              sb.append("|");
          }
          sb.append(thisValue);
          count++;
      }

      if (count > 1) {
          collector.write(key, sb.toString());
      }
  }

}
