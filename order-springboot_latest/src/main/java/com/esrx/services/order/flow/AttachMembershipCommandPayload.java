package com.esrx.services.order.flow;

public class AttachMembershipCommandPayload {

    private String refId;
    private String patientNumber;
    private String patientName;

    public String getRefId() {
        return refId;
    }

    public AttachMembershipCommandPayload setRefId(String refId) {
        this.refId = refId;
        return this;
    }

    public String getPatientNumber() {
        return patientNumber;
    }

    public AttachMembershipCommandPayload setPatientNumber(String patientNumber) {
        this.patientNumber = patientNumber;
        return this;
    }

    public String getPatientName() {
        return patientName;
    }

    public AttachMembershipCommandPayload setPatientName(String patientName) {
        this.patientName = patientName;
        return this;
    }
}
