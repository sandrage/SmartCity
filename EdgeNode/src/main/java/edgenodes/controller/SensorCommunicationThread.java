package edgenodes.controller;

import cloudserver.model.SmartCity;
import edgenodes.model.MeasurementsBuffer;

import java.io.DataInputStream;
import java.net.Socket;

public class SensorCommunicationThread extends Thread {

	private SmartCity.Node node;
	private Socket connection;

	public SensorCommunicationThread (SmartCity.Node node, Socket socket) {
		this.node = node;
		this.connection = socket;
	}

	@Override
	public void run () {
		try {
			DataInputStream inputStream = new DataInputStream(this.connection.getInputStream());
			byte[] measurements = new byte[inputStream.readInt()];
			inputStream.read(measurements);
			SmartCity.NodeMeasurement measurement = SmartCity.NodeMeasurement.parseFrom(measurements);
			MeasurementsBuffer.getInstance().addMeasurement(node, measurement);
			this.connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Errore ricezione dato da sensore");
		}

	}
}
