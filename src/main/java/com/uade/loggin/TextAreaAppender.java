package com.uade.loggin;

import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;

import com.uade.views.MainView;

public class TextAreaAppender extends WriterAppender {

	private MainView mainView;
	
	@Override
    public void append(LoggingEvent loggingEvent ){

		if(this.mainView != null) {
			this.mainView.writeMessage(this.layout.format(loggingEvent));
		}
    }

	public void setMainView(MainView mainView) {
		
		this.mainView = mainView;
	}
}