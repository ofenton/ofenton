rm -rf ../../target/output
hadoop jar ../../target/cascading-demo-1.0-SNAPSHOT-jar-with-dependencies.jar com.ofenton.cascading.test.Main ../../src/test/resources/data/rain.txt ../../target/output/rain
