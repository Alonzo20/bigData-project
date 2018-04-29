package com.alonzo.ae.dao.impl;

import com.alonzo.ae.dao.DateDimensionDao;
import com.alonzo.ae.dao.mybatis.BaseDao;
import com.alonzo.ae.model.DateDimension;

public class DateDimensionDaoImpl extends BaseDao implements DateDimensionDao {

    private static String modelClass = DateDimension.class.getName();
    private static String getDateDimensionId = modelClass + ".getDateDimensionId";

    @Override
    public Integer getDateDimensionId(DateDimension date) {
        return this.getSqlSession().selectOne(getDateDimensionId, date);
    }

    @Override
    public Integer getDateDimensionId(int year, int season, int month, int week, int day) {
        DateDimension date = new DateDimension(year, season, month, week, day);
        return getDateDimensionId(date);
    }

}