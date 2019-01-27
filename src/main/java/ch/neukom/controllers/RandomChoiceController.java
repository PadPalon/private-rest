package ch.neukom.controllers;

import java.util.List;
import java.util.Random;

import com.google.common.base.Splitter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * a helper to make a random choice
 */
@RestController
public class RandomChoiceController {
    @RequestMapping("/choicehelper")
    public String choose(@RequestParam("choices") String choices) {
        List<String> choiceIterable = Splitter.on(',').trimResults().omitEmptyStrings().splitToList(choices);
        return choiceIterable.get(new Random().nextInt(choiceIterable.size()));
    }
}
