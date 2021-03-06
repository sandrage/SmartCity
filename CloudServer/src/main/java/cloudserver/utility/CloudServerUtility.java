package cloudserver.utility;

import cloudserver.model.SmartCity;

import java.util.Comparator;
import java.util.List;

public class CloudServerUtility {
	public static double getDevStd (List<Float> elements, float mean) {
		double accumulator = 0;
		for (Float elem : elements) {
			accumulator = accumulator + Math.pow((elem - mean), 2);
		}
		return accumulator / elements.size();
	}

	public static Comparator<SmartCity.Node> getNodesDistanceComparator (int xPos, int yPos) {
		return new Comparator<SmartCity.Node>() {
			@Override
			public int compare (SmartCity.Node o1, SmartCity.Node o2) {
				return (Math.abs(o1.getXPos() - xPos) + Math.abs(o1.getYPos() - yPos)) - (Math.abs(o2.getXPos() - xPos) + Math.abs(o2.getYPos() - yPos));
			}
		};
	}

	public static int getNodesDistance (SmartCity.Node node, int xPos, int yPos) {
		return Math.abs(node.getXPos() - xPos) + Math.abs(node.getYPos() - yPos);
	}

	public static double getMean (List<SmartCity.NodeMeasurement> stats) {
		return stats.stream().map(stat -> stat.getValue()).reduce((a, b) -> a + b).orElse(0.) / stats.size();
	}

	public static double getDevstd (List<SmartCity.NodeMeasurement> stats, double mean) {
		return Math.sqrt(stats.stream().map(stat -> Math.pow(stat.getValue() - mean, 2.)).reduce((a, b) -> a + b).orElse(0.) / stats.size());
	}

	public static Comparator<SmartCity.NodeMeasurement> getStatsComparator () {
		return new Comparator<SmartCity.NodeMeasurement>() {
			@Override
			public int compare (SmartCity.NodeMeasurement o1, SmartCity.NodeMeasurement o2) {
				Long timestamp1 = o1.getTimestamp();
				Long timestamp2 = o2.getTimestamp();
				return timestamp2.compareTo(timestamp1);
			}
		};
	}
}
