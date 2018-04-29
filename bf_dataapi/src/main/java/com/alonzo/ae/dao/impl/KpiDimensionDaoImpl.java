package com.alonzo.ae.dao.impl;

import com.alonzo.ae.dao.KpiDimensionDao;
import com.alonzo.ae.dao.mybatis.BaseDao;
import com.alonzo.ae.model.KpiDimension;

public class KpiDimensionDaoImpl extends BaseDao implements KpiDimensionDao {

    private static String modelClass = KpiDimension.class.getName();
    private static String getKpiDimension = modelClass + ".getKpiDimension";

    @Override
    public KpiDimension getKpiDimension(KpiDimension kpi) {
        return this.getSqlSession().selectOne(getKpiDimension, kpi);
    }

    @Override
    public KpiDimension getKpiDimension(String name) {
        KpiDimension kpi = new KpiDimension(name);
        return getKpiDimension(kpi);
    }

    @Override
    public KpiDimension getKpiDimension(int id) {
        KpiDimension kpi = new KpiDimension(id);
        return getKpiDimension(kpi);
    }

}