package br.com.engbr.examples.punchlistapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * Regression: the whole changelog history must live in one table (public.databasechangelog). When the DB user
 * is named like the schema, the search_path moves after 0001 creates the schema and Liquibase used to split the
 * history across two tables, re-running changesets (and failing) on the next start.
 */
@IntegrationTest
class LiquibaseHistoryIT {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private SpringLiquibase liquibase;

    @Test
    void secondStartup_doesNotReapplyChangesets() throws Exception {
        // the application context already migrated once; this is the next application start
        assertThatCode(liquibase::afterPropertiesSet).doesNotThrowAnyException();
        assertThat(jdbc.queryForObject("select count(*) from public.databasechangelog", Integer.class))
                .isEqualTo(5);
    }

    @Test
    void allChangesetsAreRecordedInThePublicHistoryTable() {
        Integer recorded = jdbc.queryForObject("select count(*) from public.databasechangelog", Integer.class);
        assertThat(recorded).isEqualTo(5);
    }

    @Test
    void noHistoryTableInsideTheApplicationSchema() {
        Integer tables = jdbc.queryForObject("""
                select count(*) from information_schema.tables
                where table_schema = 'punch_list' and table_name like 'databasechangelog%'
                """, Integer.class);
        assertThat(tables).isZero();
    }
}
