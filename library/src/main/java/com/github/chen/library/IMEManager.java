package com.github.chen.library;

import android.content.Context;
import android.os.Handler;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class IMEManager {

	public static void openIME(final EditText editText) {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				InputMethodManager inputMethodManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.showSoftInput(editText, 0);
			}
		}, 150);
	}

	public static void closeIME(EditText editText) {
		InputMethodManager inputMethodManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}

	public static void disableCopyAndPaste(EditText editText) {
		editText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				return false;
			}

			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				return false;
			}

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				return false;
			}

			@Override
			public void onDestroyActionMode(ActionMode mode) {

			}
		});
		editText.setLongClickable(false);
	}

	public static void filterUsername(EditText editText) {
		editText.setFilters(new InputFilter[]{new UsernameFilter()});
	}

	public static void filterPassword(EditText editText) {
		editText.setFilters(new InputFilter[]{new PasswordFilter()});
	}

	public static abstract class LoginFilter implements InputFilter {
		private boolean mAppendInvalid;

		LoginFilter(boolean appendInvalid) {
			mAppendInvalid = appendInvalid;
		}

		LoginFilter() {
			mAppendInvalid = false;
		}

		public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
			onStart();

			for (int i = 0; i < dstart; i++) {
				char c = dest.charAt(i);
				if (!isAllowed(c)) onInvalidCharacter(c);
			}

			SpannableStringBuilder modification = null;
			int modoff = 0;

			for (int i = start; i < end; i++) {
				char c = source.charAt(i);
				if (isAllowed(c)) {
					modoff++;
				} else {
					if (mAppendInvalid) {
						modoff++;
					} else {
						if (modification == null) {
							modification = new SpannableStringBuilder(source, start, end);
							modoff = i - start;
						}
						modification.delete(modoff, modoff + 1);
					}
					onInvalidCharacter(c);
				}
			}

			for (int i = dend; i < dest.length(); i++) {
				char c = dest.charAt(i);
				if (!isAllowed(c)) onInvalidCharacter(c);
			}

			onStop();

			return modification;
		}

		public void onStart() {}

		public void onInvalidCharacter(char c) {}

		public void onStop() {}

		public abstract boolean isAllowed(char c);
	}

	public static class UsernameFilter extends LoginFilter {
		private static final String mAllowed = "@_.";

		public UsernameFilter() {
			super(false);
		}

		public UsernameFilter(boolean appendInvalid) {
			super(appendInvalid);
		}

		@Override
		public boolean isAllowed(char c) {
			if ('0' <= c && c <= '9')
				return true;
			if ('a' <= c && c <= 'z')
				return true;
			if ('A' <= c && c <= 'Z')
				return true;
			if (mAllowed.indexOf(c) != -1)
				return true;
			return false;
		}
	}

	public static class PasswordFilter extends LoginFilter {

		public PasswordFilter() {
			super(false);
		}

		public PasswordFilter(boolean appendInvalid) {
			super(appendInvalid);
		}

		@Override
		public boolean isAllowed(char c) {
			if (32 <= c && c <= 127)
				return true;
			if (160 <= c && c <= 255)
				return true;
			return false;
		}
	}

	public static String PATTERN_EMAIL = "^\\w+((-w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";

}
