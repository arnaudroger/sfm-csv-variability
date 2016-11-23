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
            blackhole.consume(processContent(content, startCell, currentIndex));
            return currentIndex + 1;
        } else if (c == '\n') {
            blackhole.consume(processContent(content, startCell, currentIndex));
            return currentIndex + 1;
        }
        return startCell;
    }

   // @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    private char[] processContent(char[] content, int startCell, int currentIndex) {
        int newLength = currentIndex - startCell;
        char[] copy = new char[newLength];
        System.arraycopy(content, startCell, copy, 0, newLength);
        return copy;
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    private void nextCharHolder(Blackhole blackhole, char[] content, int currentIndex) {
        char c = content[currentIndex];
        if (c == ',') {
            blackhole.consume(processContent(content, holder.startCell, currentIndex));
            holder.startCell = currentIndex + 1;
        } else if (c == '\n') {
            blackhole.consume(processContent(content, holder.startCell, currentIndex));
            holder.startCell = currentIndex + 1;
        }
    }

    private Holder holder = new Holder(); // wrong but ok here

    static class Holder  {
        int startCell = 0;
    }

}
