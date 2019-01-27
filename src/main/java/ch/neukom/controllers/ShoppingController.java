package ch.neukom.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create and write shopping lists
 * WIP shopping lists are only in-memory at the moment
 * TODO write tests
 */
@RestController
@RequestMapping("/shopping")
public class ShoppingController implements FeatureController {
    private Map<String, ShoppingList> shoppingLists = new HashMap<>();

    @GetMapping("/{uuid}")
    public ShoppingList get(@PathVariable("uuid") String uuid) {
        return shoppingLists.get(uuid);
    }

    @PostMapping("/{name}")
    public ShoppingList create(@PathVariable("name") String name) {
        ShoppingList shoppingList = new ShoppingList(name);
        shoppingLists.put(shoppingList.getUuid(), shoppingList);
        return shoppingList;
    }

    @PatchMapping("/{uuid}/{name}")
    public ShoppingList rename(@PathVariable("uuid") String uuid, @PathVariable("name") String name) {
        ShoppingList shoppingList = shoppingLists.get(uuid);
        if(shoppingList == null) {
            return new ShoppingList(name);
        } else {
            ShoppingList newList = new ShoppingList(name).addThings(shoppingList.getThings());
            shoppingLists.put(newList.getUuid(), newList);
            shoppingLists.put(uuid, newList);
            return newList;
        }
    }

    @DeleteMapping("/{uuid}}")
    public void remove(@PathVariable("uuid") String uuid) {
        shoppingLists.remove(uuid);
    }

    @PutMapping("/{uuid}/{thing}")
    public ShoppingList addThing(@PathVariable("uuid") String uuid, @PathVariable("thing") String thing) {
        ShoppingList shoppingList = shoppingLists.get(uuid);
        if(shoppingList == null) {
            return null;
        } else {
            return shoppingList.addThing(thing);
        }
    }

    @DeleteMapping("/{uuid}/{thing}")
    public ShoppingList removeThing(@PathVariable("uuid") String uuid, @PathVariable("thing") String thing) {
        ShoppingList shoppingList = shoppingLists.get(uuid);
        if(shoppingList == null) {
            return null;
        } else {
            return shoppingList.removeThing(thing);
        }
    }

    @Override
    public String getFeatureName() {
        return "Write shopping lists";
    }

    public static class ShoppingList {
        private final String name;
        private final List<String> things;
        private final String uuid;

        public ShoppingList(String name) {
            this.name = name;
            this.things = new ArrayList<>();
            this.uuid = UUID.randomUUID().toString();
        }

        public String getName() {
            return name;
        }

        public List<String> getThings() {
            return things;
        }

        public ShoppingList addThing(String thing) {
            things.add(thing);
            return this;
        }

        public ShoppingList addThings(List<String> things) {
            this.things.addAll(things);
            return this;
        }

        public ShoppingList removeThing(String thing) {
            things.remove(thing);
            return this;
        }

        public String getUuid() {
            return uuid;
        }
    }
}
