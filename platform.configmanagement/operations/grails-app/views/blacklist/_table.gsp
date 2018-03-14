<table>
	<thead>
		<tr>
			<g:sortableColumn property="organizationName"
				title="${message(code: 'blackList.name.label', default: 'BlackList organization Name')}" />
			<g:sortableColumn property="organizationId"
				title="${message(code: 'blackList.id.label', default: 'BlackList organization ID')}" />

		</tr>
	</thead>
	<tbody>
		<g:each in="${blackLisOrganizationList}" status="i"
			var="blackLisOrganizationListInstance">
			<tr>
				<td><b><g:link action="show"
							id="${blackLisOrganizationListInstance.id}">
							${fieldValue(bean: blackLisOrganizationListInstance, field: "organizationId")}
						</g:link></b></td>
				<td>
					${fieldValue(bean: blackLisOrganizationListInstance, field: "organizationId")}
				</td>
			</tr>
		</g:each>
	</tbody>
</table>