package com.alonzo.ae.dao;

import com.alonzo.ae.model.DateDimension;

public interface DateDimensionDao {
    public Integer getDateDimensionId(DateDimension date);

    public Integer getDateDimensionId(int year, int season, int month, int week, int day);
}