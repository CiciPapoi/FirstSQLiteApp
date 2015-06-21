package com.example.user.firstsqliteapp.utils;

/**
 * Created by user on 04.06.2015.
 */
import com.example.user.firstsqliteapp.MyApp;
import com.example.user.firstsqliteapp.data.Item;

import java.util.ArrayList;
import java.util.Random;

public class RandomGenerator
{
    private Random randomGenerator;

    public RandomGenerator()
    {
        randomGenerator = new Random();
    }

    public Item anyItem( ArrayList<Item> catalogue )
    {
        if (catalogue.size() == 0)
            return null;
        int index = 0;
        index = randomGenerator.nextInt(catalogue.size());
        while ( index == MyApp.getInstance().prev )
            index = randomGenerator.nextInt(catalogue.size());
        Item item = (Item) catalogue.get(index);
        MyApp.getInstance().prev = index;
        return item;
    }
}