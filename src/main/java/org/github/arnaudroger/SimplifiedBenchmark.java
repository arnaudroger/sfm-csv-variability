package org.github.arnaudroger;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;

/*
java -jar target/benchmarks.jar Sim  -bm sampl -tu ms -f 5 -i 10 -wi 10

Benchmark                                                    (nbCellsPerRow)  (nbRows)    Mode  Cnt    Score   Error  Units
SimplifiedBenchmark.benchmarkDirect                                       10    500000  sample  242  233.642 ± 9.727  ms/op
SimplifiedBenchmark.benchmarkDirect:benchmarkDirect·p0.00                 10    500000  sample       191.103          ms/op
SimplifiedBenchmark.benchmarkDirect:benchmarkDirect·p0.50                 10    500000  sample       206.176          ms/op
SimplifiedBenchmark.benchmarkDirect:benchmarkDirect·p0.90                 10    500000  sample       308.806          ms/op
SimplifiedBenchmark.benchmarkDirect:benchmarkDirect·p0.95                 10    500000  sample       313.367          ms/op
SimplifiedBenchmark.benchmarkDirect:benchmarkDirect·p0.99                 10    500000  sample       323.260          ms/op
SimplifiedBenchmark.benchmarkDirect:benchmarkDirect·p0.999                10    500000  sample       325.059          ms/op
SimplifiedBenchmark.benchmarkDirect:benchmarkDirect·p0.9999               10    500000  sample       325.059          ms/op
SimplifiedBenchmark.benchmarkDirect:benchmarkDirect·p1.00                 10    500000  sample       325.059          ms/op
SimplifiedBenchmark.benchmarkHolder                                       10    500000  sample  327  169.661 ± 5.611  ms/op
SimplifiedBenchmark.benchmarkHolder:benchmarkHolder·p0.00                 10    500000  sample       143.393          ms/op
SimplifiedBenchmark.benchmarkHolder:benchmarkHolder·p0.50                 10    500000  sample       157.024          ms/op
SimplifiedBenchmark.benchmarkHolder:benchmarkHolder·p0.90                 10    500000  sample       235.143          ms/op
SimplifiedBenchmark.benchmarkHolder:benchmarkHolder·p0.95                 10    500000  sample       240.281          ms/op
SimplifiedBenchmark.benchmarkHolder:benchmarkHolder·p0.99                 10    500000  sample       255.423          ms/op
SimplifiedBenchmark.benchmarkHolder:benchmarkHolder·p0.999                10    500000  sample       265.814          ms/op
SimplifiedBenchmark.benchmarkHolder:benchmarkHolder·p0.9999               10    500000  sample       265.814          ms/op
SimplifiedBenchmark.benchmarkHolder:benchmarkHolder·p1.00                 10    500000  sample       265.814          ms/op

java -jar target/benchmarks.jar SimplifiedBenchmark.benchmarkDirect   -bm avgt -tu ms -f 1 -i 10 -wi 10 -jvmArgs "-XX:+UnlockDiagnosticVMOptions -XX:+TraceClassLoading -XX:+LogCompilation -XX:+PrintAssembly -XX:LogFile=jitwatch-direct.log"
java -jar target/benchmarks.jar SimplifiedBenchmark.benchmarkHolder   -bm avgt -tu ms -f 1 -i 10 -wi 10 -jvmArgs "-XX:+UnlockDiagnosticVMOptions -XX:+TraceClassLoading -XX:+LogCompilation -XX:+PrintAssembly -XX:LogFile=jitwatch-holder.log"
 */

@State(Scope.Thread)
public class SimplifiedBenchmark {

    @Benchmark
    public void benchmarkDirect(CsvContent csvContent, Blackhole blackhole) {
        char[] content = csvContent.content;

        int startCell = 0;

        List<String> cells = new ArrayList<>();
        for(int currentIndex = 0;  currentIndex < content.length; currentIndex++) {
            char c = content[currentIndex];

            if (c == ',') {
                cells.add(new String(content, startCell, currentIndex - startCell));
                startCell = currentIndex + 1;
            } else if (c == '\n') {
                cells.add(new String(content, startCell, currentIndex - startCell));
                startCell = currentIndex + 1;
                blackhole.consume(new ArrayList<>(cells));
                cells.clear();
            }

        }

    }

    @Benchmark
    public void benchmarkHolder(CsvContent csvContent, Blackhole blackhole) {
        char[] content = csvContent.content;

        holder = new Holder();

        List<String> cells = new ArrayList<>();
        for(int currentIndex = 0;  currentIndex < content.length; currentIndex++) {
            char c = content[currentIndex];

            if (c == ',') {
                cells.add(new String(content, holder.startCell, currentIndex - holder.startCell));
                holder.startCell = currentIndex + 1;
            } else if (c == '\n') {
                cells.add(new String(content,  holder.startCell, currentIndex - holder.startCell));
                holder.startCell = currentIndex + 1;
                blackhole.consume(new ArrayList<>(cells));
                cells.clear();
            }

        }
    }

    private Holder holder;

    static class Holder  {
        int startCell = 0;
    }
}
