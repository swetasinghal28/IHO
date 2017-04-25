package com.iho.asu.Comparators;

import com.iho.asu.Tables.News;

import java.util.Comparator;

/**
 * Created by Mihir on 3/16/2017.
 */

public class NewsComparator implements Comparator<News> {

    @Override
    public int compare(News lhs, News rhs) {
        return lhs.getCreationDate().compareTo(rhs.getCreationDate());
    }
}
