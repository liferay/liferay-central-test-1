create table Vocabulary (
	vocabularyId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description VARCHAR(75) null,
	folksonomy BOOLEAN
);

alter table TagsEntry add groupId LONG;
alter table TagsEntry add vocabularyId LONG;
alter table TagsEntry add parentEntryId LONG;
