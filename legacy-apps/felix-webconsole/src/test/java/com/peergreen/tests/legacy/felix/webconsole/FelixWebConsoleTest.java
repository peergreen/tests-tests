/**
 * Copyright 2013 Peergreen S.A.S. All rights reserved.
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.peergreen.tests.legacy.felix.webconsole;

import java.io.IOException;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.peergreen.tests.runner.HtmlTest;

/**
 * Test of the Felix WebConsole component
 * @author Florent Benoit
 */
@RunWith(Arquillian.class)
public class FelixWebConsoleTest extends HtmlTest {

    @Deployment
    public static JavaArchive createDeployment() {

        MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class);

        JavaArchive javaArchive = resolver.artifact("org.apache.felix:org.apache.felix.webconsole:jar:all:4.2.0").resolveAs(JavaArchive.class).iterator().next();
        return javaArchive;
    }

    @Test
    public void testURL(/*Not a .war so no arquillian resource yet : @ArquillianResource URL baseURL*/) throws IOException {

        URL felixURL = new URL("http://localhost:9000/system/console");
        URL felixBundlesListURL = new URL("http://localhost:9000/system/console/status-Bundlelist");

        // wait for 10s
        waitForStatus200(felixURL, 10000L, "admin", "admin");

        String content = getContent(felixBundlesListURL, "admin", "admin");

        // check that we've the deployment bundle
        Assert.assertTrue(content.contains("com.peergreen.deployment.internal"));

        // check that we've the iPOJO bundle
        Assert.assertTrue(content.contains("org.apache.felix.ipojo&nbsp;"));

        // check that we've the shelbie bundle
        Assert.assertTrue(content.contains("org.ow2.shelbie.core&nbsp;("));
    }

}
