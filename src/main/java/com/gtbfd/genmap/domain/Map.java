package com.gtbfd.genmap.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;

@Entity
@Builder
public class Map {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String processNumber;
    private String pbDocProcess;
    private String sector;
    private String topic;

    public Map() {
    }

    public Map(Long id, String processNumber, String pbDocProcess, String sector, String topic) {
        this.id = id;
        this.processNumber = processNumber;
        this.pbDocProcess = pbDocProcess;
        this.sector = sector;
        this.topic = topic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcessNumber() {
        return processNumber;
    }

    public void setProcessNumber(String processNumber) {
        this.processNumber = processNumber;
    }

    public String getPbDocProcess() {
        return pbDocProcess;
    }

    public void setPbDocProcess(String pbDocProcess) {
        this.pbDocProcess = pbDocProcess;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "Map{" +
                "id=" + id +
                ", processNumber='" + processNumber + '\'' +
                ", pbDocProcess='" + pbDocProcess + '\'' +
                ", sector='" + sector + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }
}
