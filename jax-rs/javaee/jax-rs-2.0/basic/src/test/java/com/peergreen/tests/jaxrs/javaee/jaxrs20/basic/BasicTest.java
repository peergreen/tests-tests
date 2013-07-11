/**
 * Copyright 2013 Peergreen S.A.S. All rights reserved.
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.peergreen.tests.jaxrs.javaee.jaxrs20.basic;

import java.io.IOException;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.peergreen.tests.runner.HtmlTest;

/**
 * Test of the JTA component
 * @author Florent Benoit
 */
@RunWith(Arquillian.class)
public class BasicTest extends HtmlTest {


    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                                           .addClass(MyPath.class)
                                           .addClass(MyApplication.class);
        return war;
    }

    @Test
    public void testUsers(@ArquillianResource URL baseURL) throws IOException {
        String adminUsersUrl = baseURL.toExternalForm().concat("admin/users");
        Assert.assertTrue(getContent(adminUsersUrl).contains("getUser is called"));
    }

    @Test
    public void testVipUsers(@ArquillianResource URL baseURL) throws IOException {
        String adminUsersUrl = baseURL.toExternalForm().concat("admin/users/vip");
        Assert.assertTrue(getContent(adminUsersUrl).contains("getUserVIP is called"));
    }


}
