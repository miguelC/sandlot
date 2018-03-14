/**
 *======================================================================================
 * SandlotAuditMessage.java
 *======================================================================================
 *
 *	Definitions for class:
 *		- SandlotAuditMessage
 *
 *======================================================================================
 *	Modification History:
 *======================================================================================
 *
 *  Date:			Jun 1, 2015
 *  				Original development
 *  @author 		Miguel Curi
 *  @version 		1.0
 *  Description:  	Data Model object that represents an audit message related to an 
 *                  audit record
 *  
 *======================================================================================
 *	Copyright 2015, Sandlot Solutions. All rights reserved.
 *======================================================================================
 **/

// Package
package net.sandlotnow.interop.audit;

//Imports
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "interop_audit_message", indexes = { 
        @Index(name = "IDX_INTEROP_AUDIT_MESSAGE_AUDITID", columnList = "audit_id")})
public class InteropAuditMessage {

    public static final String DIRECTION_IN = "In";
    public static final String DIRECTION_OUT = "Out";
    
    @Id
    @Column(name = "id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "audit_id")
    private InteropAudit audit;

    private String direction;

    @Column(name = "message_body", columnDefinition = "text")
    private String messageBody;
    @Column(name = "message_headers_json", columnDefinition = "text")
    private String messageHeadersJson;

    @Column(name = "message_body_type")
    private String messageBodyType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InteropAudit getAudit() {
        return audit;
    }

    public void setAudit(InteropAudit audit) {
        this.audit = audit;
    }
    
    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getMessageHeadersJson() {
        return messageHeadersJson;
    }

    public void setMessageHeadersJson(String messageHeadersJson) {
        this.messageHeadersJson = messageHeadersJson;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getMessageBodyType() {
        return messageBodyType;
    }

    public void setMessageBodyType(String messageBodyType) {
        this.messageBodyType = messageBodyType;
    }
}
