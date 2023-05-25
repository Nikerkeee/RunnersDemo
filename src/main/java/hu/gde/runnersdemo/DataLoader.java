package hu.gde.runnersdemo;

import hu.gde.runnersdemo.model.LapTimeEntity;
import hu.gde.runnersdemo.model.RunnerEntity;
import hu.gde.runnersdemo.repository.RunnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class DataLoader implements CommandLineRunner {

    private final RunnerRepository runnerRepository;

    @Autowired
    public DataLoader(RunnerRepository runnerRepository) {
        this.runnerRepository = runnerRepository;
    }

    @Override
    public void run(String... args) {
        RunnerEntity runnerEntity = createRunner("Tomi", 310, 180);

        LapTimeEntity lapTime1 = createLapTimeEntry(1, 120, runnerEntity);
        LapTimeEntity lapTime2 = createLapTimeEntry(2, 110, runnerEntity);

        runnerEntity.getLaptimes().addAll(List.of(lapTime1, lapTime2));

        runnerRepository.save(runnerEntity);


        RunnerEntity runnerEntity2 = createRunner("Zsuzsi", 290, 165);

        LapTimeEntity lapTime3 = createLapTimeEntry(1, 95, runnerEntity2);
        LapTimeEntity lapTime4 = createLapTimeEntry(2, 100, runnerEntity2);

        runnerEntity2.getLaptimes().addAll(List.of(lapTime3, lapTime4));

        runnerRepository.save(runnerEntity2);


        RunnerEntity runnerEntity3 = createRunner("Béla", 300, 175);

        LapTimeEntity lapTime5 = createLapTimeEntry(1, 105, runnerEntity3);
        LapTimeEntity lapTime6 = createLapTimeEntry(2, 110, runnerEntity3);

        runnerEntity3.getLaptimes().addAll(List.of(lapTime5, lapTime6));

        runnerRepository.save(runnerEntity3);
    }

    private RunnerEntity createRunner(String name, long averagePace, int height) {
        RunnerEntity runnerEntity = new RunnerEntity();
        runnerEntity.setRunnerName(name);
        runnerEntity.setAveragePace(averagePace);
        runnerEntity.setHeight(height);
        return runnerEntity;
    }

    private LapTimeEntity createLapTimeEntry(int lapNumber, int timesSeconds, RunnerEntity runnerEntity) {
        LapTimeEntity lapTime = new LapTimeEntity();
        lapTime.setLapNumber(lapNumber);
        lapTime.setTimeSeconds(timesSeconds);
        lapTime.setRunner(runnerEntity);
        return lapTime;
    }
}

