package com.alonzo.transformer.mr.inbound;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.hadoop.conf.Configuration;

import com.alonzo.common.GlobalConstants;
import com.alonzo.transformer.model.dim.StatsInboundDimension;
import com.alonzo.transformer.model.dim.base.BaseDimension;
import com.alonzo.transformer.model.value.BaseStatsValueWritable;
import com.alonzo.transformer.model.value.reduce.InboundReduceValue;
import com.alonzo.transformer.mr.IOutputCollector;
import com.alonzo.transformer.service.rpc.IDimensionConverter;

public class InboundCollector implements IOutputCollector {

	@Override
	public void collect(Configuration conf, BaseDimension key, BaseStatsValueWritable value, PreparedStatement pstmt, IDimensionConverter converter) throws SQLException, IOException {
		StatsInboundDimension inboundDimension = (StatsInboundDimension) key;
		InboundReduceValue inboundReduceValue = (InboundReduceValue) value;

		int i = 0;
		pstmt.setInt(++i, converter.getDimensionIdByValue(inboundDimension.getStatsCommon().getPlatform()));
		pstmt.setInt(++i, converter.getDimensionIdByValue(inboundDimension.getStatsCommon().getDate()));
		pstmt.setInt(++i, inboundDimension.getInbound().getId()); // 直接设置，在mapper类中已经设置
		pstmt.setInt(++i, inboundReduceValue.getUvs());
		pstmt.setInt(++i, inboundReduceValue.getVisit());
		pstmt.setString(++i, conf.get(GlobalConstants.RUNNING_DATE_PARAMES));
		pstmt.setInt(++i, inboundReduceValue.getUvs());
		pstmt.setInt(++i, inboundReduceValue.getVisit());

		pstmt.addBatch();
	}

}
