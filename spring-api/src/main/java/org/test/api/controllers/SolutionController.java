package org.test.api.controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.test.api.model.Solution;
import org.test.api.repositories.SolutionRepository;

import lombok.Data;

@RestController
@RequestMapping("/solution")
public class SolutionController {

    private static final Comparator<? super Entry<String, List<String>>> comparator = new Comparator<Entry<String, List<String>>>() {

        @Override
        public int compare(Entry<String, List<String>> o1, Entry<String, List<String>> o2) {
            return o2.getValue().size() - o1.getValue().size();
        }

    };
    @Autowired
    private SolutionRepository repository;

    @CrossOrigin("*")
    @PostMapping
    public Solution submit(@RequestBody Solution message) {
        return repository.save(message);
    }

    @GetMapping("top3")
    @CrossOrigin("*")
    public List<Top3Report> getTop3Committed() {
        Map<String, List<String>> result = new HashMap<>();
        repository.findBySuccessOrderByName(true).stream().forEach(s -> {
            List<String> tasks = result.get(s.getName());
            if (tasks == null) {
                tasks = new ArrayList<>();
                result.put(s.getName(), tasks);
            }
            tasks.add(s.getTask().getName());
        });
        int size = result.size() > 3 ? 3 : result.size();
        List<Top3Report> report = new ArrayList<Top3Report>();
        Iterator<Entry<String, List<String>>> iterator = result.entrySet().stream().sorted(comparator).iterator();
        int count = 0;
        Entry<String, List<String>> last = null;
        while (iterator.hasNext()) {
            Entry<String, List<String>> cur = iterator.next();
            if (last == null) {
                last = cur;
            } else {
                if (last.getValue().size() != cur.getValue().size()) {
                    count++;
                    if (count > 3)
                        break;
                }
            }
            report.add(createTop3Report(cur));
        }
        return report;
    }

    private static Top3Report createTop3Report(Entry<String, List<String>> next) {
        Top3Report report = new Top3Report();
        report.name = next.getKey();
        report.count = next.getValue().size();
        report.tasks = next.getValue().stream().sorted().collect(Collectors.toSet()).toArray(new String[0]);
        return report;
    }

    @Data
    static class Top3Report {

        private String name;
        private int count;
        private String[] tasks;
    }
}
