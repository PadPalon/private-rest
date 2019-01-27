package ch.neukom.controllers;

import java.util.List;

import org.testng.annotations.*;

import com.google.common.collect.ImmutableList;

import static com.google.common.truth.Truth.*;

public class RandomChoiceControllerTest {

    private static final RandomChoiceController CONTROLLER = new RandomChoiceController();

    @Test(invocationCount = 100)
    public void testRandomChoice() {
        List<String> choices = ImmutableList.of("First", "Second", "Third", "Fourth", "Fifth");
        String choice = CONTROLLER.choose(String.join(",", choices));
        assertThat(choices).contains(choice);
    }

    @Test
    public void testEmptyChoices() {
        List<String> choices = ImmutableList.of(",", " ", "\t", "\n", "\n Actual choice\t ");
        String choice = CONTROLLER.choose(String.join(",", choices));
        assertThat(choice).isEqualTo("Actual choice");
    }
}
