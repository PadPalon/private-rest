package ch.neukom.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static java.util.stream.Collectors.*;

/**
 * list all available features
 */
@RestController
@RequestMapping("")
public class FeatureListController {
    private final List<FeatureController> controllers;

    public FeatureListController(List<FeatureController> controllers) {
        this.controllers = controllers;
    }

    @GetMapping
    public List<FeatureBean> listFeatures() {
        return controllers.stream()
            .map(this::createFeatureBean)
            .collect(toList());
    }

    private FeatureBean createFeatureBean(FeatureController controller) {
        return new FeatureBean(
            controller.getFeatureName(),
            controller.getDocumentation(),
            buildUri(controller)
        );
    }

    private String buildUri(FeatureController controller) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri()
            .path(getPathOfController(controller))
            .build()
            .toUriString();
    }

    private String getPathOfController(FeatureController controller) {
        String[] paths = controller.getClass().getAnnotation(RequestMapping.class).path();
        if (paths.length > 0) {
            return paths[0];
        } else {
            throw new IllegalStateException("Controller has no path defined");
        }
    }

    public static class FeatureBean {
        private final String name;
        private final String documentation;
        private final String uri;

        public FeatureBean(String name, String documentation, String uri) {
            this.name = name;
            this.documentation = documentation;
            this.uri = uri;
        }

        public String getName() {
            return name;
        }

        public String getDocumentation() {
            return documentation;
        }

        public String getUri() {
            return uri;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            FeatureBean that = (FeatureBean) o;

            if (!name.equals(that.name)) return false;
            if (!documentation.equals(that.documentation)) return false;
            return uri.equals(that.uri);
        }

        @Override
        public int hashCode() {
            int result = name.hashCode();
            result = 31 * result + documentation.hashCode();
            result = 31 * result + uri.hashCode();
            return result;
        }
    }
}
