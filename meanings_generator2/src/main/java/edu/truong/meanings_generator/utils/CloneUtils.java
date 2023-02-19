package edu.truong.meanings_generator.utils;

import java.util.ArrayList;
import java.util.List;

public class CloneUtils {
	public List<String> listClone(List<String> original){
		List<String> cloneList = new ArrayList<>(original.size());
		for (String item : original) {
			cloneList.add(new String(item));
		}
		return cloneList;
	}
}
