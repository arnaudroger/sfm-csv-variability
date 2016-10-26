package org.github.arnaudroger.csv;


public interface CheckedConsumer<T> {
    void accept(T t) throws Exception;
}
