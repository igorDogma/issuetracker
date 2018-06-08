package com.axmor.db.entityes.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum Status {
    Created, InProgress, Resolved, Closed;

    private static final Map<String, Status> nameToStatus = new HashMap<>(Status.values().length);

    static {
        for (Enum status : Status.values()) {
            nameToStatus.put(status.name(), (Status) status);
        }
    }

    public static Status fromString(String stringValue) {
        return nameToStatus.get(stringValue);
    }

    public static Set<String> getStatusMap(){
        return Status.nameToStatus.keySet();
    }
}
