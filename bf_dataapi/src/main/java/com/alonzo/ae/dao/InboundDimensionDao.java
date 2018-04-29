package com.alonzo.ae.dao;

import com.alonzo.ae.model.InboundDimension;

public interface InboundDimensionDao {
    public InboundDimension getInboundDimension(InboundDimension inbound);

    public InboundDimension getInboundDimension(int id);

    public InboundDimension getInboundDimension(String name);
}
