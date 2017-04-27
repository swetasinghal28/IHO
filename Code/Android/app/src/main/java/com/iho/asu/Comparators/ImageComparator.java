package com.iho.asu.Comparators;

import com.iho.asu.Model.Gallery;

import java.util.Comparator;

/**
 * Created by Mihir on 3/16/2017.
 */

public class ImageComparator implements Comparator<Gallery> {

    @Override
    public int compare(Gallery lhs, Gallery rhs) {

        return lhs.getOrder() - rhs.getOrder();

    }
}
