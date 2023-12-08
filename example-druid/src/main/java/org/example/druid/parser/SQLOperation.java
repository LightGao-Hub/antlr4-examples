package org.example.druid.parser;

public enum SQLOperation {
    SELECT("SELECT"),
    INSERT("INSERT"),
    UPDATE("UPDATE"),
    DELETE("DELETE"),
    ALTER("ALTER"),
    DROP("DROP"),
    CREATE("CREATE");

    private final String operation;

    SQLOperation(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }

    public static SQLOperation fromString(String text) {
        for (SQLOperation operation : SQLOperation.values()) {
            if (operation.operation.equalsIgnoreCase(text)) {
                return operation;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}

