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
 * Test of the Jenkins WebApplication
 * @author Florent Benoit
 */
@RunWith(Arquillian.class)
public class JenkinsTest extends HtmlTest {

    @Deployment
    public static WebArchive createDeployment() {
        MavenStrategyStage mavenStrategyStage = Maven.resolver().loadPomFromClassLoaderResource("legacy-jenkins-pom.xml").resolve("org.jenkins-ci.main:jenkins-war:war:1.522");
        WebArchive webArchive = mavenStrategyStage.withoutTransitivity().as(WebArchive.class)[0];

        // cleanup Jenkins Home directory
        String tmpDir = System.getProperty("java.io.tmpdir");
        String username = System.getProperty("user.name");
        File tmpFolder = new File(tmpDir, "peergreen-arquillian-jenkins-home-directory".concat(String.valueOf(username.hashCode())));
        delete(tmpFolder);
        System.setProperty("JENKINS_HOME", tmpFolder.getPath());


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
