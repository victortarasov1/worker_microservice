package executor.service.model;


import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Scenario {
    private String id;
    private String name;
    private String site;
    private List<Step> steps = new ArrayList<>();

    private ScenarioReport report;

    public Scenario(String name, String site, List<Step> steps) {
        this.name = name;
        this.site = site;
        this.steps = steps;
    }

}