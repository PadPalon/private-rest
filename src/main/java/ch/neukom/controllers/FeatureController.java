package ch.neukom.controllers;

/**
 * interface for features that should be listed in the root uri
 */
public interface FeatureController {
    String getFeatureName();
    default String getDocumentation() {
        return "";
    }
}
