package com.iho.asu.Comparators;

import com.iho.asu.Database.Tables.Lecturer;

import java.util.Comparator;

/**
 * Created by Mihir on 3/16/2017.
 */

public class LecturerComparator implements Comparator<Lecturer> {

    @Override
    public int compare(Lecturer lhs, Lecturer rhs) {
        return lhs.getOrder() - rhs.getOrder();
    }
}
