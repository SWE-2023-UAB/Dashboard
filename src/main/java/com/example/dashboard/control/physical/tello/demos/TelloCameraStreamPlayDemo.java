package com.example.dashboard.control.physical.tello.demos;

import java.io.IOException;

import com.example.dashboard.control.physical.tello.TelloDrone;

public class TelloCameraStreamPlayDemo {

	private static void stream() throws IOException, InterruptedException  {
		TelloDrone tello = new TelloDrone();
		tello.activateSDK();
		tello.streamOn();
		tello.hoverInPlace(60);
		tello.streamOff();
		tello.end();
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		stream();
		System.exit(0);
	}
}
