java -jar target/benchmarks.jar  SimplifiedBenchmark2.benchmarkDirect -f 1 -i 10 -wi 10 -bm avgt -tu ms -jvm /home/aroger/dev/dev8/build/linux-x86_64-normal-server-fastdebug/jdk/bin/java -jvmArgs "-XX:PrintIdealGraphLevel=3 -XX:PrintIdealGraphFile=simplified2/nodes-direct.xml"
java -jar target/benchmarks.jar  SimplifiedBenchmark2.benchmarkHolder -f 1 -i 10 -wi 10 -bm avgt -tu ms -jvm /home/aroger/dev/dev8/build/linux-x86_64-normal-server-fastdebug/jdk/bin/java -jvmArgs "-XX:PrintIdealGraphLevel=3 -XX:PrintIdealGraphFile=simplified2/nodes-holder.xml"


java -jar target/benchmarks.jar  SimplifiedBenchmark2.benchmarkDirect -f 1 -i 10 -wi 10 -bm avgt -tu ms -jvm /home/aroger/dev/dev8/build/linux-x86_64-normal-server-fastdebug/jdk/bin/java -jvmArgs "-XX:+UnlockDiagnosticVMOptions -XX:+TraceClassLoading -XX:+LogCompilation -XX:+PrintAssembly -XX:LogFile=simplified2/compile-direct.xml" 
java -jar target/benchmarks.jar  SimplifiedBenchmark2.benchmarkHolder -f 1 -i 10 -wi 10 -bm avgt -tu ms -jvm /home/aroger/dev/dev8/build/linux-x86_64-normal-server-fastdebug/jdk/bin/java -jvmArgs "-XX:+UnlockDiagnosticVMOptions -XX:+TraceClassLoading -XX:+LogCompilation -XX:+PrintAssembly -XX:LogFile=simplified2/compile-holder.xml" 
