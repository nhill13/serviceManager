package record;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Record {

    private String RecordId;
    private String description;
    private Operator assignee = null;

    private int priority;
    private int impact;

    private Date createdAt = new Date();

    private List<String> RelatedRecords = new ArrayList<>();

    public Record() {

    }

}