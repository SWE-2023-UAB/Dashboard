package com.example.dashboard.control.physical.tello.demos;

import java.io.IOException;

import com.example.dashboard.control.physical.tello.TelloDrone;

public class TelloFlightDemo {

	private static void flight() throws InterruptedException, IOException {
		TelloDrone tello = new TelloDrone();
		tello.activateSDK();
		tello.streamOn();
		tello.hoverInPlace(10);
		tello.takeoff();
		tello.flyForward(100);
		tello.turnCCW(180);
		tello.flip("b");
		tello.flyForward(100);
		tello.flip("f");
		tello.turnCW(180);
		tello.land();
		tello.streamOff();
		tello.end();
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		flight();
		System.exit(0);
	}

}
