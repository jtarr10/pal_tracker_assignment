package io.pivotal.pal.tracker;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import static java.sql.Statement.RETURN_GENERATED_KEYS;


public class JdbcTimeEntryRepository implements TimeEntryRepository {

    private JdbcTemplate dataSource;

    private final RowMapper<TimeEntry> mapper = (rs, rowNum) -> new TimeEntry(
            rs.getLong("id"),
            rs.getLong("project_id"),
            rs.getLong("user_id"),
            rs.getDate("date").toLocalDate(),
            rs.getInt("hours")
    );

    private final ResultSetExtractor<TimeEntry> extractor =
            (rs) -> rs.next() ? mapper.mapRow(rs, 1) : null;

    public JdbcTimeEntryRepository(DataSource source) {
        this.dataSource = new JdbcTemplate(source);
    }


    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO time_entries (project_id, user_id, date, hours)" +
                "VALUES (?, ?, ?, ?)";
        this.dataSource.update(connection -> {
            PreparedStatement sqlStatement = connection.prepareStatement(sql, RETURN_GENERATED_KEYS);
            sqlStatement.setLong(1, timeEntry.getProjectId());
            sqlStatement.setLong(2, timeEntry.getUserId());
            sqlStatement.setDate(3, Date.valueOf(timeEntry.getDate()));
            sqlStatement.setLong(4, timeEntry.getHours());

            return sqlStatement;
        }, generatedKeyHolder);

        if(generatedKeyHolder != null && generatedKeyHolder.getKey() != null){
            return find(generatedKeyHolder.getKey().longValue());
        } else
            return null;
    }

    @Override
    public TimeEntry find(long id) {
        String sql = "SELECT id, project_id, user_id, date, hours FROM time_entries WHERE id = ?";
        return this.dataSource.query(sql, extractor, id);
    }

    @Override
    public List<TimeEntry> list() {
        String sql = "SELECT id, project_id, user_id, date, hours FROM time_entries";
        return this.dataSource.query(sql, mapper);
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        String sql = "UPDATE time_entries SET project_id = ?, user_id = ?, date = ?,  hours = ? WHERE id = ?";
        this.dataSource.update(sql, timeEntry.getProjectId(), timeEntry.getUserId(), Date.valueOf(timeEntry.getDate()), timeEntry.getHours(), id);
        return find(id);
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM time_entries WHERE id = ?";
        this.dataSource.update(sql, id);
    }

}
