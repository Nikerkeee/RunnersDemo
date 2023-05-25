package hu.gde.runnersdemo.repository;

import hu.gde.runnersdemo.model.RunnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RunnerRepository extends JpaRepository<RunnerEntity,Long > {
}
