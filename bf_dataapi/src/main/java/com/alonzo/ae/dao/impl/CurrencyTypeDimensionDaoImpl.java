package com.alonzo.ae.dao.impl;

import com.alonzo.ae.dao.CurrencyTypeDimensionDao;
import com.alonzo.ae.dao.mybatis.BaseDao;
import com.alonzo.ae.model.CurrencyTypeDimension;

public class CurrencyTypeDimensionDaoImpl extends BaseDao implements CurrencyTypeDimensionDao {
    private static String modelClass = CurrencyTypeDimension.class.getName();
    private static String getCurrencyTypeDimension = modelClass + ".getCurrencyTypeDimension";

    @Override
    public CurrencyTypeDimension getCurrencyTypeDimension(CurrencyTypeDimension currencyTypeDimension) {
        return super.getSqlSession().selectOne(getCurrencyTypeDimension, currencyTypeDimension);
    }

    @Override
    public CurrencyTypeDimension getCurrencyTypeDimension(int id) {
        return this.getCurrencyTypeDimension(new CurrencyTypeDimension(id));
    }

    @Override
    public CurrencyTypeDimension getCurrencyTypeDimension(String currencyType) {
        return this.getCurrencyTypeDimension(new CurrencyTypeDimension(currencyType));
    }

}
