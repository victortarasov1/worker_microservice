package executor.service.model;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("scenarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Scenario {
    @Id
    private String id;
    private String name;
    private String site;
    @Reference
    private List<Step> steps;
    @Reference
    private ScenarioReport report;

    public Scenario(String name, String site, List<Step> steps) {
        this.name = name;
        this.site = site;
        this.steps = steps;
    }

}