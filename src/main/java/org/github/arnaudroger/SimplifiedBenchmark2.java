package org.github.arnaudroger;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;


@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class SimplifiedBenchmark2 {

    @Benchmark
    public void benchmarkDirect(CsvContent csvContent, Blackhole blackhole) {
        char[] content = csvContent.content;
        int startCell = 0;
        for(int currentIndex = 0;  currentIndex < content.length; currentIndex++) {
            char c = content[currentIndex];

            if (c == ',') {
                blackhole.consume(Arrays.copyOfRange(content, startCell, currentIndex));
                startCell = currentIndex + 1;
            } else if (c == '\n') {
                blackhole.consume(Arrays.copyOfRange(content, startCell, currentIndex));
                startCell = currentIndex + 1;
            }

        }

    }

    @Benchmark
    public void benchmarkHolder(CsvContent csvContent, Blackhole blackhole) {
        char[] content = csvContent.content;

        holder.startCell = 0;

        for(int currentIndex = 0;  currentIndex < content.length; currentIndex++) {
            char c = content[currentIndex];

            if (c == ',') {
                int startCell = holder.startCell;
                blackhole.consume(Arrays.copyOfRange(content, startCell, currentIndex));
                holder.startCell = currentIndex + 1;
            } else if (c == '\n') {
                int startCell = holder.startCell;
                blackhole.consume(Arrays.copyOfRange(content, startCell, currentIndex));
                holder.startCell = currentIndex + 1;
            }
        }
    }

    private Holder holder = new Holder(); // wrong but ok here

    static class Holder  {
        int startCell = 0;
    }

}
