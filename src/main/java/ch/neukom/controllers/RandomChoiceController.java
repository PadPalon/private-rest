package ch.neukom.controllers;

import java.util.List;
import java.util.Random;

import com.google.common.base.Splitter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * a helper to make a random choice
 */
@RestController
@RequestMapping(path = "/choicehelper")
public class RandomChoiceController implements FeatureController {
    @GetMapping
    public String choose(@RequestParam("choices") String choices) {
        List<String> choiceIterable = Splitter.on(',').trimResults().omitEmptyStrings().splitToList(choices);
        return choiceIterable.get(new Random().nextInt(choiceIterable.size()));
    }

    @Override
    public String getFeatureName() {
        return "Make a random choice";
    }

    @Override
    public String getDocumentation() {
        return "Pass options as a comma-separated string through 'choices' parameter";
    }
}
