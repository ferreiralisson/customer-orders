package br.com.gurumatch.customerorders.conveters;

public interface Mapper<T, U> {
    U map(T t, U u);
}
