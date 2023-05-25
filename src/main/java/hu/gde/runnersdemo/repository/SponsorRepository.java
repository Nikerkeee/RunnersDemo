package hu.gde.runnersdemo.repository;

import hu.gde.runnersdemo.model.RunnerEntity;
import hu.gde.runnersdemo.model.SponsorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SponsorRepository extends JpaRepository<SponsorEntity, Long> {
}
