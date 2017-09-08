package com.github.chen.library.iso8583;

/*
 * 目前只支持64域，不支持128域
 */

public class Easy8583App {


	public static void main(String[] args){

//		MainWindow mw = new MainWindow("Easy8583");
//		System.setOut(mw.getPs());

		String packetString ="600500010060020000000002203020048008C0801B20900000000000000115075200120031353036343739303439343730383030333332383330393034303835333131303030333135360011640000020000001600000000000009010003303030AE46C73E2B8471E2";



		Packet8583 packet8583 = new Packet8583(packetString);
		packet8583.showField();
	}
}