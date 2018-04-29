package com.alonzo.ae.dao.impl;

import com.alonzo.ae.dao.EventDimensionDao;
import com.alonzo.ae.dao.mybatis.BaseDao;
import com.alonzo.ae.model.EventDimension;

public class EventDimensionDaoImpl extends BaseDao implements EventDimensionDao {
    private static String modelClass = EventDimension.class.getName();
    private static String getEventDimension = modelClass + ".getEventDimension";

    @Override
    public EventDimension getEventDimension(EventDimension eventDimension) {
        return this.getSqlSession().selectOne(getEventDimension, eventDimension);
    }

    @Override
    public EventDimension getEventDimension(int id) {
        return this.getEventDimension(new EventDimension(id));
    }

    @Override
    public EventDimension getEventDimension(String category, String action) {
        return this.getEventDimension(new EventDimension(category, action));
    }

}
