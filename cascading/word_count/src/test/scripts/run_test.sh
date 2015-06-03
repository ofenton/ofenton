rm -rf ../../../target/output
hadoop jar ../../../target/cascading-word_count-1.0-SNAPSHOT-jar-with-dependencies.jar com.ofenton.cascading.wordcount.Main ../../../src/test/resources/data/rain.txt ../../../target/output/rain
