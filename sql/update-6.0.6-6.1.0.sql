alter table AssetCategory add description STRING null;

alter table AssetEntry add layoutUuid VARCHAR(75) null;

alter table BlogsEntry add description STRING null;
alter table BlogsEntry add smallImage BOOLEAN;
alter table BlogsEntry add smallImageId VARCHAR(75) null;
alter table BlogsEntry add smallImageURL STRING null;

alter table BookmarksEntry add userName VARCHAR(75) null;
alter table BookmarksEntry add description VARCHAR(75) null;

COMMIT_TRANSACTION;

update BookmarksEntry set description = comments;
alter table BookmarksEntry drop column comments;

alter table BookmarksFolder add userName VARCHAR(75) null;

alter table CalEvent add location STRING null;

update ClassName_ set value = 'com.liferay.portal.model.UserPersonalSite' where value = 'com.liferay.portal.model.UserPersonalCommunity';

drop index IX_975996C0 on Company;

create table DDLRecord (
	uuid_ VARCHAR(75) null,
	recordId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	versionUserId LONG,
	versionUserName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	recordSetId LONG,
	version VARCHAR(75) null,
	displayIndex INTEGER
);

create table DDLRecordSet (
	uuid_ VARCHAR(75) null,
	recordSetId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	DDMStructureId LONG,
	recordSetKey VARCHAR(75) null,
	name STRING null,
	description STRING null,
	minDisplayRows INTEGER
);

create table DDMContent (
	uuid_ VARCHAR(75) null,
	contentId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null,
	description STRING null,
	xml TEXT null
);

create table DDMStorageLink (
	uuid_ VARCHAR(75) null,
	storageLinkId LONG not null primary key,
	classNameId LONG,
	classPK LONG,
	structureId LONG
);

create table DDMStructure (
	uuid_ VARCHAR(75) null,
	structureId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	structureKey VARCHAR(75) null,
	name STRING null,
	description STRING null,
	xsd TEXT null,
	storageType VARCHAR(75) null
);

create table DDMStructureLink (
	structureLinkId LONG not null primary key,
	classNameId LONG,
	classPK LONG,
	structureId LONG
);

create table DDMTemplate (
	uuid_ VARCHAR(75) null,
	templateId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	structureId LONG,
	name VARCHAR(75) null,
	description STRING null,
	type_ VARCHAR(75) null,
	language VARCHAR(75) null,
	script TEXT null
);

create table DLDocumentMetadataSet (
	uuid_ VARCHAR(75) null,
	documentMetadataSetId LONG not null primary key,
	classNameId LONG,
	classPK LONG,
	DDMStructureId LONG,
	documentTypeId LONG,
	fileVersionId LONG
);

create table DLDocumentType (
	documentTypeId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description STRING null
);

create table DLDocumentType_DDMStructure (
	documentTypeId LONG not null,
	structureId LONG not null,
	primary key (documentTypeId, structureId)
);

alter table DLFileEntry add repositoryId LONG;
alter table DLFileEntry add mimeType VARCHAR(75) null;
alter table DLFileEntry add documentTypeId LONG;

COMMIT_TRANSACTION;

update DLFileEntry set repositoryId = groupId;

drop index IX_CE705D48 on DLFileRank;
drop index IX_40B56512 on DLFileRank;
alter table DLFileRank add fileEntryId LONG;

drop index IX_55C736AC on DLFileShortcut;
drop index IX_346A0992 on DLFileShortcut;
alter table DLFileShortcut add repositoryId LONG;
alter table DLFileShortcut add toFileEntryId LONG;

COMMIT_TRANSACTION;

update DLFileShortcut set repositoryId = groupId;

drop index IX_B413F1EC on DLFileVersion;
drop index IX_94E784D2 on DLFileVersion;
drop index IX_2F8FED9C on DLFileVersion;
alter table DLFileVersion add repositoryId LONG;
alter table DLFileVersion add fileEntryId LONG;
alter table DLFileVersion add mimeType VARCHAR(75) null;
alter table DLFileVersion add documentTypeId LONG;

COMMIT_TRANSACTION;

update DLFileVersion set repositoryId = groupId;

alter table DLFolder add repositoryId LONG;
alter table DLFolder add mountPoint BOOLEAN;

