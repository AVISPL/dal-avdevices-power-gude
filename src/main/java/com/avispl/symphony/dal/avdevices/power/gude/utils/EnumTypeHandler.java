/*
 *  Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.power.gude.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * EnumTypeHandler
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 12/28/2022
 * @since 1.0.0
 */
public class EnumTypeHandler {

	/**
	 * private/ non-parameterized constructor to prevent instance initialization
	 */
	private EnumTypeHandler() {
	}

	/**
	 * Get all name of enum to String Array
	 *
	 * @param enumType the enumType is enum class
	 */
	public static <T extends Enum<T>> List<String> getListOfEnumNames(Class<T> enumType) {
		List<String> names = new ArrayList<>();
		for (T c : enumType.getEnumConstants()) {
			try {
				Method method = c.getClass().getMethod("getUiName");
				String name = (String) method.invoke(c); // getName executed
				names.add(name);
			} catch (Exception e) {
				throw new EnumConstantNotPresentException(enumType, enumType.getSimpleName());
			}
		}
		return names;
	}

	/**
	 * Get all name of enum to String Array
	 *
	 * @param enumType the enumType is enum class
	 * @param removedValue the value will be removed from return lisr
	 */
	public static <T extends Enum<T>> List<String> getListOfEnumNames(Class<T> enumType, Enum<T> removedValue) throws EnumConstantNotPresentException {
		List<String> names = new ArrayList<>();
		for (T c : enumType.getEnumConstants()) {
			if (c.equals(removedValue)) {
				continue;
			}
			try {
				Method method = c.getClass().getMethod("getUiName");
				String name = (String) method.invoke(c); // getName executed
				names.add(name);
			} catch (Exception e) {
				throw new EnumConstantNotPresentException(enumType, enumType.getSimpleName());
			}
		}
		return names;
	}
}
