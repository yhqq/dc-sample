package com.hpe.iot.dc.service;

public class DCCounters {
	static long IOTrequestMessageReceived=0;
	static long IOTResponseMessageReceived=0;
	static long IOTRequestMessageProcessedSuccessfully=0;
	static long IOTRequestMessageProcessedFailed=0;
	static long IOTResponseMessageProcessedSuccessfully=0;
	static long IOTResponseMessageProcessedFailed=0;
	public static long getIOTrequestMessageReceived() {
		return IOTrequestMessageReceived;
	}
	public static void incrementIOTrequestMessageReceived() {
		IOTrequestMessageReceived++; 
	}
	public static long getIOTResponseMessageReceived() {
		return IOTResponseMessageReceived;
	}
	public static void incrementIOTResponseMessageReceived() {
		IOTResponseMessageReceived++ ;
	}
	public static long getIOTRequestMessageProcessedSuccessfully() {
		return IOTRequestMessageProcessedSuccessfully;
	}
	public static void incrementIOTRequestMessageProcessedSuccessfully(
			) {
		IOTRequestMessageProcessedSuccessfully ++;
	}
	public static long getIOTRequestMessageProcessedFailed() {
		return IOTRequestMessageProcessedFailed;
	}
	public static void incrementIOTRequestMessageProcessedFailed(
			) {
		IOTRequestMessageProcessedFailed ++;
	}
	public static long getIOTResponseMessageProcessedSuccessfully() {
		return IOTResponseMessageProcessedSuccessfully;
	}
	public static void incrementIOTResponseMessageProcessedSuccessfully(
			) {
		IOTResponseMessageProcessedSuccessfully++ ;
	}
	public static long getIOTResponseMessageProcessedFailed() {
		return IOTResponseMessageProcessedFailed;
	}
	public static void incrementIOTResponseMessageProcessedFailed(
			) {
		IOTResponseMessageProcessedFailed ++;
	}

}
