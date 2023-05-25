package hu.gde.runnersdemo.repository;

import hu.gde.runnersdemo.model.LapTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LapTimeRepository extends JpaRepository<LapTimeEntity, Long> {
}