<#assign totalMBThreadCount = maxMBCategoryCount * maxMBThreadCount>
<#assign totalMBMessageCount = totalMBThreadCount * maxMBMessageCount>

<#assign categoryCounterOffset = maxGroupCount + ((groupId - 1) * (maxMBCategoryCount + totalMBThreadCount + totalMBMessageCount))>

<#if (maxMBCategoryCount > 0)>
	<#list 1..maxMBCategoryCount as mbCategoryCount>
		<#assign categoryId = categoryCounterOffset + mbCategoryCount>

		<#assign mbCategory = dataFactory.addMBCategory(categoryId, groupId, companyId, firstUserId, "Test Category " + mbCategoryCount, "This is a test category " + mbCategoryCount + ".", maxMBThreadCount, maxMBThreadCount * maxMBMessageCount)>

		${sampleSQLBuilder.insertMBCategory(mbCategory)}

		<#if (maxMBThreadCount > 0) && (maxMBMessageCount > 0)>
			<#assign threadCounterOffset = categoryCounterOffset + maxMBCategoryCount + ((mbCategoryCount - 1) * maxMBThreadCount)>

			<#list 1..maxMBThreadCount as mbThreadCount>
				<#assign messageCounterOffset = categoryCounterOffset + maxMBCategoryCount + totalMBThreadCount + ((mbCategoryCount - 1) * maxMBThreadCount * maxMBMessageCount) + ((mbThreadCount - 1) * maxMBMessageCount)>

				<#assign threadId = threadCounterOffset + mbThreadCount>
				<#assign parentMessageId = 0>

				<#list 1..maxMBMessageCount as mbMessageCount>
					<#assign mbMessageCounterIncrement = mbMessageCounter.increment()>

					<#assign messageId = messageCounterOffset + mbMessageCount>

					<#assign mbMessage = dataFactory.addMBMessage(messageId, mbCategory.groupId, firstUserId, 0, 0, categoryId, threadId, parentMessageId, "Test Message " + mbMessageCount, "This is a test message " + mbMessageCount + ".")>

					${sampleSQLBuilder.insertMBMessage(mbMessage)}

					<#if (mbMessageCount_index = 0)>
						<#assign parentMessageId = mbMessage.messageId>
						<#assign rootMessageId = mbMessage.messageId>
					</#if>
				</#list>

				<#assign mbThread = dataFactory.addMBThread(threadId, mbCategory.groupId, categoryId, rootMessageId, maxMBCategoryCount, firstUserId)>

				${sampleSQLBuilder.insertMBThread(mbThread)}

				${mbMessagesCsvWriter.write(categoryId + "," + threadId + "," + rootMessageId + ",")}

				<#if (mbMessageCounter.value < (maxGroupCount * totalMBMessageCount))>
					${mbMessagesCsvWriter.write("\n")}
				</#if>
			</#list>
		</#if>
	</#list>
</#if>