package net.sandlotnow.interop.audit;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(InteropAudit.class)
public abstract class InteropAudit_ {

	public static volatile SingularAttribute<InteropAudit, String> toEndPoint;
	public static volatile SingularAttribute<InteropAudit, String> receiver;
	public static volatile SingularAttribute<InteropAudit, String> fromEndPoint;
	public static volatile SingularAttribute<InteropAudit, String> exchangePattern;
	public static volatile SingularAttribute<InteropAudit, String> propertiesJson;
	public static volatile SingularAttribute<InteropAudit, Boolean> failed;
	public static volatile SingularAttribute<InteropAudit, String> exchangeId;
	public static volatile SingularAttribute<InteropAudit, Date> timeStamp;
	public static volatile SingularAttribute<InteropAudit, String> transactionType;
	public static volatile SingularAttribute<InteropAudit, Long> processingTimeMillis;
	public static volatile SingularAttribute<InteropAudit, String> route;
	public static volatile SingularAttribute<InteropAudit, String> breadcrumbId;
	public static volatile SingularAttribute<InteropAudit, String> sender;
	public static volatile SingularAttribute<InteropAudit, String> camelContext;
	public static volatile SingularAttribute<InteropAudit, String> eventName;
	public static volatile SetAttribute<InteropAudit, InteropAuditMessage> messages;
	public static volatile SingularAttribute<InteropAudit, Long> id;
	public static volatile SingularAttribute<InteropAudit, String> exceptionStackTrace;
	public static volatile SingularAttribute<InteropAudit, String> subjectInfo;
	public static volatile SingularAttribute<InteropAudit, String> exceptionMessage;

}

