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
            startCell = nextCharDirect(blackhole, content, startCell, currentIndex);
        }

    }

    @Benchmark
    public void benchmarkHolder(CsvContent csvContent, Blackhole blackhole) {
        char[] content = csvContent.content;

        holder.startCell = 0;

        for(int currentIndex = 0;  currentIndex < content.length; currentIndex++) {
            nextCharHolder(blackhole, content, currentIndex);
        }
    }


    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    private int nextCharDirect(Blackhole blackhole, char[] content, int startCell, int currentIndex) {
        char c = content[currentIndex];
        if (c == ',') {
            char[] chars = new char[currentIndex - startCell];
            for(int i = 0; i < chars.length; i++) {
                chars[i] = content[i + startCell];
            }
            blackhole.consume(chars);
            startCell = currentIndex + 1;
        } else if (c == '\n') {
            char[] chars = new char[currentIndex - startCell];
            for(int i = 0; i < chars.length; i++) {
                chars[i] = content[i + startCell];
            }
            blackhole.consume(chars);            startCell = currentIndex + 1;
        }
        return startCell;
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    private void nextCharHolder(Blackhole blackhole, char[] content, int currentIndex) {
        char c = content[currentIndex];
        if (c == ',') {
            int startCell = holder.startCell;
            char[] chars = new char[currentIndex - startCell];
            for(int i = 0; i < chars.length; i++) {
                chars[i] = content[i + startCell];
            }
            blackhole.consume(chars);
            holder.startCell = currentIndex + 1;
        } else if (c == '\n') {
            int startCell = holder.startCell;
            char[] chars = new char[currentIndex - startCell];
            for(int i = 0; i < chars.length; i++) {
                chars[i] = content[i + startCell];
            }
            blackhole.consume(chars);
            holder.startCell = currentIndex + 1;
        }
    }

    private Holder holder = new Holder(); // wrong but ok here

    static class Holder  {
        int startCell = 0;
    }

}
