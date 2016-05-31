package chrislovecnm.k8s.gpmr.repository;

import chrislovecnm.k8s.gpmr.domain.RaceResult;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Cassandra repository for the RaceResult entity.
 */
@Repository
public class RaceResultRepository {

    @Inject
    private Session session;

    private Mapper<RaceResult> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    @PostConstruct
    public void init() {
        mapper = new MappingManager(session).mapper(RaceResult.class);
        findAllStmt = session.prepare("SELECT * FROM raceResult");
        truncateStmt = session.prepare("TRUNCATE raceResult");
    }

    public List<RaceResult> findAll() {
        List<RaceResult> raceResults = new ArrayList<>();
        BoundStatement stmt =  findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                RaceResult raceResult = new RaceResult();
                raceResult.setId(row.getUUID("id"));
                raceResult.setRaceResultId(row.getUUID("raceResultId"));
                raceResult.setRaceId(row.getUUID("raceId"));
                raceResult.setPetCategoryId(row.getUUID("petCategoryId"));
                raceResult.setRaceParticipantId(row.getUUID("raceParticipantId"));
                raceResult.setPetName(row.getString("petName"));
                raceResult.setPetType(row.getString("petType"));
                raceResult.setPetColor(row.getUUID("petColor"));
                raceResult.setPetCategoryName(row.getString("petCategoryName"));
                raceResult.setFinishPosition(row.getInt("finishPosition"));
                raceResult.setFinishTime(row.getDouble("finishTime"));
                raceResult.setStartTime(row.getDate("startTime"));
                return raceResult;
            }
        ).forEach(raceResults::add);
        return raceResults;
    }

    public RaceResult findOne(UUID id) {
        return mapper.get(id);
    }

    public RaceResult save(RaceResult raceResult) {
        if (raceResult.getId() == null) {
            raceResult.setId(UUID.randomUUID());
        }
        mapper.save(raceResult);
        return raceResult;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt =  truncateStmt.bind();
        session.execute(stmt);
    }
}
