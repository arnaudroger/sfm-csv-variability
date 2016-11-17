package org.github.arnaudroger;


import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class CsvContent {


    @Param("500000")
    public int nbRows;

    @Param("10")
    public int nbCellsPerRow;

    public char[] content;


    @Setup
    public void setUp() {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < nbRows; i++) {
            for(int j = 0; j < nbCellsPerRow; j++) {
                if (j > 0) sb.append(",");
                sb.append(Long.toHexString(i | j));
            }
            sb.append("\n");
        }

        content = sb.toString().toCharArray();
    }
}
