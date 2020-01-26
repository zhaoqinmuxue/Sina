package org.aoli.weibo.sinasdk.bean;

import java.io.Serializable;
import java.util.List;

public class StatusContents implements Serializable {
    private List<StatusContent> statuses;

    private int total_number;

    public List<StatusContent> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<StatusContent> statuses) {
        this.statuses = statuses;
    }

    public int getTotal_number() {
        return total_number;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }
}
