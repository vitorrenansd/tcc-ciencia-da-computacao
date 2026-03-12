package com.tcc.serveme.api.cashshift.repository;

import com.tcc.serveme.api.cashshift.entity.CashShift;
import com.tcc.serveme.api.cashshift.entity.CashShiftStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcCashShiftRepository implements CashShiftRepository {
    private final JdbcTemplate jdbc;
    private static final RowMapper<CashShift> ROW_MAPPER =
            (rs, rowNum) -> {
                Timestamp closed = rs.getTimestamp("closed_at");

                return new CashShift(
                        rs.getLong("id"),
                        rs.getTimestamp("opened_at").toLocalDateTime(),
                        closed != null ? closed.toLocalDateTime() : null, // evita NullPointerException
                        CashShiftStatus.valueOf(rs.getString("status"))
                        // Caso adicionar novas colunas no banco, atualizar aqui
                );
            };

    @Autowired
    public JdbcCashShiftRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Optional<CashShift> findById(Long id) {
        String sql = """
                SELECT id, opened_at, closed_at, status
                FROM cash_shift
                WHERE id = ?
                """;
        List<CashShift> result = jdbc.query(sql, ROW_MAPPER, id);
        return result.stream().findFirst();
    }

    @Override
    public Long openShift(LocalDateTime currentDateTime) {
        String sql = """
            INSERT INTO cash_shift (opened_at, status)
            VALUES (?, 'OPEN')
        """;
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, Timestamp.valueOf(currentDateTime));
            return ps;
        }, keyHolder);
        return keyHolder.getKeyAs(Long.class);
    }

    @Override
    public void closeShift(Long id, LocalDateTime currentDateTime) {
        String sql = """
            UPDATE cash_shift
            SET closed_at = ?, status = 'CLOSED'
            WHERE id = ?
        """;
        jdbc.update(sql, Timestamp.valueOf(currentDateTime), id);
    }

    // ************************
    //  Specific queries below
    // ************************

    @Override
    public Optional<CashShift> findOpenShift() {
        String sql = """
                SELECT id, opened_at, closed_at, status
                FROM cash_shift
                WHERE closed_at IS NULL
                """;
        List<CashShift> result = jdbc.query(sql, ROW_MAPPER);
        return result.stream().findFirst();
    }
}