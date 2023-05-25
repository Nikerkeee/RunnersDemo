package hu.gde.runnersdemo.controller;

import hu.gde.runnersdemo.model.SponsorEntity;
import hu.gde.runnersdemo.repository.LapTimeRepository;
import hu.gde.runnersdemo.repository.RunnerRepository;
import hu.gde.runnersdemo.model.LapTimeEntity;
import hu.gde.runnersdemo.model.RunnerEntity;
import hu.gde.runnersdemo.repository.SponsorRepository;
import hu.gde.runnersdemo.service.RunnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/runner")
public class RunnerRestController {

    private final RunnerRepository runnerRepository;
    private final SponsorRepository sponsorRepository;
    @Autowired
    private LapTimeRepository lapTimeRepository;
    @Autowired
    private RunnerService runnerService;

    @Autowired
    public RunnerRestController(RunnerRepository runnerRepository, LapTimeRepository lapTimeRepository, SponsorRepository sponsorRepository, RunnerService runnerService) {
        this.runnerRepository = runnerRepository;
        this.lapTimeRepository = lapTimeRepository;
        this.sponsorRepository = sponsorRepository;
        this.runnerService = runnerService;
    }

    @GetMapping("/{id}")
    public RunnerEntity getRunner(@PathVariable Long id) {
        return runnerRepository.findById(id).orElse(null);
    }

    @GetMapping("/{id}/averagelaptime")
    public double getAverageLaptime(@PathVariable Long id) {
        return runnerService.getAverageLaptime(id);
    }

    @GetMapping("/tallestRunner")
    public String getTallestRunnerName() {
        return runnerService.getTallestRunnerName();
    }

    @GetMapping("")
    public List<RunnerEntity> getAllRunners() {
        return runnerRepository.findAll();
    }

    @PostMapping("/{id}/addlaptime")
    public ResponseEntity addLaptime(@PathVariable Long id, @RequestBody LapTimeRequest lapTimeRequest) {
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        if (runner != null) {
            LapTimeEntity lapTime = new LapTimeEntity();
            lapTime.setTimeSeconds(lapTimeRequest.getLapTimeSeconds());
            lapTime.setLapNumber(runner.getLaptimes().size() + 1);
            lapTime.setRunner(runner);
            lapTimeRepository.save(lapTime);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Runner with ID " + id + " not found");
        }
    }

    @PostMapping("/{id}/updateSponsor")
    public ResponseEntity updateSponsor(@PathVariable Long id, @RequestBody SponsorRequest sponsorRequest) {
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        SponsorEntity sponsor = sponsorRepository.findById((long) sponsorRequest.getSponsorId()).orElse(null);
        if (runner != null && sponsor != null) {
            runner.setSponsor(sponsor);
            runnerRepository.save(runner);
            return ResponseEntity.ok().build();
        } else if (runner == null && sponsor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Runner with ID " + id + "  and Sponsor with ID " + sponsorRequest.sponsorId + " not found");
        } else if (sponsor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sponsor with ID " + sponsorRequest.sponsorId + " not found");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Runner with ID " + id + " not found");
        }
    }

    public static class LapTimeRequest {
        private int lapTimeSeconds;

        public int getLapTimeSeconds() {
            return lapTimeSeconds;
        }

        public void setLapTimeSeconds(int lapTimeSeconds) {
            this.lapTimeSeconds = lapTimeSeconds;
        }
    }

    public static class SponsorRequest {
        private int sponsorId;

        public int getSponsorId() {
            return sponsorId;
        }

        public void setSponsorId(int sponsorId) {
            this.sponsorId = sponsorId;
        }
    }
}