COMMIT_TRANSACTION;

update DLFolder set repositoryId = groupId;

alter table Group_ add site BOOLEAN;

update Group_ set name = 'User Personal Site' where name = 'User Personal Community';
update Group_ set type_ = 3 where classNameId = (select classNameId from ClassName_ where value = 'com.liferay.portal.model.Organization');

alter table IGFolder add userName VARCHAR(75) null;

alter table IGImage add userName VARCHAR(75) null;

alter table JournalArticle add classNameId LONG null;
alter table JournalArticle add classPK LONG null;
alter table JournalArticle add layoutUuid VARCHAR(75) null;

COMMIT_TRANSACTION;

update JournalArticle set classNameId = 0;
update JournalArticle set classPK = 0;

drop index IX_FAD05595 on Layout;

alter table Layout add createDate DATE null;
alter table Layout add modifiedDate DATE null;
alter table Layout add keywords STRING null;
alter table Layout add robots STRING null;
alter table Layout drop column dlFolderId;

update Layout set createDate = CURRENT_TIMESTAMP;
update Layout set modifiedDate = CURRENT_TIMESTAMP;

COMMIT_TRANSACTION;

create table LayoutRevision (
	layoutRevisionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	layoutSetBranchId LONG,
	parentLayoutRevisionId LONG,
	head BOOLEAN,
	major BOOLEAN,
	variationName VARCHAR(75) null,
	plid LONG,
	privateLayout BOOLEAN,
	name STRING null,
	title STRING null,
	description STRING null,
	keywords STRING null,
	robots STRING null,
	typeSettings TEXT null,
	iconImage BOOLEAN,
	iconImageId LONG,
	themeId VARCHAR(75) null,
	colorSchemeId VARCHAR(75) null,
	wapThemeId VARCHAR(75) null,
	wapColorSchemeId VARCHAR(75) null,
	css STRING null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

drop index IX_5ABC2905 on LayoutSet;

create table LayoutSetBranch (
	layoutSetBranchId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	privateLayout BOOLEAN,
	name VARCHAR(75) null,
	description STRING null
);

alter table LayoutSetPrototype add uuid_ VARCHAR(75) null;

alter table MBCategory add displayStyle VARCHAR(75) null;

COMMIT_TRANSACTION;

update MBCategory set displayStyle = 'default';

alter table MBMailingList add allowAnonymous BOOLEAN;

alter table MBMessage add format VARCHAR(75) null;

COMMIT_TRANSACTION;

update MBMessage set format = 'bbcode';

alter table MBThread add companyId LONG;
alter table MBThread add rootMessageUserId LONG;

create table Repository (
	repositoryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	name VARCHAR(75) null,
	description STRING null,
	portletId VARCHAR(75) null,
	typeSettings TEXT null,
	dlFolderId LONG
);

create table RepositoryEntry (
	uuid_ VARCHAR(75) null,
	repositoryEntryId LONG not null primary key,
	groupId LONG,
	repositoryId LONG,
	mappedId VARCHAR(75) null
);

alter table ResourcePermission add ownerId LONG;

alter table SocialEquityLog add extraData VARCHAR(255) null;

update Role_ set name = 'Site Administrator' where name = 'Community Administrator';
update Role_ set name = 'Site Member' where name = 'Community Member';
update Role_ set name = 'Site Owner' where name = 'Community Owner';
update Role_ set name = 'Organization User' where name = 'Organization Member';

alter table User_ add status int;

COMMIT_TRANSACTION;

update User_ set status = 0;
update User_ set status = 5 where active_ = FALSE;

alter table User_ drop column active_;

create table UserGroups_Teams (
	userGroupId LONG not null,
	teamId LONG not null,
	primary key (userGroupId, teamId)
);

create table UserNotificationEvent (
	uuid_ VARCHAR(75) null,
	userNotificationEventId LONG not null primary key,
	companyId LONG,
	userId LONG,
	type_ VARCHAR(75) null,
	timestamp LONG,
	deliverBy LONG,
	payload TEXT null
);

create table VirtualHost (
	virtualHostId LONG not null primary key,
	companyId LONG,
	layoutSetId LONG,
	hostname VARCHAR(75) null
);

alter table WorkflowDefinitionLink add classPK LONG;