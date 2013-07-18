/**
 * Copyright 2013 Peergreen S.A.S. All rights reserved.
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.peergreen.tests.legacy.nexus;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenStrategyStage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.peergreen.tests.runner.HtmlTest;

/**
 * Test of the Nexus WebApplication
 * @author Florent Benoit
 */
@RunWith(Arquillian.class)
public class NexusTest extends HtmlTest {

    @Deployment
    public static WebArchive createDeployment() {
        // Change the default sonatype work directory
        MavenStrategyStage mavenStrategyStage = Maven.resolver().resolve("org.sonatype.nexus:nexus-webapp:war:2.5.1-01");
        WebArchive webArchive = mavenStrategyStage.withoutTransitivity().as(WebArchive.class)[0];

        // cleanup work directory
        String tmpDir = System.getProperty("java.io.tmpdir");
        String username = System.getProperty("user.name");
        File tmpFolder = new File(tmpDir, "peergreen-arquillian-nexus-work-directory".concat(String.valueOf(username.hashCode())));
        delete(tmpFolder);
        System.setProperty("plexus.nexus-work", tmpFolder.getPath());

        return webArchive;
    }

    @Test @RunAsClient
    public void testNexus(@ArquillianResource URL baseURL) throws IOException {
        System.out.println("Nexus URL = " + baseURL);


        URL indexURL = new URL(baseURL.toExternalForm() + "index.html");

        // Wait that nexus being initialized (up to 30 seconds)
        waitForStatus200(indexURL, 30000L);

        String content = getContent(indexURL);

        // Check the neux version is in the HTML page
        Assert.assertNotNull(content);
        Assert.assertTrue(content.contains("Sonatype Nexus&trade; 2.5.1-01"));

        // Loading UI code
        Assert.assertTrue(content.contains("<div>Loading Nexus UI...</div>"));

    }


}
