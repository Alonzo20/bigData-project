package com.alonzo.ae.dao.impl;

import com.alonzo.ae.dao.InboundDimensionDao;
import com.alonzo.ae.dao.mybatis.BaseDao;
import com.alonzo.ae.model.InboundDimension;

public class InboundDimensionDaoImpl extends BaseDao implements InboundDimensionDao {
    private static String modelClass = InboundDimension.class.getName();
    private static String getInboundDimension = modelClass + ".getInboundDimension";

    @Override
    public InboundDimension getInboundDimension(InboundDimension inbound) {
        return this.getSqlSession().selectOne(getInboundDimension, inbound);
    }

    @Override
    public InboundDimension getInboundDimension(int id) {
        InboundDimension inbound = new InboundDimension(id);
        return getInboundDimension(inbound);
    }

    @Override
    public InboundDimension getInboundDimension(String name) {
        InboundDimension inbound = new InboundDimension(name);
        return getInboundDimension(inbound);
    }

}
