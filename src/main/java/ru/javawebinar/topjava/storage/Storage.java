package ru.javawebinar.topjava.storage;

import java.util.List;

public interface Storage<T> {
    void save(T item);

    T get(Long id);

    void update(T item);

    void delete(T item);

    List<T> getAll();
}
