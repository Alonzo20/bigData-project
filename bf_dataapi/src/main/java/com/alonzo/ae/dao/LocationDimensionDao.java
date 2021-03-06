package com.alonzo.ae.dao;

import com.alonzo.ae.model.LocationDimension;

public interface LocationDimensionDao {
    public LocationDimension getLocationDimension(LocationDimension location);

    public LocationDimension getLocationDimension(String country, String province, String city);

    public LocationDimension getLocationDimension(int id);
}