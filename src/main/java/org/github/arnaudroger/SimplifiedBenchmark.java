package org.github.arnaudroger;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

java8
java -jar target/benchmarks.jar SimplifiedBenchmark.benchmarkDirect   -bm avgt -tu ms -f 1 -i 10 -wi 10 -jvmArgs "-XX:-TieredCompilation -server -XX:+UnlockDiagnosticVMOptions -XX:+TraceClassLoading -XX:+LogCompilation -XX:+PrintAssembly -XX:LogFile=jitwatch-direct.log"
java -jar target/benchmarks.jar SimplifiedBenchmark.benchmarkHolder   -bm avgt -tu ms -f 1 -i 10 -wi 10 -jvmArgs "-XX:-TieredCompilation -server -XX:+UnlockDiagnosticVMOptions -XX:+TraceClassLoading -XX:+LogCompilation -XX:+PrintAssembly -XX:LogFile=jitwatch-holder.log"
 */

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class SimplifiedBenchmark {

    @Benchmark
    public void benchmarkDirect(CsvContent csvContent, Blackhole blackhole) {
        char[] content = csvContent.content;


        char[][] cells = new char[csvContent.nbCellsPerRow][];

        int startCell = 0;
        int cellIndex = 0;
        for(int currentIndex = 0;  currentIndex < content.length; currentIndex++) {
            char c = content[currentIndex];

            if (c == ',') {
                cells[cellIndex++] = Arrays.copyOfRange(content, startCell, currentIndex);
                startCell = currentIndex + 1;
            } else if (c == '\n') {
                cells[cellIndex++] = Arrays.copyOfRange(content, startCell, currentIndex);
                startCell = currentIndex + 1;
                blackhole.consume(cells);
                cellIndex = 0;

            }

        }

    }

    @Benchmark
    public void benchmarkHolder(CsvContent csvContent, Blackhole blackhole) {
        char[] content = csvContent.content;

        holder.startCell = 0;

        char[][] cells = new char[csvContent.nbCellsPerRow][];
        int cellIndex = 0;
        for(int currentIndex = 0;  currentIndex < content.length; currentIndex++) {
            char c = content[currentIndex];

            if (c == ',') {
                int startCell = holder.startCell;
                cells[cellIndex++] = Arrays.copyOfRange(content, startCell, currentIndex);
                holder.startCell = currentIndex + 1;
            } else if (c == '\n') {
                int startCell = holder.startCell;
                cells[cellIndex++] = Arrays.copyOfRange(content, startCell, currentIndex);
                holder.startCell = currentIndex + 1;
                    blackhole.consume(cells);
                    cellIndex = 0;
                }

        }
    }

    private Holder holder = new Holder(); // wrong but ok here

    static class Holder  {
        int startCell = 0;
    }


    @Benchmark
    public void benchmarkBranch(CsvContent csvContent, Blackhole blackhole) {
        char[] content = csvContent.content;


        char[][] cells = new char[csvContent.nbCellsPerRow][];
        int cellIndex = 0;

        int startCell = 0;
        for(int currentIndex = 0;  currentIndex < content.length; currentIndex++) {
            char c = content[currentIndex];

            if (c == ','  || c == '\n') {
                cells[cellIndex] = Arrays.copyOfRange(content,  startCell, currentIndex);
                cellIndex ++;
                startCell = currentIndex + 1;
                if (c == '\n') {
                    blackhole.consume(cells);
                    cellIndex = 0;
                }

            }

        }
    }

}
