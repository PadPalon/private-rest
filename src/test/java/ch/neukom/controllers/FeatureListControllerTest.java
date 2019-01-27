package ch.neukom.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.testng.annotations.*;

import ch.neukom.controllers.FeatureListController.FeatureBean;
import org.mockito.Mockito;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static com.google.common.truth.Truth.*;

public class FeatureListControllerTest {
    @Test
    public void testFeatureList() {
        FeatureListController controller = new FeatureListController(List.of(new FakeFeatureController()));

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getScheme()).thenReturn("http");
        Mockito.when(request.getServerName()).thenReturn("testserver");
        Mockito.when(request.getServerPort()).thenReturn(8080);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        List<FeatureBean> featureBeans = controller.listFeatures();
        assertThat(featureBeans).contains(new FeatureBean("feature name", "feature documentation", "http://testserver:8080/path"));
    }

    @RequestMapping(path = "path")
    private static class FakeFeatureController implements FeatureController {
        @Override
        public String getFeatureName() {
            return "feature name";
        }

        @Override
        public String getDocumentation() {
            return "feature documentation";
        }
    }
}
