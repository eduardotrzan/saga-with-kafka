package com.order.payment.generic.exceptions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface IAppError {

    String getDescription();

    default AppError buildError(Object... args) {
        String description = this.getDescription();

        String formattedDescription;
        try {
            formattedDescription = replaceIfPossible(description, args);
        } catch (Exception e) {
            formattedDescription = description;
        }

        return AppError.builder()
                .description(formattedDescription)
                .build();
    }

    private String replaceIfPossible(String description, Object... args) {
        Pattern pattern = Pattern.compile("[^{}]*\\{}");
        Matcher matcher = pattern.matcher(description);

        int count = 0;
        while (matcher.find()) {
            count++;
        }

        if (count == 0) {
            return description;
        }

        Object[] descriptionArgs = new Object[count];
        for (int i = 0; i < count; i++) {
            Object toAdd = null;
            if (args != null && args.length >= i + 1) {
                toAdd = args[i];
            }
            descriptionArgs[i] = toAdd;
        }

        String replacedDescription = description.replaceAll("\\{}", "%s");
        return String.format(replacedDescription, descriptionArgs);
    }
}
