package com.alonzo.ae.dao;

import com.alonzo.ae.model.EventDimension;

public interface EventDimensionDao {
    public EventDimension getEventDimension(EventDimension eventDimension);

    public EventDimension getEventDimension(int id);

    public EventDimension getEventDimension(String category,String action);

}
