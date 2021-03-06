package com.zprogress.reporsitory;

import com.zprogress.domain.Goal;
import com.zprogress.domain.repository.GoalRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class GoalRepositoryImpl extends AbstractRepository implements GoalRepository {

    private static final String INSERT_GOAL = "INSERT INTO GOAL (name, description, deadline, user_name) values (?, ?, ?, ?)";
    private static final String SELECT_GOAL = "SELECT id, name, description, deadline, user_name FROM GOAL WHERE id = ? AND user_name = ?";
    private static final String SELECT_ALL = "SELECT id, name, description, deadline, user_name FROM GOAL WHERE user_name = ?";
    private static final GoalResultSetHandler goalResultSetHandler = new GoalResultSetHandler();

    public GoalRepositoryImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Goal create(Goal goal) {
        var idHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            var ps = connection
                    .prepareStatement(INSERT_GOAL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, goal.getName());
            ps.setString(2, goal.getDescription());
            ps.setObject(3, goal.getDeadline());
            ps.setString(4, goal.getUsername());
            return ps;
        }, idHolder);
        goal.setId(idHolder.getKey().longValue());
        return goal;
    }

    @Override
    public void update(Goal object) {

    }

    @Override
    public void delete(Goal object) {

    }

    @Override
    public Goal getById(Long id) {
        return jdbcTemplate.query(SELECT_GOAL, (ResultSetExtractor<Goal>) goalResultSetHandler, id);
    }

    @Override
    public List<Goal> getAll() {
        return jdbcTemplate.query(SELECT_ALL, (RowMapper<Goal>) goalResultSetHandler);
    }

    private static class GoalResultSetHandler implements RowMapper<Goal>, ResultSetExtractor<Goal> {

        @Override
        public Goal mapRow(ResultSet resultSet, int i) throws SQLException {
            return extractSingleRow(resultSet);
        }

        @Override
        public Goal extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            if (resultSet.next()) {
                return extractSingleRow(resultSet);
            }
            return null;
        }

        private Goal extractSingleRow(ResultSet resultSet) throws SQLException {
            Goal goal = new Goal();
            goal.setId(resultSet.getLong(1));
            goal.setName(resultSet.getString(2));
            goal.setDescription(resultSet.getString(3));
            goal.setDeadline(resultSet.getDate(4).toLocalDate());
            goal.setUsername(resultSet.getString(5));
            return goal;
        }
    }
}
