package summer.base.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import summer.core.constant.GlobalConstant;


/**
 * Created by JOEL on 2014/9/2.
 */
public class ImageUrlTypeHandler implements TypeHandler<String> {

	public void setParameter(PreparedStatement preparedStatement, int i,
			String url, JdbcType jdbcType) throws SQLException {

	}

	public String getResult(ResultSet resultSet, String columnName)
			throws SQLException {

		String url = (resultSet.getString(columnName)==null||resultSet.getString(columnName).equals(""))?"":resultSet.getString(columnName);

		return (url.contains("http://")||url.equals(""))?url:(GlobalConstant.UPLOAD_SERVER + url);
	}

	public String getResult(ResultSet resultSet, int columnIndex)
			throws SQLException {
		String url = (resultSet.getString(columnIndex)==null||resultSet.getString(columnIndex).equals(""))?"":resultSet.getString(columnIndex);

		return (url.contains("http://")||url.equals(""))?url:(GlobalConstant.UPLOAD_SERVER + url);
	}

	public String getResult(CallableStatement callableStatement, int i)
			throws SQLException {
		String url = (callableStatement.getString(i)==null||callableStatement.getString(i).equals(""))?"":callableStatement.getString(i);

		return (url.contains("http://")||url.equals(""))?url:(GlobalConstant.UPLOAD_SERVER + url);
	}
}
