/*
 * Copyright 2014-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.amazon.titan.diskstorage.dynamodb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.thinkaurelius.titan.diskstorage.configuration.ModifiableConfiguration;
import org.junit.AfterClass;

import com.amazon.titan.TestGraphUtil;
import com.thinkaurelius.titan.diskstorage.BackendException;
import com.thinkaurelius.titan.diskstorage.MultiWriteKeyColumnValueStoreTest;
import com.thinkaurelius.titan.diskstorage.configuration.BasicConfiguration;
import com.thinkaurelius.titan.diskstorage.configuration.WriteConfiguration;
import com.thinkaurelius.titan.diskstorage.keycolumnvalue.KeyColumnValueStoreManager;
import com.thinkaurelius.titan.graphdb.configuration.GraphDatabaseConfiguration;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
*
* @author Alexander Patrikalakis
*
*/
@RunWith(Parameterized.class)
public class DynamoDBMultiWriteStoreTest extends MultiWriteKeyColumnValueStoreTest {

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return TestCombination.PARAMETER_LIST;
    }
    private final TestCombination combination;
    public DynamoDBMultiWriteStoreTest(TestCombination combination) {
        this.combination = combination;
    }

    @Override
    public KeyColumnValueStoreManager openStorageManager() throws BackendException {
        final List<String> storeNames = new ArrayList<>(2);
        storeNames.add("testStore1");
        storeNames.add("testStore2");
        final WriteConfiguration wc = TestGraphUtil.instance().getStoreConfig(combination.getDataModel(), storeNames);
        final ModifiableConfiguration config = new ModifiableConfiguration(GraphDatabaseConfiguration.ROOT_NS, wc,
                BasicConfiguration.Restriction.NONE);
        config.set(Constants.DYNAMODB_USE_TITAN_LOCKING, combination.getUsingTitanLocking());

        return new DynamoDBStoreManager(config);
    }

    @AfterClass
    public static void cleanUpTables() throws Exception {
        TestGraphUtil.instance().cleanUpTables();
    }
}
