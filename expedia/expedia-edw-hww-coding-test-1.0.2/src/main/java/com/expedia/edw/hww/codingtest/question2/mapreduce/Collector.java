package com.expedia.edw.hww.codingtest.question2.mapreduce;

/**
 * Collects values from the map and reduce phases.
 * <p/>
 * Candidates <u>should not</u> modify this class.
 */
public interface Collector {

  void write(String key, String value);

}
