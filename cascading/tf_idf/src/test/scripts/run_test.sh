rm -rf ../../../target/output
hadoop jar ../../../target/cascading-tf_idf-1.0-SNAPSHOT-jar-with-dependencies.jar com.ofenton.cascading.hashjoin.Main ../../../src/test/resources/data/rain.txt ../../../target/output ../../../src/test/resources/data/en.stop
