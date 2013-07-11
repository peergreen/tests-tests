/**
 * Copyright 2013 Peergreen S.A.S. All rights reserved.
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.peergreen.tests.jta.javaee.jta11.basic;

import static org.testng.Assert.assertNotNull;

import javax.annotation.Resource;
import javax.transaction.UserTransaction;

import org.testng.annotations.Test;

/**
 * Defines the component that will be tested
 * @author Florent Benoit
 */
public class Component {
    @Resource
    private UserTransaction userTransaction;

    private UserTransaction setterUserTransaction;

    @SuppressWarnings("unused")
    private void setTx(UserTransaction setterUserTransaction) {
        this.setterUserTransaction = setterUserTransaction;
    }


    @Test
    public void testInjectionFieldUserTransaction() throws Exception {
            assertNotNull(userTransaction);
    }


    @Test
    public void testInjectionSetterUserTransaction() throws Exception {
            assertNotNull(setterUserTransaction);
    }


}
