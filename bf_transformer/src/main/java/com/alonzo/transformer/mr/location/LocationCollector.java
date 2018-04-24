package com.alonzo.transformer.mr.location;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.hadoop.conf.Configuration;

import com.alonzo.common.GlobalConstants;
import com.alonzo.transformer.model.dim.StatsLocationDimension;
import com.alonzo.transformer.model.dim.base.BaseDimension;
import com.alonzo.transformer.model.value.BaseStatsValueWritable;
import com.alonzo.transformer.model.value.reduce.LocationReducerOutputValue;
import com.alonzo.transformer.mr.IOutputCollector;
import com.alonzo.transformer.service.IDimensionConverter;

public class LocationCollector implements IOutputCollector {

	@Override
	public void collect(Configuration conf, BaseDimension key, BaseStatsValueWritable value, PreparedStatement pstmt, IDimensionConverter converter) throws SQLException, IOException {
		StatsLocationDimension locationDimension = (StatsLocationDimension) key;
		LocationReducerOutputValue locationReducerOutputValue = (LocationReducerOutputValue) value;

		int i = 0;
		pstmt.setInt(++i, converter.getDimensionIdByValue(locationDimension.getStatsCommon().getPlatform()));
		pstmt.setInt(++i, converter.getDimensionIdByValue(locationDimension.getStatsCommon().getDate()));
		pstmt.setInt(++i, converter.getDimensionIdByValue(locationDimension.getLocation()));
		pstmt.setInt(++i, locationReducerOutputValue.getUvs());
		pstmt.setInt(++i, locationReducerOutputValue.getVisits());
		pstmt.setInt(++i, locationReducerOutputValue.getBounceNumber());
		pstmt.setString(++i, conf.get(GlobalConstants.RUNNING_DATE_PARAMES));
		pstmt.setInt(++i, locationReducerOutputValue.getUvs());
		pstmt.setInt(++i, locationReducerOutputValue.getVisits());
		pstmt.setInt(++i, locationReducerOutputValue.getBounceNumber());

		pstmt.addBatch();

	}

}
