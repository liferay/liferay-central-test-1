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

package com.liferay.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class UniqueList<E> extends ArrayList<E> {

	public UniqueList() {
		super();
	}

	public boolean add(E e) {
		if (!contains(e)) {
			return super.add(e);
		}
		else {
			return false;
		}
	}

	public void add(int index, E e) {
		if (!contains(e)) {
			super.add(index, e);
		}
	}

	public boolean addAll(Collection<? extends E> c) {
		c = new ArrayList<E>(c);

		Iterator<? extends E> itr = c.iterator();

		while (itr.hasNext()) {
			E e = itr.next();

			if (contains(e)) {
				itr.remove();
			}
		}

		return super.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		c = new ArrayList<E>(c);

		Iterator<? extends E> itr = c.iterator();

		while (itr.hasNext()) {
			E e = itr.next();

			if (contains(e)) {
				itr.remove();
			}
		}

		return super.addAll(index, c);
	}

	public E set(int index, E e) {
		if (!contains(e)) {
			return super.set(index, e);
		}
		else {
			return e;
		}
	}

}