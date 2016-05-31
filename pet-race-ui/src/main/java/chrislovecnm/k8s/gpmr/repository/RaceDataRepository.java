package chrislovecnm.k8s.gpmr.repository;

import chrislovecnm.k8s.gpmr.domain.RaceData;

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
 * Cassandra repository for the RaceData entity.
 */
@Repository
public class RaceDataRepository {

    @Inject
    private Session session;

    private Mapper<RaceData> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    @PostConstruct
    public void init() {
        mapper = new MappingManager(session).mapper(RaceData.class);
        findAllStmt = session.prepare("SELECT * FROM raceData");
        truncateStmt = session.prepare("TRUNCATE raceData");
    }

    public List<RaceData> findAll() {
        List<RaceData> raceData = new ArrayList<>();
        BoundStatement stmt =  findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                RaceData rd = new RaceData();
                rd.setId(row.getUUID("id"));
                rd.setRaceDataId(row.getUUID("raceDataId"));
                rd.setPetId(row.getUUID("petId"));
                rd.setRaceId(row.getUUID("raceId"));
                rd.setPetName(row.getString("petName"));
                rd.setPetCategoryName(row.getString("petCategoryName"));
                rd.setPetCategoryId(row.getUUID("petCategoryId"));
                rd.setInterval(row.getInt("interval"));
                rd.setRunnerPosition(row.getInt("runnerPosition"));
                rd.setRunnerDistance(row.getDouble("runnerDistance"));
                rd.setStartTime(row.getDate("startTime"));
                rd.setFinished(row.getBool("finished"));
                rd.setRunnerPreviousDistance(row.getDouble("runnerPreviousDistance"));
                return rd;
            }
        ).forEach(raceData::add);
        return raceData;
    }

    public RaceData findOne(UUID id) {
        return mapper.get(id);
    }

    public RaceData save(RaceData raceData) {
        if (raceData.getId() == null) {
            raceData.setId(UUID.randomUUID());
        }
        mapper.save(raceData);
        return raceData;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt =  truncateStmt.bind();
        session.execute(stmt);
    }
}
