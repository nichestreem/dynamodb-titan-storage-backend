package com.amazon.titan.diskstorage.dynamodb;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by amcp on 2017/02/27.
 */
@RequiredArgsConstructor
public enum TestCombination {
    SINGLE_ITEM_DYNAMODB_LOCKING(BackendDataModel.SINGLE, false),
    SINGLE_ITEM_TITAN_LOCKING(BackendDataModel.SINGLE, true),
    MULTIPLE_ITEM_DYNAMODB_LOCKING(BackendDataModel.MULTI, false),
    MULTIPLE_ITEM_TITAN_LOCKING(BackendDataModel.MULTI, true);

    @Getter
    private final BackendDataModel dataModel;
    @Getter
    private final Boolean usingTitanLocking;

    public static final Collection<Object[]> PARAMETER_LIST =
            Lists.newArrayList(
                    SINGLE_ITEM_DYNAMODB_LOCKING,
                    SINGLE_ITEM_TITAN_LOCKING,
                    MULTIPLE_ITEM_DYNAMODB_LOCKING,
                    MULTIPLE_ITEM_TITAN_LOCKING).stream()
                    .map(Collections::singletonList)
                    .map(List::toArray)
                    .collect(Collectors.toList());

    public String toString() {
        return dataModel.getCamelCaseName() + (usingTitanLocking ? "Titan" : "DynamoDB") + "Locking";
    }
}
