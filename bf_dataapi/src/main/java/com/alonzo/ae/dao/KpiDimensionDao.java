package com.alonzo.ae.dao;

import com.alonzo.ae.model.KpiDimension;

public interface KpiDimensionDao {
    public KpiDimension getKpiDimension(KpiDimension kpi);

    public KpiDimension getKpiDimension(String name);

    public KpiDimension getKpiDimension(int id);
}