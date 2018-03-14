package net.sandlotnow.config.interop

import java.text.MessageFormat;

class InteropServer {
	
	static final String SERVICE_GET_AUDIT_BYDATETIMES = '/audits/auditService/audit/startTime/{0}/stopTime/{1}/status/{2}'
	static final String SERVICE_GET_AUDIT_BYID = '/audits/auditService/audit/{0}'
	static final String SERVICE_GET_AUDIT_BYEXCHANGEID = '/audits/auditService/audits/exchange/{0}'
	static final String SERVICE_GET_AUDIT_MESSAGE_BYAUDITID = '/audits/auditService/audit/messages/audit/{0}'
	static final String SERVICE_GET_AUDIT_MESSAGE_BYID = '/audits/auditService/audit/message/{0}'
	static final String SERVICE_GET_AUDIT_SEARCH = '/audits/auditService/audit/search'
	
	static final String SERVICE_GET_EDGE_SYSTEM_LIST = '/admin/configservice/ihe/edgesystems'
	static final String SERVICE_GET_EDGE_SYSTEM_LIST_EAGER = '/admin/configservice/ihe/edgesystems/eager'
	static final String SERVICE_GET_EDGE_SYSTEM_BYID = '/admin/configservice/ihe/edgesystem/{0}'
	static final String SERVICE_GET_EDGE_SYSTEM_BYID_LAZY = '/admin/configservice/ihe/edgesystem/{0}/lazy'
	static final String SERVICE_GET_EDGE_ORGANIZATION_LIST = '/admin/configservice/ihe/organizations'
	static final String SERVICE_GET_EDGE_ORGANIZATION_BYID = '/admin/configservice/ihe/organization/{0}'
	static final String SERVICE_GET_EDGE_ORGANIZATION_SAVE = '/admin/configservice/ihe/organization/save'
	static final String SERVICE_GET_EDGE_ENDPOINT_SAVE = '/admin/configservice/ihe/edgesystem/endpoint/save'
	static final String SERVICE_GET_EDGE_TRANSACTIONS = '/admin/configservice/ihe/transactions'
	static final String SERVICE_GET_EDGE_SYSTEM_SAVE = '/admin/configservice/ihe/edgesystem/save'
	static final String SERVICE_GET_EDGE_SYSTEM_DEVICE_TYPES = '/admin/configservice/ihe/edgesystem/device/types'
	
	
	String id
    String name
	String description
    String baseUri
	
	/**
	 * Audits Service URLs
	 */
	
	String getServiceUrlAuditByDateTimes(String fromDate, String toDate , String status){
		return baseUri + MessageFormat.format(SERVICE_GET_AUDIT_BYDATETIMES, fromDate, toDate , status)
	}
	
	String getServiceUrlAuditById(String auditId){
		return baseUri + MessageFormat.format(SERVICE_GET_AUDIT_BYID, auditId)
	}
	
	String getServiceUrlAuditByExchangeId(String exchangeId){
		return baseUri + MessageFormat.format(SERVICE_GET_AUDIT_BYEXCHANGEID, exchangeId)
	}
	
	String getServiceUrlAuditMessageByAuditId(String auditId){
		return baseUri + MessageFormat.format(SERVICE_GET_AUDIT_MESSAGE_BYAUDITID, auditId)
	}
	
	String getServiceUrlAuditMessageById(String auditMessageId){
		return baseUri + MessageFormat.format(SERVICE_GET_AUDIT_MESSAGE_BYID, auditMessageId)
	}
   
	/**
	 * Edge Systems service URLs
	 */
	
	String getServiceUrlEdgeSystemList(){
		return baseUri + SERVICE_GET_EDGE_SYSTEM_LIST
	}
	
	String getServiceUrlEdgeSystemListEager(){
		return baseUri + SERVICE_GET_EDGE_SYSTEM_LIST_EAGER
	}
	
	String getServiceUrlEdgeSystemById(String edgeId){
		return baseUri  + MessageFormat.format(SERVICE_GET_EDGE_SYSTEM_BYID, edgeId)
	}
	
	String getServiceUrlEdgeSystemByIdLazy(String edgeId){
		return baseUri  + MessageFormat.format(SERVICE_GET_EDGE_SYSTEM_BYID_LAZY, edgeId)
	}
	
	String getServiceUrlEdgeOrganizationList(){
		return baseUri + SERVICE_GET_EDGE_ORGANIZATION_LIST
	}
	
	String getServiceUrlEdgeOrganizationById(String edgeOrgId){
		return baseUri  + MessageFormat.format(SERVICE_GET_EDGE_ORGANIZATION_BYID, edgeOrgId)
	}
	
	String getServiceUrlEdgeOrganizationSave(){
		return baseUri  + SERVICE_GET_EDGE_ORGANIZATION_SAVE
	}
	
	String getServiceUrlEdgeEndpointSave(){
		return baseUri  + SERVICE_GET_EDGE_ENDPOINT_SAVE
	}
	
	String getServiceUrlEdgeTransaction() {
		
		return baseUri  + SERVICE_GET_EDGE_TRANSACTIONS
	}
	
	String getServiceUrlEdgeSystemSave() {
		
		return baseUri  + SERVICE_GET_EDGE_SYSTEM_SAVE
	}
	
	String getSericeUrlEdgeSystemDevice() {
		
		return baseUri  + SERVICE_GET_EDGE_SYSTEM_DEVICE_TYPES
	}
	
	String getAuditSearch() {
		
		return baseUri  + SERVICE_GET_AUDIT_SEARCH
	}
	
}
