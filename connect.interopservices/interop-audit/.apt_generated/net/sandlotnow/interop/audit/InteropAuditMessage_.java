package net.sandlotnow.interop.audit;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(InteropAuditMessage.class)
public abstract class InteropAuditMessage_ {

	public static volatile SingularAttribute<InteropAuditMessage, String> messageHeadersJson;
	public static volatile SingularAttribute<InteropAuditMessage, String> messageBodyType;
	public static volatile SingularAttribute<InteropAuditMessage, String> messageBody;
	public static volatile SingularAttribute<InteropAuditMessage, InteropAudit> audit;
	public static volatile SingularAttribute<InteropAuditMessage, Long> id;
	public static volatile SingularAttribute<InteropAuditMessage, String> direction;

}

