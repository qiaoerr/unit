package com.tixa.industry.util;

import java.io.IOException;

public interface RequestListener {
	public void onComplete(String response);

	public void onIOException(IOException e);

	public void onError(TixaException e);
}
