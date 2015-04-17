package com.example.user.firstsqliteapp.database;

/**
 * Created by user on 16.04.2015.
 */
public abstract class ItemListener<T> {
    protected abstract boolean onItem(T item);
}
