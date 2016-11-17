package org.github.arnaudroger;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;

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
