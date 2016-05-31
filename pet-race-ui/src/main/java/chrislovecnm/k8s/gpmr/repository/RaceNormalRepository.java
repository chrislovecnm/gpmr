package chrislovecnm.k8s.gpmr.repository;

import chrislovecnm.k8s.gpmr.domain.RaceNormal;

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
 * Cassandra repository for the RaceNormal entity.
 */
@Repository
public class RaceNormalRepository {

    @Inject
    private Session session;

    private Mapper<RaceNormal> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    @PostConstruct
    public void init() {
        mapper = new MappingManager(session).mapper(RaceNormal.class);
        findAllStmt = session.prepare("SELECT * FROM raceNormal");
        truncateStmt = session.prepare("TRUNCATE raceNormal");
    }

    public List<RaceNormal> findAll() {
        List<RaceNormal> raceNormals = new ArrayList<>();
        BoundStatement stmt =  findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                RaceNormal raceNormal = new RaceNormal();
                raceNormal.setId(row.getUUID("id"));
                raceNormal.setRaceNormalId(row.getUUID("raceNormalId"));
                raceNormal.setRaceId(row.getUUID("raceId"));
                raceNormal.setPetCategoryId(row.getUUID("petCategoryId"));
                raceNormal.setPetCategoryName(row.getString("petCategoryName"));
                raceNormal.setCurrentTime(row.getDate("currentTime"));
                raceNormal.setNormalLoc(row.getFloat("normalLoc"));
                raceNormal.setNormalScale(row.getFloat("normalScale"));
                raceNormal.setNormalSize(row.getInt("normalSize"));
                return raceNormal;
            }
        ).forEach(raceNormals::add);
        return raceNormals;
    }

    public RaceNormal findOne(UUID id) {
        return mapper.get(id);
    }

    public RaceNormal save(RaceNormal raceNormal) {
        if (raceNormal.getId() == null) {
            raceNormal.setId(UUID.randomUUID());
        }
        mapper.save(raceNormal);
        return raceNormal;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt =  truncateStmt.bind();
        session.execute(stmt);
    }
}
