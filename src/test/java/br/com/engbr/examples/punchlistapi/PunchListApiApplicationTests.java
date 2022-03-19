package br.com.engbr.examples.punchlistapi;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PunchListApiApplicationTests {

	@Autowired
	private DataSource datasource;

	@Test
	void verifyConnectionDatabase_ExpectOk() throws SQLException {
		Connection connection = datasource.getConnection();
		assertThat(connection).isNotNull();
	}

}
