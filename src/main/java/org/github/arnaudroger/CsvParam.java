package org.github.arnaudroger;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.zip.GZIPInputStream;

@State(Scope.Benchmark)
public class CsvParam {



    public static final String url = new String("http://www.maxmind.com/download/worldcities/worldcitiespop.txt.gz");

    public static final String fileName = getFileDirectory() + File.separator + "worldcitiespop.txt";

    private static String getFileDirectory() {
        return System.getProperty("csv.dir", System.getProperty("java.io.tmpdir"));
    }

    public Reader getReader() throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            byte[] buffer = new byte[4096];
            try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                 BufferedInputStream bis = new BufferedInputStream(new GZIPInputStream(new URL(url).openStream()))
                ) {
                int l;
                while((l = bis.read(buffer)) != -1) {
                    bos.write(buffer, 0, l);
                }
            }
        }
        return newReader(file);
    }

    private static Reader newReader(File file) throws FileNotFoundException {
        return new InputStreamReader(new FileInputStream(file));
    }

}
