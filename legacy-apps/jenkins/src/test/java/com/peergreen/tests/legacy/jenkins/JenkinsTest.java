/**
 * Copyright 2013 Peergreen S.A.S. All rights reserved.
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.peergreen.tests.legacy.jenkins;

import java.io.IOException;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.peergreen.tests.runner.HtmlTest;

/**
 * Test of the Jenkins WebApplication
 * @author Florent Benoit
 */
@RunWith(Arquillian.class)
public class JenkinsTest extends HtmlTest {

    @Deployment
    public static WebArchive createDeployment() {

        MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).goOffline();
        WebArchive webArchive = resolver.artifact("org.jenkins-ci.main:jenkins-war:war:1.522").resolveAs(WebArchive.class).iterator().next();
        return webArchive;
    }

    @Test @RunAsClient
    public void testJenkins(@ArquillianResource URL baseURL) throws IOException {
        // Wait that jenkins being initialized (up to 30 seconds)
        waitForStatus200(baseURL, 30000L);

        String content = getContent(baseURL);

        // Check the jenkins version is in the HTML page
        Assert.assertNotNull(content);
        Assert.assertTrue(content.contains(">Jenkins ver. 1.522</a>"));

        // Able to start a new job
        Assert.assertTrue(content.contains("new-package.png"));




    }

}
