<#include "macro.ftl">

insert into Layout values ('${layout.uuid}', ${layout.plid}, ${layout.groupId}, ${layout.companyId}, '${dataFactory.getDateString(layout.createDate)}', '${dataFactory.getDateString(layout.modifiedDate)}', ${layout.privateLayout?string}, ${layout.layoutId}, ${layout.parentLayoutId}, '${layout.name}', '${layout.title}', '${layout.description}', '${layout.keywords}', '${layout.robots}', '${layout.type}', '${layout.typeSettings}', ${layout.hidden?string}, '${layout.friendlyURL}', ${layout.iconImage?string}, ${layout.iconImageId}, '${layout.themeId}', '${layout.colorSchemeId}', '${layout.wapThemeId}', '${layout.wapColorSchemeId}', '${layout.css}', ${layout.priority}, '${layout.layoutPrototypeUuid}', ${layout.layoutPrototypeLinkEnabled?string}, '${layout.sourcePrototypeLayoutUuid}');

${sampleSQLBuilder.insertResourcePermission("com.liferay.portal.model.Layout", stringUtil.valueOf(layout.plid))}

${sampleSQLBuilder.insertMBDiscussion(layout.groupId, dataFactory.layoutClassNameId, layout.plid, counter.get(), counter.get(), 0)}