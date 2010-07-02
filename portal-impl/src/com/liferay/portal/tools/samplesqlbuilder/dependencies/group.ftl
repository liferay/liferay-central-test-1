<#setting number_format = "0">

insert into Group_ values (${group.groupId}, ${companyId}, ${defaultUserId}, ${group.classNameId}, ${group.classPK}, 0, 0, '${group.name}', '', 0, '', '${group.friendlyURL}', TRUE);

${sampleSQLBuilder.insertSecurity("com.liferay.portal.model.Group", group.groupId)}

insert into LayoutSet values (${counter.get()}, ${group.groupId}, ${companyId}, TRUE, FALSE, 0, 'classic', '01', '', '', '', ${privateLayouts?size}, '', '', 0);
insert into LayoutSet values (${counter.get()}, ${group.groupId}, ${companyId}, FALSE, FALSE, 0, 'classic', '01', '', '', '', ${publicLayouts?size}, '', '', 0);

<#list privateLayouts as layout>
	insert into Layout values ('${portalUUIDUtil.generate()}', ${layout.plid}, ${group.groupId}, ${companyId}, TRUE, ${layout.getLayoutId()}, 0, '<?xml version="1.0"?>\n\n<root>\n<name>${layout.name}</name>\n</root>', '', '', 'portlet', '${layout.typeSettings}', FALSE, '${layout.friendlyURL}', FALSE, 0, '', '', '', '', '', 0, 0, 0);

	${sampleSQLBuilder.insertSecurity("com.liferay.portal.model.Layout", layout.plid)}
</#list>

<#list publicLayouts as layout>
	insert into Layout values ('${portalUUIDUtil.generate()}', ${layout.plid}, ${group.groupId}, ${companyId}, FALSE, ${layout.getLayoutId()}, 0, '<?xml version="1.0"?>\n\n<root>\n<name>${layout.name}</name>\n</root>', '', '', 'portlet', '${layout.typeSettings}', FALSE, '${layout.friendlyURL}', FALSE, 0, '', '', '', '', '', 0, 0, 0);

	${sampleSQLBuilder.insertSecurity("com.liferay.portal.model.Layout", layout.plid)}
</#list>