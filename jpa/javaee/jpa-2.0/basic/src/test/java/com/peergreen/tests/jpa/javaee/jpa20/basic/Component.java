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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.testng.annotations.Test;

/**
 * Defines the component that will be tested
 * @author Florent Benoit
 */
public class Component {

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    private UserTransaction userTransaction;

    @Test
    public void testFindEntity() throws Exception {
        userTransaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");

        if (userTransaction != null) {
            userTransaction.begin();
        }
        try {
            Customer customer = new Customer();
            customer.setName("Florent");
            customer.setId(1L);

            entityManager.persist(customer);
            entityManager.flush();
            Customer found = entityManager.find(Customer.class, 1L);
            assertNotNull(found);
            assertEquals(found.getName(), customer.getName());
            entityManager.remove(found);
        } finally {
            if (userTransaction != null) {
                userTransaction.commit();
            }
        }
    }


    @Test
    public void testMetamodel() throws Exception {
        entityManager.getMetamodel();
    }




}
