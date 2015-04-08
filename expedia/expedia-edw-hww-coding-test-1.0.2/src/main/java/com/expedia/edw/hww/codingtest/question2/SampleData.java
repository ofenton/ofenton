package com.expedia.edw.hww.codingtest.question2;

import java.util.List;

import com.expedia.edw.hww.codingtest.question2.mapreduce.KeyValue;
import com.google.common.collect.ImmutableList;

public final class SampleData {

  public static List<KeyValue> TEXT_CORPUS = new ImmutableList.Builder<KeyValue>()
      .add(new KeyValue("01", "and the flat downs mark englands south"))
      .add(new KeyValue("02", "coast running down to dramatic cornwall"))
      .add(new KeyValue("03", "london where cutting edge art and fashions"))
      .add(new KeyValue("04", "tumbling rivers where the buzzing scottish"))
      .add(new KeyValue("05", "capital edinburgh is home to a medieval old"))
      .add(new KeyValue("06", "perched off the coast of mainland europe"))
      .add(new KeyValue("07", "london where cutting edge art and fashions"))
      .add(new KeyValue("08", "england scotland and wales sandy beaches"))
      .add(new KeyValue("09", "great britain is a land of three nations"))
      .add(new KeyValue("10", "the river thames snakes through the capital"))
      .add(new KeyValue("11", "dot the farming villages in the dales with"))
      .add(new KeyValue("12", "to scotlands remote islands mountains and"))
      .add(new KeyValue("13", "in derbyshire the lake district edges north"))
      .add(new KeyValue("14", "steep peaks and caves in the peak district"))
      .add(new KeyValue("15", "great britain is a land of three nations"))
      .add(new KeyValue("16", "lie alongside roman remains further north"))
      .add(new KeyValue("17", "into yorkshire limestone walls and sheep"))
      .add(new KeyValue("18", "london where cutting edge art and fashions"))
      .add(new KeyValue("19", "city to the west are the valleys and dramatic"))
      .add(new KeyValue("20", "mountain peaks of wales with celtic roots")).build().asList();

  private SampleData() {
  }

}
