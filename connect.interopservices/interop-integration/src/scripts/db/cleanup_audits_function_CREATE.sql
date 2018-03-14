CREATE OR REPLACE FUNCTION cleanup_audits_function()
RETURNS void
LANGUAGE sql
AS $body$
	delete from interop_audit_message where audit_id in (
	select id from interop_audit where timestamp < now() - interval '5 days');

	delete from interop_audit where timestamp < now() - interval '5 days';
$body$;