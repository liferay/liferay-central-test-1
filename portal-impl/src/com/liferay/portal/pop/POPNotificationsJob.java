/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.pop;

import com.liferay.portal.kernel.job.IntervalJob;
import com.liferay.portal.kernel.job.JobExecutionContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.Account;
import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.mail.MailEngine;

import java.util.Iterator;
import java.util.List;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message.RecipientType;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

/**
 * <a href="POPNotificationsJob.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class POPNotificationsJob implements IntervalJob {

	public static final long INTERVAL = GetterUtil.getLong(PropsUtil.get(
		PropsKeys.POP_SERVER_NOTIFICATIONS_INTERVAL)) * Time.MINUTE;

	public void execute(JobExecutionContext context) {
		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Executing");
			}

			pollPopServer();
		}
		catch (Exception e) {
			_log.error(e, e);

			_store = null;
			_inboxFolder = null;
		}
	}

	public long getInterval() {
		return INTERVAL;
	}

	protected String getEmailAddress(Address[] addresses) {
		if ((addresses == null) || (addresses.length == 0)) {
			return StringPool.BLANK;
		}

		InternetAddress internetAddress = (InternetAddress)addresses[0];

		return internetAddress.getAddress();
	}

	protected void initInboxFolder() throws Exception {
		if ((_inboxFolder == null) || !_inboxFolder.isOpen()) {
			initStore();

			Folder defaultFolder = _store.getDefaultFolder();

			Folder[] folders = defaultFolder.list();

			if (folders.length == 0) {
				throw new MessagingException("Inbox not found");
			}
			else {
				_inboxFolder = folders[0];

				_inboxFolder.open(Folder.READ_WRITE);
			}
		}
	}

	protected void initStore() throws Exception {
		if ((_store == null) || !_store.isConnected()) {
			Session session = MailEngine.getSession();

			String storeProtocol = GetterUtil.getString(
				session.getProperty("mail.store.protocol"));

			if (!storeProtocol.equals(Account.PROTOCOL_POPS)) {
				storeProtocol = Account.PROTOCOL_POP;
			}

			_store = session.getStore(storeProtocol);

			String prefix = "mail." + storeProtocol + ".";

			String host = session.getProperty(prefix + "host");

			String user = session.getProperty(prefix + "user");

			if (Validator.isNull(user)) {
				user = session.getProperty("mail.smtp.user");
			}

			String password = session.getProperty(prefix + "password");

			if (Validator.isNull(password)) {
				password = session.getProperty("mail.smtp.password");
			}

			_store.connect(host, user, password);
		}
	}

	protected void nostifyListeners(
			List<MessageListener> listeners, Message message)
		throws Exception {

		String from = getEmailAddress(message.getFrom());
		String recipient = getEmailAddress(
			message.getRecipients(RecipientType.TO));

		if (_log.isDebugEnabled()) {
			_log.debug("From " + from);
			_log.debug("Recipient " + recipient);
		}

		Iterator<MessageListener> itr = listeners.iterator();

		while (itr.hasNext()) {
			MessageListener messageListener = itr.next();

			try {
				if (messageListener.accept(from, recipient, message)) {
					messageListener.deliver(from, recipient, message);
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	protected void nostifyListeners(Message[] messages) throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Messages " + messages.length);
		}

		List<MessageListener> listeners = POPServerUtil.getListeners();

		for (int i = 0; i < messages.length; i++) {
			Message message = messages[i];

			if (_log.isDebugEnabled()) {
				_log.debug("Message " + message);
			}

			nostifyListeners(listeners, message);
		}
	}

	protected void pollPopServer() throws Exception {
		initInboxFolder();

		Message[] messages = _inboxFolder.getMessages();

		try {
			nostifyListeners(messages);
		}
		finally {
			if (_log.isDebugEnabled()) {
				_log.debug("Deleting messages");
			}

			_inboxFolder.setFlags(
				messages, new Flags(Flags.Flag.DELETED), true);

			_inboxFolder.close(true);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(POPNotificationsJob.class);

	private Store _store;
	private Folder _inboxFolder;

}