/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.words.util;

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Randomizer;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.util.ContentUtil;
import com.liferay.portlet.words.ScramblerException;
import com.liferay.util.jazzy.BasicSpellCheckListener;
import com.liferay.util.jazzy.InvalidWord;

import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.event.DefaultWordFinder;
import com.swabunga.spell.event.SpellChecker;
import com.swabunga.spell.event.StringWordTokenizer;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class WordsUtil {

	public static List<InvalidWord> checkSpelling(String text) {
		return _instance._checkSpelling(text);
	}

	public static List<String> getDictionaryList() {
		return _instance._getDictionaryList();
	}

	public static Set<String> getDictionarySet() {
		return _instance._getDictionarySet();
	}

	public static String getRandomWord() {
		return _instance._getRandomWord();
	}

	public static boolean isDictionaryWord(String word) {
		return _instance._isDictionaryWord(word);
	}

	public static String[] scramble(String word) throws ScramblerException {
		Scrambler scrambler = new Scrambler(word);

		return scrambler.scramble();
	}

	public static String[] unscramble(String word) throws ScramblerException {
		return _instance._unscramble(word);
	}

	private WordsUtil() {
		_dictionaryList = ListUtil.fromArray(StringUtil.split(
			ContentUtil.get("com/liferay/portlet/words/dependencies/words.txt"),
			"\n"));

		_dictionaryList = new UnmodifiableList<String>(_dictionaryList);

		_dictionarySet = new HashSet<String>(_dictionaryList.size());

		_dictionarySet.addAll(_dictionaryList);

		_dictionarySet = Collections.unmodifiableSet(_dictionarySet);

		try {
			_spellDictionary = new SpellDictionaryHashMap();

			String[] dics = new String[] {
				"center.dic", "centre.dic", "color.dic", "colour.dic",
				"eng_com.dic", "english.0", "english.1", "ise.dic", "ize.dic",
				"labeled.dic", "labelled.dic", "yse.dic", "yze.dic"
			};

			for (int i = 0; i < dics.length; i++) {
				_spellDictionary.addDictionary(new UnsyncStringReader(
					ContentUtil.get(
						"com/liferay/portlet/words/dependencies/" + dics[i])));
			}
		}
		catch (IOException ioe) {
			_log.error(ioe);
		}
	}

	private List<InvalidWord> _checkSpelling(String text) {
		SpellChecker checker = new SpellChecker(_spellDictionary);

		BasicSpellCheckListener listener = new BasicSpellCheckListener(text);

		checker.addSpellCheckListener(listener);

		checker.checkSpelling(
			new StringWordTokenizer(new DefaultWordFinder(text)));

		return listener.getInvalidWords();
	}

	private List<String> _getDictionaryList() {
		return _dictionaryList;
	}

	private Set<String> _getDictionarySet() {
		return _dictionarySet;
	}

	private String _getRandomWord() {
		int pos = Randomizer.getInstance().nextInt(_dictionaryList.size());

		return _dictionaryList.get(pos);
	}

	private boolean _isDictionaryWord(String word) {
		return _dictionarySet.contains(word);
	}

	private String[] _unscramble(String word) throws ScramblerException {
		List<String> validWords = new ArrayList<String>();

		String[] words = scramble(word);

		for (int i = 0; i < words.length; i++) {
			if (_dictionarySet.contains(words[i])) {
				validWords.add(words[i]);
			}
		}

		return validWords.toArray(new String[validWords.size()]);
	}

	private static Log _log = LogFactoryUtil.getLog(WordsUtil.class);

	private static WordsUtil _instance = new WordsUtil();

	private List<String> _dictionaryList;
	private Set<String> _dictionarySet;
	private SpellDictionaryHashMap _spellDictionary;

}