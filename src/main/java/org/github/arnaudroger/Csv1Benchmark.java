/*
 * Copyright (c) 2005, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package org.github.arnaudroger;

import org.github.arnaudroger.csv.orig.CsvCharConsumer;
import org.github.arnaudroger.csv.orig.CsvReader;
import org.github.arnaudroger.csv.orig.ReaderCharBuffer;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.io.Reader;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
public class Csv1Benchmark {
    @Benchmark
    public void parseCsv(Blackhole blackhole, CsvParam csvParam) throws IOException {
        try(Reader reader = csvParam.getReader()) {
            getCsvReader(reader).read(blackhole::consume);
        }
    }

    private CsvReader getCsvReader(Reader reader) {
        return new CsvReader(new CsvCharConsumer(new ReaderCharBuffer(4 * 1024, Integer.MAX_VALUE, reader)));
    }

}
