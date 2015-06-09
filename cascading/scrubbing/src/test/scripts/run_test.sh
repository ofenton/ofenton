rm -rf ../../../target/output
hadoop jar ../../../target/cascading-scrubbing-1.0-SNAPSHOT-jar-with-dependencies.jar com.ofenton.cascading.scrubbing.Main ../../../src/test/resources/data/rain.txt ../../../target/output/wc
