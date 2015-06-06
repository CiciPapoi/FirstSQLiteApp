package com.example.user.firstsqliteapp.utils;

/**
 * Created by user on 04.06.2015.
 */
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

        int index = randomGenerator.nextInt(catalogue.size());
        Item item = catalogue.get(index);
        return item;
    }
}