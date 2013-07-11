/**
 * Copyright 2013 Peergreen S.A.S. All rights reserved.
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.peergreen.tests.jpa.javaee.jpa20.basic;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.peergreen.tests.runner.ComponentTester;

/**
 * Test of the JPA component
 * @author Florent Benoit
 */
@RunWith(Arquillian.class)
public class BasicTest extends ComponentTester {

    @Deployment
    public static JavaArchive createMultipleDeployment() {
        Asset persistenceAsset = new ClassLoaderAsset("persistence.xml");

        JavaArchive javaArchive = ShrinkWrap.create(JavaArchive.class)
                                            .addClass(Customer.class)
                                            .addClass(Component.class)
                                            .addAsResource(persistenceAsset, "META-INF/persistence.xml");
        return javaArchive;
    }

    @Test @RunAsClient
    public void testRunner() {
        super.check();
    }



}
