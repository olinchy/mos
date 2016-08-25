package com.zte.mos.persistent.database.connectionpool;

import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by xy on 15-1-5.
 */
public class TestDBConnection {

	private static ConnectionPool pool;
	private static Connection connection;

	@BeforeClass
	public static void setUp() throws Exception {
		DBInfo bean = new DBInfo();
		bean.setUserName("system");
		bean.setPassword("oracle");
		bean.setUrl("jdbc:oracle:thin:@10.86.58.73:1521:orcl");
		bean.setDriverName("oracle.jdbc.driver.OracleDriver");
		pool = new ConnectionPool();
		pool.init(bean);
		connection = pool.getConnection();

	}

	//    @Ignore
	@Test
	public void test_getPrimaryKeys() throws Exception {
		StringBuilder builder = new StringBuilder();
		DatabaseMetaData dbMetaData = connection.getMetaData();
		ResultSet resultSet = dbMetaData.getPrimaryKeys(null, "UEPPM4X", "PM_NEPODEF_TABLE");
		while (resultSet.next()) {
			builder.append(resultSet.getString("COLUMN_NAME") + ";");
		}
		assertThat(builder.toString(), is("MOCID;NETYPEID;POID;POTABLENAME;"));
	}

	//    @Ignore
	@Test
	public void test_getColumns() throws Exception {
		StringBuilder builder = new StringBuilder();
		DatabaseMetaData dbMetaData = connection.getMetaData();
		ResultSet resultSet = dbMetaData.getColumns(null, "UEPPM4X", "PM_NEPODEF_TABLE", null);
		while (resultSet.next()) {
			builder.append(resultSet.getString("COLUMN_NAME") + ";");
		}
		assertThat(builder.toString(), is("NETYPEID;MOCID;POID;PONAME;PODES;POHTMLDES;POTABLENAME;GROUPID;VISIBIFLAG;ISREALTIMESUSTAINED;RESERVATIONA;RESERVATIONB;RESERVATIONC;ISPARTITION;BASICTYPE;SCALETYPE;PLMNMODE;ISCOLLECT;PRODUCT;ISMULTICOLUMN;REBUILDINDEX;"));
	}

	//    @Ignore
	@Test
	public void test_query() throws Exception {
		StringBuilder builder = new StringBuilder();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select * from UEPPM4X.PM_DBCONFIG_TABLE");
		while (resultSet.next()) {
			builder.append(resultSet.getString("MOCID") + ";");
		}
		assertThat(builder.toString(), is("mw.nr8250.stm1;mw.nr8250.odu;mw.nr8250.rf;mw.nr8250.we;mw.nr8250.xpic;mw.nr8250.acm;mw.nr8250.rmonqos;mw.nr8250.vlan;mw.nr8250.e1ber;mw.nr8250.env;mw.nr8250.ces;mw.nr8250.wireeth;mw.nr8150.stm1;mw.nr8150.odu;mw.nr8150.rf;mw.nr8150.we;mw.nr8150.xpic;mw.nr8150.acm;mw.nr8150.rmonqos;mw.nr8150.vlan;mw.nr8150.e1ber;mw.nr8150.env;mw.nr8150.ces;mw.nr8150.wireeth;mw.nr8120.odu;mw.nr8120.rf;mw.nr8120.we;mw.nr8120.xpic;mw.nr8120.acm;mw.nr8120.rmonqos;mw.nr8120.vlan;mw.nr8120.e1ber;mw.nr8120.env;mw.nr8120.ces;mw.nr8120.wireeth;mw.nr8950.odu;mw.nr8950.rf;mw.nr8950.we;mw.nr8950.xpic;mw.nr8950.acm;mw.nr8950.rmonqos;mw.nr8950.vlan;mw.nr8950.env;mw.nr8950.wireeth;mw.nr8250.eth;mw.ev2000.vr2000.odu;mw.ev2000.vr2000.ethpm;mw.ev2000.er2000.radiolink;mw.ev2000.er2000.odu;mw.ev2000.er2000.ethpm;mw.ev2000.er2000.vlan;mw.pr10.s500.wl;mw.pr10.s500.eth;mw.pr10.s500.odu;mw.pr10.s400.wl;mw.pr10.s400.eth;mw.pr10.s400.odu;mw.sr10.s340.wl;mw.sr10.s340.eth;mw.sr10.s340.odu;mw.sr10.s340.mrmc;mw.sr10.s340.mse;mw.sr10.s340.stm1;mw.sr10.s340.e1;mw.sr10.s340.xpic;mw.sr10.s220.dmr;mw.sr10.s220.mux;mw.sr10.s240.wl;"));
	}
}
