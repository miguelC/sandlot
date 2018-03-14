/**
 *======================================================================================
 * SandlotAudit.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- SandlotAudit
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			May 27, 2015
 *  				Original development
 *  @author 		Miguel Curi
 *  @version 		1.0
 *  Description:  	Data Model object that represents an audit record for a camel event
 *                  such as MessageSentEvent or MessageFailedEvent
 *  
 *======================================================================================
 *	Copyright 2015, Sandlot Solutions. All rights reserved.
 *======================================================================================
 **/

// Package
package net.sandlotnow.interop.audit;

//Imports

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "interop_audit", indexes = { 
        @Index(name = "IDX_INTEROP_AUDIT_TIMESTAMP", columnList = "timestamp"),
        @Index(name = "IDX_INTEROP_AUDIT_EXCHANGE_ID", columnList = "exchange_id"),
        @Index(name = "IDX_INTEROP_AUDIT_SUBJECT_INFO", columnList = "subject_info")})
public class InteropAudit {

    @Id
    @Column(name = "id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "exchange_id", nullable = false)
    private String exchangeId;
    @Column(name = "breadcrumb_id")
    private String breadcrumbId;

    @Column(name = "timestamp", nullable = false)
    private Date timeStamp;

    @Column(name = "properties_json", columnDefinition = "text")
    private String propertiesJson;
    private String route;
    @Column(name = "from_endpoint")
    private String fromEndPoint;
    @Column(name = "to_endpoint")
    private String toEndPoint;
    @Column(name = "processing_time_millis", nullable = true)
    private long processingTimeMillis;
    private boolean failed;
    @Column(name = "exception_message", columnDefinition = "text", nullable = true)
    private String exceptionMessage;
    @Column(name = "exception_stacktrace", columnDefinition = "text", nullable = true)
    private String exceptionStackTrace;
    @Column(name = "exchange_pattern")
    private String exchangePattern;
    @Column(name = "camel_context")
    private String camelContext; 
    @Column(name = "event_name", nullable = false)   
    private String eventName;
    @Column(name = "sender")
    private String sender; 
    @Column(name = "receiver")
    private String receiver; 
    @Column(name = "subject_info", columnDefinition = "text", nullable = true)
    private String subjectInfo; 
    @Column(name = "transaction_type")
    private String transactionType; 
    @OneToMany(mappedBy = "audit", cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    @OrderBy("direction asc")
    @JsonIgnore
    private Set<InteropAuditMessage> messages;

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
    
    public String getCamelContext() {
        return camelContext;
    }

    public void setCamelContext(String camelContext) {
        this.camelContext = camelContext;
    }

    public String getExchangePattern() {
        return exchangePattern;
    }

    public void setExchangePattern(String exchangePattern) {
        this.exchangePattern = exchangePattern;
    }

    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }

    public String getBreadcrumbId() {
        return breadcrumbId;
    }

    public void setBreadcrumbId(String breadcrumbId) {
        this.breadcrumbId = breadcrumbId;
    }
    
    public String getExceptionStackTrace() {
        return exceptionStackTrace;
    }

    public void setExceptionStackTrace(String exceptionStackTrace) {
        this.exceptionStackTrace = exceptionStackTrace;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exception) {
        this.exceptionMessage = exception;
    }

    public boolean isFailed() {
        return failed;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    @JsonIgnore
    public Set<InteropAuditMessage> getMessages() {
        return messages;
    }

    @JsonProperty
    public void setMessages(Set<InteropAuditMessage> messages) {
        this.messages = messages;
    }

    public long getProcessingTimeMillis() {
        return processingTimeMillis;
    }

    public void setProcessingTimeMillis(long processingTimeMillis) {
        this.processingTimeMillis = processingTimeMillis;
    }

    public String getToEndPoint() {
        return toEndPoint;
    }

    public void setToEndPoint(String toEndPoint) {
        this.toEndPoint = toEndPoint;
    }
    
    public String getFromEndPoint() {
        return fromEndPoint;
    }

    public void setFromEndPoint(String fromEndPoint) {
        this.fromEndPoint = fromEndPoint;
    }

    public InteropAudit() {
    }

    public InteropAudit(Long id, String message) {
        this.id = id;
        this.propertiesJson = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPropertiesJson() {
        return propertiesJson;
    }

    public void setPropertiesJson(String properties_json) {
        this.propertiesJson = properties_json;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    
    public String getSubjectInfo() {
        return subjectInfo;
    }

    public void setSubjectInfo(String subjectInfo) {
        this.subjectInfo = subjectInfo;
    }
    
    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }


    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    @Override
    public String toString() {
        return "SandlotAudit [id=" + id + ", message=" + propertiesJson + "]";
    }

    public void addAuditMessage(InteropAuditMessage message){
        if(this.messages == null){
            messages = new HashSet<InteropAuditMessage>();            
        }
        messages.add(message);
    }
}
