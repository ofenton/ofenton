package com.expedia.edw.hww.codingtest.question2;

import java.util.List;

import com.expedia.edw.hww.codingtest.question2.mapreduce.KeyValue;
import com.expedia.edw.hww.codingtest.question2.mapreduce.MapReduceExecutor;
import com.expedia.edw.hww.codingtest.question2.mapreduce.Program;

/**
 *
 */
public class DuplicateLineRunner {

    public static void main(String[] args) {
        Program program = new DuplicateLineDetectorProgram();
        List<KeyValue> output = new MapReduceExecutor().execute(program, SampleData.TEXT_CORPUS);
        System.out.println("Output: " + output);
    }
}