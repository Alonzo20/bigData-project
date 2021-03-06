package com.alonzo.transformer.mr.am;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;

import com.alonzo.common.GlobalConstants;
import com.alonzo.common.KpiType;
import com.alonzo.transformer.model.dim.StatsUserDimension;
import com.alonzo.transformer.model.dim.base.BaseDimension;
import com.alonzo.transformer.model.value.BaseStatsValueWritable;
import com.alonzo.transformer.model.value.reduce.MapWritableValue;
import com.alonzo.transformer.mr.IOutputCollector;
import com.alonzo.transformer.service.rpc.IDimensionConverter;

/**
 * 定义具体的active member kpi的输出类
 * 
 * @author alonzo
 *
 */
public class ActiveMemberCollector implements IOutputCollector {

	@Override
	public void collect(Configuration conf, BaseDimension key, BaseStatsValueWritable value, PreparedStatement pstmt, IDimensionConverter converter) throws SQLException, IOException {
		// 第一步: 将key&value进行强制转换
		StatsUserDimension statsUser = (StatsUserDimension) key;
		IntWritable activeMembers = (IntWritable) ((MapWritableValue) value).getValue().get(new IntWritable(-1));

		int i = 0;
		pstmt.setInt(++i, converter.getDimensionIdByValue(statsUser.getStatsCommon().getPlatform()));
		pstmt.setInt(++i, converter.getDimensionIdByValue(statsUser.getStatsCommon().getDate()));
		if (KpiType.BROWSER_ACTIVE_MEMBER.name.equals(statsUser.getStatsCommon().getKpi().getKpiName())) {
			// 表示输出结果是统计browser active member的，那么进行browser维度信息设置
			pstmt.setInt(++i, converter.getDimensionIdByValue(statsUser.getBrowser()));
		}
		pstmt.setInt(++i, activeMembers.get());
		pstmt.setString(++i, conf.get(GlobalConstants.RUNNING_DATE_PARAMES));
		pstmt.setInt(++i, activeMembers.get());

		// 将pstmt添加到批量执行中
		pstmt.addBatch();
	}

}
