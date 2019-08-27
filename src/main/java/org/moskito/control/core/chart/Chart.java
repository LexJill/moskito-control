package org.moskito.control.core.chart;

import org.moskito.control.core.accumulator.AccumulatorDataItem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a chart element of an application.
 *
 * @author lrosenberg
 * @since 18.06.13 23:01
 */
public class Chart {

	/**
	 * Name of the chart.
	 */
	private String name;

	/**
	 * Chart lines. Chart line is a combination of component and accumulator.
	 */
	private List<ChartLine> lines = new LinkedList<ChartLine>();

	/**
	 * Limit for number of elements in the chart. -1 Means no limit.
	 */
	private int limit = -1;

	/**
	 * Creates a new chart.
	 * @param aName name of the chart.
	 */
	public Chart(String aName, int aLimit){
		name = aName;
		limit = aLimit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Adds a new line to this chart.
	 * @param component
	 * @param accumulator
	 * @param caption
	 */
	public void addLine(String component, String accumulator, String caption) {
		lines.add(new ChartLine(component, accumulator, caption));
	}

	public void addLine(String component, String accumulator) {
		addLine(component, accumulator, null);
	}
	/**
	 * Returns the list of accumulator names from given component required for this chart.
	 * @param componentName name of the component.
	 * @return list with accumulator names or empty list.
	 */
	public List<String> getNeededAccumulatorsForComponent(String componentName){
		ArrayList<String> ret = new ArrayList<String>();
		for (ChartLine line : lines){
			if (line.getComponent().equals(componentName))
				ret.add(line.getAccumulator());
		}
		return ret;
	}

	@Override public String toString(){
		return " Chart: "+getName();
	}

	public void notifyNewData(String componentName, String accumulatorName, List<AccumulatorDataItem> data) {
		if (limit!=-1 && data.size()>limit){
			data = data.subList(data.size()-limit, data.size());
		}
		for (ChartLine line : lines){
			if (line.getComponent().equals(componentName) && line.getAccumulator().equals(accumulatorName)){
				line.setData(data);
			}
		}
	}

	public List<ChartLine> getLines() {
		return lines;
	}

	/**
	 * Returns a legend string about this chart containing chart name and last values.
	 * @return legend string about this chart
	 */
	public String getLegend() {
		StringBuilder ret = new StringBuilder();

		ret.append(getName()).append(", Last: ");
		for (ChartLine line : lines){
			ret.append(" ").append(line.getChartCaption()).append(" ").append(line.getLegend());
		}


		return ret.toString();
	}
}

