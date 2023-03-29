package com.warders.api.common.utils;

import com.warders.api.common.base.LegacyCommonType;
import java.util.EnumSet;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LegacyEnumValueConvertUtils {
    public static <T extends Enum<T> & LegacyCommonType> T ofLegacyCode(Class<T> enumClass, String legacyCode) {
        if (!StringUtils.hasText(legacyCode)) {
            return null;
        }
        return EnumSet.allOf(enumClass).stream()
            .filter(v -> v.getLegacyType().equals(legacyCode))
            .findAny()
            .orElseThrow(() -> new EnumConstantNotPresentException(enumClass, legacyCode));
    }

    public static <T extends Enum<T> & LegacyCommonType> String toLegacyCode(T enumValue) {
        if (enumValue == null) {
            return "";
        }
        return enumValue.getLegacyType();
    }

}
